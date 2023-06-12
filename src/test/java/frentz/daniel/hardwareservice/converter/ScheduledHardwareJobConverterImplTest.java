package frentz.daniel.hardwareservice.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;

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
