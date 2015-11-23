import org.w3c.dom.*;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.bind.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hossein on 11/21/15.
 */
public class xmlParser {

    private Document doc;
    private Element rootElement;

    public xmlParser(File inputFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            rootElement = doc.getDocumentElement();
            System.out.println("Root element : " + doc.getDocumentElement().getNodeName());

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTerminalId() {
        return rootElement.getAttribute("id");
    }

    public String getTerminalType() {
        return rootElement.getAttribute("type");
    }

    public String getServerIP() {
        return ((Element)doc.getElementsByTagName("server").item(0)).getAttribute("ip");
    }

    public int getServerPort() {
        return Integer.parseInt(((Element)doc.getElementsByTagName("server").item(0)).getAttribute("port"));
    }

    public String getOutLogPath() {
        return ((Element)doc.getElementsByTagName("outLog").item(0)).getAttribute("path");
    }

    public ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        NodeList transactionList = doc.getElementsByTagName("transaction");
        //sysout -> number of given transactions
        for (int i = 0; i < transactionList.getLength(); i++) {
            Node transactionNode = transactionList.item(i);
            //sysout -> current element: transactionNode.getName()
            if (transactionNode.getNodeType() == Node.ELEMENT_NODE) {
                Element transactionElement = (Element) transactionNode;
                Transaction transaction = new Transaction(transactionElement.getAttribute("id"),
                        transactionElement.getAttribute("type"), transactionElement.getAttribute("amount"),
                        transactionElement.getAttribute("deposit"));
                transactions.add(transaction);
            }
        }
        return transactions;
    }
}
