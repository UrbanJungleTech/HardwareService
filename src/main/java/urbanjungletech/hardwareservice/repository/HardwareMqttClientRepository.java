package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareMqttClientEntity;

@Repository
public interface HardwareMqttClientRepository extends JpaRepository<HardwareMqttClientEntity, Long> {
}
