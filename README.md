# IoT Automation Framework Accelerator

## Overview

Welcome to the IoT Automation Framework Accelerator, a foundational platform designed to expedite the development and management of IoT systems. This project serves as a robust starting point for integrating and automating a wide array of IoT devices and sensors, tailored to a variety of applications including home automation, automated gardening, and more.

### Key Features

- **Modular Entity Management**: Streamlines the handling of diverse IoT entities like hardware devices, sensors, and controllers.
- **Dynamic Alert System**: Enables responsive system behavior with complex alert conditions and actions based on real-time data.
- **Scheduling and Timers**: Offers precise scheduling capabilities for device actions and sensor readings.
- **Customizable Conditions and Actions**: Adaptable to specific requirements, ensuring flexibility across different IoT applications.
- **Data-Driven Decision Making**: Facilitates informed automation decisions using sensor data.
- **Extensible and Scalable**: Easily expands to accommodate new devices and sensors, growing with your project needs.

### Project Scope

1. **Accelerator, Not a Complete Product**: This framework is designed to provide the majority of functionalities required for integrating hardware with a smart controller. It is an accelerator, meaning it sets up the foundation but leaves aspects such as security to be implemented by the end user according to their specific requirements.
2. **Custom Integration Support**: For integrations not included out-of-the-box, we provide guidelines and methods to seamlessly incorporate your unique hardware and requirements into the system.

### Ideal for Custom IoT Solutions

Whether it's for automating a compact home garden, orchestrating a complex smart home system, or building a tailored IoT solution, this framework offers the tools and adaptability needed for a wide range of IoT projects.

### Getting Started

Explore our extensive documentation to learn how to set up and utilize the framework effectively. You'll find detailed entity setups, flow documentation, and example scenarios to guide you in your IoT journey.

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

Hardware controllers have specific types that define their communication protocols and functionalities. 

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

### Weather Controller
- **Description:** This controller type is intended to measure aspects of the weather at any location in the world. Its sensors are completely virtual and use an API to gather the sensor readings. Each sensor needs to have a few fields configured which will determine in what location and what aspect of the weather it will return. The API used is tomorrow.io.

#### Supported Sensor Types

| Sensor Type       | Description                   |
|-------------------|-------------------------------|
| SensorType        | The aspect of the weather to measure|
| latitude          | The latitude of the location. |
| longitude         | The longitude of the location.|



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

# Credentials Entity Documentation

## Overview
The Credentials entity serves as an abstract base class for different types of authentication credentials within the system. It abstracts the commonalities shared by various credential types, providing a foundation for specific credential implementations. This entity is crucial for managing authentication mechanisms across different components and services, ensuring secure access and interaction.

## Fields

The Credentials class is designed with polymorphism in mind, allowing for the dynamic inclusion of various credential types based on a type property. This design facilitates the easy addition of new credential types as the system evolves.


| Field Name     | Data Type            | Description                                             |
|----------------|----------------------|---------------------------------------------------------|
| `id`           | Long                 | The database ID of the Credential instance.             |
| `type`         | String               | The derived type of the Credentials class               |

## Supported Credential Types

The system supports various types of credentials, each tailored for specific authentication mechanisms. Below are the details of each supported credential type.

### CertificateCredentials

- **Purpose**: Authenticate using certificates.
  
| Field         | Data Type | Description               |
|---------------|-----------|---------------------------|
| `certificate` | `Byte[]`  | The certificate data.     |

### DatabaseCredentials

- **Purpose**: Authenticate with a database.
  
| Field       | Data Type  | Description            |
|-------------|------------|------------------------|
| `username`  | `String`   | The database username. |
| `password`  | `String`   | The database password. |

### TokenCredentials

- **Purpose**: Authenticate using a token.
  
| Field         | Data Type | Description       |
|---------------|-----------|-------------------|
| `tokenValue`  | `String`  | The token value.  |

### UsernamePasswordCredentials

- **Purpose**: Authenticate using a username and password.
  
| Field       | Data Type | Description            |
|-------------|-----------|------------------------|
| `username`  | `String`  | The username.          |
| `password`  | `String`  | The password.          |

Each type of credentials is designed to cater to different authentication needs, ensuring the system's flexibility and security in handling authentication across various services and components.

# Credentials API Endpoints

The following table outlines the API endpoints available for managing credentials within the system.

| Endpoint               | Method | Description                                      | Query Parameters | Request Body          |
|------------------------|--------|--------------------------------------------------|------------------|-----------------------|
| `/credentials/`        | POST   | Creates new credentials and stores them securely.|                  | `Credentials` object  |

This endpoint allows for the creation of various types of credentials, leveraging the polymorphic nature of the `Credentials` class to accommodate different authentication mechanisms.

## Secure Storage and Retrieval

Credentials are never stored in their raw form in the database. Instead, they are processed through an implementation of CredentialsRetrievalService, which securely stores actual credential values in a secure store and replaces them with hashes for database storage.

## Supported implementations

Currently there are two implementations
- Azure Keyvault
- Hashicorp vault

  The choice can be made by setting the property secure-storage.type

### CredentialsRetrievalService

This service defines methods for retrieving, persisting, deleting, and updating credentials, handling them through secure storage.

```java
public interface CredentialsRetrievalService {
    Credentials getCredentials(Credentials credentials);
    Credentials persistCredentials(Credentials credentials);
    void deleteCredentials(Credentials credentials);
    void updateCredentials(Credentials credentialsKeys, Credentials credentialsValues);
}
```

### SpecificCredentialRetrievalService

A dedicated service for each model of credentials, responsible for the secure handling of credential fields.

```java
public interface SpecificCredentialRetrievalService <T extends Credentials>{
    T getCredentials(T credentials);
    T persistCredentials(T credentials);
    void deleteCredentials(T credentials);
    void updateCredentials(T credentialsKeys, T credentialsValues);
}
```

### SecureStorageService

Defines the interaction with the secure store, ensuring the confidentiality of credential values.

```java
public interface SecureStorageService {
    String saveSecret(String secret);
    void saveSecret(String secretId, String secret);
    String getSecret(String secretId);
    void deleteSecret(String secretId);
}
```

The AzureSecureStorage implementation interacts with an Azure Key Store, storing credential values as secrets and using hashes as keys for retrieval.

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

# Event System

## Overview

The event system in our IoT Automation Framework is designed around CRUD (Create, Read, Update, Delete) events for each entity. These events facilitate easy integration and interaction with the system without modifying the core code. Events are based on Spring's event publishers, allowing developers to implement services with event handlers to respond to these events. This is the prefered way to integrate with the system if it suits the developers needs as it will not require modifying core business logic and thus is indepenent of any other integrations.

## Event Handling

To listen and react to an event, developers need to implement a service containing their event handlers. Below is an example demonstrating how to handle CRUD events related to the Hardware entity:
```java
/**
 * Listens for CRUD events related to the Hardware entity and calls the hardware communication service
 * to execute appropriate actions based on the event.
 */
@Service
public class HardwareControllerHardwareEventListener {
    private static final Logger logger = LoggerFactory.getLogger(HardwareControllerHardwareEventListener.class);
    private final ControllerCommunicationService controllerCommunicationService;
    private final HardwareQueryService hardwareQueryService;

    public HardwareControllerHardwareEventListener(ControllerCommunicationService controllerCommunicationService, HardwareQueryService hardwareQueryService){
        this.controllerCommunicationService = controllerCommunicationService;
        this.hardwareQueryService = hardwareQueryService;
    }

    @Async
    @TransactionalEventListener
    public void handleHardwareCreateEvent(HardwareCreateEvent HardwareCreateEvent){
        logger.debug("Sending hardware create event to hardware controller.");
        Hardware hardware = this.hardwareQueryService.getHardware(HardwareCreateEvent.getHardwareId());
        this.controllerCommunicationService.registerHardware(hardware);
    }

    @Async
    @EventListener
    public void handleHardwareDeleteEvent(HardwareDeleteEvent hardwareDeleteEvent){
        Hardware hardware = this.hardwareQueryService.getHardware(hardwareDeleteEvent.getHardwareId());
        this.controllerCommunicationService.deregisterHardware(hardware);
    }
}
```

## Key Annotations

@Async: Ensures that the processing of the event does not block the thread on which it was published. This is crucial as multiple event handlers might be listening to the same event.
@TransactionalEventListener: Guarantees that the event handler is invoked only after the completion of the CRUD operation it is linked to. It also ensures that in case of a transaction failure, the event handler will not be executed.

## Event Data

All events carry only the ID of the associated entity. This design is intentional, as events are published before the completion of the transaction, and a complete model of the entity might not be available. To retrieve the complete model, use the query service associated with that entity.

# Exception Handling
## Overview

In our IoT Automation Framework, exception handling is centralized and not performed where the exception occurs. This approach is based on the premise that while we cannot always recover from exceptions, it is crucial to inform the user of the occurrence and handle any necessary cleanup, such as transaction rollbacks.
Throwing Exceptions

## Custom Exceptions
When an exception is anticipated, it should be caught and rethrown as a custom exception. Custom exceptions must extend RuntimeException to avoid mandatory catch or specify clauses.

### Logging
Before rethrowing the exception, log the occurrence at the INFO level. This level is chosen as it provides useful information in a production environment.

### HTTP Exceptions

Central Exception Handler (ErrorHandler): HTTP exceptions are managed in a central exception handler. To handle new types of exceptions, you can add methods following this template:

```java
@ExceptionHandler(value = StandardErrorException.class)
public ResponseEntity<WebRequestException> handleError(StandardErrorException exception){
    WebRequestException error = new WebRequestException();
    error.setMessage(exception.getMessage());
    error.setHttpStatus(exception.getStatus().value());
    return ResponseEntity.status(exception.getStatus()).body(error);
}
```

### Entity Name Service

Usage: The EntityNameService provides human-readable names for entities, useful in logs and HTTP responses. Names are configured in Spring under entity.names.
Configured Names:
urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareControllerEntity: Hardware Controller
urbanjungletech.hardwareservice.entity.sensor.SensorEntity: Sensor
(and other entity names)

An example is in the NotFoundException

```java
    @Override
    public NotFoundException createNotFoundException(Class clazz, long id) {
        String name = this.entityNameService.getName(clazz);
        NotFoundException result = new NotFoundException(name, id);
        return result;
    }
```

### ExceptionService

For exceptions that are frequently used but require specific logic for field population, new methods should be added to the ExceptionService.

Example Method: The createNotFoundException method in ExceptionService is an example. It returns a NotFoundException with a custom message and HTTP status code 404.

```java
NotFoundException createNotFoundException(Class clazz, long id);
```

# Polymorphic Entities Documentation

This section outlines the approach for handling polymorphic entities in our project, focusing on the ConnectionDetails as a primary example. This mechanism is essential for representing various types of connections with common attributes while allowing for specific differences.

## Model Definition
### Abstract Base Class

The abstract base class defines shared fields and employs the @JsonTypeInfo annotation to facilitate polymorphic JSON serialization and deserialization based on a type field.

## Polymorphic Model Definition
### Abstract Base Model

The foundation of polymorphic handling in our models is established through an abstract base class annotated with @JsonTypeInfo. This setup enables automatic type resolution during JSON serialization and deserialization processes.

```java
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
              include = JsonTypeInfo.As.PROPERTY,
              property = "type")
public abstract class ConnectionDetails {
    private Long id;
    // Shared fields across all connection types
}
```

The type property dictates the concrete class to be instantiated when processing JSON data.

### Concrete Model Implementations

Derived classes extend this base class, incorporating specific fields and behaviors relevant to their unique context.

    Example: AzureConnectionDetails
```java
public class AzureConnectionDetails extends ConnectionDetails {
    private String url;
    private Credentials credentials;
    // Azure-specific fields
}
```

Entity Structure for Persistence

### Base Entity

Entities mirror the polymorphic structure of models, with a base entity class using JPA annotations to define a suitable inheritance strategy.

Base Entity: ConnectionDetailsEntity

```java
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ConnectionDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // Common entity attributes
}
```

### Concrete Entity Classes

These classes provide persistence mechanisms for each type of connection detail, extending the base entity class.

Example: AzureConnectionDetailsEntity

```java
@Entity
public class AzureConnectionDetailsEntity extends ConnectionDetailsEntity {
    private String url;
    @ManyToOne
    private CredentialsEntity credentials;
    // Attributes specific to Azure connections
}
```

## Converters

Converters play a crucial role in translating between entity and model representations. This section outlines the architecture designed to facilitate this process efficiently, accommodating the polymorphic nature of our data.

### Proxy Converter

A proxy converter interface acts as a unified facade for all conversion operations, simplifying the conversion process for consumers.

Interface: ConnectionDetailsConverter
```java
public interface ConnectionDetailsConverter {
    ConnectionDetails toModel(ConnectionDetailsEntity entity);
    ConnectionDetailsEntity createEntity(ConnectionDetails model);
    void fillEntity(ConnectionDetailsEntity entity, ConnectionDetails model);
}
```

### Implementation with Delegation

The concrete implementation of this converter manages a collection of specific converters, delegating tasks based on the runtime type of the objects involved.

Implementation: ConnectionDetailsConverterImpl

```java
@Service
public class ConnectionDetailsConverterImpl implements ConnectionDetailsConverter {
    private final Map<Class<?>, SpecificConnectionDetailsConverter<?, ?>> converterMap;

    // Implementation details focusing on delegation logic
}
```

### Specific Converters

For each concrete subclass of ConnectionDetails, a corresponding converter is defined to handle its specific conversion logic.

Specific Converter Interface

```java
public interface SpecificConnectionDetailsConverter<T extends ConnectionDetails, U extends ConnectionDetailsEntity> {
    T toModel(U entity);
    U createEntity(T model);
    void fillEntity(U entity, T model);
}
```

## Configuration and Registration

To ensure that all specific converters are correctly instantiated and accessible, a configuration class is employed to populate a map with converter instances. This map facilitates the dynamic selection of converters based on the class of the object being converted.

Converter Configuration

```Java
@Bean
public Map<Class<?>, SpecificConnectionDetailsConverter<?, ?>> configureConverters(List<SpecificConnectionDetailsConverter<?, ?>> converters) {
    Map<Class<?>, SpecificConnectionDetailsConverter<?, ?>> map = new HashMap<>();
    // Populate the map with converter instances
    return map;
}
```


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

# CRUD Operations

## Overview

CRUD operations in the IoT Automation Framework are divided into two main service types: Addition Services and Query Services. These services manage the creation, update, deletion, and querying of entities in the system.

## Addition Services

Addition services are used for creating, updating and deleting entities.

### Interface

Addition services implement the AdditionService interface, defined as follows:

```java
public interface AdditionService <T>{
    T create(T t);
    void delete(long id);
    T update(long id, T t);
    List<T> updateList(List<T> models);
}
```

### Behavior Pattern

#### Creation:
The addition service first calls the DAO for its primary entity, then handles any child entities.
For example, in the Hardware Controller Addition Service, it:
- Creates the hardware controller entity using its DAO.
- Sets the hardware controller ID for all related hardware.
- Repeats the process for sensors.
- Converts and returns the hardware controller entity as a model.

#### Updates:
- The updateList method is generally used for updates.
- The method checks each element in the list to determine if it's a new or existing entity, calling create or update accordingly.
-
Note: Updating a parent entity updates and creates children as needed, but does not delete. Deletion should be handled separately.

## DAO (Data Access Object) Layer Documentation

## Overview

The DAO layer acts as the bridge between the application's business logic and the database, abstracting the complexity of data access operations. It offers a streamlined interface for CRUD operations on entities, ensuring data integrity and encapsulating database interaction logic.

## DAO Interfaces

For each entity in the system, there's a corresponding DAO interface that outlines possible operations, including creating, retrieving, updating, and deleting entity instances. Finder methods for entity retrieval based on specific criteria are also defined here, returning entities rather than models.

### Example: HardwareDAO Interface

```java
public interface HardwareDAO {
    HardwareEntity createHardware(Hardware hardware);
    HardwareEntity getHardware(long hardwareId);
    HardwareEntity updateHardware(Hardware hardware);
    void deleteHardware(long hardwareId);
}
```

Responsibilities:

- Create: Insert a new entity into the database.
- Retrieve: Fetch an entity using its ID.
- Update: Modify an existing entity.
- Delete: Remove an entity from the database.

### DAO Implementations

Implementations of DAOs are responsible for carrying out the defined operations. They interact with the database through repositories and adhere to key principles:

- Entity-Specific Operations: Implementations focus solely on operations related to their respective entities.
- Relationship Management: DAOs manage an entity's relationship with its parent where necessary but do not alter child entity fields directly.

Example Implementation: HardwareDAO

```java
@Override
public HardwareEntity createHardware(Hardware hardware) {
    HardwareEntity hardwareEntity = this.hardwareConverter.createEntity(hardware);
    this.hardwareConverter.fillEntity(hardwareEntity, hardware);
    HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerRepository.findById(hardware.getHardwareControllerId())
        .orElseThrow(() -> this.exceptionService.createNotFoundException(HardwareControllerEntity.class, hardware.getHardwareControllerId()));
    hardwareEntity.setHardwareController(hardwareControllerEntity);
    hardwareEntity = this.hardwareRepository.save(hardwareEntity);
    hardwareControllerEntity.getHardware().add(hardwareEntity);
    this.hardwareControllerRepository.save(hardwareControllerEntity);
    return hardwareEntity;
}
```

Principles:

- Repository Usage: DAOs leverage associated repositories for database interactions.
- Entity Retrieval: DAOs utilize the appropriate DAO for needed entities of other types, rather than repositories directly.
- Child-Parent Relationships: Maintained by child entities, indicating updates to their linkage with parents, not the reverse.
- Exception Handling: A NotFoundException is thrown for non-existent entities during operations like updates.

### Handling NotFoundException:

```java
HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerRepository.findById(hardware.getHardwareControllerId())
    .orElseThrow(() -> this.exceptionService.createNotFoundException(HardwareControllerEntity.class, hardware.getHardwareControllerId()));
```

This code snippet shows the approach for throwing a NotFoundException when a required parent entity is missing, ensuring robust error handling.

## Query Services

Query services are used for retrieving entities from the database.

### Typical Pattern:
- The DAO is called to retrieve the entity.
- The entity is converted to a model using the appropriate converter.
- The converted model is returned.

### Note: query services should only return models, not entities.


### DAO vs. Query Services

DAOs focus on direct entity management operations, while the transformation of entities to models and the encapsulation of business logic are responsibilities of the Query Services layer. This separation maintains clarity and focus within the system architecture.
Conclusion

The DAO layer is essential for clean separation of concerns between business logic and data storage, facilitating maintainability, scalability, and ease of updates and enhancements within the system.

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

# Application Configuration Options

This table outlines the configurable properties within the application.yml file for our IoT Automation Framework Accelerator. These settings are designed to provide flexibility and customization of the application's behavior in different environments. Each property can be overridden by specifying the corresponding environment variable, allowing for seamless integration with various deployment pipelines and cloud services. The configuration covers aspects from application naming and logging levels to integration with Azure services and MQTT settings, ensuring a comprehensive setup for development and production environments.

| Property Name                                      | Description                                                                                         | Environment Variable                 |
|----------------------------------------------------|-----------------------------------------------------------------------------------------------------|--------------------------------------|
| `spring.application.name`                          | The name of the Spring application.                                                                 | `APPLICATION_NAME`                   |
| `spring.jpa.generate-ddl`                          | Enables automatic generation of database schema (DDL scripts). Defaults to `true` if not specified. | `JPA_HIBERNATE_GENERATE_DDL`         |
| `spring.jpa.database-platform`                     | Specifies the database platform dialect used by Hibernate.                                          | `JPA_HIBERNATE_DATABASE_PLATFORM`    |
| `spring.jpa.show-sql`                              | Logs SQL statements. Defaults to `false` if not specified.                                          | `JPA_HIBERNATE_SHOW_SQL`             |
| `spring.cloud.azure.keyvault.secret.endpoint`      | URI of the Azure Key Vault.                                                                         | `AZURE_KEYVAULT_URI`                 |
| `logging.level.root`                               | The logging level for the root logger. Defaults to `INFO` if not specified.                         | `ROOT_LOG_LEVEL`                     |
| `logging.level.urbanjungletech`                    | The logging level for `urbanjungletech`. Defaults to `INFO` if not specified.                       | `ROOT_LOG_LEVEL`                     |
| `logging.level.org.hibernate.SQL`                  | The logging level for Hibernate SQL. Defaults to `INFO` if not specified.                           | `SQL_LOG_LEVEL`                      |
| `logging.level.mqtt`                               | The logging level for MQTT. Defaults to `INFO` if not specified.                                    | `MQTT_LOG_LEVEL`                     |
| `azure.keyvault.uri`                               | URI for Azure Key Vault access.                                                                     | `AZURE_KEYVAULT_URI`                 |
| `azure.keyvault.enabled`                           | Enables Azure Key Vault integration. Defaults to `false`.                                           | `AZURE_KEYVAULT_ENABLED`             |
| `azure.keyvault.client-id`                         | Azure Key Vault client ID.                                                                          | `AZURE_KEYVAULT_CLIENT_ID`           |
| `azure.keyvault.client-secret`                     | Secret key for Azure Key Vault client.                                                              | `AZURE_KEYVAULT_CLIENT_SECRET`       |
| `azure.keyvault.tenant-id`                         | Tenant ID for Azure Key Vault access.                                                               | `AZURE_KEYVAULT_TENANT_ID`           |
| `mqtt-rpc.uri`                                     | URI for MQTT RPC server.                                                                            | `SYSTEM_MQTT_URI`                    |
| `mqtt-rpc.queue`                                   | Queue name for MQTT RPC messages.                                                                   | `SYSTEM_MQTT_QUEUE`                  |
| `mqtt-rpc.enabled`                                 | Enables MQTT RPC functionality. Defaults to `true`.                                                 | `SYSTEM_MQTT_ENABLED`                |
| `secure-storage.type`                              | Specifies the type of secure storage being used.                                                    | `SECURE_STORAGE_TYPE`                |
| `development.mqtt.client.enabled`                  | Enables the MQTT client in development environments. Defaults to `true`.                            | `DEV_MQTT_CLIENT_ENABLED`            |
| `development.azure.storageQueue.key`               | Access key for Azure Storage Queue in development.                                                  | `DEV_AZURE_STORAGE_QUEUE_KEY`        |
| `weather.apikey`                                   | API key for accessing the weather service in development environments.                              | `DEV_WEATHER_API_KEY`                |
| `digitaltwins.client-id`                           | Client ID for Azure Digital Twins.                                                                  | `DIGITAL_TWINS_CLIENT_ID`            |
| `digitaltwins.client-secret`                       | Client secret for Azure Digital Twins.                                                              | `DIGITAL_TWINS_CLIENT_SECRET`        |
| `digitaltwins.tenant-id`                           | Tenant ID for Azure Digital Twins.                                                                  | `DIGITAL_TWINS_TENANT_ID`            |
| `digitaltwins.digital-twins-uri`                   | URI for Azure Digital Twins instance.                                                               | `DIGITAL_TWINS_URL`                  |
| `digitaltwins.enabled`                             | Enables Azure Digital Twins integration. Defaults to `false`.                                       | `DIGITAL_TWINS_ENABLED`              |
