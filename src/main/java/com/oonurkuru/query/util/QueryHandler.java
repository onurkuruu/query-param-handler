package com.oonurkuru.query.util;

import com.oonurkuru.query.enums.SortDirection;
import com.oonurkuru.query.exceptions.QueryCreatorException;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Onur Kuru on 28.7.2017.
 */
public abstract class QueryHandler {

    public static Map<String, Object> handleForCriteriaFields(MultivaluedMap<String, String> queryMap, Class<?> targetClass) throws IllegalArgumentException {

        Map<String, Object> criteria = new HashMap<>();
        Integer page = Constants.CRITERIA_PAGE_DEFAULT_VALUE;
        Integer size = Constants.CRITERIA_LIMIT_DEFAULT_VALUE;
        SortDirection direction = Constants.CRITERIA_SORT_DIRECTION_DEFAULT_VALUE;
        List<String> sortBy = new ArrayList<>();

        for (Map.Entry<String, List<String>> entrySet : queryMap.entrySet()) {
            if (FieldUtils.isCriteriaField(entrySet.getKey().toLowerCase())) {

                switch (entrySet.getKey().toLowerCase()) {
                    case Constants.CRITERIA_PAGE:
                        page = (Integer) getValueFromStringList(entrySet.getValue(), page);
                        page = page < Constants.CRITERIA_PAGE_MIN_VALUE ? Constants.CRITERIA_PAGE_DEFAULT_VALUE : page;
                        break;
                    case Constants.CRITERIA_LIMIT:
                        size = (Integer) getValueFromStringList(entrySet.getValue(), size);
                        size = size < Constants.CRITERIA_LIMIT_MIN_VALUE || size > Constants.CRITERIA_LIMIT_MAX_VALUE ? Constants.CRITERIA_LIMIT_DEFAULT_VALUE : size;
                        break;
                    case Constants.CRITERIA_SORT_DIRECTION:
                        direction = (SortDirection) getValueFromStringList(entrySet.getValue(), direction);
                        break;
                    case Constants.CRITERIA_SORT_BY:
                        sortBy = (List<String>) FieldUtils.getExistFieldsInTargetClass(entrySet.getValue(), targetClass);
                        break;
                }
            }
        }
        criteria.put(Constants.CRITERIA_PAGE, page);
        criteria.put(Constants.CRITERIA_LIMIT, size);
        criteria.put(Constants.CRITERIA_SORT_DIRECTION, direction);
        criteria.put(Constants.CRITERIA_SORT_BY, sortBy);

        return criteria;
    }

    private static Object getValueFromStringList(List<String> valueList, Object defaultValue) {

        if (valueList == null) {
            return defaultValue;
        }

        if (defaultValue instanceof Integer) {

            String value = valueList.get(0);
            return StringUtils.isEmpty(value) ? defaultValue : Integer.valueOf(value);

        } else if (defaultValue instanceof Sort.Direction) {

            String value = valueList.get(0);

            if (StringUtils.isEmpty(value)) {
                return defaultValue;
            }

            return (Constants.CRITERIA_DESC_SORT.equalsIgnoreCase(value) ? SortDirection.DESC : SortDirection.ASC);
        }
        return defaultValue;
    }
}
