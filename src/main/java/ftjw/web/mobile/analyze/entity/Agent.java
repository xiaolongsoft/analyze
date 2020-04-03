package ftjw.web.mobile.analyze.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 殷晓龙
 * 2020/3/25 17:36
 * 代理商类
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "ydh_agent")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    /**
     * 代理商账号
     */
    String account;
    /**
     * 代理商密码
     */
    String password;
    /**
     * 代理商公司名称
     */
    @NotNull
    String name;
    /**
     * 申请时间
     */
    Date ctime;
    /**
     * 套餐
     */
    String  code;
    /**
     * 状态    0.暂停 1.有效  2 申请中 3，过期
     */
    @Column
    Integer status;
    /**
     * 账户
     */
    Double amount;
    /**
     * 可用金额
     */
    Double realAmount;
    String salt;
    /**
     * 角色
     */
    String role;
    /**
     * 备注
     */
    String remark;
    /**
     * 联系人
     */
    @NotNull
    String contacts;
    /**
     * 联系人手机
     */
    @Length(min = 11)
    String phone;
    /**
     * 微信
     */
    String weixin;

    /**
     * 购买意向
     */
    String intention;

}
