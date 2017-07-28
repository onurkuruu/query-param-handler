package com.oonurkuru.query.criteria;

/**
 * Created by Onur Kuru on 27.7.2017.
 */

/**
 * Sorgulama kriterlerinizi belirlemenizi sağlar.
 * <p>
 * Sorgularda sayfalama, büyük-küçük karakter hassasiyeti ya da sıralama ölçütü kriterlerinin dikkate alınıp alınmayacağını
 * bildirmenizi sağlar.
 * <p>
 * pageable true değeri için sayfalama yapılacağı
 * ignoreCase true değeri için harf hassasiyetinin ihmal edileceği
 * sortable true değeri için sıralama ölçütünün ASC ya da DESC olacağı belirtilir.
 */
public class Criteria {

    private boolean pageable;
    private boolean ignoreCase;
    private boolean sortable;

    public boolean isPageable() {
        return pageable;
    }

    public void setPageable(boolean pageable) {
        this.pageable = pageable;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }
}
