package ftjw.web.mobile.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 殷晓龙
 * 2020/3/25 17:36
 * 代理商类
 */
@Entity
@Data
@Table(name = "ydh_agent")
public class Agent {
    @Id
    Integer id;
    String account;
    String password;
    String name;
    Date ctime;
    String  code;
    Integer status;
    Double amount;
    Double realAmount;
    String salt;
    String role;

}
