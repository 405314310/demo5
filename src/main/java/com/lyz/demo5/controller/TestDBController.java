package com.lyz.demo5.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.lyz.demo5.dao.db1.TestLyzMapper;
import com.lyz.demo5.dao.db2.PermissionDao;
import com.lyz.demo5.model.Department;
import com.lyz.demo5.model.Menu;
import com.lyz.demo5.model.Test;
import com.lyz.demo5.model.TestLyz;
import com.lyz.demo5.model.VO.MenuVO;
import com.lyz.demo5.service.DepartmentService;
import com.lyz.demo5.service.TestLyzService;
import com.lyz.demo5.utils.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/testDB")
public class TestDBController {

    @Resource
    private DepartmentService departmentService;
    @Resource
    private PermissionDao permissionDao;
    @Resource
    private ExcelUtils excelUtils;
    @Resource
    private TestLyzService testLyzService;


    @GetMapping("/db1")
    public Object db1(HttpServletResponse res) throws Exception {

        List<Department> departmentList = departmentService.selectAll();


        HSSFWorkbook workbook =  excelUtils.writeExcel(departmentList,Department.class);
        HSSFWorkbook workbook1 = new HSSFWorkbook();
        workbook1 = workbook;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss_SS");
        String dateStr = simpleDateFormat.format(date);
        OutputStream os = new FileOutputStream("E://file/department"+dateStr+".xls");
        workbook.write(os);
        os.flush();
        os.close();

        String fileName = "department"+dateStr+".xls";

        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"),"iso-8859-1"));
        ServletOutputStream sos = res.getOutputStream();
        workbook1.write(sos);
        sos.flush();
        sos.close();

        return null;
    }


    @GetMapping("/db2")
    public Object db2(){
       List list = testLyzService.list();
       return list;
    }

    public void simpleWrite(){
        List<Department> departmentList = new ArrayList<>();
        /*
        * 第二次提交demo
        *
        * */
        Department d1 = new Department();
        d1.setName("d1");
        d1.setId("id1");
        Department d2 = new Department();
        d2.setName("d不闷2");
        d2.setId("id2");
        departmentList.add(d1);
        departmentList.add(d2);


        String filePath = "E://file/lyzTest1.xlsx";
        Test t = new Test();


        EasyExcel.write(filePath,Department.class).sheet("first").doWrite(departmentList);
    }

    public static void main(String[] args) {
        TestDBController tb = new TestDBController();
        List<Menu> menuList = tb.initialization();
        List<MenuVO> menuVOList = new ArrayList<>();
        for(Menu menu :menuList){
            MenuVO menuVO = new MenuVO();
            menuVO.setId(menu.getId());
            menuVO.setName(menu.getName());
            menuVO.setParentId(menu.getParentId());
            menuVO.setUrl(menu.getUrl());
            menuVO.setMenuVOList(new ArrayList<>());
            menuVOList.add(menuVO);
        }

        for (MenuVO menuVO:menuVOList){
            if(menuVO.getParentId().equals("0")){
                tb.getChildren(menuVO,menuVOList);
                System.out.println(menuVO.getMenuVOList().get(2).getMenuVOList().size());
            }
        }
    }

    public void getChildren(MenuVO menuVO1,List<MenuVO> menuVOList){
        for(MenuVO menuVO: menuVOList){
            if(menuVO.getParentId().equals(menuVO1.getId())){
                menuVO1.getMenuVOList().add(menuVO);
                getChildren(menuVO,menuVOList);
            }
        }
    }

    public List<Menu> initialization(){
        Menu m1 = new Menu();
        m1.setId("1");
        m1.setName("一级主菜单");
        m1.setParentId("0");
        m1.setUrl("test");
        Menu m2 = new Menu();
        m2.setId("2");
        m2.setName("二级菜单A");
        m2.setParentId("1");
        m2.setUrl("test");
        Menu m3 = new Menu();
        m3.setId("3");
        m3.setName("二级菜单B");
        m3.setParentId("1");
        m3.setUrl("test");
        Menu m4 = new Menu();
        m4.setId("4");
        m4.setName("二级菜单C");
        m4.setParentId("1");
        m4.setUrl("test");
        Menu m5 = new Menu();
        m5.setId("5");
        m5.setName("三级菜单AA");
        m5.setParentId("2");
        m5.setUrl("test");
        Menu m6 = new Menu();
        m6.setId("6");
        m6.setName("三级菜单AB");
        m6.setParentId("2");
        m6.setUrl("test");
        Menu m7 = new Menu();
        m7.setId("7");
        m7.setName("三级菜单BA");
        m7.setParentId("3");
        m7.setUrl("test");
        List<Menu> menuList = new ArrayList<>();
        menuList.add(m1);
        menuList.add(m2);
        menuList.add(m3);
        menuList.add(m4);
        menuList.add(m5);
        menuList.add(m6);
        menuList.add(m7);
        return menuList;
    }


}
