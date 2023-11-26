package urbanjungletech.hardwareservice.weather;

import urbanjungletech.hardwareservice.services.http.SensorTestService;
import urbanjungletech.hardwareservice.model.Sensor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class WeatherSensorIT {

    private SensorTestService sensorTestService;
    @Test
    public void readTemperatureTest(){
        Sensor sensor = new Sensor();
    }
}
