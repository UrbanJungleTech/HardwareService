package frentz.daniel.hardwareservice.service;

@FunctionalInterface
public interface UpdateFunction<T> {
    UpdateCallback update();
}
