package ftjw.web.mobile.analyze.core;

import cn.hutool.http.HttpException;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import ftjw.web.mobile.analyze.dao.DataRepository;
import ftjw.web.mobile.analyze.dao.SaleRepository;
import ftjw.web.mobile.analyze.dao.SiteRepository;
import ftjw.web.mobile.analyze.entity.AnalyzeData;
import ftjw.web.mobile.analyze.entity.Count;
import ftjw.web.mobile.analyze.entity.SaleMan;
import ftjw.web.mobile.analyze.entity.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 殷晓龙
 * 2020/3/27 17:06
 */
@Component
@EnableScheduling
public class EmailTask {
    @Autowired
    EmailSender emailSender;

    @Autowired
    DataRepository dataRepository;
    @Resource
    SaleRepository saleRepository;
    @Resource
    SiteRepository siteRepository;
    SeleniumAnalyze seleniumAnalyze;
    static int index=0;


    @Scheduled(cron = "0  30  18  ?  *  MON-FRI")
    public void sendEmail(){
        List<Map<String, Object>> maps = dataRepository.countAnalyzeDataByStatus();
        StringBuilder sb=new StringBuilder("本周移动化网站数据分析\r\n");
        maps.forEach(m->{
            if((int)(m.get("status"))==1){
                sb.append("正常网站数:");

            }else if((int)m.get("status")==0){
                sb.append("已过期站点数:");
            }else if((int)m.get("status")==2){
                sb.append("已失效数量:");
            }else if((int)m.get("status")==3){
                sb.append("访问异常数量:");
            }

            sb.append(m.get("total")+"\r\n");
        });
        sb.append("前往  https://ydhtg.wqying.cn/web/index   查看");

        List<SaleMan> saleManList = saleRepository.findByEmailNotNull();
        List<String> emalis = saleManList.stream().map(SaleMan::getEmail).collect(Collectors.toList());

        emailSender.sendEmail(emalis,"移动化网站数据分析报告",sb.toString());
    }

    /**
     * 定时任务每次执行10条
     */
    @Scheduled(fixedRate = 1000*60*60)
    public void autoAnalyze(){
        List<Site> list = siteRepository.findSites();
        int x=0;
        for (Site site:list){
            x++;
            if(x<index){
                continue;
            }
            if(index>=list.size()){
                break;
            }
            if(x==index+10){
                break;
            }
            Long current=System.currentTimeMillis();
            JSONObject obj = JSONUtil.parseObj(site.getOption());
            String protocol=obj.getStr("ORIGIN_HOST_PROTOCOL");
            if(protocol==null){
                protocol="http";
            }
            String web=protocol.replace(":","")+"://"+obj.getStr("ORIGIN_HOST");
            obj.getInt("expire_time");
            String res;
            if(site.getUid()==14){
                res =  IBossUtill.query_a2sc(site.getId());
            }else {
                res =  IBossUtill.query_awsc(site.getId());
            }
            if("err".equals(res)){
                continue;
            }
            JSONObject jsonObject = JSONUtil.parseObj(res);
            JSONArray array = jsonObject.getJSONArray("obj");
            Count c = array.get(0, Count.class);
            AnalyzeData ad=new AnalyzeData();
            ad.setName(site.getName());
            ad.setId(site.getId());
            ad.setPv(c.getTotal());
            if(current>Long.valueOf(obj.getInt("expire_time",0)+"000")){
                ad.setStatus(0);

            }else{
                this.seleniumAnalyze =new SeleniumAnalyze();
                try {
                    if(seleniumAnalyze.webUrlCheck(web)){
                        ad.setStatus(1);
                    }else {
                        ad.setStatus(2);
                    }
                }catch (Exception e) {
                    if (e instanceof HttpException){
                        ad.setStatus(3);
                    }else {
                        ad.setStatus(3);
                    }

                }
                seleniumAnalyze.quite();
            }
            ad.setWeb(web);
            ad.setExpireTime(obj.getInt("expire_time",0));
            ad.setFirstPubTime(obj.getInt("first_pub_time",0));
            dataRepository.save(ad);

        }
        index+=10;
        if(index>=list.size()){
            index=0;
        }

    }
}
