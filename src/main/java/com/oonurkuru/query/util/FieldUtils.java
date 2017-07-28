package com.oonurkuru.query.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

/**
 * Field'lar ile ilgili işlemler yapıldığı fonksiyonları içerir.
 */
public abstract class FieldUtils {


    /**
     * Verilen field ismini arama kriterleri arasında olup olmadığına bakılır.
     */
    public static boolean isCriteriaField(String fieldName) {
        return Constants.criteriaList.contains(fieldName);
    }

    /**
     * Verilen alan isimlerinin, verilen sınıfda olup olmadığını kontrol eder.
     *
     * @param fieldNames  kontrol edilecek alan isimlerini içerir.
     * @param targetClass alanların bulunduğu sınıfı işaret eder.
     * @return fieldNames arasında sınıfta uygun alanlar bulunursa geri döndürülür.
     */
    public static Iterable<String> getExistFieldsInTargetClass(Iterable<String> fieldNames, Class<?> targetClass) {

        List<String> existFields = new ArrayList<>();

        fieldNames.forEach(fieldName -> {
            try {
                targetClass.getDeclaredField(fieldName);
                existFields.add(fieldName);
            } catch (NoSuchFieldException e) {
            }
        });

        if (existFields.size() == 0) {
            throw new IllegalArgumentException("Fields not found for target class.");
        }

        return existFields;
    }

}
