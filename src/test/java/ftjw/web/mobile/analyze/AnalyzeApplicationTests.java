package ftjw.web.mobile.analyze;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import ftjw.web.mobile.analyze.core.IBossUtill;
import ftjw.web.mobile.analyze.dao.DataRepository;
import ftjw.web.mobile.analyze.dao.SiteRepository;
import ftjw.web.mobile.analyze.entity.AnalyzeData;
import ftjw.web.mobile.analyze.entity.Count;
import ftjw.web.mobile.analyze.entity.Site;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class AnalyzeApplicationTests {

    @Resource
    SiteRepository siteRepository;
    @Resource
    DataRepository dataRepository;

    @Test
    public void findSiteList(){
       List<Site> list = siteRepository.findSites();
       for (Site site:list){
           Long current=System.currentTimeMillis();
           JSONObject obj = JSONUtil.parseObj(site.getOption());

           String web=obj.getStr("ORIGIN_HOST_PROTOCOL")+"://"+obj.getStr("ORIGIN_HOST");
           obj.getInt("expire_time");

           String res;
           if(site.getUid()==14){
               res =  IBossUtill.query_a2sc(site.getId());
           }else {
               res =  IBossUtill.query_awsc(site.getId());
           }
           JSONObject jsonObject = JSONUtil.parseObj(res);
           JSONArray array = jsonObject.getJSONArray("obj");
           Count c = array.get(0, Count.class);
           System.out.println(res);
           AnalyzeData ad=new AnalyzeData();
           ad.setName(site.getName());
           ad.setId(site.getId());
           ad.setPv(c.getTotal());
           if(current>Long.valueOf(obj.getInt("expire_time",0)+"000")){
               ad.setStatus("过期");
           }else{
               ad.setStatus("正常");
           }
           ad.setWeb(web);
           ad.setExpireTime(obj.getInt("expire_time",0));
           ad.setFirstPubTime(obj.getInt("first_pub_time",0));
           dataRepository.save(ad);

       }



    }

}
