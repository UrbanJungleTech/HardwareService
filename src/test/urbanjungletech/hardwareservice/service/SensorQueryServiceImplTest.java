package urbanjungletech.hardwareservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.converter.ScheduledSensorReadingConverter;
import urbanjungletech.hardwareservice.converter.SensorConverter;
import urbanjungletech.hardwareservice.dao.HardwareControllerDAO;
import urbanjungletech.hardwareservice.dao.ScheduledSensorReadingDAO;
import urbanjungletech.hardwareservice.dao.SensorDAO;
import urbanjungletech.hardwareservice.entity.SensorEntity;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.ControllerCommunicationService;
import urbanjungletech.hardwareservice.service.query.implementation.SensorQueryServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.*;
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
    void readAverageSensor() {
        String[] sensorPorts = new String[]{"1","2"};
        List<SensorEntity> sensors = this.getTestSensorListWithPorts(sensorPorts);
        when(sensorDAO.findByHardwareControllerIdAndSensorType(anyLong(), any())).thenReturn(sensors);
        when(controllerCommunicationService.getAverageSensorReading(eq(sensorPorts))).thenReturn(2.0);
        double result = this.sensorService.readAverageSensor(1, "temperature");
        assertEquals(2.0, result);
    }

    @Test
    void createScheduledReading() {
    }

    @Test
    void getSensor() {
        long sensorId = 1234;
        Sensor expectedResult = new Sensor();
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
