package com.joel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
/**
 * Created by Joel Gayle on 02 Aug, 2020
 */
public class Csv2Json {
    public static void main(String[] args) throws Exception {
        File input = new File("C:\\git\\basictypeconverters\\src\\main\\resources\\result.csv");
        File output = new File("C:\\git\\basictypeconverters\\src\\main\\resources\\output.json");

        CsvSchema csvSchema = CsvSchema.builder().build();
        CsvMapper csvMapper = new CsvMapper();

        //strip first 2 lines from the csv
        Scanner scanner = new Scanner(input);
        ArrayList<String> csvLines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            csvLines.add(line);
        }

        csvLines.remove(0);
        csvLines.remove(0);

        StringBuilder stringBuilder = new StringBuilder();
        for(String s : csvLines)
            stringBuilder.append(s);

        StringReader reader = new StringReader(stringBuilder.toString());


        Map map = null;

        String [] keys = csvLines.get(0).split(",");

        for (String key: keys){
           System.out.println(key);
        }




        // Read data from CSV file
        List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(reader).readAll();

        ObjectMapper mapper = new ObjectMapper();

        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Write JSON formated data to output.json file
        for (Object row : readAll) {

            //  Map<String, String> map = (Map<String, String>) row;

            mapper.writerWithDefaultPrettyPrinter().writeValue(output, row);

//            ObjectMapper mapper = new ObjectMapper();

            // Write JSON formated data to output.json file
//        mapper.writerWithDefaultPrettyPrinter().writeValue(output, readAll);

            // Write JSON formated data to stdout
//        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readAll));
        }
    }}
