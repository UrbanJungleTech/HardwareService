package frentz.daniel.hardwareservice.repository;

import frentz.daniel.hardwareservice.entity.RegulatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegulatorRepository extends JpaRepository<RegulatorEntity, Long> {
}
