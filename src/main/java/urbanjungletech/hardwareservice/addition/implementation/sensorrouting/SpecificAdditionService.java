package urbanjungletech.hardwareservice.addition.implementation.sensorrouting;

public interface SpecificAdditionService <Model> {
    void create(Model routerModel);
    void delete(long id);
    void update(long id, Model routerModel);
}
