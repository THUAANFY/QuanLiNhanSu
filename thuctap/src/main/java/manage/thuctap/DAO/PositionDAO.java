package manage.thuctap.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import manage.thuctap.Models.Position;

@Repository
public interface PositionDAO extends JpaRepository<Position, Long>{

}
