package by.training.zakharchenya.entity;


public enum MoneyType {
    BANKS("banks"),
    CARDACCOUNT("cardaccount"),
    BANKDEPOSIT("bankdeposit"),
    CCV("ccv"),
    COUNTRY("country"),
    ID("id"),
    TYPE("type"),
    BANKNAME("bankname"),
    DEPOSITOR("depositor"),
    NAME("name"),
    SURNAME("surname"),
    ADDRESS("address"),
    PASSPORTNUMBER("passportnumber"),
    AMOUNTONDEPOSIT("amountondeposit"),
    PROFITABILITY("profitability"),
    TIME("time");
    private String value;
    private MoneyType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
