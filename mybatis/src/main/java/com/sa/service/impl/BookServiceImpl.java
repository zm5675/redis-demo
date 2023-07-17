package com.sa.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sa.mapper.BookMapper;
import com.sa.pojo.Book;
import com.sa.service.BookService;
import org.springframework.stereotype.Service;

/**
* @author 张鑫
* @description 针对表【tbl_book】的数据库操作Service实现
* @createDate 2023-04-13 18:42:15
*/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

}




