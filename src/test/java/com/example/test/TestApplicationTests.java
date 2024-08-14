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

	}
}
