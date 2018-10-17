package com.speedyao.office;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by speedyao on 2018/8/17.
 */
public class ExcelUtil {
    static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static void write2007(List<List<String>> list, String path) {
        if (list == null) return;
        SXSSFWorkbook wb = new SXSSFWorkbook();
        SXSSFSheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < list.size(); i++) {
            SXSSFRow row = sheet.createRow(i);
            for (int j = 0; j < list.get(i).size(); j++) {
                SXSSFCell cell = row.createCell(j);
                cell.setCellValue(list.get(i).get(j));
            }
        }
        File file = new File(path);//Excel文件生成后存储的位置。
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream fos;
        try {
            fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取excel数据 map的key是sheetName,value是 JSON(表头cell值,数据cell值)的List
     *
     * @param filePath
     * @param titleRow   表头所在的行减一
     * @param sheetNames 读取sheet的名称
     * @return
     */
    public static Map<String, List<JSONObject>> read2007(String filePath, int titleRow, String[] sheetNames) {
        Map<String, List<JSONObject>> map = new HashMap<>();
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(filePath));
            int numberOfSheets = workbook.getNumberOfSheets();
            logger.info("共有" + numberOfSheets + "个sheet");
            if (sheetNames != null) {
                for (String sheetName : sheetNames) {
                    XSSFSheet sheet = workbook.getSheet(sheetName);
                    List<JSONObject> list = reedSheet(sheet, titleRow);
                    map.put(sheetName, list);
                }
            } else {
                for (int i = 0; i < numberOfSheets; i++) {
                    XSSFSheet sheet = workbook.getSheetAt(i);
                    List<JSONObject> list = reedSheet(sheet, titleRow);
                    map.put(sheet.getSheetName(), list);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * 读取sheet数据
     *
     * @param sheet
     * @param titleRowNum 表头所在的行减一
     * @return
     */
    public static List<JSONObject> reedSheet(XSSFSheet sheet, int titleRowNum) {
        logger.info("开始读取:" + sheet.getSheetName());
        List<JSONObject> list = new ArrayList<>();
        XSSFRow titleRow = sheet.getRow(titleRowNum);
        short lastCellNum = titleRow.getLastCellNum();
        String[] titleNames = new String[lastCellNum];
        for (short i = 0; i < lastCellNum; i++) {
            titleNames[i] = titleRow.getCell(i).getStringCellValue().trim();
        }
        int lastRowNum = sheet.getLastRowNum();
        for (int i = titleRowNum + 1; i <= lastRowNum; i++) {
            JSONObject json = new JSONObject();
            XSSFRow row = sheet.getRow(i);
            for (short j = 0; j < lastCellNum; j++) {
                XSSFCell cell = row.getCell(j);
                Object value;
                if (cell != null) {
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            value = cell.getStringCellValue().trim();
                            break;
                        case NUMERIC:
                            cell.setCellType(CellType.STRING);
                            value = cell.getStringCellValue().trim();
                            break;
                        case BOOLEAN:
                            value = cell.getBooleanCellValue() ? 1 : 0;
                            break;
                        case BLANK:
                            value = "";
                            break;
                        case _NONE:
                            value = "";
                            break;
                        default:
                            logger.info("单元格格式不支持");
                            value = "";
                            break;

                    }
                } else {
                    //空单元格默认值为""
                    value = "";
                }
                json.put(titleNames[j], value);
            }
            list.add(json);
        }
        logger.info("结束读取:" + sheet.getSheetName());
        return list;
    }
}
