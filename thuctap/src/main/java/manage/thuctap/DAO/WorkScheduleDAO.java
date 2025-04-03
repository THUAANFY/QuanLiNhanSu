package manage.thuctap.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import manage.thuctap.Models.WorkSchedule;

@Repository
public interface WorkScheduleDAO extends JpaRepository<WorkSchedule, Long>{

}
