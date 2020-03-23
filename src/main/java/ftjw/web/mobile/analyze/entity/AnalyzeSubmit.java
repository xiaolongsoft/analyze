package ftjw.web.mobile.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 殷晓龙
 * 2020/3/20 16:47
 */
@Entity
@Table(name = "analyze_submit")
@Data
public class AnalyzeSubmit {

    @Id
    Integer id;
    /**
     * 客户留言
     */
    String message;
    /**
     * 电话
     */
    String phone;
}
