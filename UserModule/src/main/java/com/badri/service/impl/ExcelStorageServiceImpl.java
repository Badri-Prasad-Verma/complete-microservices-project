package com.badri.service.impl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelStorageServiceImpl<T> {
    private static final String DIRECTORY = "excel-files";
    public void saveToExcel(List<T> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Data list cannot be null or empty.");
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data"); // Use default sheet name

        try {
            // Create header row dynamically based on object fields
            Row headerRow = sheet.createRow(0);
            Field[] fields = data.get(0).getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                headerRow.createCell(i).setCellValue(fields[i].getName());
            }

            // Add data rows dynamically
            int rowIndex = 1; // Start from the second row
            for (T item : data) {
                Row dataRow = sheet.createRow(rowIndex++);
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    Object value = fields[i].get(item);
                    Cell cell = dataRow.createCell(i);
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }

            // Ensure the directory exists
            File directory = new File(DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Create a unique file name with a timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = "data_" + timestamp + ".xlsx";
            File file = new File(directory, fileName);

            // Write the workbook to the file
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }

            workbook.close();
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException("Error while writing to Excel file", e);
        }
    }
}
