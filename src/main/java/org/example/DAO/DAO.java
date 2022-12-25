package org.example.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    public T getObject(ResultSet resultSet) throws SQLException;
    public List<T> findAll();
    public int write(T obj) throws SQLException;
    public int delete(T obj);
    public int update(T obj) throws SQLException;

}
