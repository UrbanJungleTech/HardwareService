package urbanjungletech.hardwareservice.config.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConditionalOnProperty(value = "mqtt-rpc.enabled", havingValue = "true")
public class SystemMqttInterfaceConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    private IMqttClient defaultSystemMqttClient;
    private Map<SystemMqttClientProperties, IMqttClient> serverMqttClients;
    private RpcProperties rpcProperties;

    public SystemMqttInterfaceConfiguration(IMqttClient defaultSystemMqttClient,
                                            Map<SystemMqttClientProperties, IMqttClient> serverMqttClients,
                                            RpcProperties rpcProperties) {
        this.defaultSystemMqttClient = defaultSystemMqttClient;
        this.serverMqttClients = serverMqttClients;
        this.rpcProperties = rpcProperties;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String serverUri = rpcProperties.getUri();
        String serverQueue = rpcProperties.getQueue();
        SystemMqttClientProperties properties = new SystemMqttClientProperties(serverUri, serverQueue);
        this.serverMqttClients.put(properties, this.defaultSystemMqttClient);
    }
}
