package com.lyz.demo5.service.impl;
import  com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.demo5.dao.db1.TestLyzMapper;
import com.lyz.demo5.model.TestLyz;
import com.lyz.demo5.service.TestLyzService;
import org.springframework.stereotype.Service;

@Service
public class TestLyzServiceImpl extends ServiceImpl<TestLyzMapper, TestLyz> implements TestLyzService {
}
