package ftjw.web.mobile.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 殷晓龙
 * 2020/4/3 11:19
 */
@Data
@Entity
@Table(name = "url_access_log")
public class UrlAccessLog {
    @Id
    @GeneratedValue
    Integer id;
     Date date;
    String url;
}
