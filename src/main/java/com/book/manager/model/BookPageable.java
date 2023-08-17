//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.book.manager.model;

import java.io.Serializable;

public class BookPageable implements Serializable {
    private static final long serialVersionUID = -223176660059002691L;
    private Integer currentPage = 0;
    private Integer sizePerPage = 10;

    public BookPageable() {
    }

    public Integer getCurrentPage() {
        return this.currentPage > 0 ? this.currentPage - 1 : 0;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getSizePerPage() {
        return this.sizePerPage;
    }

    public void setSizePerPage(Integer sizePerPage) {
        this.sizePerPage = sizePerPage;
    }
}
