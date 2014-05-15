package resources.resource_system;

import supplies.ReflectionHelper;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import resources.Resource;

public class SaxHandler extends DefaultHandler {
    private static final String CLASSNAME = "class";
    private boolean inElement = false;
    private String element = null;
    private Resource resource = null;

    public Resource getResource() {
        return resource;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (!qName.equals(CLASSNAME)) {
            element = qName;
            inElement = true;
        }
        else {
            String className = attributes.getValue(0);
            resource = (Resource) ReflectionHelper.createInstance(className);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        element = null;
        inElement = false;
    }

    @Override
    public void characters(char ch[], int start, int length) {
        if(inElement && element != null) {
            String value = new String(ch, start, length);
            ReflectionHelper.setFieldValue(resource, element, value);
        }
    }


}
