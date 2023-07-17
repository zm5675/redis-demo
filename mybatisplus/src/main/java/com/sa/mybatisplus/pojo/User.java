package com.sa.mybatisplus.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.springframework.context.annotation.Scope;


@Data
@Scope(value = "property")
public class User {
    @TableId(value = "uid",type = IdType.ASSIGN_ID) // value属性映射到数据库中的字段名
    private Long id;

    private String name;

    private Integer age;

    private String email;

    @TableLogic
    private Integer isDeleted;

}
