package com.oonurkuru.query.criteria;

import org.springframework.util.Assert;

/**
 * Created by Onur Kuru on 27.7.2017.
 */

/**
 * Sorgu kriterleri oluşturmak için kullanılan sınıf
 * Varsayılan olarak kriterler; pageable=true, ignoreCase=true, sortable= true olarak atanır.
 */
public class CriteriaBuilder {

    private Criteria criteria;

    public CriteriaBuilder() {
        this.criteria = new Criteria();
        this.criteria.setPageable(true);
        this.criteria.setIgnoreCase(true);
        this.criteria.setSortable(true);
    }

    /**
     * Pageable değerinin modele set edilmesini sağlar
     *
     * @param isPageable modele atanacak değer, true değeri için sorgularda sayfalama özelliği olacaktır.
     * @return CriteriaBuilder modelin zincir fonksiyonlar ile oluşturulmasını sağlamak için döndürülür.
     */
    public CriteriaBuilder isPageable(boolean isPageable) {
        Assert.notNull(isPageable, "Parameter is required.");
        this.criteria.setPageable(isPageable);
        return this;
    }

    /**
     * Harf hassasiyetinin olup olmayacağı bildirilir.
     *
     * @param ignoreCase modele atanacak değer, true değeri için harf hassasiyetinin dikkate alınmayacağı bildilir.
     * @return CriteriaBuilder modelin zincir fonksiyonlar ile oluşturulmasını sağlamak için döndürülür.
     */
    public CriteriaBuilder ignoreCase(boolean ignoreCase) {
        Assert.notNull(ignoreCase, "Parameter is required.");
        this.criteria.setIgnoreCase(ignoreCase);
        return this;
    }

    /**
     * Sıralama ölçütünün dikkate alınıp alınmayacağını belirtir.
     *
     * @param sortable true değeri için sıralama kuralları işleyecektir.
     * @return CriteriaBuilder modelin zincir fonksiyonlar ile oluşturulmasını sağlamak için döndürülür.
     */
    public CriteriaBuilder sortable(boolean sortable) {
        Assert.notNull(sortable, "Parameter is required.");
        this.criteria.setSortable(sortable);
        return this;
    }

    /**
     * Criteria modeline setlenen değer ile elde edilen model geri döndürülür
     */
    public Criteria build() {
        return this.criteria;
    }
}
