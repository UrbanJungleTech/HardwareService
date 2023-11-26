package urbanjungletech.hardwareservice.repository;

import org.springframework.data.repository.CrudRepository;
import urbanjungletech.hardwareservice.entity.SensorReadingEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorReadingRepository extends CrudRepository<SensorReadingEntity, Long> {
    List<SensorReadingEntity> findByReadingTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
