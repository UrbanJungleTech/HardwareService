package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.converter.implementation.HardwareConverterImpl;
import urbanjungletech.hardwareservice.entity.HardwareControllerEntity;
import urbanjungletech.hardwareservice.entity.HardwareEntity;
import urbanjungletech.hardwareservice.entity.HardwareStateEntity;
import urbanjungletech.hardwareservice.entity.TimerEntity;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HardwareConverterImplTest {

    @Mock
    HardwareStateConverter hardwareStateConverter;
    @Mock
    TimerConverter timerConverter;
    @InjectMocks
    HardwareConverterImpl hardwareConverter;

    @Test
    public void toModel() {
        String testPort = "1";
        String hardwareType = "HEATER";
        long hardwareId = 2L;
        String hardwareName = "name";
        long hardwareControllerId = 3L;
        //test entities
        HardwareEntity hardwareEntity = new HardwareEntity();
        hardwareEntity.setHardwareCategory(hardwareType);
        hardwareEntity.setPort(testPort);
        hardwareEntity.setId(hardwareId);
        hardwareEntity.setName(hardwareName);
        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();
        hardwareControllerEntity.setId(hardwareControllerId);
        hardwareEntity.setHardwareController(hardwareControllerEntity);
        //perform test
        Hardware result = hardwareConverter.toModel(hardwareEntity);
        assertEquals(hardwareId, result.getId());
        assertEquals(testPort, result.getPort());
        assertEquals(hardwareName, result.getName());
        assertEquals(hardwareType, result.getType());
    }

    @Test
    public void toModelTimerConverterIsCalled(){
        HardwareEntity hardwareEntity = new HardwareEntity();
        //hardware controller is only needed to avoid null pointer exception, not used in test.
        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();
        hardwareEntity.setHardwareController(hardwareControllerEntity);
        //setup the timers that should be added to the result.
        TimerEntity timerEntity1 = new TimerEntity();
        hardwareEntity.getTimers().add(timerEntity1);
        TimerEntity timerEntity2 = new TimerEntity();
        hardwareEntity.getTimers().add(timerEntity2);
        //setup timer converter mock to return the correct timer objects
        Timer timer1 = new Timer();
        Timer timer2 = new Timer();
        when(timerConverter.toModels(List.of(timerEntity1, timerEntity2))).thenReturn(List.of(timer1, timer2));
        //ensure that 2 timers were added to the hardware, and that both are different. order doesn't matter.
        Hardware result = this.hardwareConverter.toModel(hardwareEntity);
        assertEquals(2, result.getTimers().size());
        assertSame(timer1, result.getTimers().stream().filter((timer) -> {return timer.equals(timer1);}).findFirst().get());
        assertSame(timer2, result.getTimers().stream().filter((timer) -> {return timer.equals(timer2);}).findFirst().get());
    }

    @Test
    public void toModelHardwareStateConverter(){
        HardwareStateEntity currentStateEntity = new HardwareStateEntity();
        HardwareStateEntity desiredStateEntity = new HardwareStateEntity();
        HardwareEntity hardwareEntity = new HardwareEntity();
        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();
        hardwareEntity.setHardwareController(hardwareControllerEntity);
        hardwareEntity.setDesiredState(desiredStateEntity);
        hardwareEntity.setCurrentState(currentStateEntity);
        HardwareState currentState = new HardwareState();
        HardwareState desiredState = new HardwareState();
        when(this.hardwareStateConverter.toModel(desiredStateEntity)).thenReturn(desiredState);
        when(this.hardwareStateConverter.toModel(currentStateEntity)).thenReturn(currentState);
        Hardware result = this.hardwareConverter.toModel(hardwareEntity);
        assertSame(desiredState, result.getDesiredState());
        assertSame(currentState, result.getCurrentState());
    }

    @Test
    public void toModels() {
    }

    @Test
    public void fillEntity() {
    }
}
