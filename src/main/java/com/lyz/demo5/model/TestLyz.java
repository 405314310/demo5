package com.lyz.demo5.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "testlyz")
public class TestLyz {

    private String id;
    private String name;
}
