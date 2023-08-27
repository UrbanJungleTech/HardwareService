package urbanjungletech.hardwareservice.repository;

import urbanjungletech.hardwareservice.entity.TimerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<TimerEntity, Long> {
}
