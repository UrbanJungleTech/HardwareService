package urbanjungletech.hardwareservice.repository;

import urbanjungletech.hardwareservice.entity.ScheduledHardwareEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HardwareCronJobRepository extends JpaRepository<ScheduledHardwareEntity, Long> {
}