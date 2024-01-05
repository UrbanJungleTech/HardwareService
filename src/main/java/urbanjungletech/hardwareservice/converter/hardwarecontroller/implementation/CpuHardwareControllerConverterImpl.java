package urbanjungletech.hardwareservice.converter.hardwarecontroller.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.SpecificHardwareControllerConverter;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.CpuHardwareControllerEntity;
import urbanjungletech.hardwareservice.model.hardwarecontroller.CpuHardwareController;

@Service
public class CpuHardwareControllerConverterImpl implements SpecificHardwareControllerConverter<CpuHardwareController, CpuHardwareControllerEntity> {


    @Override
    public void fillEntity(CpuHardwareControllerEntity hardwareControllerEntity, CpuHardwareController hardwareController) {

    }

    @Override
    public CpuHardwareController toModel(CpuHardwareControllerEntity hardwareControllerEntity) {
        CpuHardwareController result = new CpuHardwareController();
        return result;
    }

    @Override
    public CpuHardwareControllerEntity createEntity(CpuHardwareController hardwareController) {
        CpuHardwareControllerEntity result = new CpuHardwareControllerEntity();
        return result;
    }
}
