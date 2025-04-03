package manage.thuctap.DAO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import manage.thuctap.Models.Employee;
import manage.thuctap.Models.Salary;

@Repository
public interface SalaryDAO extends JpaRepository<Salary, Long>{
    List<Salary> findByEmployee(Employee employee);
    List<Salary> findByMonth(Date month);
    Optional<Salary> findByEmployeeAndMonth(Employee employee, LocalDate month);
}
