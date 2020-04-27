package ftjw.web.mobile.analyze.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * 殷晓龙
 * 2020/4/3 14:29
 */
@Entity
@Data
@Table(name = "t_user")
@ApiModel("用户实体类")
public class User {
    @Id
    Integer id;
    String name;
    Integer status;
    String tel;
    String headImage;
    @Transient
    @ApiModelProperty(value = "机构名称(全局唯一)")
    @NotNull(message = "机构名称不能为空")
    String orgname;

    @ApiModelProperty(value = "登录帐号")
    @NotNull(message = "账号不能为空")
    String login;

    @Transient
    @ApiModelProperty(value = "登录密码")
    @NotNull(message = "密码不能为空")
    String password;
    /**
     *
     */
    @ApiModelProperty(value = "套餐ID")
    @NotNull(message = "套餐id不能为空")
    String serviceid;
    /**
     * 机构描述信息
     */
    String introduction;
    /**
     * 区域ID
     */
    String region;
    /**
     *默认套餐额外时长(单位:天,   有效时间为: 基础时长1年 + 额外时长)
     */
    @Transient
    String addtime;

    //平台id
    @Transient
    private Integer productId;
    //平台价格
    @Transient
    private Integer productPrice;
    //制作费用
    @Transient
    private Integer productMakePrice;
    //服务年数
    @Transient
    private Integer serveYear;
    //页数
    @Transient
    private Integer webPage;
    @Transient
    private String website;
}
