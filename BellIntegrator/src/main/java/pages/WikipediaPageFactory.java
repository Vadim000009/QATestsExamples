package pages;

import constants.WebUrls.Wikipedia;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

/**
 * Класс, отвечающий за работу на странице Википедии
 */
public class WikipediaPageFactory {
    protected WebDriver chromeDriver;

    @FindBy(how = How.XPATH, using = Wikipedia.TABLE_ON_PAGE)
    WebElement table;

    @FindBy(how = How.XPATH, using = Wikipedia.TABLE_ROWS)
    List<WebElement> tableRows;

    /**
     * Конструктор
     *
     * @param chromeDriver - поле, в котором содержится веб-драйвер
     */
    public WikipediaPageFactory(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    public List<WebElement> getTableRows() {
        return tableRows;
    }
}

