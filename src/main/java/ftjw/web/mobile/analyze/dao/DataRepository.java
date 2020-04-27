package ftjw.web.mobile.analyze.dao;

import ftjw.web.mobile.analyze.entity.AnalyzeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 殷晓龙
 * 2020/3/18 14:50
 */
@Repository
public interface DataRepository extends JpaRepository<AnalyzeData,Integer> {

    @Query("SELECT new map( COUNT(*) as total,a.status as status)  FROM AnalyzeData a GROUP BY a.status")
    List<Map<String,  Object>> countAnalyzeDataByStatus();
}
