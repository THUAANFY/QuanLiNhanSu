package manage.thuctap.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import manage.thuctap.DAO.WorkScheduleDAO;
import manage.thuctap.Models.WorkSchedule;

@Service
public class WorkScheduleService {
    @Autowired
    private WorkScheduleDAO workScheduleDAO;
    
    public List<WorkSchedule> getAllWorkSchedules() {
        return workScheduleDAO.findAll();
    }
    
    public Optional<WorkSchedule> getWorkScheduleById(Long id) {
        return workScheduleDAO.findById(id);
    }
    
    public WorkSchedule saveWorkSchedule(WorkSchedule workSchedule) {
        return workScheduleDAO.save(workSchedule);
    }
    
    public void deleteWorkSchedule(Long id) {
        workScheduleDAO.deleteById(id);
    }
}
