package urbanjungletech.hardwareservice.repository;

import urbanjungletech.hardwareservice.entity.SensorReadingAlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorReadingAlertRepository extends JpaRepository<SensorReadingAlertEntity, Long> {
}
