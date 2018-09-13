package by.htp.task3.service;

import by.htp.task3.criteria.Criteria;
import by.htp.task3.entity.News;
import by.htp.task3.exception.ServiceException;

import java.util.List;

public interface CatalogService {

    List<News> find(Criteria criteria) throws ServiceException;
    News add(Criteria criteria) throws ServiceException;
}
