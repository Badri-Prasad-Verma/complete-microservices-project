package com.badri.controller;

import com.badri.dto.UserRequestDto;
import com.badri.dto.UserResponseDTO;
import com.badri.service.ExcelStorageService;
import com.badri.service.UserService;
import com.badri.service.impl.ExcelStorageServiceImpl;
import com.badri.util.ExcelExportService;
import com.badri.util.PdfExportService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private UserService userService;
    @Autowired
    private PdfExportService pdfExportService;
    @Autowired
    private ExcelExportService excelExportService;
    @Autowired
    private ExcelStorageServiceImpl excelStorageService;
    public ReportController(ExcelStorageServiceImpl<UserRequestDto> excelStorageService) {
        this.excelStorageService = excelStorageService;
    }

    @GetMapping("/excel-report/download")
    public void downloadUserReport(
            @RequestParam(value = "page",defaultValue = "0",required = false) int page,
            @RequestParam(value = "size",defaultValue = "10",required = false) int size,
            HttpServletResponse response) throws IOException {
        List<UserResponseDTO> users = (List<UserResponseDTO>) userService.getAllUsers(page,size);
        excelExportService.exportToExcel(users, response);
    }

    @GetMapping("/pdf-report/download")
    public void downloadUserPdf(
            @RequestParam(value = "page",defaultValue = "0",required = false) int page,
            @RequestParam(value = "size",defaultValue = "10",required = false) int size,
            HttpServletResponse response) throws IOException, DocumentException, DocumentException {
        List<UserResponseDTO> users = (List<UserResponseDTO>) userService.getAllUsers(page,size);
        pdfExportService.exportToPdf(users, response);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveToExcel(@RequestBody List<UserResponseDTO> data) {
        if (data == null || data.isEmpty()) {
            return ResponseEntity.badRequest().body("Data list cannot be null or empty.");
        }

        excelStorageService.saveToExcel(data);
        return ResponseEntity.ok("Data saved to a new Excel file successfully.");
    }


}
