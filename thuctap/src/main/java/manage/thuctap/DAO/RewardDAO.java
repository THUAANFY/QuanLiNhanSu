package manage.thuctap.DAO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import manage.thuctap.Models.Employee;
import manage.thuctap.Models.Reward;

@Repository
public interface RewardDAO extends JpaRepository<Reward, Long>{
    List<Reward> findByEmployee(Employee employee);
    List<Reward> findByType(String type);
    List<Reward> findByDateBetween(Date startDate, Date endDate);
    List<Reward> findByEmployeeAndDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
}
