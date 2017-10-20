package by.training.zakharchenya.factory;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import by.training.zakharchenya.entity.BankDeposit;
import by.training.zakharchenya.entity.CardAccount;
import by.training.zakharchenya.entity.Money;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

public class MoneyDOMBuilder extends AbstractMoneyBuilder {

	private static final Logger LOGGER = LogManager.getLogger(MoneyDOMBuilder.class);

	//private Set<Money> money;
	private DocumentBuilder docBuilder;

	public MoneyDOMBuilder() {
		this.money = new HashSet<Money>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) { 
			LOGGER.log(Level.ERROR, e);
		}
	}

	public Set<Money> getMoney() {
		return money;
	}

	public void buildSetMoney(String fileName) {
		Document doc = null;
		try {
			doc = docBuilder.parse(fileName);
			Element root = doc.getDocumentElement();
					
			NodeList bankdepositList = root.getElementsByTagName("bankdeposit");
			for (int i = 0; i < bankdepositList.getLength(); i++) {
				Element accountElement = (Element) bankdepositList.item(i);
				Money mon = buildBankdeposit(accountElement);
				money.add(mon);
			}
			
			NodeList cardaccountList = root.getElementsByTagName("cardaccount");
			for (int i = 0; i < cardaccountList.getLength(); i++) {
				Element accountElement = (Element) cardaccountList.item(i);
				Money account = buildCardAccount(accountElement);
				money.add(account);
			}

			
		} catch (IOException e) {
			LOGGER.log(Level.ERROR, e);
		} catch (SAXException e) {
			LOGGER.log(Level.ERROR, e);
		}
	}	
	
	private Money buildBankdeposit(Element personalAccountElement) {
		BankDeposit deposit = new BankDeposit();

		deposit.setName(getElementTextContent(personalAccountElement, "name"));
		deposit.setBankname(getElementTextContent(personalAccountElement, "bankname"));
		deposit.setCountry(getElementTextContent(personalAccountElement, "country"));
		deposit.setAmountondeposit(new BigInteger(getElementTextContent(personalAccountElement, "amountondeposit")));
		deposit.setProfitability(new BigInteger(getElementTextContent(personalAccountElement, "profitability")));
		deposit.setId(getElementTextContent(personalAccountElement, "id"));
		deposit.setTime(new BigInteger(getElementTextContent(personalAccountElement, "time")));
		deposit.setSurname(getElementTextContent(personalAccountElement, "surname"));
		deposit.setAddress(getElementTextContent(personalAccountElement, "address"));
		deposit.setPassportnumber(new BigInteger(getElementTextContent(personalAccountElement, "passportnumber")));

		return deposit;
	}
	
	private Money buildCardAccount(Element cardAccountElement) {
		CardAccount cardAccount = new CardAccount();

		cardAccount.setName(getElementTextContent(cardAccountElement, "name"));
		cardAccount.setBankname(getElementTextContent(cardAccountElement, "bankname"));
		cardAccount.setCountry(getElementTextContent(cardAccountElement, "country"));
		cardAccount.setAmountondeposit(new BigInteger(getElementTextContent(cardAccountElement, "amountondeposit")));
		cardAccount.setProfitability(new BigInteger(getElementTextContent(cardAccountElement, "profitability")));
		cardAccount.setId(getElementTextContent(cardAccountElement, "id"));
		cardAccount.setTime(new BigInteger(getElementTextContent(cardAccountElement, "time")));
		cardAccount.setSurname(getElementTextContent(cardAccountElement, "surname"));
		cardAccount.setAddress(getElementTextContent(cardAccountElement, "address"));
		cardAccount.setPassportnumber(new BigInteger(getElementTextContent(cardAccountElement, "passportnumber")));

		return cardAccount;
	}
	
	private static String getElementTextContent(Element element, String elementName) {
		NodeList nodeList = element.getElementsByTagName(elementName);
		Node node = nodeList.item(0);
		String text = node.getTextContent();
		return text;
	}
}