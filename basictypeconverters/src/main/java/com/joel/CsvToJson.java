package com.joel;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joel Gayle on 02 Aug, 2020
 */
public class CsvToJson {
    public static void main(String args[]) throws Exception {
        File input = new File("C:\\git\\basictypeconverters\\src\\main\\resources\\result.csv");
//        try {
//            CsvSchema csv = CsvSchema.emptySchema().withHeader();
//            CsvMapper csvMapper = new CsvMapper();
//            MappingIterator<Map<?, ?>> mappingIterator =  csvMapper.reader().forType(Map.class).with(csv).readValues(input);
//            List<Map<?, ?>> list = mappingIterator.readAll();
//            System.out.println(list);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }


        try (InputStream in = new FileInputStream("C:\\git\\basictypeconverters\\src\\main\\resources\\result.csv")) {
            CsvParser csv = new CsvParser(',', in );
            List < String > fieldNames = null;
            if (csv.hasNext()) fieldNames = new ArrayList< >(csv.next());
            List < Map < String, String >> list = new ArrayList < > ();
            while (csv.hasNext()) {
                List < String > x = csv.next();
                Map < String, String > obj = new LinkedHashMap< >();
                for (int i = 0; i < fieldNames.size(); i++) {
                    obj.put(fieldNames.get(i), x.get(i));
                }
                list.add(obj);
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(System.out, list);
        }


    }
}
