package urbanjungletech.hardwareservice.model.sensorreadingrouter;

public class DatabaseSensorReadingRouter extends SensorReadingRouter{
    private String tableName;
    private String timestampColumn;
    private String valueColumn;

    public DatabaseSensorReadingRouter() {
        super("databaseSensorReadingRouter");
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTimestampColumn() {
        return timestampColumn;
    }

    public void setTimestampColumn(String timestampColumn) {
        this.timestampColumn = timestampColumn;
    }

    public String getValueColumn() {
        return valueColumn;
    }

    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }
}
