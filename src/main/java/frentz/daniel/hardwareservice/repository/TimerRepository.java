package frentz.daniel.hardwareservice.repository;

import frentz.daniel.hardwareservice.entity.TimerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<TimerEntity, Long> {
}
