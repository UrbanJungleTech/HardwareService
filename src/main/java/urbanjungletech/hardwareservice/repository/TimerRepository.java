package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urbanjungletech.hardwareservice.entity.TimerEntity;

public interface TimerRepository extends JpaRepository<TimerEntity, Long> {
}
