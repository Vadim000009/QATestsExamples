package pages;

import constants.WebUrls.YandexMarket;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Класс, отвечающий за работу на странице Яндекс Маркета
 */
public class YandexMarketPageFactory {

    protected WebDriver webDriver;
    protected Actions actions;
    protected WebDriverWait wait;

    @FindBy(how = How.XPATH, xpath = YandexMarket.CATALOG_BUTTON)
    WebElement catalogMarket;

    @FindBy(how = How.XPATH, xpath = YandexMarket.CATALOG_NOTEBOOKS_AND_PCS)
    WebElement catalogNotebooksAndPCs;

    @FindBy(how = How.XPATH, xpath = YandexMarket.CATALOG_NOTEBOOKS)
    WebElement catalogNotebooks;

    @FindBy(how = How.XPATH, xpath = YandexMarket.SEE_ALL)
    WebElement seeAllInMarket;

    @FindBy(how = How.XPATH, xpath = YandexMarket.NOTEBOOK_PRICE)
    List<WebElement> notebookPrice;

    @FindBy(how = How.XPATH, xpath = YandexMarket.NOTEBOOK_MANUFACTURING)
    List<WebElement> notebookManufacturing;

    @FindBy(how = How.XPATH, xpath = YandexMarket.RESULTS_OF_SEARCHING)
    List<WebElement> resultsOfSearching;

    @FindBy(how = How.XPATH, xpath = YandexMarket.SHOW_MORE_BUTTON)
    WebElement showMoreButton;

    @FindBy(how = How.XPATH, xpath = YandexMarket.NEXT_PAGE_BUTTON)
    WebElement nextPageButton;

    @FindBy(how = How.XPATH, xpath = YandexMarket.SEE_MORE_MANUFACTURING)
    WebElement seeMoreManufacturingButton;

    @FindBy(how = How.XPATH, xpath = YandexMarket.SEARCH_FIELD_ON_MARKET)
    WebElement searchFieldOnMarket;

    @FindBy(how = How.XPATH, xpath = YandexMarket.SEARCH_BUTTON_ON_MARKET)
    WebElement buttonSearchOnMarket;


    /**
     * Конструктор
     *
     * @param chromeDriver - поле, в котором содержится веб-драйвер
     */
    public YandexMarketPageFactory(WebDriver chromeDriver) {
        this.webDriver = chromeDriver;
        this.actions = new Actions(chromeDriver);
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
    }

    /**
     * Метод для обхода неработающих категорий
     */
    public void seeAll() {
        seeAllInMarket.click();
    }

    /**
     * Метод, в котором выполняется открытие каталога и переход по вкладкам Компьютеры -> Ноутбуки
     */
    public void openCatalogWithNotebooks() {
        catalogMarket.click();
        actions.moveToElement(catalogNotebooksAndPCs).release();
        catalogNotebooks.click();
    }

    /**
     * Метод, в котором выполняется установка определённого производителя
     */
    public void setNotebookManufacturing(List<String> manufacturing) {
        actions.moveToElement(seeMoreManufacturingButton).click().build().perform();
        notebookManufacturing.clear();
        waitFor(By.xpath(YandexMarket.SEE_MORE_MANUFACTURING));
        notebookManufacturing.stream()
                .filter(manufacture -> manufacturing.stream().anyMatch(choose -> manufacture.findElement(By.xpath(YandexMarket.NOTEBOOK_MANUFACTURING + "/following-sibling::span/span[text()]")).getText().equals(choose)))
                .limit(1)
                .forEachOrdered(manufacture -> actions.moveToElement(manufacture).click().build().perform());
    }

    /**
     * Метод, в котором выполняется установка определённой цены
     */
    public void setNotebookPrice(List<Integer> price) {
        IntStream.range(0, notebookPrice.size()).forEach(i -> {
            actions.moveToElement(notebookPrice.get(i)).click().build().perform();
            waitFor(By.xpath(YandexMarket.NOTEBOOK_PRICE));
            notebookPrice.get(i).sendKeys(price.get(i).toString());
        });
    }

    /**
     * Метод, в котором выполняется получение наименования модели из определённой позиции
     */
    public String getNotebookFromPosition(Integer position) {
        return resultsOfSearching.get(position).findElement(By.xpath(YandexMarket.RESULTS_OF_SEARCHING + "//h3")).getText();
    }

    /**
     * Метод, в котором задаётся условие поиска в Яндекс Маркете
     */
    public void setSearch(String condition) {
        actions.moveToElement(searchFieldOnMarket).click().build().perform();
        waitFor(By.xpath(YandexMarket.SEARCH_FIELD_ON_MARKET));
        searchFieldOnMarket.sendKeys(condition);
    }

    /**
     * Метод, в котором выполняется нажатие кнопки "Найти"
     */
    public void pressSearch() {
        actions.moveToElement(buttonSearchOnMarket).click().build().perform();
        resultsOfSearching.clear();
    }


    /**
     * Метод, в котором выполняется сбор всех результатов до последней страницы
     */
    public List<WebElement> getListAfterSearching() {
        resultsOfSearching.clear();
        while (true) {
            actions.scrollToElement(showMoreButton).perform();
            waitFor(By.xpath(YandexMarket.NEXT_PAGE_BUTTON));
            if (!nextPageButton.isEnabled()) {
                break;
            }
            actions.moveToElement(nextPageButton).click().build().perform();
            System.out.println(resultsOfSearching.size());
        }
        return resultsOfSearching;
    }

    /**
     * Метод, в котором выполняется ожидание элементов (имитация человека)
     */
    private WebElement waitFor(By selector) {
        return wait.until(ExpectedConditions.elementToBeClickable(webDriver
                .findElement(selector)));
    }
}
