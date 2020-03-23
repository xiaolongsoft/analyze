package ftjw.web.mobile.analyze.dao;

import ftjw.web.mobile.analyze.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 殷晓龙
 * 2020/3/18 14:50
 */
public interface SiteRepository extends JpaRepository<Site,Integer> {

    @Query(value = "SELECT `option`,id ,uid,name FROM `t_site` WHERE sitetype=5 AND `option` LIKE '%pub_time%'",nativeQuery = true)
    List<Site> findSites();
}
