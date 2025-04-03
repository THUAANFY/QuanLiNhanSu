package manage.thuctap.Models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private LocalDate hireDate;
    
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Attendance> attendances;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Salary> salaries;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Reward> rewards;
    
    private boolean isAdmin;
}
