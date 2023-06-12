package frentz.daniel.hardwareservice.repository;

import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HardwareCronJobRepository extends JpaRepository<ScheduledHardwareEntity, Long> {
}
