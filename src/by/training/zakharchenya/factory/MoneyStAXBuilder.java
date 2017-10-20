package by.training.zakharchenya.factory;

import by.training.zakharchenya.entity.BankDeposit;
import by.training.zakharchenya.entity.CardAccount;
import by.training.zakharchenya.entity.Money;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Set;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import by.training.zakharchenya.entity.MoneyType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoneyStAXBuilder extends AbstractMoneyBuilder {
    static final Logger LOGGER = LogManager.getLogger(MoneyStAXBuilder.class);
    //private HashSet<Money> money = new HashSet<>();
    private XMLInputFactory inputFactory;
    public MoneyStAXBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }
    public Set<Money> getMoney() {
        return money;
    }
    public void buildSetMoney(String fileName) {
        FileInputStream inputStream = null;
        XMLStreamReader reader = null;
        String name;
        try {
            inputStream = new FileInputStream(new File(fileName));
            reader = inputFactory.createXMLStreamReader(inputStream);
            // StAX parsing
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (MoneyType.valueOf(name.toUpperCase()) == MoneyType.BANKDEPOSIT) {
                        Money st = buildMoney(reader, 0);
                        money.add(st);
                    }
                    else if(MoneyType.valueOf(name.toUpperCase()) == MoneyType.CARDACCOUNT) {
                        Money st = buildMoney(reader, 1);
                        money.add(st);
                    }
                }
            }
        } catch (XMLStreamException ex) {
            LOGGER.log(Level.ERROR,"StAX parsing error! " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.ERROR,"File " + fileName + " not found! " + ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOGGER.log(Level.ERROR,"Impossible close file "+fileName+" : "+e);
            }
        }
    }
    private Money buildMoney(XMLStreamReader reader, int flag) throws XMLStreamException {
        Money st = null;
        if(flag == 1){
            st = new CardAccount();
            ((CardAccount) st).setType(reader.getAttributeValue(null, MoneyType.TYPE.getValue()));
            ((CardAccount) st).setCcv(new BigInteger(reader.getAttributeValue(null,MoneyType.CCV.getValue()))); // проверить на null
        } else{
            st = new BankDeposit();
            ((BankDeposit) st).setType(reader.getAttributeValue(null, MoneyType.TYPE.getValue()));
            //((BankDeposit) st).setResponsiblemanager(reader.getAttributeValue(null, MoneyType.TYPE.getValue()));
        }
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (MoneyType.valueOf(name.toUpperCase())) {
                        case BANKNAME:
                            st.setBankname(getXMLText(reader));
                            break;
                        case COUNTRY:
                            st.setCountry(getXMLText(reader));
                            break;
                        case AMOUNTONDEPOSIT:
                            name = getXMLText(reader);
                            st.setAmountondeposit(new BigInteger(name));
                            break;
                        case PROFITABILITY:
                            name = getXMLText(reader);
                            st.setProfitability(new BigInteger(name));
                            break;
                        case ID:
                            st.setId(getXMLText(reader));
                            break;
                        case TIME:
                            name = getXMLText(reader);
                            st.setTime(new BigInteger(name));
                            break;
                        case NAME:
                            st.setName(getXMLText(reader));
                            break;
                        case SURNAME:
                            st.setSurname(getXMLText(reader));
                            break;
                        case ADDRESS:
                            st.setAddress(getXMLText(reader));
                            break;
                        case PASSPORTNUMBER:
                            name = getXMLText(reader);
                            st.setPassportnumber(new BigInteger(name));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();

                    if(flag == 1){
                        if (MoneyType.valueOf(name.toUpperCase()) == MoneyType.CARDACCOUNT) {
                            return st;
                        }
                    } else if(flag == 0){
                        if (MoneyType.valueOf(name.toUpperCase()) == MoneyType.BANKDEPOSIT) {
                            return st;
                        }
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Money");
    }
    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}