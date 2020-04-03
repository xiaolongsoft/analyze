package ftjw.web.mobile.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 商户充值消费记录
 * 殷晓龙
 * 2020/4/3 15:27
 */
@Data
@Entity
@Table(name = "ydh_user_pay_log")
public class UserPayLog {
    @Id
    @GeneratedValue
    Integer id;
    Integer userId;
    String action;
    Double money;
    private Date ctime;
}
