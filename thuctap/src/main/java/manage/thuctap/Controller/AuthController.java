package manage.thuctap.Controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import manage.thuctap.Models.Employee;
import manage.thuctap.Services.EmployeeService;


@Controller
public class AuthController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email, 
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        
        Optional<Employee> employeeOpt = employeeService.getEmployeeByEmail(email);
        
        if (employeeOpt.isPresent() && employeeOpt.get().getPassword().equals(password)) {
            Employee employee = employeeOpt.get();
            session.setAttribute("employee", employee);
            
            if (employee.isAdmin()) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/employee/dashboard";
            }
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}