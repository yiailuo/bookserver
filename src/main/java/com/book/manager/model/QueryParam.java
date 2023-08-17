//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.book.manager.model;

import java.io.Serializable;
import java.util.Optional;

public class QueryParam<T> implements Serializable {
    private static final long serialVersionUID = 8869417489067924393L;
    private Integer pi;
    private Integer ps;
    private BookPageable page;
    private BookSort sort;
    private Boolean paging = true;
    private T query;
    private String sortparam;

    public QueryParam() {
    }

    public BookPageable getPage() {
        BookPageable page_ = (BookPageable)Optional.ofNullable(this.page).orElse(new BookPageable());
        return page_;
    }

    public void setPage(BookPageable page) {
        this.page = page;
    }

    public T getQuery() {
        return this.query;
    }

    public void setQuery(T query) {
        this.query = query;
    }

    public Boolean getPaging() {
        return this.paging;
    }

    public void setPaging(Boolean paging) {
        this.paging = paging;
    }

    public BookSort getSort() {
        return (BookSort)Optional.ofNullable(this.sort).orElse(new BookSort());
    }

    public void setSort(BookSort sort) {
        this.sort = sort;
    }

    public Integer getPi() {
        return this.pi;
    }

    public void setPi(Integer pi) {
        if (pi != null) {
            this.page.setCurrentPage(pi);
        }

        this.pi = pi;
    }

    public Integer getPs() {
        return this.ps;
    }

    public void setPs(Integer ps) {
        if (ps != null) {
            this.page.setSizePerPage(ps);
        }

        this.ps = ps;
    }

    public String getSortparam() {
        return this.sortparam;
    }

    public void setSortparam(String sortparam) {
        if (sortparam != null) {
            String[] sorts = sortparam.split("\\.");
            if (sorts.length > 1) {
                this.sort.setSortName(sorts[0]);
                if (sorts[1].toUpperCase().startsWith("ASC")) {
                    this.sort.setDirectionMethod("ASC");
                } else {
                    this.sort.setDirectionMethod("DESC");
                }
            }
        }

        this.sortparam = sortparam;
    }
}
