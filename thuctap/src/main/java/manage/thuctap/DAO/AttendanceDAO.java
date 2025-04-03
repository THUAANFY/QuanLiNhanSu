package manage.thuctap.DAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import manage.thuctap.Models.Attendance;
import manage.thuctap.Models.Employee;

@Repository
public interface AttendanceDAO extends JpaRepository<Attendance, Long>{
    List<Attendance> findByEmployeeAndDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
    Optional<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);
}
