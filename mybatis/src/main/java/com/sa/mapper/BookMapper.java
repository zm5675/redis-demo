package com.sa.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sa.pojo.Book;
import org.springframework.stereotype.Repository;

/**
* @author 张鑫
* @description 针对表【tbl_book】的数据库操作Mapper
* @createDate 2023-04-13 18:42:15
* @Entity com.sa.mybatisplus.pojo.Book
*/
@Repository
public interface BookMapper extends BaseMapper<Book> {

}




