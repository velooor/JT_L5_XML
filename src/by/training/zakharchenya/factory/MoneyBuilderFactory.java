package by.training.zakharchenya.factory;

public class MoneyBuilderFactory {
    private enum TypeParser {
        SAX, STAX, DOM
    }
    public AbstractMoneyBuilder createMoneyBuilder(String typeParser) {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM:
                return new MoneyDOMBuilder();
            case STAX:
                return new MoneyStAXBuilder();
            case SAX:
                return new MoneySAXBuilder();
            default:
                throw new EnumConstantNotPresentException (type.getDeclaringClass(), type.name());
        }
    }
}