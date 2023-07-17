package com.sa;

import com.sa.pojo.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisDemoTest {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testString(){
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        stringObjectValueOperations.set("name","张三");
        Object sa = redisTemplate.opsForValue().get("name");
        System.out.println(sa);
    }
   @Test
    public void testObj(){
        Book book = new Book();
        book.setName("完美世界");
        book.setDescription("玄幻小说");
        book.setType("aa");
        book.setId(1000);
        redisTemplate.opsForValue().set("book",book);
        Book book1 = (Book) redisTemplate.opsForValue().get("book");
        System.out.println(book1);
    }
}
