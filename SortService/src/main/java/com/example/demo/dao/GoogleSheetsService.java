package com.example.demo.dao;

import java.util.List;
import java.util.Map;

public interface GoogleSheetsService {

    List<Map<String, String>> readDataFromPublicSheet(String url, int startRow, int endRow);
}
