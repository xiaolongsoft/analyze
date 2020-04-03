package ftjw.web.mobile.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 代理商充值消费记录
 * 殷晓龙
 * 2020/4/3 15:27
 */
@Data
@Entity
@Table(name = "ydh_agent_pay_log")
public class AgentPayLog {
    @Id
    @GeneratedValue
    Integer id;
    Integer agentId;
    String action;
    Double money;
    Date ctime;
}
