package repo;

import java.util.List;

public interface CrudRepo <T,ID>{
     boolean save(T entity);
     boolean update(T entity);
     boolean delete(ID entity);
     ID search(String id);
     List<T> getAll();
}
