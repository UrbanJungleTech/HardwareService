package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import urbanjungletech.hardwareservice.entity.alert.condition.SensorReadingAlertConditionEntity;

import java.util.List;

@Repository
public interface SensorReadingAlertConditionRepository extends JpaRepository<SensorReadingAlertConditionEntity, Long> {
    List<SensorReadingAlertConditionEntity> findBySensorId(Long sensorId);
}
