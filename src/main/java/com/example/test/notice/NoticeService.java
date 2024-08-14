package com.example.test.notice;

import com.example.test.common.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public String noticeList(int currentPageNo,int recordCountPerPage, int pageSize, String searchText){
        Paging paging = new Paging(currentPageNo, recordCountPerPage, pageSize, Math.toIntExact(noticeRepository.noticeTotal(searchText)), searchText);

        String result = "{ \"result\" : \n";
        result += "\t{ \"paging\" : {\n";
        result += "\t\t\t\"currentPageNo\" : "+currentPageNo+",\n";
        result += "\t\t\t\"recordCountPerPage\" : "+recordCountPerPage+",\n";
        result += "\t\t\t\"totalRecordCount\" : "+noticeRepository.noticeTotal(searchText)+",\n";
        result += "\t\t\t\"totalPageCount\" : "+paging.getTotalPageCount()+",\n";
        result += "\t\t\t\"firstPageNoOnPageList\" : "+paging.getFirstPageNoOnPageList()+",\n";
        result += "\t\t\t\"lastPageNoOnPageList\" : "+paging.getLastPageNoOnPageList()+",\n";
        result += "\t\t\t\"searchText\" : "+searchText+",\n";
        result += "\t},\n";

        result += "\t{ \"data\" : [\n";
        List<Notice> list = noticeRepository.noticeList(currentPageNo,recordCountPerPage,searchText);
        for(int i=0;i<list.size();i++) {
            result += "\t\t{\n";
            result += "\t\t\t\"id\":"+list.get(i).getNoticeId()+",\n";
            result += "\t\t\t\"title\":"+list.get(i).getTitle()+",\n";
            result += "\t\t\t\"contentData\":"+list.get(i).getContentData()+",\n";
            result += "\t\t}";
            if(i > list.size()-1) result += ",";
            result += "\n";
        }
        result += "\t]}\n";
        result += "}";
        return result;
    }

    public String noticeListAll(int currentPageNo,int recordCountPerPage, int pageSize, String searchText){
        Paging paging = new Paging(currentPageNo, recordCountPerPage, pageSize, Math.toIntExact(noticeRepository.noticeTotal(searchText)), searchText);

//        String result = "{ \"result\" : \n";
//        result += "\t{ \"paging\" : {\n";
//        result += "\t\t\t\"currentPageNo\" : "+currentPageNo+",\n";
//        result += "\t\t\t\"recordCountPerPage\" : "+recordCountPerPage+",\n";
//        result += "\t\t\t\"totalRecordCount\" : "+noticeRepository.noticeTotal(searchText)+",\n";
//        result += "\t\t\t\"totalPageCount\" : "+paging.getTotalPageCount()+",\n";
//        result += "\t\t\t\"firstPageNoOnPageList\" : "+paging.getFirstPageNoOnPageList()+",\n";
//        result += "\t\t\t\"lastPageNoOnPageList\" : "+paging.getLastPageNoOnPageList()+",\n";
//        result += "\t\t\t\"searchText\" : "+searchText+",\n";
//        result += "\t},\n";
//
//        result += "\t{ \"data\" : [\n";
        List<Notice> list = noticeRepository.noticeListAll(searchText);
//        for(int i=0;i<list.size();i++ ) {
//            result += "\t\t{\n";
//            result += "\t\t\t\"id\":"+list.get(i).getNoticeId()+",\n";
//            result += "\t\t\t\"title\":"+list.get(i).getTitle()+",\n";
//            result += "\t\t\t\"contentData\":"+list.get(i).getContentData()+",\n";
//            result += "\t\t}";
//            if(i > list.size()-1) result += ",";
//            result += "\n";
//        }
//        result += "\t]}\n";
//        result += "}";
        return "result";
    }

    public void insert(Notice notice){
        noticeRepository.save(notice);
    }
}
