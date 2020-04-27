package ftjw.web.mobile.analyze.core;

import ftjw.web.mobile.analyze.dao.DataRepository;
import ftjw.web.mobile.analyze.dao.SaleRepository;
import ftjw.web.mobile.analyze.entity.SaleMan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 殷晓龙
 * 2020/3/27 17:06
 */
//@Component
//@EnableScheduling
//@Slf4j
public class EmailTask {
    @Autowired
    EmailSender emailSender;

    @Autowired
    DataRepository dataRepository;
    @Resource
    SaleRepository saleRepository;


    /**
     * 定时向售员发送统计结果邮件
     * @throws MessagingException
     */
    //@Scheduled(cron = "0  22  18  ?  *  MON-FRI")
    public void sendEmail() throws MessagingException {
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

        for (String email:emalis){
            emailSender.sendEmail(email,"移动化网站数据分析报告",sb.toString());
        }

    }


}
