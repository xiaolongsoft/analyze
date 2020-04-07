package ftjw.web.mobile.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 殷晓龙
 * 2020/4/3 17:20
 */
@Entity
@Data
@Table(name = "agent_info")
public class AgentInfo {
    @Id
    Integer id;
    String  email;
}
