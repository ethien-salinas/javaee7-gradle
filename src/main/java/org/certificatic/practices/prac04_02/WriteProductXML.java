package org.certificatic.practices.prac04_02;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class WriteProductXML {

  public static void writeXML() {

    Product p = new Product("Widget", 25.00, "The all new multifunction widget");

    try {
      JAXBContext jc = JAXBContext.newInstance(Product.class);
      Marshaller m = jc.createMarshaller();
      m.marshal(p, new File("product.xml"));
      m.marshal(p, System.out);
    } catch (JAXBException e) {
      System.out.println("JAXB Exception: " + e.getMessage());
    }
  }
}
