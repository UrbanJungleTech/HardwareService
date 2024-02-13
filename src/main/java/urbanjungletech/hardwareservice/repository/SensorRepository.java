package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import urbanjungletech.hardwareservice.entity.sensor.SensorEntity;

import java.util.Optional;

public interface SensorRepository extends JpaRepository<SensorEntity, Long> {

    @Query("SELECT s FROM SensorEntity s WHERE s.hardwareController.serialNumber = :serialNumber AND s.port = :port")
    Optional<SensorEntity> findByHardwareControllerSerialNumberAndPort(String serialNumber, String port);
}
