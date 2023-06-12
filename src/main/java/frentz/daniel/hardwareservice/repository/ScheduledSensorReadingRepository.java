package frentz.daniel.hardwareservice.repository;

import frentz.daniel.hardwareservice.entity.ScheduledSensorReadingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledSensorReadingRepository extends JpaRepository<ScheduledSensorReadingEntity, Long> {
}
