package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urbanjungletech.hardwareservice.entity.alert.condition.HardwareStateChangeAlertConditionEntity;

import java.util.List;

public interface HardwareStateChangeAlertConditionRepository extends JpaRepository<HardwareStateChangeAlertConditionEntity, Long> {
    List<HardwareStateChangeAlertConditionEntity> findByHardwareStateId(long hardwareStateId);
}
