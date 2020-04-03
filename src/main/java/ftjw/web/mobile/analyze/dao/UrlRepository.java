package ftjw.web.mobile.analyze.dao;

import ftjw.web.mobile.analyze.entity.UrlAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 殷晓龙
 * 2020/3/25 17:41
 */
public interface UrlRepository extends JpaRepository<UrlAccessLog,Integer> {


}
