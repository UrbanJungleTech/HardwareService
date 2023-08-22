package urbanjungletech.hardwareservice.action.repository;

import urbanjungletech.hardwareservice.action.entity.ActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<ActionEntity, Long> {
}
