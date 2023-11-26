package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urbanjungletech.hardwareservice.entity.alert.AlertEntity;

public interface AlertRepository extends JpaRepository<AlertEntity, Long> {
}
