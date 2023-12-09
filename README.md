
# Hardware Entity

## Overview
The `Hardware` entity abstracts real-world hardware devices within the system. It allows for managing hardware states, scheduling through timers, and adding custom metadata.

## Fields

| Field Name             | Data Type             | Description                                               |  
|------------------------|-----------------------|-----------------------------------------------------------|  
| `id`                   | Long                  | The database ID of the hardware.                          |  
| `port`                 | String                | The port on the controller used to address the hardware.  |  
| `name`                 | String                | A user-defined name for easy identification.              |  
| `type`                 | String                | The user-defined type of the hardware (e.g., light).      |  
| `desiredState`         | HardwareState         | The state that the user desires for the hardware.         |  
| `currentState`         | HardwareState         | The state of the hardware as reported by the controller.  |  
| `hardwareControllerId` | Long                  | The ID of the hardware controller managing this hardware. |  
| `timers`               | List\<Timer\>         | Timers to schedule state changes.                         |  
| `metadata`             | Map\<String, String\> | Additional user-defined data in key-value pairs.          |  
| `configuration`        | Map\<String, String\> | The configuration settings for the hardware.              |  

## Hardware API Endpoints

| Endpoint Name                             | Description                                              | Query Parameters                                    | Request Body          |
|-------------------------------------------|----------------------------------------------------------|-----------------------------------------------------|-----------------------|
| `GET /hardware/{hardwareId}`              | Retrieves a specific hardware entity by its ID.          | HardwareId - Path variable - the ID of the hardware |                       |
| `GET /hardware/`                          | Fetches a list of all hardware entities.                 |                                                     |                       |
| `DELETE /hardware/{hardwareId}`           | Deletes a specific hardware entity by its ID.            | HardwareId - Path variable - the ID of the hardware |                       |
| `PUT /hardware/{hardwareId}`              | Updates the details of a specific hardware entity.       | HardwareId - Path variable - the ID of the hardware | Updated hardware data |
| `POST /hardware/{hardwareId}/timer`       | Adds a timer to a specific hardware entity.              | HardwareId - Path variable - the ID of the hardware | Timer data            |
| `PUT /hardware/{hardwareId}/currentstate` | Updates the current state of a specific hardware entity. | HardwareId - Path variable - the ID of the hardware | Hardware state data   |
| `PUT /hardware/{hardwareId}/desiredstate` | Updates the desired state of a specific hardware entity. | HardwareId - Path variable - the ID of the hardware | Hardware state data   |

## Creating Hardware
Note: Hardware entities are created under the `HardwareController` endpoint.


## Hardware Entity Events

| Event Name            | Trigger                        | Payload Description                            |
|-----------------------|--------------------------------|------------------------------------------------|
| `HardwareCreateEvent` | Creation of a hardware entity. | Contains the ID of the newly created hardware. |
| `HardwareDeleteEvent` | Deletion of a hardware entity. | Contains the ID of the deleted hardware.       |


# Timer Entity

## Overview
The Timer entity is used to schedule state changes for hardware. It uses a cron string to determine when it should update the state of the hardware, along with a state to set the hardware to.

## Fields

| Field Name   | Data Type | Description                                                                              |
|--------------|-----------|------------------------------------------------------------------------------------------|
| `id`         | Long      | The database ID of the timer.                                                            |
| `skipNext`   | Boolean   | Determines if the next execution of the timer should be skipped.                         |
| `state`      | String    | A string representing the desired state of the hardware. Refer to the `Hardware` entity. |
| `level`      | Long      | The power level to set for the hardware. Refer to the `Hardware` entity.                 |
| `cronString` | String    | The cron expression defining the schedule for the timer.                                 |
| `hardwareId` | Long      | The ID of the hardware associated with this timer.                                       |

## Timer API Endpoints

| Endpoint Name                     | Description                                        | Query Parameters                                   | Request Body        |
|-----------------------------------|----------------------------------------------------|----------------------------------------------------|---------------------|
| `GET /timer/{timerId}`            | Retrieves a specific timer entity by its ID.       | TimerId - Path variable - the ID of the timer      |                     |
| `GET /timer/`                     | Fetches a list of all timer entities.              |                                                    |                     |
| `DELETE /timer/{timerId}`         | Deletes a specific timer entity by its ID.         | TimerId - Path variable - the ID of the timer      |                     |
| `PUT /timer/{timerId}`            | Updates the details of a specific timer entity.    | TimerId - Path variable - the ID of the timer      | Updated timer data  |
| `POST /timer/`                    | Creates a new timer entity.                        |                                                    | Timer data          |

## Creating Timers
To create a new timer, use the `POST /timer/` endpoint with the appropriate timer data in the request body.

## Events

| Event Name         | Trigger                     | Payload Description                               |
|--------------------|-----------------------------|---------------------------------------------------|
| `TimerCreateEvent` | Creation of a timer entity. | Contains the ID of the newly created timer.       |
| `TimerUpdateEvent` | Update of a timer entity.   | Contains the ID and updated details of the timer. |
| `TimerDeleteEvent` | Deletion of a timer entity. | Contains the ID of the deleted timer.             |



# Sensor Entity

## Overview
The `Sensors` entity abstracts real-world sensors within the system. It provides functionalities for reading and interpreting sensor data as numeric values, managing sensor configurations, scheduling sensor readings, and storing custom metadata.

## Fields

| Field Name                | Data Type                      | Description                                             |  
|---------------------------|--------------------------------|---------------------------------------------------------|  
| `id`                      | Long                           | The database ID of the sensor.                          |  
| `sensorType`              | String                         | The type of the sensor.                                 |  
| `port`                    | String                         | The port on the controller used to address the sensor.  |  
| `name`                    | String                         | A user-defined name for easy identification.            |  
| `hardwareControllerId`    | Long                           | The ID of the hardware controller managing this sensor. |  
| `scheduledSensorReadings` | List\<ScheduledSensorReading\> | List of scheduled readings for the sensor.              |  
| `metadata`                | Map\<String, String\>          | Additional user-defined data in key-value pairs.        |  
| `configuration`           | Map\<String, String\>          | The configuration settings for the sensor.              |  

## Sensor API Endpoints

| Endpoint Name                              | Description                                                                            | Query Parameters                                  | Request Body        |  
|--------------------------------------------|----------------------------------------------------------------------------------------|---------------------------------------------------|---------------------|  
| `GET /sensor/{sensorId}`                   | Retrieves a specific sensor entity by its ID.                                          | SensorId - Query parameter - the id of the sensor |                     |  
| `GET /sensor/{sensorId}/reading`           | Returns the current reading of a specific sensor.                                      | SensorId - Query parameter - the id of the sensor |                     |  
| `GET /sensor/{sensorId}/readings`          | Fetches a list of sensor readings for a specific sensor within a specified date range. | SensorId - Query parameter - the id of the sensor |
| `POST /sensor/{sensorId}/scheduledreading` | Schedules a new sensor reading for a specific sensor.                                  | SensorId - Query parameter - the id of the sensor | A scheduled reading |  
| `DELETE /sensor/{sensorId}`                | Deletes a specific sensor entity by its ID.                                            | SensorId - Query parameter - the id of the sensor |                     |  
| `PUT /sensor/{sensorId}`                   | Updates the details of a specific sensor entity.                                       | SensorId - Query parameter - the id of the sensor | The updated sensor  |  


## Creating Sensors
Note: Sensors are created under the `HardwareController` endpoint. This ensures each sensor is appropriately managed by its hardware controller.

## Sensor Entity Events

| Event Name          | Trigger                      | Payload Description                          |
|---------------------|------------------------------|----------------------------------------------|
| `SensorCreateEvent` | Creation of a sensor entity. | Contains the ID of the newly created sensor. |
| `SensorDeleteEvent` | Deletion of a sensor entity. | Contains the ID of the deleted sensor.       |



# Hardware Controller Entity

## Overview

## Hardware Controller API Endpoints

| Endpoint Name                           | Description                                                              | Query Parameters                                                | Request Body            |
|-----------------------------------------|--------------------------------------------------------------------------|-----------------------------------------------------------------|-------------------------|
| `GET /{hardwareControllerId}`           | Retrieves a specific hardware controller by its ID.                      | HardwareControllerId - Path variable - the ID of the controller |                         |
| `GET /`                                 | Fetches a list of all hardware controllers.                              |                                                                 |                         |
| `DELETE /{hardwareControllerId}`        | Deletes a specific hardware controller by its ID.                        | HardwareControllerId - Path variable - the ID of the controller |                         |
| `DELETE /`                              | Deletes all hardware controllers.                                        |                                                                 |                         |
| `PUT /{hardwareControllerId}`           | Updates the details of a specific hardware controller.                   | HardwareControllerId - Path variable - the ID of the controller | Updated controller data |
| `GET /{hardwareControllerId}/hardware`  | Retrieves the list of hardware devices managed by a specific controller. | HardwareControllerId - Path variable - the ID of the controller |                         |
| `POST /{hardwareControllerId}/hardware` | Adds a hardware device to a specific controller.                         | HardwareControllerId - Path variable - the ID of the controller | Hardware data           |
| `POST /{hardwareControllerId}/sensor`   | Adds a sensor to a specific controller.                                  | HardwareControllerId - Path variable - the ID of the controller | Sensor data             |
| `GET /{hardwareControllerId}/sensor`    | Retrieves the list of sensors managed by a specific controller.          | HardwareControllerId - Path variable - the ID of the controller |                         |

## Hardware Controller Entity Events

| Event Name                      | Trigger                                   | Payload Description                                          |
|---------------------------------|-------------------------------------------|--------------------------------------------------------------|
| `HardwareControllerCreateEvent` | Creation of a hardware controller entity. | Contains the ID and details of the newly created controller. |


## Hardware Controller Types and Communication Service Implementation

## Hardware Controller Types

Hardware controllers have specific types that define their communication protocols and functionalities. There are two standard types of controllers: MQTT and CPU.

### MQTT Controller
- **Description:** Uses a custom protocol with messages sent to an MQTT queue.
- **Payloads:** JSON-RPC format. The ID is usually 0 unless a response is required.

#### Configuration Requirements

| Field           | Description                         |
|-----------------|-------------------------------------|
| `serialNumber`  | Identifier for the physical device. |
| `server`        | Address of the MQTT server.         |
| `responseQueue` | Queue for sending responses.        |
| `requestQueue`  | Queue for receiving requests.       |

### CPU Controller
- **Description:** This controller type is intended to measure aspects of the cpu. It only supports sensors.

#### Supported Sensor Types

| Sensor Type       | Description                   |
|-------------------|-------------------------------|
| `TEMPERATURE`     | Measures CPU temperature.     |
| `CLOCK_SPEED`     | Measures CPU clock speed.     |
| `CORE_VOLTAGE`    | Measures CPU core voltage.    |
| `CORE_MULTIPLIER` | Measures CPU core multiplier. |
| `FAN_SPEED`       | Measures CPU fan speed.       |
| `LOAD`            | Measures CPU load.            |


# Hardware State Entity Documentation

## Overview
The `HardwareState` entity represents the state of a hardware device within the system. It is used to track and manage the state and level of the hardware.

## Fields

| Field Name   | Data Type | Description                                     |
|--------------|-----------|-------------------------------------------------|
| `id`         | Long      | The unique identifier of the hardware state.    |
| `level`      | long      | A numerical value representing the state level. |
| `state`      | String    | The state of the hardware.                      |
| `hardwareId` | Long      | The ID of the associated hardware entity.       |

## Hardware State API Endpoints

| Endpoint Name                          | Description                                | Path Variable                                                 | Request Body                                       | 
|----------------------------------------|--------------------------------------------|---------------------------------------------------------------|----------------------------------------------------|
| `GET /hardwarestate/{hardwareStateId}` | Retrieves a specific hardware state by ID. | `hardwareStateId` - The ID of the hardware state to retrieve. |                                                    |
| `PUT /hardwarestate/{hardwareStateId}` | Updates a specific hardware state.         | `hardwareStateId` - The ID of the hardware state to update.   | `HardwareState` - The updated hardware state data. |

## Hardware State Entity Events

| Event Name                 | Trigger                                         | Payload Description                                    |
|----------------------------|-------------------------------------------------|--------------------------------------------------------|
| `HardwareStateUpdateEvent` | Occurs when a hardware state entity is updated. | Contains the updated `HardwareState` entity's details. |


## Notes
- The `level` field is a generic numerical representation and can be adapted based on the specific hardware type.
- The `state` field uses the `ONOFF` enum, which should have values `ON` and `OFF`.


# Sensor Reading Entity

## Overview
The `SensorReading` entity is used to store the values obtained from reading a sensor. These entities are created internally within the application whenever a sensor is read.

## Fields

| Field Name    | Data Type     | Description                                           |
|---------------|---------------|-------------------------------------------------------|
| `id`          | Long          | The unique identifier of the sensor reading.          |
| `sensorId`    | Long          | The ID of the sensor from which the reading is taken. |
| `reading`     | double        | The numerical value of the sensor reading.            |
| `readingTime` | LocalDateTime | The date and time when the reading was taken.         |


## Sensor Reading Entity Events

| Event Name                 | Trigger                              | Payload Description                                    |
|----------------------------|--------------------------------------|--------------------------------------------------------|
| `SensorReadingCreateEvent` | Creation of a sensor reading entity. | Contains details of the created sensor reading entity. |

## Notes
- `SensorReading` entities are not directly manipulated via API endpoints; they are generated and managed internally by the application.
- The `readingTime` field is captured automatically at the time of reading the sensor.


# Scheduled Reading Entity

## Overview
The `ScheduledReading` entity represents a timed reading of a sensor. It is used to schedule sensor readings at specified intervals, defined by a CRON expression, and manage where these readings are routed.

## Fields

| Field Name   | Data Type                   | Description                                                 |
|--------------|-----------------------------|-------------------------------------------------------------|
| `id`         | Long                        | The unique identifier of the scheduled reading.             |
| `sensorId`   | Long                        | The ID of the sensor for which readings are scheduled.      |
| `cronString` | String                      | The CRON expression defining the reading schedule.          |
| `routers`    | List\<SensorReadingRouter\> | The routers that determine where sensor readings are saved. |

## Scheduled Reading API Endpoints

| Endpoint Name                            | Method   | Description                                   | Path Variable                                                       | Request Body                                                   |
|------------------------------------------|----------|-----------------------------------------------|---------------------------------------------------------------------|----------------------------------------------------------------|
| `/scheduledreading/{scheduledReadingId}` | `GET`    | Retrieves a specific scheduled reading by ID. | `scheduledReadingId` - The ID of the scheduled reading to retrieve. |                                                                |
| `/scheduledreading/{scheduledReadingId}` | `PUT`    | Updates a specific scheduled reading.         | `scheduledReadingId` - The ID of the scheduled reading to update.   | `ScheduledSensorReading` - The updated scheduled reading data. |
| `/scheduledreading/{scheduledReadingId}` | `DELETE` | Deletes a specific scheduled reading by ID.   | `scheduledReadingId` - The ID of the scheduled reading to delete.   |                                                                |
| `/scheduledreading/`                     | `GET`    | Retrieves all scheduled readings.             |                                                                     |                                                                |

## Events

### Scheduled Reading Events

| Event Name                    | Trigger                                 | Payload Description                                       |
|-------------------------------|-----------------------------------------|-----------------------------------------------------------|
| `ScheduledReadingCreateEvent` | Creation of a scheduled reading entity. | Contains details of the created scheduled reading entity. |

## Notes
- The `cronString` field should be a valid CRON expression that dictates the scheduling of sensor readings.
- The `routers` field is a list of `SensorReadingRouter` objects, determining the destinations for the sensor readings.


# Sensor Reading Router Entity

## Overview
The `Router` entity is designed to route sensor readings to various destinations. It acts as a child entity within the `ScheduledSensorReading` and is essential for determining how and where sensor readings are dispatched.

## Fields

| Field Name                   | Data Type | Description                                                  |
|------------------------------|-----------|--------------------------------------------------------------|
| `id`                         | Long      | The unique identifier of the router.                         |
| `type`                       | String    | The type of router, determining its routing functionality.   |
| `ScheduledSensorReadingId`   | Long      | The ID of the associated `ScheduledSensorReading` entity.    |

## Existing Router Types
Out-of-the-box, the system includes the following router types:
- `databaseSensorReadingRouter`
- `kafkaSensorReadingRouter`
- `basicDatabaseSensorReadingRouter`
- `azureQueueSensorReadingRouter`

## Adding a New Router Type

To add a new router type:

1. **Create a New Model**: Define a new model class with specific fields for the new router type. This class must extends from SensorReadingRouter. ie:

```java
public class AzureQueueSensorReadingRouter extends SensorReadingRouter{  
    public AzureQueueSensorReadingRouter() {  
        super("azureQueueSensorReadingRouter");  
  }  
  
 private String queueName;  
 private Credentials credentials;  
```

1. **Create a new Entity**: Define a new entity class with the fields that you need to save to the database. These will generally be the same as the models fields. This class must extend from SensorReadingRouterEntity, ie:

```java
@Entity  
public class AzureQueueSensorReadingRouterEntity extends SensorReadingRouterEntity{  
    @Id  
  private Long id;  
 private String queueName;  
  @ManyToOne  
  private CredentialsEntity credentials;
  }
```

1. **Update SensorReadingRouter Metadata**:
   The following metadata needs to be updated in the class SensorReadingRouter. This is required to let the system know the type of the router during serialization and deserialization.
```java
   @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
   @JsonSubTypes({
       @JsonSubTypes.Type(value = [YourNewRouter].class, name = "[yourNewRouter]")
       // Other routers...
   })
```

1. **Implement SpecificSensorReadingRouterConverter Interface**:

Define a new implementation of the SpecificSensorReadingRouterConverter . This is used to convert between models and entities.
```java
   public interface SpecificSensorReadingRouterConverter<Model extends SensorReadingRouter, Entity extends SensorReadingRouterEntity> {
    Model toModel(Entity entity);
    Entity createEntity(Model model);
    void fillEntity(Entity entity, Model model);
}
```

1. **Implement SpecificSensorReadingRouterService Interface**:

Implement the SpecificSensorReadingRouterService. This will be called as the integration point to send your readings to an external system. ie:
```java
@Service  
public class AzureQueueSensorReadingRouterService implements SpecificSensorReadingRouterService<AzureQueueSensorReadingRouter>{  
  
    private final ClientGenerator clientGenerator;  
  
 public AzureQueueSensorReadingRouterService(ClientGenerator clientGenerator) {  
        this.clientGenerator = clientGenerator;  
  }  
    @Override  
  public void route(AzureQueueSensorReadingRouter routerData, SensorReading sensorReading) {  
        QueueClient queueClient = clientGenerator.generateCredentials(routerData.getCredentials(), QueueClient.class);  
  queueClient.sendMessage(sensorReading.toString());  
  }  
}
```

# Alert Entity

## Overview
Alerts in the system are designed to trigger actions based on specific conditions. Each alert comprises a list of conditions and a list of actions. Once all the specified conditions are met, the associated actions are executed.

## Fields

| Field Name     | Data Type            | Description                                             |
|----------------|----------------------|---------------------------------------------------------|
| `id`           | Long                 | The database ID of the alert.                           |
| `name`         | String               | A user-defined name for the alert.                      |
| `description`  | String               | A description of the alert's purpose.                   |
| `conditions`   | AlertConditions      | The conditions under which the alert will trigger.      |
| `alertActions` | List\<AlertAction\>  | The actions to perform when the alert triggers.         |

## Alert API Endpoints

| Endpoint Name         | Description                              | Query Parameters                           | Request Body       |
|-----------------------|------------------------------------------|--------------------------------------------|--------------------|
| `GET /alerts/{id}`    | Retrieves a specific alert by its ID.    | `id` - Path variable - the ID of the alert |                    |
| `GET /alerts/`        | Fetches a list of all alerts.            |                                            |                    |
| `DELETE /alerts/{id}` | Deletes a specific alert by its ID.      | `id` - Path variable - the ID of the alert |                    |
| `PUT /alerts/{id}`    | Updates the details of a specific alert. | `id` - Path variable - the ID of the alert | Updated alert data |
| `POST /alerts/`       | Creates a new alert.                     |                                            | Alert data         |

## Creating Alerts
To create a new alert, use the `POST /alerts/` endpoint with the alert data in the request body. This includes defining the conditions and actions for the alert.


# Condition Entity

## Overview
Conditions in the system represent situations that can be either true or false. They are used as part of the alert system to determine when an alert should be triggered.

## Fields

| Field Name | Data Type | Description                                                 |
|------------|-----------|-------------------------------------------------------------|
| `id`       | Long      | The database ID of the condition.                           |
| `alertId`  | Long      | The ID of the alert this condition is part of.              |
| `type`     | String    | The type of condition. This determines the triggering logic |
| `active`   | Boolean   | Indicates whether the condition is active.                  |

## Condition Types

Out of the box the following condition types are supported.

| Name                  | Subfields                                                                       | Triggering Logic                                                               |
|-----------------------|---------------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| Hardware State Change | `state` (String), `hardwareId` (Long)                                           | Triggered when the specified hardware enters the specified state.              |
| Sensor Reading        | `sensorId` (Long), `threshold` (Numeric), `thresholdType` (String: ABOVE/BELOW) | Triggered when the sensor reading goes above or below the specified threshold. |

## Condition API Endpoints

| Endpoint Name                     | Description                               | Query Parameters              | Request Body   |
|-----------------------------------|-------------------------------------------|-------------------------------|----------------|
| `POST /condition/`                | Creates a new condition.                  |                               | Condition data |
| `GET /condition/{conditionId}`    | Retrieves a specific condition by its ID. | `conditionId` - Path variable |                |
| `DELETE /condition/{conditionId}` | Deletes a specific condition by its ID.   | `conditionId` - Path variable |                |

## Creating Conditions
To create a new condition, use the `POST /condition/` endpoint with the necessary condition data in the request body. Define the type of condition and its specific subfields as required.

## Example Use Case
- A "Sensor Reading" condition is set to monitor the moisture level sensor. If the moisture level falls below a certain threshold, the condition becomes true, potentially triggering an associated alert.

# AlertAction Entity

## Overview
The `AlertAction` entity defines actions to be performed when an alert's conditions are met. Each action is associated with a specific alert and is executed upon the triggering of that alert.

## Fields

| Field Name | Data Type | Description                                      |
|------------|-----------|--------------------------------------------------|
| `id`       | Long      | The database ID of the action.                   |
| `type`     | String    | The type of action.                              |
| `alertId`  | Long      | The ID of the alert associated with this action. |

## Subtypes of AlertAction

### CancelNextScheduledHardwareAlertAction

#### Fields

| Field Name            | Data Type | Description                           |
|-----------------------|-----------|---------------------------------------|
| `scheduledHardwareId` | Long      | The ID of the scheduled hardware.     |

#### Description
This action will skip the next timer event for the specified hardware.

#### Example Use Case
- Used to skip a sprinkler timer when rain is expected.

### HardwareStateChangeAlertAction

#### Fields

| Field Name   | Data Type | Description                             |
|--------------|-----------|-----------------------------------------|
| `hardwareId` | Long      | The ID of the target hardware.          |
| `state`      | String    | The state to set the hardware to.       |
| `level`      | Long      | The power level to set the hardware to. |

#### Description
This action sets the desired state and power level of the specified hardware.

#### Example Use Case
- If the temperature drops too far, this action can be used to turn on a heater.


# System Flows

# Scheduled Sensor Reading Flow

## Overview
The Scheduled Sensor Reading flow is triggered when a Scheduled Sensor Reading entity is created. This entity is associated with a schedule, which is executed according to a specified cron string. The flow consists of several steps to handle the sensor data effectively.

## Flow Steps

1. **Sensor Reading Request**:
   - The system requests a sensor reading from the controller at the scheduled time defined by the cron string.

2. **Saving to Database**:
   - The sensor reading is saved to the local database. This data can then be utilized by event handlers that are set up to react to specific sensor readings.

3. **Router Service Call**:
   - After saving the sensor reading, the system calls the router service.

4. **Executing Router Entities**:
   - For each router associated with the scheduled sensor reading, the system executes the corresponding router service. This step typically involves passing the router entity for execution.
   - The execution often results in sending the sensor data to a third-party data store or messaging system, as defined by the specific router entity.

## Example Use Case
- A scheduled sensor reading is set up to monitor soil moisture levels. The reading is scheduled to occur every morning at 6 AM.
- Once the reading is taken, the data is stored in the local database and then routes the reading to an azure table queue.


# Alert Flow

## Overview
An alert in the system is triggered when all of its conditions are met. A condition, once set to true, remains true until re-evaluated and set to false. Upon triggering, the alert executes all associated actions.

## Condition Flow
Each type of condition has its own triggering logic, but all are based on events.

### Hardware State Change Condition
- Triggered by the `HardwareStateChangeEvent`.
- The evaluation and triggering flow begins when this event is raised.

### Sensor Reading Condition
- Relies on the `SensorReadingCreate` event.
- Upon this event, the condition is evaluated against the set thresholds.

For both types, a service retrieves conditions related to the specified entity and determines whether each condition is triggered, updating the `active` field accordingly. If `active` is updated, the associated alert is retrieved, and the condition is moved to either the active or inactive set. The alert's actions are triggered if all conditions move to the active set.

## Action Flow
- The action service retrieves all actions associated with the alert and executes each by calling the specific action service based on the action type.

## Example Scenario

In this scenario the user has a garden, and would like to make sure they keep the temperature between 30 and 40 degrees. To achieve this they have a heater, a fan, and setup 2 alerts to automate the temperature management.

### Alert Setup
- **Conditions**:
   1. Sensor reading condition: Temperature above 40 degrees.
   2. Hardware state change condition: Fan is off.
- **Action**:
   - Hardware state change: Turn the fan on.

### Triggering
- The fan turns off.
- Later, the temperature rises above 40 degrees.
- Both conditions are now true, triggering the action to turn the fan on.

### Additional Alert for Regulation
- **Conditions**:
   1. Temperature below 30 degrees.
   2. Fan is on.
- **Action**:
   - Turn the fan off.

This setup effectively regulates the temperature within a desired range.

