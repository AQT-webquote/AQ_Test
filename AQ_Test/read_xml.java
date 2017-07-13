package AQ_test;


import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class read_xml {
   public static void main(String[] args) {
      try {
         File inputFile = new File("C:\\Users\\045313\\Desktop\\Webtesting\\workspace\\TestNG_WebTest\\test-output\\RFQ Submission\\Auto Quote.xml");
         SAXReader reader = new SAXReader();
         Document document = reader.read( inputFile );

         System.out.println("Root element :" 
            + document.getRootElement().getName());

         Element classElement = document.getRootElement();

         List<Node> nodes = document.selectNodes("/testsuite" );
         System.out.println("----------------------------");
         for (Node node : nodes) {
            System.out.println("\nCurrent Element :" 
               + node.valueOf("@name"));
            System.out.println("Time : " 
               + node.valueOf("@time") );
            System.out.println("Class Name : " + node.selectNodes("testcase").get(0));
            System.out.println("Last Name : " + node.selectSingleNode("testcase").valueOf("@name"));
            System.out.println("First Name : " + node.selectSingleNode("nickname").getText());
            System.out.println("Marks : " + node.selectSingleNode("marks").getText());
         }
      } catch (DocumentException e) {
         e.printStackTrace();
      }
   }
}