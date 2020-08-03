package com.joel;

import java.io.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
/**
 * Created by Joel Gayle on 02 Aug, 2020
 */
public class JsonToCsv {

    private String jsonString;

    private List<Object[]> sheetMatrix = null;

    private List<String> pathList = null;

    private String tmp[] = null;

    private HashSet<String> primitivePath = null;
    private HashSet<String> primitiveUniquePath = null;
    private List<String> unique = null;

    private String regex = "(\\[[0-9]*\\]$)";
    private Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

    private JsonElement ele = null;

    private String tmpPath = null;

    private OrderJson makeOrder = new OrderJson();


    public JsonToCsv(String jsonString) {
        this.jsonString = jsonString;
    }



    public JsonToCsv json2Sheet() {

        Configuration.setDefaults(new Configuration.Defaults() {
            private final JsonProvider jsonProvider = new JacksonJsonProvider();
            private final MappingProvider mappingProvider = new JacksonMappingProvider();

            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            public MappingProvider mappingProvider() {
                return mappingProvider;
            }

            public Set options() {
                return EnumSet.noneOf(Option.class);
            }
        });

        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL)
                .addOptions(Option.SUPPRESS_EXCEPTIONS);

        Configuration pathConf = Configuration.defaultConfiguration().addOptions(Option.AS_PATH_LIST)
                .addOptions(Option.ALWAYS_RETURN_LIST);

        DocumentContext parse;

        sheetMatrix = new ArrayList<>();

        ele = new JsonParser().parse(this.jsonString);

        pathList = JsonPath.using(pathConf).parse(this.jsonString).read("$..*");

        parse = JsonPath.using(conf).parse(this.jsonString);

        primitivePath = new LinkedHashSet<>();
        primitiveUniquePath = new LinkedHashSet<>();

        for (String o : pathList) {
            Object tmp = parse.read(o);

            if (tmp == null) {
                primitivePath.add(o);

            } else {
                String dataType = tmp.getClass().getSimpleName();
                if (dataType.equals("Boolean") || dataType.equals("Integer") || dataType.equals("String")
                        || dataType.equals("Double") || dataType.equals("Long")) {
                    primitivePath.add(o);
                } else {
                }
            }
        }

        for (String o : primitivePath) {

            Matcher m = pattern.matcher(o);

            if (m.find()) {
                tmp = o.replace("$", "").split("(\\[[0-9]*\\]$)");
                tmp[0] = tmp[0].replaceAll("(\\[[0-9]*\\])", "");
                primitiveUniquePath.add("/" + (tmp[0] + m.group()).replace("'][", "/").replace("[", "").replace("]", "")
                        .replace("''", "/").replace("'", ""));
            } else {
                primitiveUniquePath.add("/" + o.replace("$", "").replaceAll("(\\[[0-9]*\\])", "").replace("[", "")
                        .replace("]", "").replace("''", "/").replace("'", ""));
            }
        }



        //Print the header rows to match the CDS schema csv format
        unique = new ArrayList<>(primitiveUniquePath);

        Object[] colNumRow = new Object[unique.size()];
        Object[] dataTypeRow = new Object[unique.size()];
        Object[] header = new Object[unique.size()];

        int i = 0;
        for (String fieldValue : unique) {
            header[i] = "\"" + fieldValue + "\"";
            colNumRow[i] = "\"json_" + i + "\"";
            dataTypeRow[i] = getDataType(fieldValue);
            i++;
        }

        //header rows of the csv
        sheetMatrix.add(colNumRow);
        sheetMatrix.add(dataTypeRow);
        sheetMatrix.add(header);

        //adding all the content of csv
        sheetMatrix.add(make2D(new Object[unique.size()], ele, "$"));

        Object last[] = sheetMatrix.get(sheetMatrix.size() - 1);
        Object secondLast[] = sheetMatrix.get(sheetMatrix.size() - 2);

        boolean delete = true;

        for (Object o : last) {
            if (o != null) {
                delete = false;
                break;
            }
        }

        if (!delete) {
            delete = true;
            for (int DEL = 0; DEL < last.length; DEL++) {
                if (last[DEL] != null && !last[DEL].equals(secondLast[DEL])) {
                    delete = false;
                    break;
                }
            }
        }

        if (delete)
            sheetMatrix.remove(sheetMatrix.size() - 1);
        return this;
    }

    //TODO case statements to determine field value data type and return as string
    //Probably don't need this though, as we can just send every field as a String just to get through CDS
    private String getDataType(String fieldValue){
        return "\"string\"";
    }

    private Object[] make2D(Object[] old, JsonElement ele, String path) {
        Object[] cur = old.clone();
        boolean gotArray;
        if (ele.isJsonObject()) {
            ele = makeOrder.orderJson(ele);
            for (Map.Entry<String, JsonElement> entry : ele.getAsJsonObject().entrySet()) {
                if (entry.getValue().isJsonPrimitive()) {
                    tmpPath = path +"['"+ entry.getKey()+"']";
                    Matcher m = pattern.matcher(tmpPath);
                    if (m.find()) {
                        String[] tmp = tmpPath.replace("$", "").split("(\\[[0-9]*\\]$)");
                        tmp[0] = tmp[0].replaceAll("(\\[[0-9]*\\])", "");
                        tmpPath = ("/" + (tmp[0] + m.group()).replace("'][", "/").replace("[", "")
                                .replace("]", "").replace("''", "/").replace("'", ""));
                    } else {
                        tmpPath = ("/" + tmpPath.replace("$", "").replaceAll("(\\[[0-9]*\\])", "").replace("[", "")
                                .replace("]", "").replace("''", "/").replace("'", ""));
                    }
                    if (unique.contains(tmpPath)) {
                        int index = unique.indexOf(tmpPath);
                        cur[index] = entry.getValue().getAsJsonPrimitive();
                    }
                    tmpPath = null;
                } else if (entry.getValue().isJsonObject()) {
                    cur = make2D(cur, entry.getValue().getAsJsonObject(),
                            path +"['" + entry.getKey()+"']");
                } else if (entry.getValue().isJsonArray()) {
                    cur = make2D(cur, entry.getValue().getAsJsonArray(),
                            path +"['" + entry.getKey()+"']");
                }
            }

        } else if (ele.isJsonArray()) {
            int arrIndex = 0;

            for (JsonElement tmp : ele.getAsJsonArray()) {

                if (tmp.isJsonPrimitive()) {
                    tmpPath = path +"['"+ arrIndex +"']";
                    Matcher m = pattern.matcher(tmpPath);

                    if (m.find()) {
                        String tmp1[] = tmpPath.replace("$", "").split("(\\[[0-9]*\\]$)");
                        tmp1[0] = tmp1[0].replaceAll("(\\[[0-9]*\\])", "");
                        tmpPath = ("/" + (tmp1[0] + m.group()).replace("'][", "/").replace("[", "")
                                .replace("]", "").replace("''", "/").replace("'", ""));
                    } else {
                        tmpPath = ("/" + tmpPath.replace("$", "").replaceAll("(\\[[0-9]*\\])", "").replace("[", "")
                                .replace("]", "").replace("''", "/").replace("'", ""));
                    }

                    if (unique.contains(tmpPath)) {
                        int index = unique.indexOf(tmpPath);
                        cur[index] = tmp.getAsJsonPrimitive();
                    }
                    tmpPath = null;
                } else {
                    if (tmp.isJsonObject()) {
                        gotArray = isInnerArray(tmp);
                        sheetMatrix.add(make2D(cur, tmp.getAsJsonObject(), path +"[" + arrIndex + "]"));
                        if (gotArray) {
                            sheetMatrix.remove(sheetMatrix.size() - 1);
                        }
                    } else if (tmp.isJsonArray()) {
                        make2D(cur, tmp.getAsJsonArray(), path+"["+arrIndex+"]");
                    }
                }
                arrIndex++;
            }
        }
        return cur;
    }

    private boolean isInnerArray(JsonElement ele) {

        for (Map.Entry<String, JsonElement> entry : ele.getAsJsonObject().entrySet()) {
            if (entry.getValue().isJsonArray()) {
                if (entry.getValue().getAsJsonArray().size() > 0)
                    for (JsonElement checkPrimitive : entry.getValue().getAsJsonArray()) {
                        if (checkPrimitive.isJsonObject()) {
                            return true;
                        }
                    }
            }
        }
        return false;
    }

    public JsonToCsv headerSeparator() throws Exception{
        return headerSeparator(" ");
    }


    public JsonToCsv headerSeparator(String separator) throws Exception{
        try{

            int sheetMatrixLen = this.sheetMatrix.get(0).length;

            for(int I=0; I < sheetMatrixLen; I++){

                this.sheetMatrix.get(0)[I] = this.sheetMatrix.get(0)[I].toString().replaceFirst("^\\/", "").replaceAll("/", separator).trim();
            }

        }catch(NullPointerException nullex){
            throw new Exception("null pointer exception");
        }
        return this;
    }


    public List<Object[]> getJsonAsSheet() {
        return this.sheetMatrix;
    }


    public List<String> getUniqueFields() {
        return this.unique;
    }


    public void write2csv(String destination) throws FileNotFoundException, UnsupportedEncodingException {
        this.write2csv(destination, ',');
    }


    public void write2csv(String destination, char delimiter)
            throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(new File(destination), "UTF-8");
        writer.write(write2csv(delimiter));
        writer.close();
    }


    public String write2csv(char delimiter) {
        boolean comma;
        StringBuffer buffer = new StringBuffer();

        for (Object[] o : this.sheetMatrix) {
            comma = false;
            for (Object t : o) {
                if (t == null) {
                    buffer.append( comma ? delimiter + "\"\"" : "\"\"");
                } else {
                    buffer.append(  comma ? delimiter + t.toString() : t.toString() );
                }
                if (!comma)
                    comma = true;
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }
}