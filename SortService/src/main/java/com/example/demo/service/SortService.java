package com.example.demo.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface SortService {

    List<String[]> processDataFromGoogleSheets(String url, int startRow, int endRow) throws GeneralSecurityException, IOException;

}
