package manage.thuctap.Services;

import java.time.LocalTime;
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
    
    public WorkSchedule getOrCreateDefaultWorkSchedule() {
        List<WorkSchedule> schedules = getAllWorkSchedules();
        if (!schedules.isEmpty()) {
            WorkSchedule existingSchedule = schedules.get(0);
            // Update the start time to 08:30 if it's not already
            if (!existingSchedule.getStartTime().equals(LocalTime.of(8, 30))) {
                existingSchedule.setStartTime(LocalTime.of(8, 30));
                return saveWorkSchedule(existingSchedule);
            }
            return existingSchedule;
        } else {
            // Create a default work schedule with start time at 08:30
            WorkSchedule defaultSchedule = new WorkSchedule();
            defaultSchedule.setName("Default Schedule");
            defaultSchedule.setStartTime(LocalTime.of(8, 30)); // 8:30 AM
            defaultSchedule.setEndTime(LocalTime.of(17, 0));   // 5:00 PM
            defaultSchedule.setWorkingDays(31);  // Monday to Friday (11111)
            return saveWorkSchedule(defaultSchedule);
        }
    }
}
