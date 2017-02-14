package com.xmg.p2p.base.query;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter@Setter
public class PageResult {
    private List<?> listData;
    private Integer totalCount;
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPage;
    private Integer prevPage;
    private Integer nextPage;

    public static PageResult empty(Integer pageSize) {
        return new PageResult(Collections.emptyList(), 0 , 1, pageSize);
    }


    public PageResult(List<?> listData, Integer totalCount, Integer currentPage, Integer pageSize) {
        this.listData = listData;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;

        totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        if (totalPage == 0) {
            totalPage = 1;
        }
        prevPage = currentPage > 1 ? currentPage - 1 : 1;
        nextPage = currentPage < totalPage ? currentPage + 1 : totalPage;

    }

}

