package by.training.zakharchenya.factory;

import by.training.zakharchenya.entity.Money;

import java.util.HashSet;
import java.util.Set;
public abstract class AbstractMoneyBuilder {
    protected Set<Money> money;
    public AbstractMoneyBuilder() {
        money = new HashSet<Money>();
    }
    public AbstractMoneyBuilder(Set<Money> banks) {
        this.money = banks;
    }
    public Set<Money> getMoney() {
        return money;
    }
    abstract public void buildSetMoney(String fileName);
}