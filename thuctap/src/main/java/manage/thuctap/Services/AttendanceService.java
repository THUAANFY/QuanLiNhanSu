package manage.thuctap.Services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import manage.thuctap.DAO.AttendanceDAO;
import manage.thuctap.Models.Attendance;
import manage.thuctap.Models.Employee;
import manage.thuctap.Models.WorkSchedule;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceDAO attendanceDAO;
    
    public List<Attendance> getAllAttendances() {
        return attendanceDAO.findAll();
    }
    
    public Optional<Attendance> getAttendanceById(Long id) {
        return attendanceDAO.findById(id);
    }
    
    public List<Attendance> getAttendancesByEmployeeAndDateRange(Employee employee, LocalDate startDate, LocalDate endDate) {
        return attendanceDAO.findByEmployeeAndDateBetween(employee, startDate, endDate);
    }
    
    public Optional<Attendance> getAttendanceByEmployeeAndDate(Employee employee, LocalDate date) {
        return attendanceDAO.findByEmployeeAndDate(employee, date);
    }
    
    public Attendance checkIn(Employee employee, WorkSchedule workSchedule) {
        LocalDate today = LocalDate.now();
        // Set fixed check-in time to 08:30 instead of 09:00
        LocalTime fixedCheckInTime = LocalTime.of(8, 30);
        
        Optional<Attendance> existingAttendance = getAttendanceByEmployeeAndDate(employee, today);
        
        if (existingAttendance.isPresent()) {
            Attendance attendance = existingAttendance.get();
            // Only update check-in if it hasn't been set yet
            if (attendance.getCheckIn() == null) {
                attendance.setCheckIn(fixedCheckInTime);
                
                // Determine status based on check-in time
                if (fixedCheckInTime.isAfter(workSchedule.getStartTime())) {
                    attendance.setStatus("LATE");
                } else {
                    attendance.setStatus("PRESENT");
                }
                
                return attendanceDAO.save(attendance);
            }
            return attendance;
        }
        
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(today);
        attendance.setCheckIn(fixedCheckInTime);
        attendance.setWorkSchedule(workSchedule);
        
        // Determine status based on check-in time
        if (fixedCheckInTime.isAfter(workSchedule.getStartTime())) {
            attendance.setStatus("LATE");
        } else {
            attendance.setStatus("PRESENT");
        }
        
        return attendanceDAO.save(attendance);
    }
    
    public Attendance checkOut(Employee employee) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        
        Optional<Attendance> existingAttendance = getAttendanceByEmployeeAndDate(employee, today);
        
        if (existingAttendance.isPresent()) {
            Attendance attendance = existingAttendance.get();
            attendance.setCheckOut(now);
            return attendanceDAO.save(attendance);
        }
        
        return null;
    }
    
    public Attendance saveAttendance(Attendance attendance) {
        return attendanceDAO.save(attendance);
    }
    
    public void deleteAttendance(Long id) {
        attendanceDAO.deleteById(id);
    }
}
