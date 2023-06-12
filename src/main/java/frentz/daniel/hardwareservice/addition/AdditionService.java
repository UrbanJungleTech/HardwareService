package frentz.daniel.hardwareservice.addition;


import java.util.List;

public interface AdditionService <T>{
    T create(T t);
    void delete(long id);
    T update(long id, T t);
    List<T> updateList(List<T> models);
}
