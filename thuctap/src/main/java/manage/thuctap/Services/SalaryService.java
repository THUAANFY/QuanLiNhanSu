package manage.thuctap.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import manage.thuctap.DAO.SalaryDAO;
import manage.thuctap.Models.Attendance;
import manage.thuctap.Models.Employee;
import manage.thuctap.Models.Reward;
import manage.thuctap.Models.Salary;

@Service
public class SalaryService {
    @Autowired
    private SalaryDAO salaryDAO;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private RewardService rewardService;
    
    public List<Salary> getAllSalaries() {
        return salaryDAO.findAll();
    }
    
    public Optional<Salary> getSalaryById(Long id) {
        return salaryDAO.findById(id);
    }
    
    public List<Salary> getSalariesByEmployee(Employee employee) {
        return salaryDAO.findByEmployee(employee);
    }
    
    public Optional<Salary> getSalaryByEmployeeAndMonth(Employee employee, LocalDate month) {
        return salaryDAO.findByEmployeeAndMonth(employee, month);
    }
    
    public Salary calculateSalary(Employee employee, LocalDate month) {
        // First day of the month
        LocalDate firstDay = month.withDayOfMonth(1);
        // Last day of the month
        LocalDate lastDay = month.withDayOfMonth(month.lengthOfMonth());
        
        // Get attendance for the month
        List<Attendance> attendances = attendanceService.getAttendancesByEmployeeAndDateRange(employee, firstDay, lastDay);
        
        // Get rewards/disciplines for the month
        List<Reward> rewards = rewardService.getRewardsByEmployeeAndDateRange(employee, firstDay, lastDay);
        
        // Calculate base salary
        double baseSalary = employee.getPosition().getBaseSalary();
        
        // Calculate attendance-based salary
        double attendanceMultiplier = calculateAttendanceMultiplier(attendances);
        double attendanceSalary = baseSalary * attendanceMultiplier;
        
        // Calculate bonus from rewards
        double bonus = rewards.stream()
                .filter(r -> r.getType().equals("REWARD"))
                .mapToDouble(Reward::getAmount)
                .sum();
        
        // Calculate deductions from disciplines
        double deduction = rewards.stream()
                .filter(r -> r.getType().equals("DISCIPLINE"))
                .mapToDouble(Reward::getAmount)
                .sum();
        
        // Calculate total salary
        double totalAmount = attendanceSalary + bonus - deduction;
        
        // Create or update salary record
        Optional<Salary> existingSalary = getSalaryByEmployeeAndMonth(employee, firstDay);
        
        Salary salary;
        if (existingSalary.isPresent()) {
            salary = existingSalary.get();
        } else {
            salary = new Salary();
            salary.setEmployee(employee);
            salary.setMonth(firstDay);
        }
        
        salary.setAmount(attendanceSalary);
        salary.setBonus(bonus);
        salary.setDeduction(deduction);
        salary.setTotalAmount(totalAmount);
        
        return salaryDAO.save(salary);
    }
    
    private double calculateAttendanceMultiplier(List<Attendance> attendances) {
        // Simple calculation: percentage of days present
        long presentDays = attendances.stream()
                .filter(a -> a.getStatus().equals("PRESENT"))
                .count();
        
        long lateDays = attendances.stream()
                .filter(a -> a.getStatus().equals("LATE"))
                .count();
        
        // Assuming 22 working days in a month
        int workingDays = 22;
        
        // Late days count as 0.5 present
        return (presentDays + (lateDays * 0.5)) / workingDays;
    }
    
    public Salary saveSalary(Salary salary) {
        return salaryDAO.save(salary);
    }
    
    public void deleteSalary(Long id) {
        salaryDAO.deleteById(id);
    }
}
