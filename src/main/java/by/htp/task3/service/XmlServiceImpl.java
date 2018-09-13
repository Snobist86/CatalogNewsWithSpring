package by.htp.task3.service;

import by.htp.task3.exception.DAOException;
import by.htp.task3.exception.ServiceException;
import by.htp.task3.dao.XmlParser;
import by.htp.task3.entity.Catalog;

public class XmlServiceImpl implements XmlService {

    private XmlParser xmlParser;

    public XmlServiceImpl() {
    }

    public XmlServiceImpl(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    @Override
    public Catalog readXml() throws ServiceException {
        Catalog catalog;
        try {
            catalog = xmlParser.readXml();
        } catch (DAOException e) {
            throw new ServiceException("Read xml Jaxb Exception", e);
        }
        return catalog;
    }

    @Override
    public void saveXml(Catalog catalog) throws ServiceException {
        try {
            xmlParser.saveXml(catalog);
            System.out.println("Changes in Catalog.xml saved");
        } catch (DAOException e) {
            throw new ServiceException("Save xml Jaxb Exception", e);
        }
    }

}
