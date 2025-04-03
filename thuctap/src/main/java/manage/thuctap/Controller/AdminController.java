package manage.thuctap.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import manage.thuctap.Models.Attendance;
import manage.thuctap.Models.Employee;
import manage.thuctap.Models.Position;
import manage.thuctap.Models.Reward;
import manage.thuctap.Models.Salary;
import manage.thuctap.Models.WorkSchedule;
import manage.thuctap.Services.AttendanceService;
import manage.thuctap.Services.EmployeeService;
import manage.thuctap.Services.PositionService;
import manage.thuctap.Services.RewardService;
import manage.thuctap.Services.SalaryService;
import manage.thuctap.Services.WorkScheduleService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private WorkScheduleService workScheduleService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private SalaryService salaryService;
    @Autowired
    private RewardService rewardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        model.addAttribute("employeeCount", employeeService.getAllEmployees().size());
        model.addAttribute("positionCount", positionService.getAllPositions().size());
        model.addAttribute("admin", admin);
        
        return "admin/dashboard";
    }

    // Employee Management
    @GetMapping("/employees")
    public String listEmployees(Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        model.addAttribute("admin", admin);
        
        return "admin/employees";
    }
    
    @GetMapping("/employees/add")
    public String showAddEmployeeForm(Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        List<Position> positions = positionService.getAllPositions();
        model.addAttribute("positions", positions);
        model.addAttribute("employee", new Employee());
        model.addAttribute("admin", admin);
        
        return "admin/employee-form";
    }
    
    @PostMapping("/employees/save")
    public String saveEmployee(@ModelAttribute Employee employee, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        employeeService.saveEmployee(employee);
        return "redirect:/admin/employees";
    }
    
    @GetMapping("/employees/edit/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            List<Position> positions = positionService.getAllPositions();
            model.addAttribute("positions", positions);
            model.addAttribute("employee", employee.get());
            model.addAttribute("admin", admin);

            System.out.println("Password: " + employee.get().getPassword());
            return "admin/employee-form";
        }
        
        return "redirect:/admin/employees";
    }
    
    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        employeeService.deleteEmployee(id);
        return "redirect:/admin/employees";
    }
    
    // Position Management
    @GetMapping("/positions")
    public String listPositions(Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        List<Position> positions = positionService.getAllPositions();
        model.addAttribute("positions", positions);
        model.addAttribute("admin", admin);
        
        return "admin/positions";
    }
    
    @GetMapping("/positions/add")
    public String showAddPositionForm(Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        model.addAttribute("position", new Position());
        model.addAttribute("admin", admin);
        
        return "admin/position-form";
    }
    
    @PostMapping("/positions/save")
    public String savePosition(@ModelAttribute Position position, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        positionService.savePosition(position);
        return "redirect:/admin/positions";
    }
    
    @GetMapping("/positions/edit/{id}")
    public String showEditPositionForm(@PathVariable Long id, Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        Optional<Position> position = positionService.getPositionById(id);
        if (position.isPresent()) {
            model.addAttribute("position", position.get());
            model.addAttribute("admin", admin);
            return "admin/position-form";
        }
        
        return "redirect:/admin/positions";
    }
    
    @GetMapping("/positions/delete/{id}")
    public String deletePosition(@PathVariable Long id, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        positionService.deletePosition(id);
        return "redirect:/admin/positions";
    }
    
    // Work Schedule Management
    @GetMapping("/schedules")
    public String listWorkSchedules(Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        List<WorkSchedule> schedules = workScheduleService.getAllWorkSchedules();
        model.addAttribute("schedules", schedules);
        model.addAttribute("admin", admin);
        
        return "admin/schedules";
    }
    
    @GetMapping("/schedules/add")
    public String showAddWorkScheduleForm(Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        model.addAttribute("schedule", new WorkSchedule());
        model.addAttribute("admin", admin);
        
        return "admin/schedule-form";
    }
    
    @PostMapping("/schedules/save")
    public String saveWorkSchedule(@ModelAttribute WorkSchedule schedule, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        workScheduleService.saveWorkSchedule(schedule);
        return "redirect:/admin/schedules";
    }
    
    @GetMapping("/schedules/edit/{id}")
    public String showEditWorkScheduleForm(@PathVariable Long id, Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        Optional<WorkSchedule> schedule = workScheduleService.getWorkScheduleById(id);
        if (schedule.isPresent()) {
            model.addAttribute("schedule", schedule.get());
            model.addAttribute("admin", admin);
            return "admin/schedule-form";
        }
        
        return "redirect:/admin/schedules";
    }
    
    @GetMapping("/schedules/delete/{id}")
    public String deleteWorkSchedule(@PathVariable Long id, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        workScheduleService.deleteWorkSchedule(id);
        return "redirect:/admin/schedules";
    }
    
    // Attendance Management
    @GetMapping("/attendances")
    public String listAttendances(Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        List<Attendance> attendances = attendanceService.getAllAttendances();
        model.addAttribute("attendances", attendances);
        model.addAttribute("admin", admin);
        
        return "admin/attendances";
    }
    
    @GetMapping("/attendances/add")
    public String showAddAttendanceForm(Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        List<Employee> employees = employeeService.getAllEmployees();
        List<WorkSchedule> schedules = workScheduleService.getAllWorkSchedules();
        
        model.addAttribute("attendance", new Attendance());
        model.addAttribute("employees", employees);
        model.addAttribute("schedules", schedules);
        model.addAttribute("admin", admin);
        
        return "admin/attendance-form";
    }
    
    @PostMapping("/attendances/save")
    public String saveAttendance(@ModelAttribute Attendance attendance, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        attendanceService.saveAttendance(attendance);
        return "redirect:/admin/attendances";
    }
    
    @GetMapping("/attendances/edit/{id}")
    public String showEditAttendanceForm(@PathVariable Long id, Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        Optional<Attendance> attendance = attendanceService.getAttendanceById(id);
        if (attendance.isPresent()) {
            List<Employee> employees = employeeService.getAllEmployees();
            List<WorkSchedule> schedules = workScheduleService.getAllWorkSchedules();
            
            model.addAttribute("attendance", attendance.get());
            model.addAttribute("employees", employees);
            model.addAttribute("schedules", schedules);
            model.addAttribute("admin", admin);
            return "admin/attendance-form";
        }
        return "redirect:/admin/attendances";
    }
    
    @GetMapping("/attendances/delete/{id}")
    public String deleteAttendance(@PathVariable Long id, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        attendanceService.deleteAttendance(id);
        return "redirect:/admin/attendances";
    }
    
    // Salary Management
    @GetMapping("/salaries")
    public String listSalaries(Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        List<Salary> salaries = salaryService.getAllSalaries();
        model.addAttribute("salaries", salaries);
        model.addAttribute("admin", admin);
        
        return "admin/salaries";
    }
    
    @GetMapping("/salaries/calculate")
    public String showCalculateSalaryForm(Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        model.addAttribute("admin", admin);
        
        return "admin/salary-calculate";
    }
    
    @PostMapping("/salaries/calculate")
    public String calculateSalary(@RequestParam Long employeeId,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate month,
                                 HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        if (employee.isPresent()) {
            salaryService.calculateSalary(employee.get(), month);
        }
        
        return "redirect:/admin/salaries";
    }
    
    @GetMapping("/salaries/edit/{id}")
    public String showEditSalaryForm(@PathVariable Long id, Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        Optional<Salary> salary = salaryService.getSalaryById(id);
        if (salary.isPresent()) {
            model.addAttribute("salary", salary.get());
            model.addAttribute("admin", admin);
            return "admin/salary-form";
        }
        
        return "redirect:/admin/salaries";
    }
    
    @PostMapping("/salaries/save")
    public String saveSalary(@ModelAttribute Salary salary, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        salaryService.saveSalary(salary);
        return "redirect:/admin/salaries";
    }
    
    @GetMapping("/salaries/delete/{id}")
    public String deleteSalary(@PathVariable Long id, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        salaryService.deleteSalary(id);
        return "redirect:/admin/salaries";
    }
    
    // Reward Management
    @GetMapping("/rewards")
    public String listRewards(Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        List<Reward> rewards = rewardService.getAllRewards();
        model.addAttribute("rewards", rewards);
        model.addAttribute("admin", admin);
        
        return "admin/rewards";
    }
    
    @GetMapping("/rewards/add")
    public String showAddRewardForm(Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        model.addAttribute("reward", new Reward());
        model.addAttribute("admin", admin);
        
        return "admin/reward-form";
    }
    
    @PostMapping("/rewards/save")
    public String saveReward(@ModelAttribute Reward reward, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        rewardService.saveReward(reward);
        return "redirect:/admin/rewards";
    }
    
    @GetMapping("/rewards/edit/{id}")
    public String showEditRewardForm(@PathVariable Long id, Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        Optional<Reward> reward = rewardService.getRewardById(id);
        if (reward.isPresent()) {
            List<Employee> employees = employeeService.getAllEmployees();
            model.addAttribute("employees", employees);
            model.addAttribute("reward", reward.get());
            model.addAttribute("admin", admin);
            return "admin/reward-form";
        }
        
        return "redirect:/admin/rewards";
    }
    
    @GetMapping("/rewards/delete/{id}")
    public String deleteReward(@PathVariable Long id, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        rewardService.deleteReward(id);
        return "redirect:/admin/rewards";
    }
    
    // Statistics
    @GetMapping("/statistics")
    public String showStatistics(Model model, HttpSession session) {
        Employee admin = (Employee) session.getAttribute("employee");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        
        List<Employee> employees = employeeService.getAllEmployees();
        List<Position> positions = positionService.getAllPositions();
        List<Salary> salaries = salaryService.getAllSalaries();
        
        model.addAttribute("employees", employees);
        model.addAttribute("positions", positions);
        model.addAttribute("salaries", salaries);
        model.addAttribute("admin", admin);
        
        return "admin/statistics";
    }
}
