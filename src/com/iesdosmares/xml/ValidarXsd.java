package com.iesdosmares.xml;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class ValidarXsd {
    public static void main(String args[]) {
        ValidarXsd validacion = new ValidarXsd();
        try {
            boolean valido = validacion.validar("/home/ricardo/Descargas/lm/xml odoo/xml/xsd/marcadores.xsd",
                    "/home/ricardo/Descargas/lm/xml odoo/xml/xsd/marcadores.xml");
            if (valido) {
                System.out.println("El documento es válido respecto al esquema");
            }
            else {
                System.out.println("El documento NO es válido respecto al esquema, consultar el error en la traza");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean validar(String xsdPath, String xmlPath){
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
            return true;
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            return false;
        }
    }
}
