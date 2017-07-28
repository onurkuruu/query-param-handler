package com.oonurkuru.query.query_creator;

import com.oonurkuru.query.criteria.Criteria;
import com.oonurkuru.query.criteria.CriteriaBuilder;
import com.oonurkuru.query.enums.QueryCreatorType;

/**
 * Created by Onur Kuru on 27.7.2017.
 */

/**
 * istenilen tipte QueryCreator üretilmesi sağlanır.
 */
public class QueryCreatorFactory {

    /**
     * istenilen tipte QueryCreator oluşturur.
     *
     * @param queryCreatorType Oluşturulması istenen tipi belirler.
     * @param criteria         sorguları oluştururken sayfalama, sıralama vs. özelliklerin dikkate alınmasını sağlar.
     * @param targetClass      Sorguların oluşturulacağı entity sınıfını belirler.
     */
    public static QueryCreator create(QueryCreatorType queryCreatorType, Criteria criteria, Class<?> targetClass) throws IllegalAccessException, InstantiationException {
        switch (queryCreatorType) {
            case SPRING_DATA:
                return new SpringDataQueryCreator(criteria, targetClass);
            default:
                throw new IllegalArgumentException("Not supported type.");
        }
    }

    public static QueryCreator create(QueryCreatorType queryCreatorType, Class<?> targetClass) throws InstantiationException, IllegalAccessException {
        Criteria criteria = new CriteriaBuilder().build();
        return create(queryCreatorType, criteria, targetClass);
    }
}
