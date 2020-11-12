package com.iesdosmares.xml;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ValidarDtd {
    public static void main(String args[]) {
        ValidarDtd validacion = new ValidarDtd();
        boolean valido = validacion.validar("/home/ricardo/Descargas/lm/xml odoo/xml/flota.xml");
        if (valido) {
            System.out.println("El documento es válido respecto al dtd");
        }
        else {
            System.out.println("El documento NO es válido respecto al dtd, consultar el error en la traza");
        }
    }

    public boolean validar(String ruta) {
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void error(SAXParseException exception) throws SAXException {
                    // do something more useful in each of these handlers
                    exception.printStackTrace();
                }
                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    exception.printStackTrace();
                }

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    exception.printStackTrace();
                }
            });
            Document doc = builder.parse(ruta);
            return true;
        }
        catch (ParserConfigurationException | IOException | SAXException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
