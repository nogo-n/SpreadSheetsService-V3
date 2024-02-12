package com.example.demo.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class GoogleSheetsServiceImpl implements GoogleSheetsService{

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final RestTemplate restTemplate;

    public GoogleSheetsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Map<String, String>> readDataFromPublicSheet(String url, int startRow, int endRow) {
        // Google Sheetsの公開URLを使ってデータを取得
        String sheetsData = restTemplate.getForObject(url, String.class);
        System.out.println("sheetsData" + sheetsData);

        // CSV形式のデータを行ごとに分割
        String[] rows = sheetsData.split("\n");

        String[] newRows = Arrays.copyOfRange(rows, startRow + 1, endRow + 2);

        String lastValue = newRows[newRows.length - 1];
        int lastIndex = lastValue.indexOf("\"");
        if (lastIndex != -1) {
            newRows[newRows.length - 1] = lastValue.substring(0, lastIndex);
        }
        // newRows を出力
        System.out.println("newRows: " + Arrays.toString(newRows));

        // データ行からMapのリストを作成(keyがA行、valueがB行)
        List<Map<String, String>> dataList = new ArrayList<>();
        for (int i = 0; i < newRows.length; i++) {
            String[] values = newRows[i].split(",");
            Map<String, String> dataMap = new HashMap<>();
            if (values.length > 1) {
                dataMap.put(values[0], values[1]);
            }
            dataList.add(dataMap);
        }

        dataList.forEach(System.out::println);

        return dataList;
    }

}
