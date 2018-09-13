package by.htp.task3.dao;

import by.htp.task3.exception.DAOException;
import by.htp.task3.entity.Catalog;
import by.htp.task3.util.Constant;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class XmlParserImpl implements XmlParser {

    public XmlParserImpl() {
    }

    @Override
    public Catalog readXml() throws DAOException {
        String xmlSource = System.getProperty("user.dir") + Constant.pathCatalog;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Catalog) unmarshaller.unmarshal(new File(xmlSource));
        } catch (Exception e) {
            throw new DAOException("JAXBContext exception", e);
        }
    }

    @Override
    public void saveXml(Catalog catalog) throws DAOException {
        JAXBContext jaxbContext;
        String xmlTarget = System.getProperty("user.dir") + Constant.pathCatalog;
        try (OutputStream outputStream = new FileOutputStream(xmlTarget)) {
            jaxbContext = JAXBContext.newInstance(Catalog.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(catalog, outputStream);
        } catch (JAXBException | IOException e) {
            throw new DAOException("Marshaller error", e);
        }
    }
}