package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import urbanjungletech.hardwareservice.entity.SensorEntity;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<SensorEntity, Long> {
    List<SensorEntity> findByHardwareControllerIdAndSensorType(long hardwareControllerId, String sensorType);

    @Query("SELECT s FROM SensorEntity s WHERE s.hardwareController.serialNumber = :serialNumber AND s.port = :port")
    Optional<SensorEntity> findByHardwareControllerSerialNumberAndPort(String serialNumber, String port);
}
