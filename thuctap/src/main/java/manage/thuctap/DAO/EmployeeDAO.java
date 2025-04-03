package manage.thuctap.DAO;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import manage.thuctap.Models.Employee;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Long>{
    // public Employee findByUser_Username(String username);
    Optional<Employee> findByEmail(String email);
}
