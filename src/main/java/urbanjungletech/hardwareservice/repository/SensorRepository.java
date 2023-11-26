package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urbanjungletech.hardwareservice.entity.SensorEntity;

import java.util.List;

public interface SensorRepository extends JpaRepository<SensorEntity, Long> {
    List<SensorEntity> findByHardwareControllerIdAndSensorType(long hardwareControllerId, String sensorType);
}
