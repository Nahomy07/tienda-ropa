package org.nahomy.services;

import org.nahomy.dao.IGenericDAO;

import java.util.List;

public interface IGenericService<T> extends IGenericDAO<T>
{
    List<T> getAll();

    void deleteAll();

    T getById(int i);

    T getId(long i);

    T getByName(String name);
}
