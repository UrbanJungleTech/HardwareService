package urbanjungletech.hardwareservice.entity.sensorreadingrouter;

import jakarta.persistence.Entity;

@Entity
public class DatabaseSensorReadingRouterEntity extends SensorReadingRouterEntity{
    private String tableName;
    private String valueColumn;
    private String timestampColumn;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String table) {
        this.tableName = table;
    }

    public String getValueColumn() {
        return valueColumn;
    }

    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }

    public String getTimestampColumn() {
        return timestampColumn;
    }

    public void setTimestampColumn(String timestampColumn) {
        this.timestampColumn = timestampColumn;
    }
}
