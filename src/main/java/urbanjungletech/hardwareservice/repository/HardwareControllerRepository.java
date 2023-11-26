package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import urbanjungletech.hardwareservice.entity.HardwareControllerEntity;

import java.util.Optional;

public interface HardwareControllerRepository extends JpaRepository<HardwareControllerEntity, Long> {// Query to find by serial number
    @Query("SELECT h FROM HardwareControllerEntity h " +
            "JOIN h.configuration config " +
            "WHERE KEY(config) = 'serialNumber' " +
            "AND VALUE(config) = :serialNumber")
    HardwareControllerEntity findBySerialNumber(@Param("serialNumber") String serialNumber);

    // Query to find the ID by serial number
    @Query("SELECT h.id FROM HardwareControllerEntity h " +
            "JOIN h.configuration config " +
            "WHERE KEY(config) = 'serialNumber' " +
            "AND VALUE(config) = :serialNumber")
    long findIdBySerialNumber(@Param("serialNumber") String serialNumber);

    // Query to check if an entity exists by serial number
    @Query("SELECT COUNT(h) > 0 FROM HardwareControllerEntity h " +
            "JOIN h.configuration config " +
            "WHERE KEY(config) = 'serialNumber' " +
            "AND VALUE(config) = :serialNumber")
    boolean existsBySerialNumber(@Param("serialNumber") String serialNumber);

    @Query("SELECT VALUE(config) FROM HardwareControllerEntity h " +
            "JOIN h.configuration config " +
            "WHERE h.id = :hardwareControllerId " +
            "AND KEY(config) = 'serialNumber'")
    String getSerialNumberById(@Param("hardwareControllerId") long hardwareControllerId);

    @Query("SELECT VALUE(config) FROM HardwareControllerEntity h " +
            "JOIN h.configuration config " +
            "WHERE VALUE(config) = :serialNumber " +
            "AND KEY(config) = 'type'")
    Optional<String> getControllerType(@Param("serialNumber") String serialNumber);

}
