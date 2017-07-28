package com.oonurkuru.query;

import com.oonurkuru.query.enums.QueryCreatorType;
import com.oonurkuru.query.query_creator.QueryCreatorFactory;
import com.oonurkuru.query.query_creator.SpringDataQueryCreator;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

/**
 * Created by Onur Kuru on 28.7.2017.
 */
public class QueryCreatorTest {

    @Test
    public void createQueryCreator() throws InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        SpringDataQueryCreator springDataQueryCreator = (SpringDataQueryCreator) QueryCreatorFactory.create(QueryCreatorType.SPRING_DATA, Tests.class);

        MultivaluedMap<String, String> queryMap = new MultivaluedHashMap<>();
        queryMap.put("page", Collections.singletonList("5"));

        PageRequest pageRequest = springDataQueryCreator.createPageRequest(queryMap);
        Example<Tests> example = springDataQueryCreator.createExample(queryMap);

        Assert.assertNotNull(pageRequest);
        Assert.assertNotNull(example);
    }
}
