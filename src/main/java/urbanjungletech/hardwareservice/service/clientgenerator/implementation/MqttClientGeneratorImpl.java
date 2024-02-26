package urbanjungletech.hardwareservice.service.clientgenerator.implementation;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.exception.exception.MqttClientConnectionException;
import urbanjungletech.hardwareservice.exception.exception.MqttClientGenerationException;
import urbanjungletech.hardwareservice.model.connectiondetails.MqttConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.UsernamePasswordCredentials;
import urbanjungletech.hardwareservice.service.clientgenerator.MqttClientGenerator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Note: This implementation does not account for multiple instances of the application.
 * Future enhancements should consider this scenario, possibly through a wrapper class or
 * a delegated service.
 */
@Service
public class MqttClientGeneratorImpl implements MqttClientGenerator {
    private final Map<String, MqttClient> clientCache = new ConcurrentHashMap<>();

    @Override
    public MqttClient generateClient(MqttConnectionDetails connectionDetails) {
        try {
            MqttClient client = null;
            // Check to see if the client is already in the cache otherwise create a new one and cache it.
            String clientId = connectionDetails.getClientId();
            if (clientCache.containsKey(clientId)) {
                client = clientCache.get(clientId);
            }
            else {
                client = new MqttClient(connectionDetails.getBroker(), clientId);
                clientCache.put(clientId, client);
            }
            // Either way check to see if the client is connected and if not connect before returning.
            if(!client.isConnected()) {
                MqttConnectOptions options = new MqttConnectOptions();
                UsernamePasswordCredentials credentials = (UsernamePasswordCredentials) connectionDetails.getCredentials();
                options.setUserName(credentials.getUsername());
                options.setPassword(credentials.getPassword().toCharArray());
                client.connect(options);
            }
            return client;
        } catch(MqttException ex) {
            throw new MqttClientConnectionException(ex);
        } catch(Exception e) {
            throw new MqttClientGenerationException(e);
        }
    }
}
