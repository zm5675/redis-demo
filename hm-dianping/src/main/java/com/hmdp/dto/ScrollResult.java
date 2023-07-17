package com.hmdp.dto;

import lombok.Data;
/**
 * 滚动分页数据
 */
import java.util.List;

@Data
public class ScrollResult {
    private List<?> list;
    private Long minTime;
    private Integer offset;
}
