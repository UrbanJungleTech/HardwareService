package urbanjungletech.hardwareservice.repository;

import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledSensorReadingRepository extends JpaRepository<ScheduledSensorReadingEntity, Long> {
}
