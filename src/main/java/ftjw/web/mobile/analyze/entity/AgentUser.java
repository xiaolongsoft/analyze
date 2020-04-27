package ftjw.web.mobile.analyze.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 殷晓龙
 * 2020/4/2 13:59
 */
@Data
@Entity
@Table(name = "ydh_agent_user")
public class AgentUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer agentId;
    Integer userId;
    Integer status;
    Date ctime;
    Date expireTime;
}
