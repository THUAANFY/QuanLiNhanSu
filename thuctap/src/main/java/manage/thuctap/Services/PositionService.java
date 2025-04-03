package manage.thuctap.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import manage.thuctap.DAO.PositionDAO;
import manage.thuctap.Models.Position;

@Service
public class PositionService {
    @Autowired
    private PositionDAO positionDAO;

    public List<Position> getAllPositions() {
        return positionDAO.findAll();
    }
    
    public Optional<Position> getPositionById(Long id) {
        return positionDAO.findById(id);
    }
    
    public Position savePosition(Position position) {
        return positionDAO.save(position);
    }
    
    public void deletePosition(Long id) {
        positionDAO.deleteById(id);
    }
}
