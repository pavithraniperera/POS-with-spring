package lk.ijse.posbackend.Service;

import java.util.List;

public interface CRUDService<T> extends SuperService{
    void save(T entity) ;
    void update(T entity) ;
    void delete(String id) ;
    List<T> getAll() ;
    T getById(String id) ;
}
