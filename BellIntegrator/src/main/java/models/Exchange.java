package models;

/**
 * Модель обмена валют
 */
public class Exchange {
    private String currency;
    private Double buyPrice;
    private Double sellPrice;

    /**
     * Конструктор
     *
     * @param currency  - поле, в котором содержится значение валюты
     * @param buyPrice  - поле, в котором содержится цена покупки Банком
     * @param sellPrice - поле, в котором содержится цена продажи Банком
     */
    public Exchange(String currency, Double buyPrice, Double sellPrice) {
        this.currency = currency;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }
}
