package frentz.daniel.hardwareservice.update;

import java.util.List;

public abstract class AdditionService<T, K> {
    public abstract void additionSideEffects();
    public abstract void updateSideEffects();
    public abstract T update(T t);
    public abstract T add(T t);
    public abstract void delete(T t);
    public abstract T getExisting(T t);
    public void updateList(List<T> updates){

    }
}
