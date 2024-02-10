package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;

import java.util.Optional;

public interface HardwareRepository extends JpaRepository<HardwareEntity, Long> {
    @Query(value="SELECT hardware FROM HardwareEntity hardware "
            + "JOIN hardware.hardwareController "
            + "WHERE hardware.port = :port "
            + "AND hardware.hardwareController.serialNumber = :serialNumber")
    Optional<HardwareEntity> findHardwareBySerialNumberAndPort(@Param("serialNumber") String serialNumber, @Param("port") String port);

    @Query(value="SELECT hardware FROM HardwareEntity hardware WHERE hardware.port = :port and hardware.hardwareController.id = :hardwareControllerId")
    Optional<HardwareEntity> findHardwareByHardwareIdAndPort(@Param("hardwareControllerId") long hardwareControllerId, @Param("port") Long port);
    @Query(value="SELECT hardware FROM HardwareEntity hardware WHERE hardware.desiredState.id = :hardwareStateId or hardware.currentState.id = :hardwareStateId")
    Optional<HardwareEntity> findHardwareByHardwareStateId(long hardwareStateId);
}
