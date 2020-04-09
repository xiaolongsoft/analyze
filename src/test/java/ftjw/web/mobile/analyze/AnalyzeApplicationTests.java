package ftjw.web.mobile.analyze;

import cn.hutool.http.HttpException;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Lists;
import ftjw.web.mobile.analyze.core.EmailSender;
import ftjw.web.mobile.analyze.core.IBossUtill;
import ftjw.web.mobile.analyze.core.SeleniumAnalyze;
import ftjw.web.mobile.analyze.dao.AgentInfoRepository;
import ftjw.web.mobile.analyze.dao.DataRepository;
import ftjw.web.mobile.analyze.dao.SiteRepository;
import ftjw.web.mobile.analyze.entity.AgentInfo;
import ftjw.web.mobile.analyze.entity.AnalyzeData;
import ftjw.web.mobile.analyze.entity.Count;
import ftjw.web.mobile.analyze.entity.Site;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class AnalyzeApplicationTests {

    @Resource
    SiteRepository siteRepository;
    @Resource
    DataRepository dataRepository;
    SeleniumAnalyze seleniumAnalyze;



    @BeforeEach
    public void init(){
        System.out.println("初始化");
    }

//    @Test
//    public void findSiteList(){
//       List<Site> list = siteRepository.findSites();
//
//       for (Site site:list){
//
//           System.out.println(site.getId()+site.getName());
//           Long current=System.currentTimeMillis();
//           JSONObject obj = JSONUtil.parseObj(site.getOption());
//           String protocol=obj.getStr("ORIGIN_HOST_PROTOCOL");
//           if(protocol==null){
//               protocol="http";
//           }
//           String web=protocol.replace(":","")+"://"+obj.getStr("ORIGIN_HOST");
//           obj.getInt("expire_time");
//           String res;
//           if(site.getUid()==14){
//               res =  IBossUtill.query_a2sc(site.getId());
//           }else {
//               res =  IBossUtill.query_awsc(site.getId());
//           }
//           if("err".equals(res)){
//               continue;
//           }
//           JSONObject jsonObject = JSONUtil.parseObj(res);
//           JSONArray array = jsonObject.getJSONArray("obj");
//           Count c = array.get(0, Count.class);
//           AnalyzeData ad=new AnalyzeData();
//           ad.setName(site.getName());
//           ad.setId(site.getId());
//           ad.setPv(c.getTotal());
//           if(current>Long.valueOf(obj.getInt("expire_time",0)+"000")){
//               ad.setStatus(0);
//
//           }else{
//               this.seleniumAnalyze =new SeleniumAnalyze();
//               try {
//                    if(seleniumAnalyze.webUrlCheck(web)){
//                        ad.setStatus(1);
//                    }else {
//                        ad.setStatus(2);
//                    }
//               }catch (Exception e) {
//                   if (e instanceof HttpException){
//                       ad.setStatus(3);
//                   }else {
//                       ad.setStatus(3);
//                   }
//
//               }
//               seleniumAnalyze.quite();
//           }
//           ad.setWeb(web);
//           ad.setExpireTime(obj.getInt("expire_time",0));
//           ad.setFirstPubTime(obj.getInt("first_pub_time",0));
//           dataRepository.save(ad);
//
//       }
//
//    }

    @Resource
    EmailSender sender;
    @Resource
    AgentInfoRepository agentInfoRepository;

    @Test
    public void sendEmail() throws MessagingException {

        List<String> strings=Lists.newArrayList();
        PageRequest pa=PageRequest.of(6,50);
        Page<AgentInfo> all = agentInfoRepository.findByEmailNotNull(pa);
        all.forEach(agentInfo -> {
            strings.add(agentInfo.getEmail());
        });
//        String content="<div><table style=\"width: 99.8%;\"><tbody><tr><td id=\"\" style=\"background:url(https://rescdn.qqmail.com/zh_CN/htmledition/images/xinzhi/bg/a_08.jpg) no-repeat #f3f3eb; min-height:550px; padding: 100px 55px 200px 120px;\"><div><img src=\"/cgi-bin/viewfile?f=A204DD6DBCAC0080B9A6B9F8A3C4D5EE4DDDBEAE66F583FC48D18E1B430CD9F3C565992BC1A25755B27B05175B3B6FC98F0BC5BBE51CE9DC27F81B398069A9258690E7A259FE96289284B55BFD1D6428B5F58B5C17C8DB238B8CC559FC8A7314&amp;mailid=ZL1803-K0LeqVZUJDy7OTZoOSFxMa4&amp;sid=O1K23COq__NlCQZY&amp;net=889192575\" modifysize=\"25%\" diffpixels=\"-1px\" style=\"width: 150px; height: 52px;\"></div><div><br></div><div style=\" text-align: center ; ; ; \"><br></div><div><font editorwarp__=\"1\" style=\"display: inline; background-color: rgba(0, 0, 0, 0); font-weight: 700;\" size=\"5\">想要盘活经营多年的客户，提升客户价值吗？</font></div><div><br></div><div><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"486\" style=\"border-collapse: collapse; width: 486pt;\"><colgroup><col width=\"243\" span=\"2\" style=\"width: 243pt;\"></colgroup><tbody><tr height=\"40\" style=\"height: 40pt;\"><td colspan=\"2\" height=\"40\" class=\"xl68\" width=\"486\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 18pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 40pt; width: 486pt;\"><font color=\"#ff6600\">移动化适配</font><font class=\"font6\" style=\"font-size: 18pt; font-weight: 400;\">——将网站和应用系统变为移动版访问形态</font></td></tr><tr height=\"28\" style=\"height: 28pt;\"><td height=\"28\" class=\"xl67\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 28pt;\">网站移动化</td><td class=\"xl67\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center;\">应用移动化</td></tr><tr height=\"35\" style=\"height: 35pt;\"><td height=\"35\" class=\"xl65\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 35pt;\"><font class=\"font7\" style=\" font-size: 12pt ; ; ; \">\uFEFF</font><font class=\"font0\" style=\"font-size: 12pt;\">★ 企业门户网站</font></td><td class=\"xl66\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\"><font class=\"font7\" style=\" font-size: 12pt ; ; ; \">\uFEFF&nbsp; &nbsp; &nbsp; </font><font class=\"font0\" style=\"font-size: 12pt;\">★ 政企办公应用</font></td></tr><tr height=\"35\" style=\"height: 35pt;\"><td height=\"35\" class=\"xl65\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 35pt;\"><font class=\"font7\" style=\" font-size: 12pt ; ; ; \">\uFEFF</font><font class=\"font0\" style=\"font-size: 12pt;\">★ 行业资讯网站</font></td><td class=\"xl66\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\"><font class=\"font7\" style=\" font-size: 12pt ; ; ; \">\uFEFF&nbsp; &nbsp; &nbsp; </font><font class=\"font0\" style=\"font-size: 12pt;\">★ ERP、CRM等应用</font></td></tr><tr height=\"35\" style=\"height: 35pt;\"><td height=\"35\" class=\"xl65\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 35pt;\"><font class=\"font7\" style=\" font-size: 12pt ; ; ; \">\uFEFF</font><font class=\"font0\" style=\"font-size: 12pt;\">★ 政府机构网站</font></td><td class=\"xl66\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\"><font class=\"font7\" style=\" font-size: 12pt ; ; ; \">\uFEFF&nbsp; &nbsp; &nbsp; </font><font class=\"font0\" style=\"font-size: 12pt;\">★ 其他业务系统应用</font></td></tr></tbody></table></div><div><span style=\"font-weight: bold; font-size: large;\"><br></span></div><div><span style=\"font-weight: bold; font-size: large;\">我们的优势</span><span style=\"font-size: large;\">：</span></div><div></div><div><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"468\" style=\"border-collapse: collapse; width: 468pt;\"><colgroup><col width=\"234\" span=\"2\" style=\"width: 234pt;\"></colgroup><tbody><tr height=\"32\" style=\"height: 32pt;\"><td height=\"32\" class=\"xl63\" width=\"234\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; height: 32pt; width: 234pt;\"><font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">1、</font><font class=\"font7\" style=\"color: red; font-size: 14pt;\">先进技术</font><font class=\"font6\" style=\"font-size: 14pt;\">，竟品少</font></td><td class=\"xl63\" width=\"234\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; width: 234pt;\"><font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">4、</font><font class=\"font7\" style=\"color: red; font-size: 14pt;\">上市平台</font><font class=\"font6\" style=\"font-size: 14pt;\">优势</font></td></tr><tr height=\"32\" style=\"height: 32pt;\"><td height=\"32\" class=\"xl63\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; height: 32pt;\"><font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">2、</font><font class=\"font7\" style=\"color: red; font-size: 14pt;\">千亿</font><font class=\"font6\" style=\"font-size: 14pt;\">规模</font><font class=\"font7\" style=\"color: red; font-size: 14pt;\">市场</font></td><td class=\"xl63\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap;\">5、可观<font class=\"font7\" style=\"color: red; font-size: 14pt;\">利润</font><font class=\"font6\" style=\"font-size: 14pt;\">空间</font></td></tr><tr height=\"32\" style=\"height: 32pt;\"><td height=\"32\" class=\"xl63\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; height: 32pt;\">3、市场<font class=\"font7\" style=\"color: red; font-size: 14pt;\">趋势</font><font class=\"font6\" style=\"font-size: 14pt;\">，</font><font class=\"font7\" style=\"color: red; font-size: 14pt;\">政策</font><font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">支持</font></td><td class=\"xl63\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap;\">6、深度<font class=\"font7\" style=\"color: red; font-size: 14pt;\">营销支持</font></td></tr></tbody></table></div><div><span style=\"font-size: large;\"><span style=\"font-weight: bold;\"><br></span></span></div><div><span style=\"font-size: large;\"><span style=\"font-weight: bold;\">扶持政策</span>：</span></div><div><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"450\" style=\"border-collapse: collapse; width: 450pt;\"><colgroup><col width=\"150\" span=\"3\" style=\"width: 150pt;\"></colgroup><tbody><tr height=\"32\" style=\"height: 32pt;\"><td height=\"32\" class=\"xl64\" width=\"150\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 32pt; width: 150pt;\"><font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">1、</font><font class=\"font7\" style=\"color: red; font-size: 14pt;\">区域</font><font class=\"font6\" style=\"font-size: 14pt;\">保护</font></td><td class=\"xl64\" width=\"150\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; width: 150pt;\"><font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">3、</font><font class=\"font7\" style=\"color: red; font-size: 14pt;\">推广</font><font class=\"font6\" style=\"font-size: 14pt;\">支持</font></td><td class=\"xl64\" width=\"150\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; width: 150pt;\"><font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">5、</font><font class=\"font7\" style=\"color: red; font-size: 14pt;\">销售</font><font class=\"font6\" style=\"font-size: 14pt;\">支持</font></td></tr><tr height=\"32\" style=\"height: 32pt;\"><td height=\"32\" class=\"xl64\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 32pt;\"><font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">2、</font><font class=\"font7\" style=\"color: red; font-size: 14pt;\">技术</font><font class=\"font6\" style=\"font-size: 14pt;\">支持</font></td><td class=\"xl64\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center;\"><font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">4、</font><font class=\"font7\" style=\"color: red; font-size: 14pt;\">培训</font><font class=\"font6\" style=\"font-size: 14pt;\">支持</font></td><td class=\"xl64\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center;\"><font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">6、</font><font class=\"font7\" style=\"color: red; font-size: 14pt;\">物料</font><font class=\"font6\" style=\"font-size: 14pt;\">支持</font></td></tr></tbody></table></div><div><span style=\"font-size: large;\"><br></span></div><div><span style=\"font-size: xx-large;\">不想看文字？我们还有图解哦～</span></div><div><img src=\"/cgi-bin/viewfile?f=A204DD6DBCAC0080B9A6B9F8A3C4D5EE4DDDBEAE66F583FC48D18E1B430CD9F3C565992BC1A25755B27B05175B3B6FC98F0BC5BBE51CE9DCB2F0B25E87548E565A3C18130E253C8C7E594F7AB2B74D3059E73184BFEE9C5D4729F73C23C05E64&amp;mailid=ZL1803-K0LeqVZUJDy7OTZoOSFxMa4&amp;sid=O1K23COq__NlCQZY&amp;net=889192575\" modifysize=\"100%\" diffpixels=\"25px\" style=\"width: 750px; height: 1223px;\"></div><div><br></div><div><br></div><div><span style=\"font-size: x-large;\">部分案例详单，可点击查看哦～</span></div><div><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"552\" style=\"border-collapse: collapse; width: 552pt;\"><colgroup><col width=\"138\" span=\"4\" style=\"width: 138pt;\"></colgroup><tbody><tr height=\"40\" style=\"height: 40pt;\"><td colspan=\"2\" height=\"40\" class=\"xl65\" width=\"276\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: red; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 40pt; width: 276pt;\">政府案例</td><td colspan=\"2\" class=\"xl65\" width=\"276\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: red; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; width: 276pt;\">企业案例</td></tr><tr height=\"25\" style=\"height: 25pt;\"><td height=\"25\" class=\"xl66\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; height: 25pt;\">四川省人民政府</td><td class=\"xl67\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: rgb(5, 99, 193); font-size: 12pt; text-decoration-line: underline; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\"><a href=\"http://www.sc.gov.cn/\">www.sc.gov.cn</a></td><td class=\"xl66\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap;\">京芥子园画院</td><td class=\"xl67\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: rgb(5, 99, 193); font-size: 12pt; text-decoration-line: underline; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\"><a href=\"http://www.jzyhy.com/\">www.jzyhy.com</a></td></tr><tr height=\"25\" style=\"height: 25pt;\"><td height=\"25\" class=\"xl66\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; height: 25pt;\">贵州省人民政府</td><td class=\"xl67\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: rgb(5, 99, 193); font-size: 12pt; text-decoration-line: underline; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\"><a href=\"http://www.guizhou.gov.cn/\">www.guizhou.gov.cn</a></td><td class=\"xl66\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap;\">蓝宝天禾科技</td><td class=\"xl67\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: rgb(5, 99, 193); font-size: 12pt; text-decoration-line: underline; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\"><a href=\"http://www.lanbe.com/\">www.lanbe.com</a></td></tr><tr height=\"25\" style=\"height: 25pt;\"><td height=\"25\" class=\"xl66\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; height: 25pt;\">济宁市卫生健康委员会</td><td class=\"xl67\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: rgb(5, 99, 193); font-size: 12pt; text-decoration-line: underline; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\"><a href=\"http://wjw.jining.gov.cn/\">wjw.jining.gov.cn</a></td><td class=\"xl66\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap;\">浙江邦程科技有限公司</td><td class=\"xl67\" style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: rgb(5, 99, 193); font-size: 12pt; text-decoration-line: underline; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\"><a href=\"http://www.zjbckj.com/\">www.zjbckj.com</a></td></tr></tbody></table><img src=\"/cgi-bin/viewfile?f=A204DD6DBCAC0080B9A6B9F8A3C4D5EE4DDDBEAE66F583FC48D18E1B430CD9F3C565992BC1A25755B27B05175B3B6FC98F0BC5BBE51CE9DC01AD4C3CD5E9DE99FC01A4A73CF9685560A20A904B8A8FB468813B4D27913AD740341B3FAEBF66F9&amp;mailid=ZL1803-K0LeqVZUJDy7OTZoOSFxMa4&amp;sid=O1K23COq__NlCQZY&amp;net=889192575\" modifysize=\"100%\" diffpixels=\"25px\" style=\"width: 145px; height: 146px;\"><font size=\"5\">更多案例请扫码查看……</font></div><div><br></div><div><br></div><div><span style=\"font-size: x-large; color: rgb(128, 0, 0);\">了解更多信息，请联系招商经理，免费一对一解答～</span></div><div><span style=\"font-size: x-large; color: rgb(128, 0, 0);\"><br></span></div><div><span style=\"font-size: large;\">招商经理：【ZSJL】</span></div><div><span style=\"font-size: large;\">招商电话：【ZSDH】</span></div><div><span style=\"font-size: large;\">全国统一热线：4000020000 </span></div><div style=\"font-size: 14px;\"><br></div><div style=\"font-size: 14px;\"><br><img src=\"/cgi-bin/viewfile?f=A204DD6DBCAC0080B9A6B9F8A3C4D5EE4DDDBEAE66F583FC48D18E1B430CD9F3C565992BC1A25755B27B05175B3B6FC98F0BC5BBE51CE9DC5848490EF9429649704D9B6DE9F56D17C05E9FA948D22270BE51210029C74E39AD1284F503D4D8C2&amp;mailid=ZL1803-K0LeqVZUJDy7OTZoOSFxMa4&amp;sid=O1K23COq__NlCQZY&amp;net=889192575\"><br></div><div style=\"font-size: 14px;\"><br></div><div style=\"font-size: 14px;\"><div style=\"font-size: medium;\"><span style=\"font-size: large;\">北京飞天经纬科技股份有限公司</span></div><div style=\"font-size: medium;\"><span style=\"font-size: large;\">公司地址：<span class=\"readmail_locationTip\" over=\"0\" style=\"position: relative; border-bottom: 1px dashed rgb(171, 171, 171); z-index: auto;\"><span class=\"js_location_string\" style=\"border-bottom: 1px dashed rgb(171, 171, 171); z-index: 1; position: static;\">北京市朝阳区北苑东路19号院2号楼27层</span></span>2709</span></div></div></td></tr></tbody></table></div>";


        String content="<div style=\"background:#F3F3EB;padding: 100px; width: 750px;margin: 0 auto;\">\n" +
                "    <div style=\" text-align: center ; ; \"><br><br></div>\n" +
                "    <div>\n" +
                "        <font editorwarp__=\"1\" size=\"5\" style=\"display: inline; background-color: rgba(0, 0, 0, 0); font-weight: 700;\">\n" +
                "            想要盘活经营多年的客户，提升客户价值吗？</font>\n" +
                "    </div>\n" +
                "    <div><br></div>\n" +
                "    <div>\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse; width: 486pt;\" width=\"486\">\n" +
                "            <colgroup>\n" +
                "                <col span=\"2\" style=\"width: 243pt;\" width=\"243\">\n" +
                "            </colgroup>\n" +
                "            <tbody>\n" +
                "                <tr height=\"40\" style=\"height: 40pt;\">\n" +
                "                    <td class=\"xl68\" colspan=\"2\" height=\"40\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 18pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 40pt; width: 486pt;\"\n" +
                "                        width=\"486\">\n" +
                "                        <font color=\"#ff6600\">移动化适配</font>\n" +
                "                        <font class=\"font6\" style=\"font-size: 18pt; font-weight: 400;\">——将网站和应用系统变为移动版访问形态</font>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr height=\"28\" style=\"height: 28pt;\">\n" +
                "                    <td class=\"xl67\" height=\"28\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 28pt;\">\n" +
                "                        网站移动化</td>\n" +
                "                    <td class=\"xl67\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center;\">\n" +
                "                        应用移动化</td>\n" +
                "                </tr>\n" +
                "                <tr height=\"35\" style=\"height: 35pt;\">\n" +
                "                    <td class=\"xl65\" height=\"35\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 35pt;\">\n" +
                "                        <font class=\"font7\" style=\" font-size: 12pt ; ; ; \">&#65279;</font>\n" +
                "                        <font class=\"font0\" style=\"font-size: 12pt;\">★ 企业门户网站</font>\n" +
                "                    </td>\n" +
                "                    <td class=\"xl66\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\">\n" +
                "                        <font class=\"font7\" style=\" font-size: 12pt ; ; ; \">&#65279;&nbsp; &nbsp; &nbsp; </font>\n" +
                "                        <font class=\"font0\" style=\"font-size: 12pt;\">★ 政企办公应用</font>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr height=\"35\" style=\"height: 35pt;\">\n" +
                "                    <td class=\"xl65\" height=\"35\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 35pt;\">\n" +
                "                        <font class=\"font7\" style=\" font-size: 12pt ; ; ; \">&#65279;</font>\n" +
                "                        <font class=\"font0\" style=\"font-size: 12pt;\">★ 行业资讯网站</font>\n" +
                "                    </td>\n" +
                "                    <td class=\"xl66\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\">\n" +
                "                        <font class=\"font7\" style=\" font-size: 12pt ; ; ; \">&#65279;&nbsp; &nbsp; &nbsp; </font>\n" +
                "                        <font class=\"font0\" style=\"font-size: 12pt;\">★ ERP、CRM等应用</font>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr height=\"35\" style=\"height: 35pt;\">\n" +
                "                    <td class=\"xl65\" height=\"35\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 35pt;\">\n" +
                "                        <font class=\"font7\" style=\" font-size: 12pt ; ; ; \">&#65279;</font>\n" +
                "                        <font class=\"font0\" style=\"font-size: 12pt;\">★ 政府机构网站</font>\n" +
                "                    </td>\n" +
                "                    <td class=\"xl66\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\">\n" +
                "                        <font class=\"font7\" style=\" font-size: 12pt ; ; ; \">&#65279;&nbsp; &nbsp; &nbsp; </font>\n" +
                "                        <font class=\"font0\" style=\"font-size: 12pt;\">★ 其他业务系统应用</font>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "    <div><br></div>\n" +
                "    <div>我们的优势：</div>\n" +
                "    <div></div>\n" +
                "    <div>\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse; width: 468pt;\" width=\"468\">\n" +
                "            <colgroup>\n" +
                "                <col span=\"2\" style=\"width: 234pt;\" width=\"234\">\n" +
                "            </colgroup>\n" +
                "            <tbody>\n" +
                "                <tr height=\"32\" style=\"height: 32pt;\">\n" +
                "                    <td class=\"xl63\" height=\"32\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; height: 32pt; width: 234pt;\"\n" +
                "                        width=\"234\">\n" +
                "                        <font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">1、</font>\n" +
                "                        <font class=\"font7\" style=\"color: red; font-size: 14pt;\">先进技术</font>\n" +
                "                        <font class=\"font6\" style=\"font-size: 14pt;\">，竟品少</font>\n" +
                "                    </td>\n" +
                "                    <td class=\"xl63\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; width: 234pt;\"\n" +
                "                        width=\"234\">\n" +
                "                        <font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">4、</font>\n" +
                "                        <font class=\"font7\" style=\"color: red; font-size: 14pt;\">上市平台</font>\n" +
                "                        <font class=\"font6\" style=\"font-size: 14pt;\">优势</font>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr height=\"32\" style=\"height: 32pt;\">\n" +
                "                    <td class=\"xl63\" height=\"32\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; height: 32pt;\">\n" +
                "                        <font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">2、</font>\n" +
                "                        <font class=\"font7\" style=\"color: red; font-size: 14pt;\">千亿</font>\n" +
                "                        <font class=\"font6\" style=\"font-size: 14pt;\">规模</font>\n" +
                "                        <font class=\"font7\" style=\"color: red; font-size: 14pt;\">市场</font>\n" +
                "                    </td>\n" +
                "                    <td class=\"xl63\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap;\">\n" +
                "                        5、可观<font class=\"font7\" style=\"color: red; font-size: 14pt;\">利润</font>\n" +
                "                        <font class=\"font6\" style=\"font-size: 14pt;\">空间</font>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr height=\"32\" style=\"height: 32pt;\">\n" +
                "                    <td class=\"xl63\" height=\"32\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; height: 32pt;\">\n" +
                "                        3、市场<font class=\"font7\" style=\"color: red; font-size: 14pt;\">趋势</font>\n" +
                "                        <font class=\"font6\" style=\"font-size: 14pt;\">，</font>\n" +
                "                        <font class=\"font7\" style=\"color: red; font-size: 14pt;\">政策</font>\n" +
                "                        <font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">支持</font>\n" +
                "                    </td>\n" +
                "                    <td class=\"xl63\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap;\">\n" +
                "                        6、深度<font class=\"font7\" style=\"color: red; font-size: 14pt;\">营销支持</font>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "    <div><br></div>\n" +
                "    <div>扶持政策：</div>\n" +
                "    <div>\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse; width: 450pt;\" width=\"450\">\n" +
                "            <colgroup>\n" +
                "                <col span=\"3\" style=\"width: 150pt;\" width=\"150\">\n" +
                "            </colgroup>\n" +
                "            <tbody>\n" +
                "                <tr height=\"32\" style=\"height: 32pt;\">\n" +
                "                    <td class=\"xl64\" height=\"32\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 32pt; width: 150pt;\"\n" +
                "                        width=\"150\">\n" +
                "                        <font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">1、</font>\n" +
                "                        <font class=\"font7\" style=\"color: red; font-size: 14pt;\">区域</font>\n" +
                "                        <font class=\"font6\" style=\"font-size: 14pt;\">保护</font>\n" +
                "                    </td>\n" +
                "                    <td class=\"xl64\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; width: 150pt;\"\n" +
                "                        width=\"150\">\n" +
                "                        <font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">3、</font>\n" +
                "                        <font class=\"font7\" style=\"color: red; font-size: 14pt;\">推广</font>\n" +
                "                        <font class=\"font6\" style=\"font-size: 14pt;\">支持</font>\n" +
                "                    </td>\n" +
                "                    <td class=\"xl64\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; width: 150pt;\"\n" +
                "                        width=\"150\">\n" +
                "                        <font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">5、</font>\n" +
                "                        <font class=\"font7\" style=\"color: red; font-size: 14pt;\">销售</font>\n" +
                "                        <font class=\"font6\" style=\"font-size: 14pt;\">支持</font>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr height=\"32\" style=\"height: 32pt;\">\n" +
                "                    <td class=\"xl64\" height=\"32\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 32pt;\">\n" +
                "                        <font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">2、</font>\n" +
                "                        <font class=\"font7\" style=\"color: red; font-size: 14pt;\">技术</font>\n" +
                "                        <font class=\"font6\" style=\"font-size: 14pt;\">支持</font>\n" +
                "                    </td>\n" +
                "                    <td class=\"xl64\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center;\">\n" +
                "                        <font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">4、</font>\n" +
                "                        <font class=\"font7\" style=\"color: red; font-size: 14pt;\">培训</font>\n" +
                "                        <font class=\"font6\" style=\"font-size: 14pt;\">支持</font>\n" +
                "                    </td>\n" +
                "                    <td class=\"xl64\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center;\">\n" +
                "                        <font class=\"font8\" style=\"color: windowtext; font-size: 14pt;\">6、</font>\n" +
                "                        <font class=\"font7\" style=\"color: red; font-size: 14pt;\">物料</font>\n" +
                "                        <font class=\"font6\" style=\"font-size: 14pt;\">支持</font>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "    <div><br></div>\n" +
                "    <div><br></div>\n" +
                "    <div>\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse; width: 552pt;\" width=\"552\">\n" +
                "            <colgroup>\n" +
                "                <col span=\"4\" style=\"width: 138pt;\" width=\"138\">\n" +
                "            </colgroup>\n" +
                "            <tbody>\n" +
                "                <tr height=\"40\" style=\"height: 40pt;\">\n" +
                "                    <td class=\"xl65\" colspan=\"2\" height=\"40\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: red; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; height: 40pt; width: 276pt;\"\n" +
                "                        width=\"276\">政府案例</td>\n" +
                "                    <td class=\"xl65\" colspan=\"2\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: red; font-size: 14pt; font-weight: 700; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; text-align: center; width: 276pt;\"\n" +
                "                        width=\"276\">企业案例</td>\n" +
                "                </tr>\n" +
                "                <tr height=\"25\" style=\"height: 25pt;\">\n" +
                "                    <td class=\"xl66\" height=\"25\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; height: 25pt;\">\n" +
                "                        四川省人民政府</td>\n" +
                "                    <td class=\"xl67\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: rgb(5, 99, 193); font-size: 12pt; text-decoration-line: underline; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\">\n" +
                "                        <a href=\"http://www.sc.gov.cn/\">www.sc.gov.cn</a></td>\n" +
                "                    <td class=\"xl66\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap;\">\n" +
                "                        京芥子园画院</td>\n" +
                "                    <td class=\"xl67\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: rgb(5, 99, 193); font-size: 12pt; text-decoration-line: underline; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\">\n" +
                "                        <a href=\"http://www.jzyhy.com/\">www.jzyhy.com</a></td>\n" +
                "                </tr>\n" +
                "                <tr height=\"25\" style=\"height: 25pt;\">\n" +
                "                    <td class=\"xl66\" height=\"25\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; height: 25pt;\">\n" +
                "                        贵州省人民政府</td>\n" +
                "                    <td class=\"xl67\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: rgb(5, 99, 193); font-size: 12pt; text-decoration-line: underline; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\">\n" +
                "                        <a href=\"http://www.guizhou.gov.cn/\">www.guizhou.gov.cn</a></td>\n" +
                "                    <td class=\"xl66\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap;\">\n" +
                "                        蓝宝天禾科技</td>\n" +
                "                    <td class=\"xl67\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: rgb(5, 99, 193); font-size: 12pt; text-decoration-line: underline; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\">\n" +
                "                        <a href=\"http://www.lanbe.com/\">www.lanbe.com</a></td>\n" +
                "                </tr>\n" +
                "                <tr height=\"25\" style=\"height: 25pt;\">\n" +
                "                    <td class=\"xl66\" height=\"25\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap; height: 25pt;\">\n" +
                "                        济宁市卫生健康委员会</td>\n" +
                "                    <td class=\"xl67\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: rgb(5, 99, 193); font-size: 12pt; text-decoration-line: underline; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\">\n" +
                "                        <a href=\"http://wjw.jining.gov.cn/\">wjw.jining.gov.cn</a></td>\n" +
                "                    <td class=\"xl66\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; font-size: 12pt; font-family: 宋体; vertical-align: middle; border: none; white-space: nowrap;\">\n" +
                "                        浙江邦程科技有限公司</td>\n" +
                "                    <td class=\"xl67\"\n" +
                "                        style=\"padding-top: 1px; padding-right: 1px; padding-left: 1px; color: rgb(5, 99, 193); font-size: 12pt; text-decoration-line: underline; font-family: 宋体, sans-serif; vertical-align: middle; border: none; white-space: nowrap;\">\n" +
                "                        <a href=\"http://www.zjbckj.com/\">www.zjbckj.com</a></td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table><br>\n" +
                "    </div>\n" +
                "    <div><br></div>\n" +
                "    <div><br></div>\n" +
                "    <div>了解更多信息，请联系招商经理，免费一对一解答～</div>\n" +
                "    <div><br></div>\n" +
                "    <div>招商经理：【ZSJL】</div>\n" +
                "    <div>招商电话：【ZSDH】</div>\n" +
                "    <div>全国统一热线：4000020000</div>\n" +
                "    <div style=\"font-size: 14px;\">\n" +
                "        <div style=\"font-size: medium;\">北京飞天经纬科技股份有限公司</div>\n" +
                "        <div style=\"font-size: medium;\">公司地址：<span class=\"js_location_string\"\n" +
                "                style=\"border-bottom: 1px dashed rgb(171, 171, 171); z-index: 1; position: static;\">北京市朝阳区北苑东路19号院2号楼27层</span>2709\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>";
                content=content.replace("【ZSJL】","许经理")
                .replace("【ZSDH】","18234419988");

        int i=0;
        for (String s :strings){
            if(StringUtil.isNotEmpty(s)){
                sender.sendEmail(s,"2020疫情过后商机，助你盘活经营多年的客户，提升客户价值\n",content);
            }
            i++;
        }


    }

    @AfterEach
    public void exit(){
        System.out.println("结束");
    }

}
