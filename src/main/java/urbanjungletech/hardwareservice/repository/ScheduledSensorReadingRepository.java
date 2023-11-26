package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;

public interface ScheduledSensorReadingRepository extends JpaRepository<ScheduledSensorReadingEntity, Long> {
}
