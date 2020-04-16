package ftjw.web.mobile.analyze.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 殷晓龙
 * 2020/4/15 14:59
 */
@Data
@Table(name = "ydh_product")
@Entity
@ApiModel("产品列表")
public class Product {
    @Id
    Integer id;
    @ApiModelProperty(value = "套餐名称")
    String proName;
    @ApiModelProperty(value = "1平台 2一次性")
    Integer type;
    @ApiModelProperty(value = "1普通代理2金牌代理3核心代理")
    Integer agentGrade;
    @ApiModelProperty(value = "代理价格")
    String agentPrice;
}
