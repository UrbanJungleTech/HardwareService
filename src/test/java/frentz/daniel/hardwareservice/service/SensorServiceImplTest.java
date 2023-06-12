package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.converter.ScheduledSensorReadingConverter;
import frentz.daniel.hardwareservice.converter.SensorConverter;
import frentz.daniel.hardwareservice.dao.HardwareControllerDAO;
import frentz.daniel.hardwareservice.dao.ScheduledSensorReadingDAO;
import frentz.daniel.hardwareservice.dao.SensorDAO;
import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.entity.SensorEntity;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import frentz.daniel.hardwareservice.client.model.Sensor;
import frentz.daniel.hardwareservice.client.model.SensorType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SensorServiceImplTest {

    @Mock
    HardwareControllerRepository hardwareControllerRepository;
    @Mock
    HardwareQueueService hardwareQueueService;
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
    SensorServiceImpl sensorService;

    @Test
    void readSensor() {

    }

    private List<SensorEntity> getTestSensorListWithPorts(long[] ports){
        List<SensorEntity> result = Arrays.stream(ports).mapToObj((port) -> {
            SensorEntity sensor = new SensorEntity();
            sensor.setPort(port);
            return sensor;
        }).collect(Collectors.toList());
        return result;
    }

    @Test
    void readAverageSensor() {
        long[] sensorPorts = new long[]{1,2};
        List<SensorEntity> sensors = this.getTestSensorListWithPorts(sensorPorts);
        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();
        when(sensorDAO.findByHardwareControllerIdAndSensorType(anyLong(), any())).thenReturn(sensors);
        when(hardwareControllerDAO.getHardwareController(anyLong())).thenReturn(hardwareControllerEntity);
        when(hardwareQueueService.getAverageSensorReading(any(), eq(sensorPorts))).thenReturn(2.0);
        double result = this.sensorService.readAverageSensor(1, SensorType.TEMPERATURE);
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
