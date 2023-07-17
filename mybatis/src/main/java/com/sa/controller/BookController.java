package com.sa.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sa.controller.utils.R;
import com.sa.pojo.Book;
import com.sa.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public R<List<Book>> queryBooks() throws IOException {
        List<Book> list = bookService.list();
//        if (true) throw new IOException("id");
        R r = R.success("查询成功", list);
        return r;
    }
    @PostMapping
    public R<Boolean> save(@RequestBody Book book){
        boolean save = bookService.save(book);
        System.out.println(save);
        return R.success(save ? "添加成功":"添加失败",null);
    }
    @PutMapping
    public R<Boolean> update(@RequestBody Book book){
        boolean update = bookService.updateById(book);
        return R.success(update ? "修改成功":"修改失败",null);
    }
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable("id") Integer id){
        boolean b = bookService.removeById(id);
        return R.success(b ? "删除成功":"删除失败",null);
    }
    @GetMapping("/{id}")
    public R<List<Book>> getById(@PathVariable("id") Integer id){

        List<Book> books = bookService.listByIds(Arrays.asList(id));

        return R.success("查询成功",books);
    }


    @GetMapping("/{current}/{pageSize}")
    public R<IPage<Book>> getBookByPage(@PathVariable("current") Integer current,@PathVariable("pageSize") Integer pageSize,Book book){
        if (current < 0)
            current = 0;
        System.out.println("abere");
        LambdaUpdateWrapper<Book> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.like(StringUtils.isNotBlank(book.getName()),Book::getName,book.getName());
        lambdaUpdateWrapper.like(StringUtils.isNotBlank(book.getDescription()),Book::getDescription,book.getDescription());
        lambdaUpdateWrapper.like(StringUtils.isNotBlank(book.getType()),Book::getType,book.getType());
        Page<Book> page = new Page<>(current,pageSize);
        bookService.page(page,lambdaUpdateWrapper);
        if (current > page.getPages()){
           page = bookService.page(new Page<Book>(page.getPages(),pageSize),lambdaUpdateWrapper);
        }
        return R.success("查询成功",page);
    }

}
