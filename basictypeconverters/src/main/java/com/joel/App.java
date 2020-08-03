package com.joel;

import java.io.*;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        String json = "{\n" +
                "\t\"id\": \"0001\",\n" +
                "\t\"type\": \"donut\",\n" +
                "\t\"name\": \"Cake\",\n" +
                "\t\"ppu\": 0.55,\n" +
                "\t\"batters\":\n" +
                "\t\t{\n" +
                "\t\t\t\"batter\":\n" +
                "\t\t\t\t[\n" +
                "\t\t\t\t\t{ \"id\": \"1001\", \"type\": \"Regular\" },\n" +
                "\t\t\t\t\t{ \"id\": \"1002\", \"type\": \"Chocolate\" },\n" +
                "\t\t\t\t\t{ \"id\": \"1003\", \"type\": \"Blueberry\" },\n" +
                "\t\t\t\t\t{ \"id\": \"1004\", \"type\": \"Devil's Food\" }\n" +
                "\t\t\t\t]\n" +
                "\t\t},\n" +
                "\t\"topping\":\n" +
                "\t\t[\n" +
                "\t\t\t{ \"id\": \"5001\", \"type\": \"None\" },\n" +
                "\t\t\t{ \"id\": \"5002\", \"type\": \"Glazed\" },\n" +
                "\t\t\t{ \"id\": \"5005\", \"type\": \"Sugar\" },\n" +
                "\t\t\t{ \"id\": \"5007\", \"type\": \"Powdered Sugar\" },\n" +
                "\t\t\t{ \"id\": \"5006\", \"type\": \"Chocolate with Sprinkles\" },\n" +
                "\t\t\t{ \"id\": \"5003\", \"type\": \"Chocolate\" },\n" +
                "\t\t\t{ \"id\": \"5004\", \"type\": \"Maple\" }\n" +
                "\t\t]\n" +
                "}";

        String json2 = "{\n" +
                "      \"_links\": {\n" +
                "          \"self\":  {\n" +
                "\n" +
                "                \"href\": \"nnmi/api/disco/v1/attachedSwitchPort?mac=0005B7049D18, 0005B7049D19,0005B7049D17\",\n" +
                "                \"class\": \"collection\"\n" +
                "\n" +
                "           },\n" +
                "\n" +
                "          \"items\": [\n" +
                "\n" +
                "              {\n" +
                "                 \"href\":\"nnmi/api/topo/v1/attachedSwitchPort/ab8bf436-f2f8-4a5d-a3e0-5e45577fad59\",\n" +
                "                 \"title\":\" 0005B7049D18\",\n" +
                "                 \"class\":\"entity\"\n" +
                "              },\n" +
                "\n" +
                "              {\n" +
                "                 \"href\":\"nnmi/api/topo/v1/attachedSwitchPort/cb9bf436-f2f8-4a5d-a3e0-5e45577fad59\",\n" +
                "                 \"title\":\" 0005B7049D19\",\n" +
                "                 \"class\":\"entity\"\n" +
                "              },\n" +
                "\n" +
                "              {\n" +
                "                 \"href\":\"nnmi/api/topo/v1/attachedSwitchPort/fb8f3436-f2f8-4a5d-a3e0-5e45577fad59\",\n" +
                "                 \"title\":\" 0005B7049D17\",\n" +
                "                 \"class\":\"entity\"\n" +
                "              }\n" +
                "\n" +
                "          ]\n" +
                "\n" +
                "      }\n" +
                "\n" +
                "}\n";


        String jsonTenable = "{\n" +
                "    \"type\" : \"regular\",\n" +
                "    \"response\" : {\n" +
                "        \"repositories\" : [\n" +
                "            {\n" +
                "                \"id\" : \"2\",\n" +
                "                \"name\" : \"Rep2\",\n" +
                "                \"description\" : \"\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"ip\" : \"192.168.0.1\",\n" +
                "        \"uuid\" : \"123e4567-e89b-12d3-a456-426655440000\",\n" +
                "        \"repositoryID\" : \"2\",\n" +
                "        \"score\" : \"2130\",\n" +
                "        \"total\" : \"322\",\n" +
                "        \"severityInfo\" : \"110\",\n" +
                "        \"severityLow\" : \"7\",\n" +
                "        \"severityMedium\" : \"41\",\n" +
                "        \"severityHigh\" : \"152\",\n" +
                "        \"severityCritical\" : \"12\",\n" +
                "        \"macAddress\" : \"00:00:00:00:00:00\",\n" +
                "        \"policyName\" : \"\",\n" +
                "        \"pluginSet\" : \"\",\n" +
                "        \"netbiosName\" : \"TARGET\\\\WIN7X64\",\n" +
                "        \"dnsName\" : \"win7x64.target.domain.com\",\n" +
                "        \"osCPE\" : \"cpe:\\/o:microsoft:windows_7::gold:x64-ultimate\",\n" +
                "        \"biosGUID\" : \"\",\n" +
                "        \"tpmID\" : \"\",\n" +
                "        \"mcafeeGUID\" : \"\",\n" +
                "        \"lastAuthRun\" : \"\",\n" +
                "        \"lastUnauthRun\" : \"\",\n" +
                "        \"severityAll\" : \"12,152,41,7,110\",\n" +
                "        \"os\" : \"Microsoft Windows 7 Ultimate\",\n" +
                "        \"hasPassive\" : \"No\",\n" +
                "        \"hasCompliance\" : \"No\",\n" +
                "        \"lastScan\" : \"1408294249\",\n" +
                "        \"links\" : [\n" +
                "            {\n" +
                "                \"name\" : \"SANS\",\n" +
                "                \"link\" : \"https:\\/\\/isc.sans.edu\\/ipinfo.html?ip=192.168.0.1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\" : \"ARIN\",\n" +
                "                \"link\" : \"http:\\/\\/whois.arin.net\\/rest\\/ip\\/192.168.0.1\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"repository\" : {\n" +
                "            \"id\" : \"2\",\n" +
                "            \"name\" : \"Rep2\",\n" +
                "            \"description\" : \"\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"error_code\" : 0,\n" +
                "    \"error_msg\" : \"\",\n" +
                "    \"warnings\" : [],\n" +
                "    \"timestamp\" : 1409848524\n" +
                "}";

        String json3 = "   {\n" +
                "    \"medications\":[{\n" +
                "            \"aceInhibitors\":[{\n" +
                "                \"name\":\"lisinopril\",\n" +
                "                \"strength\":\"10 mg Tab\",\n" +
                "                \"dose\":\"1 tab\",\n" +
                "                \"route\":\"PO\",\n" +
                "                \"sig\":\"daily\",\n" +
                "                \"pillCount\":\"#90\",\n" +
                "                \"refills\":\"Refill 3\"\n" +
                "            }],\n" +
                "            \"antianginal\":[{\n" +
                "                \"name\":\"nitroglycerin\",\n" +
                "                \"strength\":\"0.4 mg Sublingual Tab\",\n" +
                "                \"dose\":\"1 tab\",\n" +
                "                \"route\":\"SL\",\n" +
                "                \"sig\":\"q15min PRN\",\n" +
                "                \"pillCount\":\"#30\",\n" +
                "                \"refills\":\"Refill 1\"\n" +
                "            }],\n" +
                "            \"anticoagulants\":[{\n" +
                "                \"name\":\"warfarin sodium\",\n" +
                "                \"strength\":\"3 mg Tab\",\n" +
                "                \"dose\":\"1 tab\",\n" +
                "                \"route\":\"PO\",\n" +
                "                \"sig\":\"daily\",\n" +
                "                \"pillCount\":\"#90\",\n" +
                "                \"refills\":\"Refill 3\"\n" +
                "            }],\n" +
                "            \"betaBlocker\":[{\n" +
                "                \"name\":\"metoprolol tartrate\",\n" +
                "                \"strength\":\"25 mg Tab\",\n" +
                "                \"dose\":\"1 tab\",\n" +
                "                \"route\":\"PO\",\n" +
                "                \"sig\":\"daily\",\n" +
                "                \"pillCount\":\"#90\",\n" +
                "                \"refills\":\"Refill 3\"\n" +
                "            }],\n" +
                "            \"diuretic\":[{\n" +
                "                \"name\":\"furosemide\",\n" +
                "                \"strength\":\"40 mg Tab\",\n" +
                "                \"dose\":\"1 tab\",\n" +
                "                \"route\":\"PO\",\n" +
                "                \"sig\":\"daily\",\n" +
                "                \"pillCount\":\"#90\",\n" +
                "                \"refills\":\"Refill 3\"\n" +
                "            }],\n" +
                "            \"mineral\":[{\n" +
                "                \"name\":\"potassium chloride ER\",\n" +
                "                \"strength\":\"10 mEq Tab\",\n" +
                "                \"dose\":\"1 tab\",\n" +
                "                \"route\":\"PO\",\n" +
                "                \"sig\":\"daily\",\n" +
                "                \"pillCount\":\"#90\",\n" +
                "                \"refills\":\"Refill 3\"\n" +
                "            }]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"labs\":[{\n" +
                "        \"name\":\"Arterial Blood Gas\",\n" +
                "        \"time\":\"Today\",\n" +
                "        \"location\":\"Main Hospital Lab\"      \n" +
                "        },\n" +
                "        {\n" +
                "        \"name\":\"BMP\",\n" +
                "        \"time\":\"Today\",\n" +
                "        \"location\":\"Primary Care Clinic\"    \n" +
                "        },\n" +
                "        {\n" +
                "        \"name\":\"BNP\",\n" +
                "        \"time\":\"3 Weeks\",\n" +
                "        \"location\":\"Primary Care Clinic\"    \n" +
                "        },\n" +
                "        {\n" +
                "        \"name\":\"BUN\",\n" +
                "        \"time\":\"1 Year\",\n" +
                "        \"location\":\"Primary Care Clinic\"    \n" +
                "        },\n" +
                "        {\n" +
                "        \"name\":\"Cardiac Enzymes\",\n" +
                "        \"time\":\"Today\",\n" +
                "        \"location\":\"Primary Care Clinic\"    \n" +
                "        },\n" +
                "        {\n" +
                "        \"name\":\"CBC\",\n" +
                "        \"time\":\"1 Year\",\n" +
                "        \"location\":\"Primary Care Clinic\"    \n" +
                "        },\n" +
                "        {\n" +
                "        \"name\":\"Creatinine\",\n" +
                "        \"time\":\"1 Year\",\n" +
                "        \"location\":\"Main Hospital Lab\"  \n" +
                "        },\n" +
                "        {\n" +
                "        \"name\":\"Electrolyte Panel\",\n" +
                "        \"time\":\"1 Year\",\n" +
                "        \"location\":\"Primary Care Clinic\"    \n" +
                "        },\n" +
                "        {\n" +
                "        \"name\":\"Glucose\",\n" +
                "        \"time\":\"1 Year\",\n" +
                "        \"location\":\"Main Hospital Lab\"  \n" +
                "        },\n" +
                "        {\n" +
                "        \"name\":\"PT/INR\",\n" +
                "        \"time\":\"3 Weeks\",\n" +
                "        \"location\":\"Primary Care Clinic\"    \n" +
                "        },\n" +
                "        {\n" +
                "        \"name\":\"PTT\",\n" +
                "        \"time\":\"3 Weeks\",\n" +
                "        \"location\":\"Coumadin Clinic\"    \n" +
                "        },\n" +
                "        {\n" +
                "        \"name\":\"TSH\",\n" +
                "        \"time\":\"1 Year\",\n" +
                "        \"location\":\"Primary Care Clinic\"    \n" +
                "        }\n" +
                "    ],\n" +
                "    \"imaging\":[{\n" +
                "        \"name\":\"Chest X-Ray\",\n" +
                "        \"time\":\"Today\",\n" +
                "        \"location\":\"Main Hospital Radiology\"    \n" +
                "        },\n" +
                "        {\n" +
                "        \"name\":\"Chest X-Ray\",\n" +
                "        \"time\":\"Today\",\n" +
                "        \"location\":\"Main Hospital Radiology\"    \n" +
                "        },\n" +
                "        {\n" +
                "        \"name\":\"Chest X-Ray\",\n" +
                "        \"time\":\"Today\",\n" +
                "        \"location\":\"Main Hospital Radiology\"    \n" +
                "        }\n" +
                "    ]\n" +
                "}";
        //JsonToCsv jsonToCsv = new JsonToCsv();



        JsonToCsv flatMe = new JsonToCsv(jsonTenable);

//directly write the JSON document to CSV
     //   flatMe.json2Sheet().write2csv("/path/to/destination/file.json");

//directly write the JSON document to CSV but with delimiter
   //     flatMe.json2Sheet().write2csv("/path/to/destination/file.json", '|');

        //get the 2D representation of JSON document
        List<Object[]> json2csv = flatMe.json2Sheet().getJsonAsSheet();
//write the 2D representation in csv format
        flatMe.write2csv("C:\\git\\basictypeconverters\\src\\main\\resources\\result.csv");
        //StringBuilder stringBuilder = new StringBuilder();

        //jsonToCsv.jsonToCsv(json2, stringBuilder);
       // System.out.println( stringBuilder.toString());
    }
}
