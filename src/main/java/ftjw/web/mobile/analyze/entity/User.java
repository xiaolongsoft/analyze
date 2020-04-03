package ftjw.web.mobile.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 殷晓龙
 * 2020/4/3 14:29
 */
@Entity
@Data
@Table(name = "t_user")
public class User {
    @Id
    Integer id;
    String name;
    Integer status;
    String tel;
    String headImage;
}
