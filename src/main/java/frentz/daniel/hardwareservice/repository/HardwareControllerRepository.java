package frentz.daniel.hardwareservice.repository;

import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HardwareControllerRepository extends JpaRepository<HardwareControllerEntity, Long> {
    HardwareControllerEntity findBySerialNumber(String serialNumber);
    @Query("Select h.id from HardwareControllerEntity h where :serialNumber = :serialNumber")
    long findIdBySerialNumber(@Param("serialNumber") String serialNumber);
    boolean existsBySerialNumber(String serialNumber);
    @Query("Select h.serialNumber from HardwareControllerEntity h where :hardwareControllerId = h.id")
    String getSerialNumberById(long hardwareControllerId);
}
