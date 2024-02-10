package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.SensorReadingRouterEntity;

import java.util.List;

@Repository
public interface SensorReadingRouterRepository extends JpaRepository<SensorReadingRouterEntity, Long> {

}
