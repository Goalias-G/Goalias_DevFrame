package com.dev.model.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtil {

    /**
     * 读取 Excel 文件
     *
     * @param fis 文件输入流
     * @return 返回一个 List<List<String>>
     * @throws IOException
     */
    public static List<List<String>> readExcel(FileInputStream fis) throws IOException {
        List<List<String>> data = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

        for (Row row : sheet) {
            if (row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK) {
                break;
            }
            Iterator<Cell> cellIterator = row.cellIterator();

            List<String> rowData = new ArrayList<>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
//                if (cell == null || cell.getCellType() == CellType.BLANK) {
//                    break;
//                }
                switch (cell.getCellType()) {
                    case STRING -> rowData.add(cell.getStringCellValue());
                    case NUMERIC -> {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            rowData.add(cell.getDateCellValue().toString());
                        } else {
                            String s = String.valueOf(cell.getNumericCellValue());
                            if (s.contains(".")) {
                                s = s.substring(0, s.indexOf("."));
                            }
                            rowData.add(s);
                        }
                    }
                    case BOOLEAN -> rowData.add(String.valueOf(cell.getBooleanCellValue()));
                    case FORMULA -> rowData.add(cell.getCellFormula());
                    default -> rowData.add("");
                }
            }
            data.add(rowData);
        }

        workbook.close();
        fis.close();

        return data;
    }

    /**
     * 导出数据到 Excel 文件
     *
     * @param sheetName 工作表名称
     * @param headers   表头
     * @param data      数据列表，每个 List<String> 代表一行数据
     * @throws IOException
     */
    public static byte[] exportToExcel(String sheetName, List<String> headers, List<List<String>> data) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // 创建表头
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
        }

        // 填充数据
        int rowNum = 1;
        for (List<String> rowData : data) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < rowData.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(rowData.get(i));
            }
        }

        // 写入文件
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            workbook.write(baos);
            byte[] bytes = baos.toByteArray();
//            Path tempFile = Files.createTempFile("temp-excel-", ".xlsx");
//            Files.write(tempFile, bytes);
//            String fileName = iOUtil.uploadFile(IOUtil.BUCKET_NAME, UUID.fastUUID().toString(), tempFile.toString());
//            Files.delete(tempFile);
            return bytes;
        } finally {
            workbook.close();
        }
    }

}
