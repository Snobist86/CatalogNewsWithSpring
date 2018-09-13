package by.htp.task3.dao;

import by.htp.task3.entity.Catalog;
import by.htp.task3.exception.DAOException;

public interface XmlParser {

    Catalog readXml() throws DAOException;
    void saveXml(Catalog catalog) throws DAOException;
}
