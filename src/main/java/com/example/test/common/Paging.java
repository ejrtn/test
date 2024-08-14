package com.example.test.common;

public class Paging {

    /**
     * 현재 페이지 번호
     */
    private int currentPageNo;

    /**
     * 한 페이지당 게시되는 게시물 건 수
     */
    private int recordCountPerPage;

    /**
     * 페이지 리스트에 게시되는 페이지 건수
     */
    private int pageSize;

    /**
     * 전체 게시물 건 수
     */
    private int totalRecordCount;

    /**
     * 페이지 개수
     */
    private int totalPageCount;

    /**
     * 페이지 리스트의 첫 페이지 번호
     */
    private int firstPageNoOnPageList;

    /**
     * 페이지 리스트의 마지막 페이지 번호
     */
    private int lastPageNoOnPageList;

    /**
     * 검색어
     */
    private String searchText;

    public Paging(int currentPageNo, int recordCountPerPage, int pageSize, int totalRecordCount, String searchText) {
        this.currentPageNo = currentPageNo;
        this.recordCountPerPage = recordCountPerPage;
        this.pageSize = pageSize;
        this.totalRecordCount = totalRecordCount;
        this.searchText = searchText;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public int getRecordCountPerPage() {
        return recordCountPerPage;
    }

    public void setRecordCountPerPage(int recordCountPerPage) {
        this.recordCountPerPage = recordCountPerPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecordCount() {
        return totalRecordCount;
    }

    public void setTotalRecordCount(int totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }

    public int getTotalPageCount() {
        this.totalPageCount = totalRecordCount%recordCountPerPage == 0 ? totalRecordCount/recordCountPerPage : totalRecordCount/recordCountPerPage + 1;
        return totalPageCount;
    }

    public int getFirstPageNoOnPageList() {
        this.firstPageNoOnPageList = ((currentPageNo-1)/pageSize)*pageSize + 1;
        return firstPageNoOnPageList;
    }

    public int getLastPageNoOnPageList() {

        this.lastPageNoOnPageList = getFirstPageNoOnPageList()-1+pageSize;
        if(lastPageNoOnPageList > getTotalPageCount()){this.lastPageNoOnPageList=getTotalPageCount();}
        return lastPageNoOnPageList;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
