import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GmailLoginTest extends TestBase {

    GmailLoginPage loginPage;

    @BeforeClass
    public void initPage() {
        loginPage = PageFactory.initElements(driver, GmailLoginPage.class);
    }

    @Test
    public void errorShouldAppearIfFillInputWithEmptyValue() {
        loginPage.openUrl();
        //loginPage.inputPhoneValues("");
        loginPage.clickNextButton();
        Assert.assertEquals(loginPage.getErrorMessage().getCssValue("visible"), "visible");
    }

    @Test
    public void errorHasTextIfFillInputWithEmptyValue() {
        loginPage.openUrl();
        // loginPage.inputPhoneValues("");
        loginPage.clickNextButton();
        Assert.assertEquals(loginPage.getErrorMessage().getText(), "Введите адрес электронной почты или номер телефона");
    }

    @Test(dataProvider = "incorrectValues")
    public void errorShouldAppearIfFillInputWithIncorrectValue(String value) {
        loginPage.openUrl();
        loginPage.inputPhoneValues(value);
        loginPage.clickNextButton();
        Assert.assertEquals(loginPage.getErrorMessage().getCssValue("display"), "block");
    }

    @Test(dataProvider = "incorrectValues")
    public void errorHasTextIfFillInputWithIncorrectValue(String value) throws InterruptedException {
        loginPage.openUrl();
        loginPage.inputPhoneValues(value);
        loginPage.clickNextButton();
        Assert.assertEquals(loginPage.getErrorMessage().getText(), "Введите адрес электронной почты или номер телефона");
    }

    @Test(dataProvider = "correctValues")
    public void passwordAppearsIfFillInputWithCorrectValue(String value) {
        loginPage.openUrl();
        loginPage.inputPhoneValues(value);
        loginPage.clickNextButton();
        Assert.assertTrue(loginPage.isPasswordInputDisplayed());
    }


    @DataProvider(name = "incorrectValues")
    public Object[][] getIncorrectValue() {
        return new Object[][]{
                {""},
                {"!"},
                {"test"},
                {"test@"},
                {"test@.gmail"},
                {"test@.gmail."},
                {"test@.gmail"}};
    }

    @DataProvider(name = "correctValues")
    public Object[][] getCorrectValue() {
        return new Object[][]{{"0951654061"},
                {"julia.manaenko@gmail.com"}};
    }
}
