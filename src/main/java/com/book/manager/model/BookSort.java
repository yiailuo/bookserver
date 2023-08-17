//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.book.manager.model;

import org.springframework.data.domain.Sort.Direction;

import java.io.Serializable;
import java.util.Optional;

public class BookSort implements Serializable {
    private static final long serialVersionUID = -6791786000485000238L;
    private String sortName;
    private String directionMethod;

    public BookSort() {
    }

    public String getSortName() {
        return (String)Optional.ofNullable(this.sortName).orElse("createtime");
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public Direction getDirection() {
        if (this.directionMethod == null) {
            return Direction.DESC;
        } else {
            return this.directionMethod.toUpperCase().startsWith("ASC") ? Direction.ASC : Direction.DESC;
        }
    }

    public String getDirectionMethod() {
        return this.directionMethod;
    }

    public void setDirectionMethod(String directionMethod) {
        this.directionMethod = directionMethod;
    }
}
