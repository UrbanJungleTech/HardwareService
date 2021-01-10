package frentz.daniel.controller.repository;

import frentz.daniel.controller.entity.TimerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<TimerEntity, Long> {
}
