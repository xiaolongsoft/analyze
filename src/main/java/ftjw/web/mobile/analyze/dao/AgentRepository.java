package ftjw.web.mobile.analyze.dao;

import ftjw.web.mobile.analyze.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * 殷晓龙
 * 2020/3/25 17:41
 */
public interface AgentRepository extends JpaRepository<Agent,Integer> {

    Agent findOneByAccount(String account);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ydh_agent SET amount=amount+?2 WHERE id=?1 AND (amount+?2)>=0",nativeQuery = true)
    Integer updateById(Integer agentId, Double money);
}
