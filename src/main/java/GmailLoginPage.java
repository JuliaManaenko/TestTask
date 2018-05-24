import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class GmailLoginPage {

    private final WebDriver driver;

    public GmailLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(how = How.XPATH, using = ".//*[@id='identifierId']")
    private WebElement phoneEmailInput;

    @FindBy(how = How.XPATH, using = ".//*[@name='password']")
    private WebElement passwordInput;

    @FindBy(how = How.XPATH, using = "(.//*[@class='RveJvd snByac'])[3]")
    private WebElement nextButton;

    @FindBy(how = How.XPATH, using = "(.//*[@class='dEOOab RxsGPe'])[1]")
    private WebElement errorMessage;

    public void inputPhoneValues(String value) {
        phoneEmailInput.clear();
        phoneEmailInput.sendKeys(value);
    }

    public void clickNextButton() {
        nextButton.click();
        waitForJSandJQueryToLoad();
    }

    public void openUrl() {
        driver.get("https://accounts.google.com/");
    }

    public boolean isPasswordInputDisplayed() {
        try {
            passwordInput.isDisplayed();
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    protected boolean waitForJSandJQueryToLoad() {

        WebDriverWait wait = new WebDriverWait(driver, 30);
    /*method for execute Java Script: page should be loaded*/
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };

        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    // no jQuery present
                    return true;
                }
            }
        };
        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }
}
