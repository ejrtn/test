package com.example.test;

import com.example.test.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {

		System.out.println("test / test");
		System.out.println(userService.loginCheck("test","test"));

		System.out.println("aaa / aaa");
		System.out.println(userService.loginCheck("aaa","aaa"));
	}

}
