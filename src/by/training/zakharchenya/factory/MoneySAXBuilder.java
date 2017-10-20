package by.training.zakharchenya.factory;
import java.io.IOException;
import java.util.Set;

import by.training.zakharchenya.entity.Money;
import by.training.zakharchenya.handler.MoneyHandler;
import org.apache.logging.log4j.Level;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoneySAXBuilder extends AbstractMoneyBuilder {
    static final Logger LOGGER = LogManager.getLogger(MoneySAXBuilder.class);
    //private Set<Money> money;
    private MoneyHandler mh;
    private XMLReader reader;

    public MoneySAXBuilder() {
        mh = new MoneyHandler();
        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(mh);
        } catch (SAXException e) {
            LOGGER.log(Level.ERROR,"SAX parser error: " + e);
        }
    }

    public Set<Money> getMoney() {
        return money;
    }

    public void buildSetMoney(String fileName) {
        try {
            reader.parse(fileName);
        } catch (SAXException e) {
            LOGGER.log(Level.ERROR,"ошибка SAX парсера: " + e);
        } catch (IOException e) {
            LOGGER.log(Level.ERROR,"ошибка I/О потока: " + e);
        }
        money = mh.getMoney();
    }
}