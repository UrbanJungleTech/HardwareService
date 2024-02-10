package urbanjungletech.hardwareservice.exception.model;

public class InvalidRequestField {
    private String field;
    private String reason;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
