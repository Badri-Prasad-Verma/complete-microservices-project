package com.badri.service;

import com.badri.dto.UserRequestDto;

public interface ExcelStorageService {
    public void saveUserToExcel(UserRequestDto user);
}
