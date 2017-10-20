package by.training.zakharchenya.handler;

import java.math.BigInteger;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import by.training.zakharchenya.entity.BankDeposit;
import by.training.zakharchenya.entity.CardAccount;
import by.training.zakharchenya.entity.Money;
import by.training.zakharchenya.entity.MoneyType;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MoneyHandler extends DefaultHandler {
    static final Logger LOGGER = LogManager.getLogger(MoneyHandler.class);
    private Set<Money> money;
    private Money current = null;
    private MoneyType currentEnum = null;
    private EnumSet<MoneyType> withText;

    public MoneyHandler() {
        money = new HashSet<>();
        withText = EnumSet.range(MoneyType.ID, MoneyType.TIME);
    }

    public Set<Money> getMoney() {
        return money;
    }

    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if ("cardaccount".equals(localName)) {
            current = new CardAccount();
            ((CardAccount) current).setType(attrs.getValue(0));
            ((CardAccount) current).setCcv(new BigInteger(attrs.getValue(1)));
        } else if ("bankdeposit".equals(localName)) {
            current = new BankDeposit();
            ((BankDeposit) current).setType(attrs.getValue(0));
        } else {
            MoneyType temp = MoneyType.valueOf(localName.toUpperCase());
            if (withText.contains(temp)) {
                currentEnum = temp;
            }
        }
    }

    public void endElement(String uri, String localName, String qName) {
        if (("bankdeposit".equals(localName)) || ("cardaccount".equals(localName))) {
            money.add(current);
        }
    }

    public void characters(char[] ch, int start, int length) {
        String s = new String(ch, start, length).trim().toUpperCase();
        if (currentEnum != null) {
            switch (currentEnum) {
                case BANKNAME:
                    current.setBankname(new String(s));
                    break;
                case COUNTRY:
                    current.setCountry(new String(s));
                    break;
                case AMOUNTONDEPOSIT:
                    current.setAmountondeposit(new BigInteger(s));
                    break;
                case PROFITABILITY:
                    current.setProfitability(new BigInteger(s));
                    break;
                case ID:
                    current.setId(s);
                    break;
                case TIME:
                    current.setTime(new BigInteger(s));
                    break;
                case NAME:
                    current.setName(s);
                    break;
                case SURNAME:
                    current.setSurname(s);
                    break;
                case ADDRESS:
                    current.setAddress(s);
                    break;
                case PASSPORTNUMBER:
                    current.setPassportnumber(new BigInteger(s));
                    break;
                default:
                    throw new EnumConstantNotPresentException(
                            currentEnum.getDeclaringClass(), currentEnum.name());
            }
        }
        currentEnum = null;
    }
}
