package com.sa.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Book implements Serializable {
    private Integer id;
    private String type;
    private String name;
    private String description;
}
