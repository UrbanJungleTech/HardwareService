package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urbanjungletech.hardwareservice.entity.ScheduledHardwareEntity;

public interface HardwareCronJobRepository extends JpaRepository<ScheduledHardwareEntity, Long> {
}
