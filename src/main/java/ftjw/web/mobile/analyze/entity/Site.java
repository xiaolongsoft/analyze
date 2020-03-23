package ftjw.web.mobile.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
