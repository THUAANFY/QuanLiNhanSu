package manage.thuctap.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import manage.thuctap.DAO.EmployeeDAO;
import manage.thuctap.Models.Employee;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeDAO employeeDAO;

    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }
    
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeDAO.findById(id);
    }
    
    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeDAO.findByEmail(email);
    }
    
    public Employee saveEmployee(Employee employee) {
        return employeeDAO.save(employee);
    }
    
    public void deleteEmployee(Long id) {
        employeeDAO.deleteById(id);
    }
    
    public boolean authenticate(String email, String password) {
        Optional<Employee> employee = employeeDAO.findByEmail(email);
        return employee.isPresent() && employee.get().getPassword().equals(password);
    }
}
