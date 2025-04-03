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
@Table(name = "rewards")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String type; // REWARD or DISCIPLINE
    private String description;
    private double amount; // Positive for rewards, negative for discipline
    private LocalDate date;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
