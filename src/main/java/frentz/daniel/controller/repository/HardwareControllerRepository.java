package frentz.daniel.controller.repository;

import frentz.daniel.controller.entity.HardwareControllerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HardwareControllerRepository extends JpaRepository<HardwareControllerEntity, Long> {
    HardwareControllerEntity findBySerialNumber(String serialNumber);
}
