package ftjw.web.mobile.analyze.dao;

import ftjw.web.mobile.analyze.entity.SaleMan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 殷晓龙
 * 2020/3/18 14:50
 */
public interface SaleRepository extends JpaRepository<SaleMan,Integer> {

    List<SaleMan> findByEmailNotNull();
}
