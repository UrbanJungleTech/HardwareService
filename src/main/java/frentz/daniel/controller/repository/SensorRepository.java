package frentz.daniel.controller.repository;

import frentz.daniel.controller.entity.SensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<SensorEntity, Long> {
}
