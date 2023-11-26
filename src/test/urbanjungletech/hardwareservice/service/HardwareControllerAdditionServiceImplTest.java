package urbanjungletech.hardwareservice.service;

import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.addition.HardwareControllerAdditionService;
import urbanjungletech.hardwareservice.addition.SensorAdditionService;
import urbanjungletech.hardwareservice.dao.HardwareControllerDAO;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HardwareControllerAdditionServiceImplTest {


    private HardwareControllerAdditionService hardwareControllerAdditionService;

    @Mock
    private HardwareControllerDAO hardwareControllerDAO;
    @Mock
    private HardwareAdditionService hardwareAdditionService;
    @Mock
    private SensorAdditionService sensorAdditionService;
//
//    @BeforeEach
//    public void setup(){
//        this.hardwareControllerAdditionService = new HardwareControllerAdditionServiceImpl(hardwareControllerDAO, hardwareAdditionService, sensorAdditionService, hardwareControllerSubscriptionService);
//    }
//
//    @Test
//    public void testAddHardwareControllerCallsCreateHardwareController(){
//        HardwareController originalHardwareController = new HardwareController();
//        HardwareController savedHardwareController = new HardwareController();
//        savedHardwareController.setId(1L);
//        when(hardwareControllerDAO.createHardwareController(any())).thenReturn(savedHardwareController);
//        hardwareControllerAdditionService.addHardwareController(originalHardwareController);
//        verify(hardwareControllerDAO, times(1)).createHardwareController(any());
//    }
//
//    @Test
//    public void testAddHardwareControllerCallsAddSensor(){
//        HardwareController originalHardwareController = new HardwareController();
//        HardwareController savedHardwareController = new HardwareController();
//        savedHardwareController.setId(1L);
//        Sensor sensor1 = new Sensor();
//        Sensor sensor2 = new Sensor();
//        originalHardwareController.setSensors(List.of(sensor1, sensor2));
//        when(hardwareControllerDAO.createHardwareController(any())).thenReturn(savedHardwareController);
//        hardwareControllerAdditionService.addHardwareController(originalHardwareController);
//        verify(sensorAdditionService, times(1)).addSensor(sensor1);
//        verify(sensorAdditionService, times(1)).addSensor(sensor2);
//        verify(sensorAdditionService, times(2)).addSensor(any());
//    }
//
//
//    @Test
//    public void testAddHardwareControllerCallsAddHardware(){
//        HardwareController originalHardwareController = new HardwareController();
//        HardwareController savedHardwareController = new HardwareController();
//        savedHardwareController.setId(1L);
//        Hardware hardware1 = new Hardware();
//        Hardware hardware2 = new Hardware();
//        originalHardwareController.setHardware(List.of(hardware1, hardware2));
//        when(hardwareControllerDAO.createHardwareController(any())).thenReturn(savedHardwareController);
//        hardwareControllerAdditionService.addHardwareController(originalHardwareController);
//        verify(hardwareAdditionService, times(1)).addHardware(hardware1);
//        verify(hardwareAdditionService, times(1)).addHardware(hardware2);
//        verify(hardwareAdditionService, times(2)).addHardware(any());
//    }
//
//    @Test
//    public void testAddHardwareControllerCallsNotify(){
//        HardwareController originalHardwareController = new HardwareController();
//        HardwareController savedHardwareController = new HardwareController();
//        savedHardwareController.setId(1L);
//        when(hardwareControllerDAO.createHardwareController(any())).thenReturn(savedHardwareController);
//        hardwareControllerAdditionService.addHardwareController(originalHardwareController);
//        verify(hardwareControllerSubscriptionService, times(1)).notifyHardwareControllerAdded(savedHardwareController);
//    }
}
