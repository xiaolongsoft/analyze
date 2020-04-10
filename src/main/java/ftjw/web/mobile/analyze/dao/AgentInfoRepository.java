package ftjw.web.mobile.analyze.dao;

import ftjw.web.mobile.analyze.entity.AgentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 殷晓龙
 * 2020/3/25 17:41
 */
public interface AgentInfoRepository extends JpaRepository<AgentInfo,Integer> {
    Page<AgentInfo> findByEmailNotNull(Pageable pageable);
}
