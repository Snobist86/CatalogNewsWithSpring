package by.htp.task3.service;

import by.htp.task3.criteria.Criteria;
import by.htp.task3.dao.CatalogDAO;
import by.htp.task3.entity.News;
import by.htp.task3.exception.DAOException;
import by.htp.task3.exception.ServiceException;

import java.util.Collections;
import java.util.List;

public class CatalogServiceImpl implements CatalogService {

    private CatalogDAO catalogDAO;

    public CatalogServiceImpl(CatalogDAO catalogDAO){
        this.catalogDAO = catalogDAO;
    }

    @Override
    public List<News> find(Criteria criteria) throws ServiceException {
        if (!Validator.criteriaValidator(criteria)) {
            System.out.println("The entered data does not correspond to the format");
            return Collections.emptyList();
        }

        List<News> news;
        try {
            news = catalogDAO.find(criteria);
        } catch (DAOException e) {
            throw new ServiceException("Find operation in xml throw catalogDAO ending with Exception", e);
        }
        return news;
    }

    @Override
    public News add(Criteria criteria) throws ServiceException {
        if (!Validator.criteriaValidator(criteria)) {
            System.out.println("The entered data does not correspond to the format");
            return null;
        }

        News news;
        try {
            news = catalogDAO.add(criteria);
        } catch (DAOException e) {
            throw new ServiceException("Add operation to xml throw catalogDAO ending with Exception", e);
        }
        if (news == null) {
            System.out.println("There are not enough criteria for a specific add-on");
            return null;
        }
        return news;
    }
}
