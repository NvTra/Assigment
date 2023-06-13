package vn.funix.fx22252.java.asm04.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFileService {
    private static final String COMMA_DELIMITER = ",";

    public static List<List<String>> readFile(String fileName) {
        List<List<String>> data = new ArrayList<>();
        try (BufferedReader file = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = file.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                List<String> rows = new ArrayList<>();//1 rows chứa dữ liệu của 1 dòng  trong file, với phần tử ngăn cách bằng dấu ,
                for (String value : values) {
                    rows.add(value);
                }
                data.add(rows);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Tep khong ton tai ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
