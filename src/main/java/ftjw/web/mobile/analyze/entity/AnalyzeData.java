package ftjw.web.mobile.analyze.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 殷晓龙
 * 2020/3/17 10:46
 */
@Entity
@Table(name = "analyze_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyzeData {
    @Id
    Integer id;
    /**
     * 公司名称
     */
    String name;
    /**
     * 公司网址
     */
    String web;
    /**
     * 访问量统计
     */
    Integer pv;
    /**
     * 网址状态
     */
    String status;

    /**
     * 发布时间
     */
    Integer firstPubTime;
    /**
     * 过期时间
     */
    Integer expireTime;
}
