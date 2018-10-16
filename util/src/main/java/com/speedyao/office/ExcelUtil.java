package com.speedyao.office;

import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by speedyao on 2018/8/17.
 */
public class ExcelUtil {
    public static void write2007(List<List<String>> list,String path){
        if(list == null)return;
        SXSSFWorkbook wb = new SXSSFWorkbook();
        SXSSFSheet sheet = wb.createSheet("sheet1");
        for(int i = 0 ;i < list.size() ; i++){
            SXSSFRow row = sheet.createRow(i);
            for(int j = 0; j < list.get(i).size() ; j ++){
                SXSSFCell cell = row.createCell(j);
                cell.setCellValue(list.get(i).get(j));
            }
        }
        File file = new File(path);//Excel文件生成后存储的位置。
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream fos ;
        try
        {
            fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
