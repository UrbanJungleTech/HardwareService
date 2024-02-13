package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.SensorReadingRouterEntity;

@Repository
public interface SensorReadingRouterRepository extends JpaRepository<SensorReadingRouterEntity, Long> {

}
