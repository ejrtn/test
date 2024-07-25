package com.example.test;

import com.example.test.notice.Notice;
import com.example.test.notice.NoticeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestApplicationTests {

	@Autowired
	private NoticeService noticeService;

	@Test
	void contextLoads() {

		// text 0~
		int currentPageNo = 10;		//현재 페이지 번호
		int recordCountPerPage = 10;		//한 페이지당 게시되는 게시물 건 수
		int pageSize = 10;		//페이지 리스트에 게시되는 페이지 건수
		String searchText = "AAA";		//검색어

		long now = System.currentTimeMillis();
//		System.out.println(noticeService.noticeListAll(currentPageNo,recordCountPerPage,pageSize,searchText));		//88880, 1197
		System.out.println(noticeService.noticeList(currentPageNo,recordCountPerPage,pageSize,searchText));		//716, 696
		System.out.println(System.currentTimeMillis() - now);
	}

	void insert(){
		for(int i=0;i<50000;i++){
			Notice notice = new Notice();
			notice.setTitle("test "+i);
			notice.setContentData("contentData "+i);
			noticeService.insert(notice);
		}
	}
}
