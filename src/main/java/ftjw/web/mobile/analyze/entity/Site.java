package ftjw.web.mobile.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Map;

/**
 * 殷晓龙
 * 2020/3/20 17:40
 */
@Entity
@Table(name = "t_site")
@Data
public class Site {
    @Id
    Integer id;
    Integer uid;
    String option;
    String name;
    Integer status;
    String address;
    String tel;
    String logo;
}
