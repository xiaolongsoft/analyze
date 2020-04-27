package ftjw.web.mobile.analyze.dao;

import ftjw.web.mobile.analyze.entity.AgentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * 殷晓龙
 * 2020/3/25 17:41
 */
public interface AgentUserRepository extends JpaRepository<AgentUser,Integer> {

    @Query(value = "SELECT COUNT(DISTINCT user_id) FROM ydh_agent_user WHERE agent_id=?1",nativeQuery = true)
    Integer selectUserCount(Integer agentId);

    @Query(value = "SELECT COUNT( DISTINCT user_id) FROM ydh_agent_user WHERE DATE_SUB(CURDATE(), INTERVAL 15 DAY) <= DATE(`ctime`) AND agent_id=?1",nativeQuery = true)
    Integer selectAddUser(Integer agentId);

    @Query(value = "SELECT COUNT( DISTINCT user_id) FROM ydh_agent_user WHERE  DATE(`expire_time`)<= ADDDATE(CURDATE(), 30) AND agent_id=?1",nativeQuery = true)
    Integer selectExpireUser(Integer agentId);
}
