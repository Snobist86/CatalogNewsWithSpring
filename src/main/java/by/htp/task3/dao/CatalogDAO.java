package by.htp.task3.dao;

import by.htp.task3.criteria.Criteria;
import by.htp.task3.entity.News;
import by.htp.task3.exception.DAOException;

import java.util.List;

public interface CatalogDAO {

    List<News> find(Criteria criteria) throws DAOException;
    News add(Criteria criteria) throws DAOException;
}
