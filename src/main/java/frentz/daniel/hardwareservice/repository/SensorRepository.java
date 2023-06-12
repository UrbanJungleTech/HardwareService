package frentz.daniel.hardwareservice.repository;

import frentz.daniel.hardwareservice.entity.SensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<SensorEntity, Long> {
    List<SensorEntity> findByHardwareControllerIdAndSensorType(long hardwareControllerId, String sensorType);
}
