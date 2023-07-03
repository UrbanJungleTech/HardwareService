package frentz.daniel.hardwareservice.repository;

import frentz.daniel.hardwareservice.entity.HardwareStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HardwareStateRepository extends JpaRepository<HardwareStateEntity, Long> {
}
