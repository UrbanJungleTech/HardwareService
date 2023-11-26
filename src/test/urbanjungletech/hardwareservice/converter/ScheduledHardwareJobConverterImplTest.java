package urbanjungletech.hardwareservice.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.converter.implementation.ScheduledHardwareJobConverterImpl;

@ExtendWith(MockitoExtension.class)
class ScheduledHardwareJobConverterImplTest {

    @Mock
    HardwareStateConverter hardwareStateConverter;

    @InjectMocks
    ScheduledHardwareJobConverterImpl scheduledHardwareJobConverter;

    @Test
    void toModels() {
    }
}
