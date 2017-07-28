package com.oonurkuru.query.util;

import com.oonurkuru.query.exceptions.QueryCreatorException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

/**
 * Methodlar ile ilgili işlemleri gerçekleştirmek için tasarlanmıştır.
 */
public abstract class MethodUtils {

    /**
     * Verilen parametre ismine uygun set metodunun bulunmasını sağlar
     *
     * @param field       bulunması istenen set metotu
     * @param targetClass hangi sınıfta aranacak
     * @return metot bulunursa geri döndürülür.
     */
    public static Method findSetMethod(String field, Class targetClass) {

        Method method;
        try {
            method = new PropertyDescriptor(field, targetClass).getWriteMethod();
        } catch (IntrospectionException e) {
            throw new QueryCreatorException("Set method not found in target class.");
        }
        return method;
    }

    /**
     * Verilen değerin ilgili alana yüklenmesini sağlar
     *
     * @param field  hangi alana yüklenecek
     * @param value  yüklenecek değer
     * @param object hangi sınıfta
     */
    public static <T> void setValue(String field, Object value, T object) throws QueryCreatorException {
        Method method = findSetMethod(field, object.getClass());
        try {
            method.invoke(object, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new QueryCreatorException("Set value error.");
        }
    }
}
