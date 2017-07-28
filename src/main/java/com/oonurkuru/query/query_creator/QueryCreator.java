package com.oonurkuru.query.query_creator;

import com.oonurkuru.query.criteria.Criteria;
import org.springframework.util.Assert;

/**
 * Created by Onur Kuru on 27.7.2017.
 */

/**
 * QueryCreator handle edilen query param ile oluşturulacak query yöntemleri için temel birimleri oluşturur.
 */
public abstract class QueryCreator {

    private Criteria criteria;
    private Class<?> targetClass;

    public QueryCreator(Criteria criteria, Class<?> targetClass) {
        Assert.notNull(criteria, "Criteria model is required.");
        Assert.notNull(targetClass, "Target Class is required.");
        this.criteria = criteria;
        this.targetClass = targetClass;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }
}
