package frentz.daniel.hardwareservice.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HardwareAdditionServiceImplTest {

//    private HardwareAdditionServiceImpl hardwareAdditionService;
//
//    @Mock
//    private HardwareQueueService hardwareQueueService;
//    @Mock
//    private TimerAdditionService timerAdditionService;
//    @Mock
//    private HardwareStateBuilder hardwareStateBuilder;
//    @Mock
//    private HardwareDAO hardwareDAO;
//    @Mock
//    private HardwareControllerDAO hardwareControllerDAO;
//
//    @BeforeEach
//    public void before(){
//        this.hardwareAdditionService = new HardwareAdditionServiceImpl(hardwareControllerDAO, hardwareDAO, timerAdditionService, hardwareQueueService, hardwareStateBuilder);
//    }
//
//    @Test
//    public void testAddHardwareCallsTimerAdditionService(){
//        Hardware hardware = new Hardware();
//        Hardware savedHardware = new Hardware();
//        savedHardware.setId(1L);
//        hardware.setHardwareControllerId(1L);
//        Timer timer1 = new Timer();
//        Timer timer2 = new Timer();
//        hardware.getTimers().add(timer1);
//        hardware.getTimers().add(timer2);
//        when(hardwareControllerDAO.getHardwareControllerSerialNumber((anyLong()))).thenReturn("");
//        when(hardwareDAO.createHardware(hardware)).thenReturn(savedHardware);
//        hardwareAdditionService.addHardware(hardware);
//        verify(timerAdditionService, times(2)).addTimer(any());
//        verify(timerAdditionService, times(1)).addTimer(timer1);
//        verify(timerAdditionService, times(1)).addTimer(timer2);
//    }
//
//    @Test
//    public void testAddHardwareCallsQueueServiceSendState(){
//        Hardware hardware = new Hardware();
//        Hardware savedHardware = new Hardware();
//        savedHardware.setId(1L);
//        hardware.setHardwareControllerId(1L);
//        String hardwareControllerSerialNumber = "1234";
//        when(hardwareControllerDAO.getHardwareControllerSerialNumber((anyLong()))).thenReturn(hardwareControllerSerialNumber);
//        when(hardwareDAO.createHardware(hardware)).thenReturn(savedHardware);
//        hardwareAdditionService.addHardware(hardware);
//        verify(hardwareQueueService, times(1)).sendStateToController(hardwareControllerSerialNumber, savedHardware);
//    }
}
