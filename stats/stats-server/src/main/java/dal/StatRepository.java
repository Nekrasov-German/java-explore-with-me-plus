package dal;

import entity.StatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatRepository extends JpaRepository<StatEntity, Long> {
}
