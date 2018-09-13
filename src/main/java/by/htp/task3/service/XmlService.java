package by.htp.task3.service;

import by.htp.task3.exception.ServiceException;
import by.htp.task3.entity.Catalog;

public interface XmlService {

    Catalog readXml() throws ServiceException;
    void saveXml(Catalog catalog) throws ServiceException;
}
