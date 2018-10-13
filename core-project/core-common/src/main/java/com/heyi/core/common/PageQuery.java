package com.heyi.core.common;

import java.io.Serializable;

public class PageQuery implements Serializable {
    private Integer pageSize = 10;
    private Integer pageNumber = 0;

    public Integer getPageSize() {
        if (pageSize == null || pageSize < 0) pageSize = 10;
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize < 0) pageSize = 10;
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        if( pageNumber!=null && pageNumber>=1 ){
            pageNumber=pageNumber-1;
        }else {
            pageNumber=0;
        }
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        if (pageNumber == null || pageNumber < 0) pageNumber = 0;
        this.pageNumber = pageNumber;
    }
}
