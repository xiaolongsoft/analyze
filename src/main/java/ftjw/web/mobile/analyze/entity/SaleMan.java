package ftjw.web.mobile.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 殷晓龙
 * 2020/3/25 13:57
 * 销售
 */
@Data
@Entity
@Table(name = "ydh_salesman")
public class SaleMan {
    @Id
    Integer id;
    String phone;
    String name;
}
