package ftjw.web.mobile.analyze.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull(message = "代理商名称不能为空")
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
     * 状态    0.暂停 1.有效  2 申请中 3，过期 4.拒绝
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
    @NotNull(message="联系人信息不能为空")
    String contacts;
    /**
     * 联系人手机
     */
    @NotNull(message = "phone 手机号不能为空")
    @Size(min = 7,max = 11,message = "手机号格式不正确")
    String phone;
    /**
     * 微信
     */
    String weixin;

    /**
     * 购买意向
     */
    String intention;

    /**
     * 省
     */
    String province;
    /**
     * 市
     */
    String city;

}
