package urbanjungletech.hardwareservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import urbanjungletech.hardwareservice.entity.hardware.mqtt.MqttPinEntity;

@Repository
public interface MqttPinRepository extends JpaRepository<MqttPinEntity, Long>{
}
