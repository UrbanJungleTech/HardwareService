package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urbanjungletech.hardwareservice.entity.HardwareStateEntity;

public interface HardwareStateRepository extends JpaRepository<HardwareStateEntity, Long> {
}
