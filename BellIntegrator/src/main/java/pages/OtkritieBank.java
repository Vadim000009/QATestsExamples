package pages;

import constants.WebUrls;
import models.Exchange;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, отвечающий за работу на странице Банка открытие
 */
public class OtkritieBank {
    protected WebDriver webDriver;
    protected WebElement exchangeRates;

    protected List<Exchange> exchangeList;

    /**
     * Конструктор
     *
     * @param driver - поле, в котором содержится веб-драйвер
     */
    public OtkritieBank(WebDriver driver) {
        this.webDriver = driver;
        exchangeRates = driver.findElement(By.xpath(WebUrls.OtkritieBank.BLOCK_EXCHANGE));
        List<String> tempList = exchangeRates.findElements(By.xpath(WebUrls.OtkritieBank.TEXT_EXCHANGE))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        exchangeList = new ArrayList<>();
        for (int i = 0; i < tempList.size() / 3; i = i + 3) {
            exchangeList.add(new Exchange(
                    tempList.get(i),
                    Double.parseDouble(tempList.get(i + 1).replace(",", ".")),
                    Double.parseDouble(tempList.get(i + 2).replace(",", "."))
            ));
        }
    }

    public List<Exchange> getExchangeList() {
        return exchangeList;
    }
}
