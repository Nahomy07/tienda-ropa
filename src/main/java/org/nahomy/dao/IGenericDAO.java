package org.nahomy.dao;

import java.util.Map;
import java.util.Objects;
import java.util.List;

public interface IGenericDAO<T>
{
    public  T get(Class<T> cl, Integer id);

    T get(Class<T> cl, long id);

    public T save(T object);

    public void  update(T object);

    public void delete(T object);

    public List<T> query(String hsql, Map<String, Objects> params);
}
