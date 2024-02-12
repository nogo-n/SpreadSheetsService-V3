package com.example.demo.service;

import com.example.demo.dao.GoogleSheetsServiceImpl;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SortServiceImpl implements SortService{

    private final GoogleSheetsServiceImpl googleSheetsServiceImpl;

    @Autowired
    public SortServiceImpl(GoogleSheetsServiceImpl googleSheetsServiceImpl) {
        this.googleSheetsServiceImpl = googleSheetsServiceImpl;
    }

    @Override
    public List<String[]> processDataFromGoogleSheets(String url, int startRow, int endRow) throws GeneralSecurityException, IOException {
        // Google Sheetsからデータを読み込む
        List<Map<String, String>> mappedList = googleSheetsServiceImpl.readDataFromPublicSheet(url, startRow, endRow);

        // 中身を出力
        System.out.println("Original Mapped List:");
        mappedList.forEach(System.out::println);

        // 数字でソート
        sortMappedListByValue(mappedList);

        // ソート後の中身を出力
        System.out.println("Sorted Mapped List:");
        mappedList.forEach(System.out::println);

        List<String[]> resultList = convertMappedListToStringList(mappedList);
        System.out.println("Result List:");
        resultList.forEach(row -> System.out.println(Arrays.toString(row)));

        return resultList;
    }

    private static void sortMappedListByValue(List<Map<String, String>> mappedList) {
        Collections.sort(mappedList, Comparator.comparing(m -> {
            String value = m.values().iterator().next();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("NumberFormatException: " + e.getMessage() + " for value: " + value);
                return Integer.MAX_VALUE;
            }
        }, Comparator.reverseOrder()));
    }

    //List<Map<String, String>>をList<String[]>に変換している
    private static List<String[]> convertMappedListToStringList(List<Map<String, String>> mappedList) {
        return mappedList.stream()
                .map(map -> {

                    Map.Entry<String, String> entry = map.entrySet().iterator().next();
                    String alphabet = entry.getKey();
                    String number = entry.getValue();

                    // null チェックとデフォルト値の設定
                    alphabet = (alphabet != null) ? alphabet : "";
                    number = (number != null) ? number : "";

                    return new String[]{alphabet, number};
                })
                .collect(Collectors.toList());
    }
}
