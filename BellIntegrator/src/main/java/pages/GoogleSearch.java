package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Set;

/**
 * Класс, отвечающий за поиск в поисковой системе Google
 */
public class GoogleSearch {

    protected WebDriver chromeDriver;
    protected WebElement searchField;
    protected WebElement searchButton;

    /**
     * Конструктор
     *
     * @param chromeDriver - поле, в котором содержится веб-драйвер
     * @param field        - поле, в котором содержится поле поиска
     * @param button       - поле, в котором содержится кнопка поиска
     */
    public GoogleSearch(WebDriver chromeDriver, String field, String button) {
        this.chromeDriver = chromeDriver;
        this.searchField = chromeDriver.findElement(By.xpath(field));
        this.searchButton = chromeDriver.findElement(By.xpath(button));
    }

    /**
     * Метод, через который происходит поиск в системе
     *
     * @param word - поле, в котором содержится слово для поиска
     */
    public void find(String word) {
        searchField.click();
        searchField.sendKeys(word);
        searchButton.click();
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
}
