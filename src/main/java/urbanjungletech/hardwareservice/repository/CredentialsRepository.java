package urbanjungletech.hardwareservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;

@Repository
public interface CredentialsRepository extends CrudRepository<CredentialsEntity, Long> {
}
