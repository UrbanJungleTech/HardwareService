package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import urbanjungletech.hardwareservice.entity.alert.AlertConditionsEntity;

@Repository
public interface AlertConditionsRepository extends JpaRepository<AlertConditionsEntity, Long> {
}
