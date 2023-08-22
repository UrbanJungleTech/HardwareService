package urbanjungletech.hardwareservice.service;

@FunctionalInterface
public interface UpdateFunction<T> {
    UpdateCallback update();
}
