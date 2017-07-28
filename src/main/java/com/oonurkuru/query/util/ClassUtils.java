package com.oonurkuru.query.util;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

import com.oonurkuru.query.exceptions.QueryCreatorException;

/**
 * Classlar ile ilgili işlemleri içerir
 */
public abstract class ClassUtils {

    /**
     * Verilen sınıf tipinde bir nesne oluşturur ve geri döndürülür.
     */
    public static <T> T getNewInstance(Class<?> clazz) {
        T newInstance;

        try {
            if (clazz.isMemberClass()) {
                throw new IllegalArgumentException("Inner classes not supported.");
            } else {
                newInstance = (T) clazz.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new QueryCreatorException("New Instance of target class not created.");
        }

        return newInstance;
    }

}
