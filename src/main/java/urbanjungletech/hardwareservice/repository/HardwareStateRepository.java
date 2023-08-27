package urbanjungletech.hardwareservice.repository;

import urbanjungletech.hardwareservice.entity.HardwareStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HardwareStateRepository extends JpaRepository<HardwareStateEntity, Long> {
}
