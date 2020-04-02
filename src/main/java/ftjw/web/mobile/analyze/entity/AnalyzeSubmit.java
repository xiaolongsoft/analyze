package ftjw.web.mobile.analyze.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 殷晓龙
 * 2020/3/20 16:47
 */
@Entity
@Table(name = "analyze_submit")
@Data
public class AnalyzeSubmit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    /**
     * 客户姓名
     */
    @NotNull
    String name;
    /**
     * 电话
     */
    @NotNull
    @Length(min = 11)
    String phone;
    Integer sid;
    String province;
    String city;
    /**
     * 推荐人
     */
    String referrer;
    String rftel;
}
