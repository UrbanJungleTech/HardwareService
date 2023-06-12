package frentz.daniel.hardwareservice.repository;

import frentz.daniel.hardwareservice.entity.HardwareEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HardwareRepository extends JpaRepository<HardwareEntity, Long> {
    @Query(value="SELECT hardware FROM HardwareEntity hardware WHERE hardware.port = :port and hardware.hardwareController.serialNumber = :serialNumber")
    HardwareEntity findHardwareBySerialNumberAndPort(@Param("serialNumber") String serialNumber, @Param("port") Long port);
    @Query(value="SELECT hardware FROM HardwareEntity hardware WHERE hardware.port = :port and hardware.hardwareController.id = :hardwareControllerId")
    HardwareEntity findHardwareByHardwareIdAndPort(@Param("hardwareControllerId") long hardwareControllerId, @Param("port") Long port);
}
