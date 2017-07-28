package com.oonurkuru.query.query_creator;

import com.oonurkuru.query.criteria.Criteria;
import com.oonurkuru.query.enums.SortDirection;
import com.oonurkuru.query.exceptions.QueryCreatorException;
import com.oonurkuru.query.util.ClassUtils;
import com.oonurkuru.query.util.Constants;
import com.oonurkuru.query.util.MethodUtils;
import com.oonurkuru.query.util.QueryHandler;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.ws.rs.core.MultivaluedMap;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Onur Kuru on 27.7.2017.
 */

/**
 * Gelen Query paramların düzenlenerek spring data özelliklerine uygun sorgu ve sınıfların oluşturulmasını sağlar.
 */
public class SpringDataQueryCreator extends QueryCreator {

    private ExampleMatcher exampleMatcher;

    public SpringDataQueryCreator(Criteria criteria, Class<?> targetClass) {
        super(criteria, targetClass);
        initializeExampleMatcher();
    }

    /**
     * Spring data example matcher nesnesi kullanılarak sorgu kriterlerinin belirtilemesi sağlanır. Bu kriterleri belirlerken
     * kullanıcının oluşturmuş olduğu criteria nesnesinde faydalanır.
     * harf hassasiyetinin ihmal et, null değerleri getir vs.
     */
    private void initializeExampleMatcher() {
        exampleMatcher = ExampleMatcher.matching().withIncludeNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        if (this.getCriteria().isIgnoreCase()) {
            exampleMatcher = exampleMatcher.withIgnoreCase();
        }
    }

    public PageRequest createPageRequest(Integer page, Integer limit) {
        return new PageRequest(page, limit);
    }

    public PageRequest createPageRequest(Integer page, Integer limit, Sort.Direction direction, String... sortBy) {
        return new PageRequest(page, limit, direction, sortBy);
    }

    /**
     * Sayafalama kriteri isteniyorsa, sprind data'da bunu sağlayan PageRequest sınıfı oluşturulur.
     *
     * @param queryMap içerisindeki page, limit, sort değerleri alınarak PageRequest nesnesi oluşturulur.
     */
    public PageRequest createPageRequest(MultivaluedMap<String, String> queryMap) throws QueryCreatorException{

        if (!this.getCriteria().isPageable()) {
            return null;
        }

        Map<String, Object> criteria = QueryHandler.handleForCriteriaFields(queryMap, getTargetClass());
        Integer page = (Integer) criteria.get(Constants.CRITERIA_PAGE);
        Integer size = (Integer) criteria.get(Constants.CRITERIA_LIMIT);
        SortDirection sortDirection = (SortDirection) criteria.get(Constants.CRITERIA_SORT_DIRECTION);
        List<String> sortBy = (List<String>) criteria.get(Constants.CRITERIA_SORT_BY);

        if (!this.getCriteria().isSortable() || sortBy.size() == 0) {
            return createPageRequest(page, size);
        }

        Sort.Direction springSortDirection = sortDirection == SortDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;

        return createPageRequest(page, size, springSortDirection, sortBy.toArray(new String[sortBy.size()]));
    }


    /**
     * Spring data example sınıfı kullanılarak entity alanları için sorgular oluşturulması sağlanır.
     *
     * @param queryMap ile bulunan sınıf alanları example sınıfına kriter olarak eklenir ve sorguya dahil olması saplanır.
     */
    public Example createExample(MultivaluedMap<String, String> queryMap) throws QueryCreatorException {

        Object targetObject = ClassUtils.getNewInstance(this.getTargetClass());

        List<String> namesOfFields = Arrays.stream(this.getTargetClass().getDeclaredFields())
                .map(Field::getName).collect(Collectors.toList());

        for (Map.Entry<String, List<String>> entrySet : queryMap.entrySet()) {
            if (namesOfFields.contains(entrySet.getKey())) {
                namesOfFields.remove(entrySet.getKey());
                MethodUtils.setValue(entrySet.getKey(), entrySet.getValue().get(0), targetObject);
            }
        }

        exampleMatcher = exampleMatcher.withIgnorePaths(namesOfFields.toArray(new String[namesOfFields.size()]));

        return Example.of(targetObject, exampleMatcher);
    }


}
