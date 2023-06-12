package frentz.daniel.hardwareservice.dao;

import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HardwareControllerDAOImplTest {

    @Mock
    private HardwareControllerRepository hardwareControllerRepository;
    @InjectMocks
    private HardwareControllerDAOImpl hardwareControllerDAO;
    @Test
    void createHardwareController() {
    }

    @Test
    void getAllHardware() {
    }

    @Test
    void getHardwareController() {
    }

    @Test
    void getBySerialNumber() {
    }

    @Test
    void exists_returnsTrue_whenHardwareControllerExists() {
        String serialNumber = "1234";
        when(this.hardwareControllerRepository.existsBySerialNumber(eq(serialNumber))).thenReturn(true);
        boolean result = this.hardwareControllerDAO.exists(serialNumber);
        assertTrue(result);
    }

    @Test
    void exists_returnsFalse_whenHardwareControllerDoesntExist() {
        String serialNumber = "1234";
        when(this.hardwareControllerRepository.existsBySerialNumber(eq(serialNumber))).thenReturn(false);
        boolean result = this.hardwareControllerDAO.exists(serialNumber);
        assertFalse(result);
    }

    @Test
    void updateHardwareController() {
    }

    @Test
    void getHardwareControllerSerialNumber() {
    }

    @Test
    void delete() {
    }
}
