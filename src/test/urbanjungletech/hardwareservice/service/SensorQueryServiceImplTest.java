package urbanjungletech.hardwareservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.converter.ScheduledSensorReadingConverter;
import urbanjungletech.hardwareservice.converter.sensor.SensorConverter;
import urbanjungletech.hardwareservice.dao.HardwareControllerDAO;
import urbanjungletech.hardwareservice.dao.ScheduledSensorReadingDAO;
import urbanjungletech.hardwareservice.dao.SensorDAO;
import urbanjungletech.hardwareservice.entity.sensor.SensorEntity;
import urbanjungletech.hardwareservice.helpers.mock.sensor.MockSensor;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.ControllerCommunicationService;
import urbanjungletech.hardwareservice.service.query.implementation.SensorQueryServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SensorQueryServiceImplTest {

    @Mock
    HardwareControllerRepository hardwareControllerRepository;
    @Mock
    ControllerCommunicationService controllerCommunicationService;
    @Mock
    HardwareControllerDAO hardwareControllerDAO;
    @Mock
    ScheduledSensorReadingDAO scheduledSensorReadingDAO;
    @Mock
    SensorDAO sensorDAO;
    @Mock
    ScheduledSensorReadingConverter scheduledSensorReadingConverter;
    @Mock
    SensorConverter sensorConverter;

    @InjectMocks
    SensorQueryServiceImpl sensorService;

    @Test
    void readSensor() {

    }

    private List<SensorEntity> getTestSensorListWithPorts(String[] ports){
        List<SensorEntity> result = Arrays.stream(ports).map((port) -> {
            SensorEntity sensor = new SensorEntity();
            sensor.setPort(port);
            return sensor;
        }).collect(Collectors.toList());
        return result;
    }

    @Test
    void createScheduledReading() {
    }

    @Test
    void getSensor() {
        long sensorId = 1234;
        Sensor expectedResult = new MockSensor();
        SensorEntity sensorEntity = new SensorEntity();
        when(this.sensorDAO.getSensor(sensorId)).thenReturn(sensorEntity);
        when(this.sensorConverter.toModel(sensorEntity)).thenReturn(expectedResult);
        Sensor result = this.sensorService.getSensor(1234);
        assertSame(expectedResult, result);
    }

    @Test
    void testGetSensor() {
    }
}
