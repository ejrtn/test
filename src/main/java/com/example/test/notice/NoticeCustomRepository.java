package com.example.test.notice;

import java.util.List;

public interface NoticeCustomRepository {

    List<Notice> noticeList(int currentPageNo, int recordCountPerPage, String searchText);
    List<Notice> noticeListAll(String searchText);
    Long noticeTotal(String searchText);
}
