package com.sa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sa.mapper.BookMapper;
import com.sa.pojo.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = MybatisApplication.class)
class MybatisApplicationTests {
	@Autowired
	private BookMapper bookMapper;

	@Test
	void contextLoads() {
		List<Book> books = bookMapper.selectList(null);
		books.stream().forEach(System.out::println);

	}
	@Test
	void contextPage() {
		Page<Book> page = bookMapper.selectPage(new Page<>(1, 2), null);
		System.out.println("page = " + page);
		page.getRecords().stream().forEach(System.out::println);

	}

}
