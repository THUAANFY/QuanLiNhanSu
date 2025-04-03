package manage.thuctap.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import manage.thuctap.DAO.RewardDAO;
import manage.thuctap.Models.Employee;
import manage.thuctap.Models.Reward;

@Service
public class RewardService {
    @Autowired
    private RewardDAO rewardDAO;

    public List<Reward> getAllRewards() {
        return rewardDAO.findAll();
    }
    
    public Optional<Reward> getRewardById(Long id) {
        return rewardDAO.findById(id);
    }
    
    public List<Reward> getRewardsByEmployee(Employee employee) {
        return rewardDAO.findByEmployee(employee);
    }
    
    public List<Reward> getRewardsByEmployeeAndDateRange(Employee employee, LocalDate startDate, LocalDate endDate) {
        return rewardDAO.findByEmployeeAndDateBetween(employee, startDate, endDate);
    }
    
    public Reward saveReward(Reward reward) {
        return rewardDAO.save(reward);
    }
    
    public void deleteReward(Long id) {
        rewardDAO.deleteById(id);
    }
}
