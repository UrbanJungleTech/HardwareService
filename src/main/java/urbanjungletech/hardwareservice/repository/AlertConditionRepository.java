package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;

@Repository
public interface AlertConditionRepository extends JpaRepository<AlertConditionEntity, Long> {
}
