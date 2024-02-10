package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import urbanjungletech.hardwareservice.entity.connectiondetails.ConnectionDetailsEntity;

@Repository
public interface ConnectionDetailsRepository extends JpaRepository<ConnectionDetailsEntity, Long>{
}
