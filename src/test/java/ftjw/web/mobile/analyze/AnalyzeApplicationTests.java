package ftjw.web.mobile.analyze;

import ftjw.web.mobile.analyze.dao.SiteRepository;
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

    @Test
    public void findSiteList(){
       List<Site> list = siteRepository.findSites();
        System.out.println(list.get(0).getOption());
    }

}
