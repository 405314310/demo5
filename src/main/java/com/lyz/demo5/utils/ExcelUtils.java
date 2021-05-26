package com.lyz.demo5.utils;

import com.lyz.demo5.model.Department;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class ExcelUtils {

    public HSSFWorkbook writeExcel(List<?> list, Class<?> cla) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet1 = (HSSFSheet) workbook.createSheet("第一个sheet页");
        HSSFRow row1 = (HSSFRow) sheet1.createRow(0);
        HSSFCell cell1 = (HSSFCell) row1.createCell(0);
        cell1.setCellValue("excelId");

        Field[] fields = cla.getDeclaredFields();
        for(int i=0;i<fields.length;i++){
            HSSFCell cell = (HSSFCell) row1.createCell(i+1);
            cell.setCellValue(fields[i].getName());
        }
        for(int i = 0; i <list.size(); i++){
            HSSFRow row = (HSSFRow) sheet1.createRow(i+1);
            HSSFCell cell = (HSSFCell) row.createCell(0);
            cell.setCellValue("excelId:"+(i+1));
            for(int j = 0; j < fields.length;j++){
                HSSFCell cell2 = (HSSFCell) row.createCell(j+1);

                String fieldName = list.get(i).getClass().getDeclaredFields()[j].getName();
                fieldName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                Field field = list.get(i).getClass().getDeclaredFields()[j];
                Method method = list.get(i).getClass().getMethod("get" + fieldName);
                field.setAccessible(true);
                String str = method.invoke(list.get(i)).toString();


                cell2.setCellValue(str);

                System.out.println(cell2.getStringCellValue());
            }

        }

        System.out.println(workbook.getSheetAt(0).getRow(2).getCell(2));

        return workbook;
    }

}
