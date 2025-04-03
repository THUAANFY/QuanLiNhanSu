package manage.thuctap.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import manage.thuctap.Models.Attendance;
import manage.thuctap.Models.Employee;
import manage.thuctap.Models.Salary;
import manage.thuctap.Models.WorkSchedule;
import manage.thuctap.Services.AttendanceService;
import manage.thuctap.Services.EmployeeService;
import manage.thuctap.Services.SalaryService;
import manage.thuctap.Services.WorkScheduleService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private AttendanceService attendanceService;
    
    @Autowired
    private SalaryService salaryService;
    
    @Autowired
    private WorkScheduleService workScheduleService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }
        
        LocalDate today = LocalDate.now();
        Optional<Attendance> todayAttendance = attendanceService.getAttendanceByEmployeeAndDate(employee, today);
        
        model.addAttribute("employee", employee);
        model.addAttribute("todayAttendance", todayAttendance.orElse(null));
        
        return "employee/dashboard";
    }
    
    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("employee", employee);
        return "employee/profile";
    }
    
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute Employee updatedEmployee, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }
        
        // Update only allowed fields
        employee.setPhone(updatedEmployee.getPhone());
        employee.setAddress(updatedEmployee.getAddress());
        employee.setPassword(updatedEmployee.getPassword());
        
        employeeService.saveEmployee(employee);
        session.setAttribute("employee", employee);
        
        return "redirect:/employee/profile";
    }
    
    @GetMapping("/attendance")
    public String showAttendance(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }
        
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        
        List<Attendance> attendances = attendanceService.getAttendancesByEmployeeAndDateRange(
                employee, startOfMonth, today);
        
        Optional<Attendance> todayAttendance = attendanceService.getAttendanceByEmployeeAndDate(employee, today);
        
        model.addAttribute("employee", employee);
        model.addAttribute("attendances", attendances);
        model.addAttribute("todayAttendance", todayAttendance.orElse(null));
        
        return "employee/attendance";
    }
    
    @PostMapping("/attendance/check-in")
    public String checkIn(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }
        
        // Get default work schedule (in a real app, this would be assigned to the employee)
        List<WorkSchedule> schedules = workScheduleService.getAllWorkSchedules();
        if (!schedules.isEmpty()) {
            attendanceService.checkIn(employee, schedules.get(0));
        } else {
            // Create a default work schedule if none exists
            WorkSchedule defaultSchedule = new WorkSchedule();
            defaultSchedule.setName("Default Schedule");
            defaultSchedule.setStartTime(LocalTime.of(8, 0)); // 8:00 AM
            defaultSchedule.setEndTime(LocalTime.of(17, 0));  // 5:00 PM
            defaultSchedule.setWorkingDays(31);  // Monday to Friday (11111)
            WorkSchedule savedSchedule = workScheduleService.saveWorkSchedule(defaultSchedule);
            
            attendanceService.checkIn(employee, savedSchedule);
        }
        
        return "redirect:/employee/dashboard";
    }
    
    @PostMapping("/attendance/check-out")
    public String checkOut(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }
        
        attendanceService.checkOut(employee);
        return "redirect:/employee/dashboard";
    }
    
    @GetMapping("/salary")
    public String showSalary(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }
        
        List<Salary> salaries = salaryService.getSalariesByEmployee(employee);
        
        model.addAttribute("employee", employee);
        model.addAttribute("salaries", salaries);
        
        return "employee/salary";
    }
}