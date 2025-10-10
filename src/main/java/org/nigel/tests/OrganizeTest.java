package org.nigel.tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static java.time.LocalDate.parse;

public class OrganizeTest {
    static void main() {
        ArrayList<String> Data = new ArrayList<>();
        Data.add("2023-04-15|11:15:00|Invoice 1001 paid|Joe|1500.00");
        int point = 0;
        for (int i = 0; i < Data.size(); i++) {
            String[] ParsedData = Data.get(i).split("\\|");
            String Date = ParsedData[0];
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = parse(Date,dateTimeFormatter);
            ++point;
            System.out.println(localDate);
        }
    }
}
