package pages;

import constants.WebUrls.Yandex;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;
import java.util.Set;

/**
 * Класс, отвечающий за работу на странице поисковой системы Yandex
 */
public class YandexPageFactory {

    protected WebDriver chromeDriver;

    @FindBy(how = How.XPATH, using = Yandex.INPUT_FIELD)
    WebElement searchField;

    @FindBy(how = How.XPATH, using = Yandex.BUTTON_SEARCH)
    WebElement searchButton;

    @FindBy(how = How.XPATH, using = Yandex.YANDEX_SERVICES)
    WebElement servicesButton;

    @FindBy(how = How.XPATH, using = Yandex.YANDEX_MARKET)
    WebElement yandexMarket;

    @FindBy(how = How.XPATH, using = Yandex.RESULTS_OF_SEARCH)
    List<WebElement> resultSearch;

    /**
     * Конструктор
     *
     * @param chromeDriver - поле, в котором содержится веб-драйвер
     */
    public YandexPageFactory(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    /**
     * Метод, через который происходит поиск в системе
     *
     * @param word - поле, в котором содержится слово для поиска
     */
    public void find(String word) {
        searchField.click();
        searchField.sendKeys(word);
        searchButton.submit();
    }

    /**
     * Метод, через который происходит переход по страница и переключение на соответствующую вкладку
     *
     * @param link - поле, в котором содержится ссылка
     */
    public void goPageByLinkName(By link) {
        chromeDriver.findElement(link).click();
        Set<String> tabs = chromeDriver.getWindowHandles();
        for (String tab : tabs) {
            chromeDriver.switchTo().window(tab);
        }
    }

    public void goPageOnMarket() {
        servicesButton.click();
        yandexMarket.click();
        Set<String> tabs = chromeDriver.getWindowHandles();
        for (String tab : tabs) {
            chromeDriver.switchTo().window(tab);
        }
    }

    public List<WebElement> getResultSearch() {
        return resultSearch;
    }
}
