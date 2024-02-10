package urbanjungletech.hardwareservice.exception.model;

import urbanjungletech.hardwareservice.exception.exception.WebRequestException;

import java.util.List;

public class InvalidRequestError extends WebRequestException {
    private List<InvalidRequestField> fields;

    public List<InvalidRequestField> getFields() {
        return fields;
    }

    public void setFields(List<InvalidRequestField> fields) {
        this.fields = fields;
    }
}
