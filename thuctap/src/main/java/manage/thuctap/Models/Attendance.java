package manage.thuctap.Models;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendances")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate date;
    private LocalTime checkIn;
    private LocalTime checkOut;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "work_schedule_id")
    private WorkSchedule workSchedule;
    
    private String status; // PRESENT, ABSENT, LATE, etc.
}
