package test.by.training.zakharchenya.factory;
import by.training.zakharchenya.entity.Money;
import by.training.zakharchenya.factory.AbstractMoneyBuilder;
import by.training.zakharchenya.factory.MoneyBuilderFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class MoneyDOMBuilderTest {
    static final Logger LOGGER = LogManager.getLogger(MoneyDOMBuilderTest.class);
    MoneyBuilderFactory sFactory;// = new MoneyBuilderFactory();
    AbstractMoneyBuilder builder;// = sFactory.createMoneyBuilder("dom");

    @Before
    public void justRead(){
        sFactory = new MoneyBuilderFactory();
        builder = sFactory.createMoneyBuilder("dom");
        builder.buildSetMoney("data/banks.xml");

    }

    @Test
    public void TestOne(){
        LOGGER.log(Level.INFO, builder.getMoney());
        Assert.assertEquals(false, builder.getMoney().isEmpty());
    }
    @Test
    public void TestTwo(){
        LOGGER.log(Level.INFO, builder.getMoney());
        Assert.assertEquals(16,builder.getMoney().size());
    }

}
