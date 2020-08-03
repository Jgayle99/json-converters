package com.joel;

//import com.sun.org.apache.xpath.internal.operations.String;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by Joel Gayle on 02 Aug, 2020
 */
public class JsonToXmlXslt {
    public static void main(String[] args) throws SaxonApiException, FileNotFoundException {
        final String XSLT_PATH = "C:\\git\\basictypeconverters\\src\\main\\resources\\JsonToXml.xsl";
        final String JSON_PATH = "C:\\git\\basictypeconverters\\src\\main\\resources\\input.json";
//        String JSON = "{\n" +
//                "  \"student\" : \"john\",\n" +
//                "  \"class\" : \"Bachelors\",\n" +
//                "  \"subjects\" : \"<college><subject><subjects>maths</subjects><term>spring</term></subject></college>\"\n" +
//                "}";

        testJsonToXml(XSLT_PATH, JSON_PATH);
        System.out.println();

//        JSON = "{\n" +
//                "    \"color\": \"red\",\n" +
//                "    \"value\": \"#f00\"\n" +
//                "}";
//        testJsonToXml(XSLT_PATH, JSON_PATH);
//        System.out.println();
    }

    static void testJsonToXml(String xsl, String json) throws SaxonApiException, FileNotFoundException {
        OutputStream outputStream = System.out;
        Processor processor = new Processor(false);
        Serializer serializer = processor.newSerializer();
        serializer.setOutputStream(outputStream);
        XsltCompiler compiler = processor.newXsltCompiler();

        StringBuilder jsonContent = new StringBuilder();

        File input = new File(json);
        Scanner scanner = new Scanner(input);
        while (scanner.hasNextLine()) {
            jsonContent.append(scanner.nextLine());
        }

        XsltExecutable executable = compiler.compile(new StreamSource(new File(xsl)));
        XsltTransformer transformer = executable.load();
        transformer.setInitialTemplate(new QName("init"));
        transformer.setParameter(new QName("jsonText"), new XdmAtomicValue(jsonContent.toString()));
        transformer.setDestination(serializer);
        transformer.transform();
    }

}
