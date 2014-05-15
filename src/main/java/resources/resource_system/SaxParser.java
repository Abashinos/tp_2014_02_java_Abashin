package resources.resource_system;

import org.xml.sax.SAXException;
import resources.Resource;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SaxParser {
    private static SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

    public static Resource parse(String parsedString) {
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            InputStream inputStream = new ByteArrayInputStream(parsedString.getBytes(StandardCharsets.UTF_8));
            Resource resource = parse(saxParser, inputStream);
            inputStream.close();
            return resource;
        }
        catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Resource parse(SAXParser parser, InputStream inputStream) throws SAXException, IOException {
        SaxHandler saxHandler = new SaxHandler();
        parser.parse(inputStream, saxHandler);
        return saxHandler.getResource();
    }
}
