package urbanjungletech.hardwareservice.converter.alert;

import urbanjungletech.hardwareservice.entity.alert.AlertEntity;
import urbanjungletech.hardwareservice.model.alert.Alert;

public interface AlertConverter {

    Alert toModel(AlertEntity alertEntity);
    void fillEntity(AlertEntity alertEntity, Alert alert);
}
