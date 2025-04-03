package manage.thuctap.Models;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salaries")
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate month; // First day of the month
    private double amount;
    private double bonus;
    private double deduction;
    private double totalAmount;
    private boolean isPaid;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
