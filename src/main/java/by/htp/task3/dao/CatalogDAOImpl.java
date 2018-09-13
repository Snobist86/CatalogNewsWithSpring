package by.htp.task3.dao;

import by.htp.task3.criteria.Criteria;
import by.htp.task3.entity.Category;
import by.htp.task3.entity.News;
import by.htp.task3.entity.SubCategory;
import by.htp.task3.exception.DAOException;
import by.htp.task3.criteria.SearchNewsCriteria;
import by.htp.task3.entity.BaseEntity;
import by.htp.task3.entity.Catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CatalogDAOImpl implements CatalogDAO {

private XmlParser xmlParser;

    public CatalogDAOImpl() {
    }

    public void setXmlParser(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    public XmlParser getXmlParser() {
        return xmlParser;
    }

    @Override
    public List<News> find(Criteria criteria) throws DAOException {
        Catalog catalog = xmlParser.readXml();
        List<News> result = new ArrayList<>();
        for (Category category: catalog.getCategory()) {
            if(!isValidName(category, criteria, SearchNewsCriteria.News.CATEGORY_NAME))
                continue;
            for(SubCategory subCategory: category.getSubCategory()){
                if(!isValidName(subCategory, criteria, SearchNewsCriteria.News.SUBCATEGORY_NAME))
                    continue;

                for (News item: subCategory.getNews()) {
                    if(isValidNews(item, criteria)){
                        result.add(item);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public News add(Criteria criteria) throws DAOException {
        Catalog catalog = xmlParser.readXml();
        List<Category> categories = catalog.getCategory();

        Category addedCategory = getNextSearchParameter(categories,
                criteria, SearchNewsCriteria.News.CATEGORY_NAME);
        if(addedCategory == null){
            return  null;
        }

        SubCategory addedSubCategory = getNextSearchParameter(addedCategory.getSubCategory(),
                criteria, SearchNewsCriteria.News.SUBCATEGORY_NAME);
        if(addedSubCategory == null){
            return  null;
        }

        News publicNews = new News();

        for (Map.Entry<SearchNewsCriteria.News, Object> addedNews :
                criteria.getCriteria().entrySet()) {
            switch (addedNews.getKey()){
                case NAME:
                    publicNews.setNewsName((String)addedNews.getValue());
                    break;
                case AUTHOR:
                    publicNews.setAuthor((String)addedNews.getValue());
                    break;
                case NEWS_BODY:
                    publicNews.setNewsBody((String)addedNews.getValue());
                    break;
                case DATE_OF_ISSUE:
                    publicNews.setDate((String)addedNews.getValue());
                    break;
            }
        }

        int indexOfCategory = categories.indexOf(addedCategory);
        int indexOfSubCategory = categories.get(indexOfCategory).getSubCategory().indexOf(addedSubCategory);
        categories.get(indexOfCategory).getSubCategory().get(indexOfSubCategory).getNews().add(0, publicNews);
        xmlParser.saveXml(catalog);
        System.out.println("News successfully added");

        return publicNews;
    }

    private boolean isValidName(BaseEntity item, Criteria criteria, SearchNewsCriteria.News nameSearchCriteria){

        if(criteria.getCriteria().containsKey(nameSearchCriteria)) {
            Object value = criteria.getCriteria().get(nameSearchCriteria);
            return !item.getName().equals(value);
        }
        return false;
    }

    private boolean isValidNews(News parameterSearchNews, Criteria criteria){
        for (Map.Entry<SearchNewsCriteria.News, Object> cr:
                criteria.getCriteria().entrySet()) {
            switch (cr.getKey()) {
                case NAME:
                    if(!parameterSearchNews.getName().equals(cr.getValue())){
                        return false;
                    }
                    break;
                case AUTHOR:
                    if (!parameterSearchNews.getAuthor().equals(cr.getValue())){
                        return false;
                    }
                    break;
                case NEWS_BODY:
                    if (!parameterSearchNews.getNewsBody().equals(cr.getValue())){
                        return false;
                    }
                    break;
                case DATE_OF_ISSUE:
                    if (!parameterSearchNews.getDate().equals(cr.getValue())){
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    private <T extends BaseEntity> T getNextSearchParameter(List<T> items, Criteria criteria,
                                                            SearchNewsCriteria.News nameKey){
        Map<SearchNewsCriteria.News, Object> searchCriteria = criteria.getCriteria();
        if(!searchCriteria.containsKey(nameKey)){
            return  null;
        }
        Object targetCategoryName =searchCriteria.get(nameKey);
        for (T item : items) {
            if(item.getName().equals(targetCategoryName)){
                return item;
            }
        }
        return null;
    }
}
