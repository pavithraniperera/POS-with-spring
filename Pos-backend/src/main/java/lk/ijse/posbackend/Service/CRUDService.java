package lk.ijse.posbackend.Service;

import java.util.List;

public interface CRUDService<T> extends SuperService{
    void save(T entity) ;
    void update(String id,T entity) ;
    void delete(String id) ;
    List<T> getAll() ;

}
