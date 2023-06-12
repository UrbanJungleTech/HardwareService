package frentz.daniel.hardwareservice.repository;

import frentz.daniel.hardwareservice.entity.SensorReadingEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorReadingRepository extends CrudRepository<SensorReadingEntity, Long> {
    List<SensorReadingEntity> findByReadingTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
