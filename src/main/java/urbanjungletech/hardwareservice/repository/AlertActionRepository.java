package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import urbanjungletech.hardwareservice.entity.alert.action.AlertActionEntity;


@Repository
public interface AlertActionRepository extends JpaRepository<AlertActionEntity, Long> {
}
