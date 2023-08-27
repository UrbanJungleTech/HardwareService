package urbanjungletech.hardwareservice.addition;

import urbanjungletech.hardwareservice.addition.implementation.HardwareAdditionServiceImpl;
import urbanjungletech.hardwareservice.builder.HardwareStateBuilder;
import urbanjungletech.hardwareservice.converter.HardwareConverter;
import urbanjungletech.hardwareservice.dao.HardwareDAO;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.ControllerCommunicationService;
import urbanjungletech.hardwareservice.service.ObjectLoggerService;
import urbanjungletech.hardwareservice.model.Hardware;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HardwareAdditionServiceImplTest {

    @Mock
    HardwareDAO hardwareDAO;

    @Mock
    private TimerAdditionService timerAdditionService;

    @Mock
    private ControllerCommunicationService controllerCommunicationService;

    @Mock
    private HardwareStateBuilder hardwareStateBuilder;

    @Mock
    private HardwareConverter hardwareConverter;

    @Mock
    private ObjectLoggerService objectLoggerService;

    @InjectMocks
    private HardwareAdditionServiceImpl hardwareAdditionService;

    @Captor
    ArgumentCaptor<Hardware> hardwareArgumentCaptor;

    //test that create will always call the DAO to ensure that the hardware is saved and that the queue service is called
    //to register the hardware.
//    @Test
//    public void testCreate(){
//        Hardware hardware = new Hardware();
//        Hardware hardwareEntity = new Hardware();
//        Hardware result = hardwareAdditionService.create(hardware);
//        verify(this.hardwareDAO, times(1)).createHardware(hardware);
//        verify(this.hardwareQueueService, times(1)).registerHardware(hardwareEntity);
//    }

//    @Test
//    public void testIfDesiredStateNullThenGetsSetToOn(){
//        Hardware hardware = new Hardware();
//        HardwareEntity hardwareEntity = new HardwareEntity();
//        when(hardwareDAO.createHardware(any())).thenReturn(hardwareEntity);
//        HardwareState offState = new HardwareState();
//        when(hardwareStateBuilder.getOffHardwareState()).thenReturn(offState);
//        hardwareAdditionService.create(hardware);
//        verify(hardwareDAO, times(1)).createHardware(hardwareArgumentCaptor.capture());
//        Hardware result = hardwareArgumentCaptor.getValue();
//        assertSame(offState, hardware.getDesiredState());
//    }
//
//    @Test
//    public void testIfDesiredStateNotNullThenDoesNotGetSet(){
//        Hardware hardware = new Hardware();
//        HardwareState actualState = new HardwareState();
//        hardware.setDesiredState(actualState);
//        HardwareEntity hardwareEntity = new HardwareEntity();
//        when(hardwareDAO.createHardware(any())).thenReturn(hardwareEntity);
//        hardwareAdditionService.create(hardware);
//        verify(hardwareDAO, times(1)).createHardware(hardwareArgumentCaptor.capture());
//        assertSame(actualState, hardware.getDesiredState());
//    }
//
//    @Test
//    public void testWhenTimersAreNullThenCreateTimerIsNotCalled(){
//        Hardware hardware = new Hardware();
//        this.hardwareAdditionService.create(hardware);
//        verify(timerAdditionService, times(0)).create(any());
//    }
//
//    @Test
//    public void testWhenTimersAreEmptyThenCreateTimerIsNotCalled(){
//        Hardware hardware = new Hardware();
//        List<Timer> timers = new ArrayList<>();
//        hardware.setTimers(timers);
//        this.hardwareAdditionService.create(hardware);
//        verify(timerAdditionService, times(0)).create(any());
//    }
//
//    @Test
//    public void testWhenTimersAreNotEmptyThenCreateTimerIsCalledForEachTimer(){
//        Hardware hardware = new Hardware();
//        List<Timer> timers = new ArrayList<>();
//        Timer timer1 = new Timer();
//        timers.add(timer1);
//        Timer timer2 = new Timer();
//        timers.add(timer2);
//        hardware.setTimers(timers);
//        HardwareEntity hardwareEntity = new HardwareEntity();
//        hardwareEntity.setId(1l);
//        when(hardwareDAO.createHardware(any())).thenReturn(hardwareEntity);
//        this.hardwareAdditionService.create(hardware);
//        int timesToCallCreateTimers = timers.size();
//        verify(timerAdditionService, times(timesToCallCreateTimers)).create(any());
//    }

//    @Test
//    public void testDelete(){
//        long hardwareId = 1;
//        HardwareEntity hardwareEntity = new HardwareEntity();
//        hardwareEntity.setId(hardwareId);
//        when(hardwareDAO.getHardware(anyLong())).thenReturn(hardwareEntity);
//        this.hardwareAdditionService.delete(hardwareId);
//        verify(hardwareDAO, times(1)).delete(hardwareId);
//        verify(hardwareQueueService, times(1)).deregisterHardware(hardwareEntity);
//    }
}
