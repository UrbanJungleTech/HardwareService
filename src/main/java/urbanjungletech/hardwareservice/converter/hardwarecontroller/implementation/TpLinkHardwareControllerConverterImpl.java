package urbanjungletech.hardwareservice.converter.hardwarecontroller.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.SpecificHardwareControllerConverter;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.TpLinkHardwareControllerEntity;
import urbanjungletech.hardwareservice.model.hardwarecontroller.TpLinkHardwareController;

@Service
public class TpLinkHardwareControllerConverterImpl implements SpecificHardwareControllerConverter<TpLinkHardwareController, TpLinkHardwareControllerEntity> {
    @Override
    public void fillEntity(TpLinkHardwareControllerEntity hardwareControllerEntity, TpLinkHardwareController hardwareController) {

    }

    @Override
    public TpLinkHardwareController toModel(TpLinkHardwareControllerEntity hardwareControllerEntity) {
        TpLinkHardwareController result = new TpLinkHardwareController();
        return result;
    }

    @Override
    public TpLinkHardwareControllerEntity createEntity(TpLinkHardwareController hardwareController) {
        TpLinkHardwareControllerEntity result = new TpLinkHardwareControllerEntity();
        return result;
    }
}
