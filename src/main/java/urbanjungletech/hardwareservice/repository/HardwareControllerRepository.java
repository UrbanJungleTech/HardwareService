package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareControllerEntity;

import java.util.Optional;

public interface HardwareControllerRepository extends JpaRepository<HardwareControllerEntity, Long> {// Query to find by serial number
    HardwareControllerEntity findBySerialNumber(@Param("serialNumber") String serialNumber);

    // Query to find the ID by serial number
    @Query("SELECT h.id FROM HardwareControllerEntity h " +
            "WHERE h.serialNumber = :serialNumber")
    long findIdBySerialNumber(@Param("serialNumber") String serialNumber);

    // Query to check if an entity exists by serial number
    boolean existsBySerialNumber(@Param("serialNumber") String serialNumber);

    @Query("SELECT 'serialNumber' as s FROM HardwareControllerEntity h " +
            "WHERE h.id = :hardwareControllerId")
    String getSerialNumberById(@Param("hardwareControllerId") long hardwareControllerId);

    @Query("SELECT 'type' as t FROM HardwareControllerEntity h " +
            "WHERE h.serialNumber = :serialNumber")
    Optional<String> getControllerType(@Param("serialNumber") String serialNumber);

}
