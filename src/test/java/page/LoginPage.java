package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement txtUserName;
    @FindBy(id = "inputPassword")
    private WebElement txtPassword;
    @FindBy(id = "login-button")
    private WebElement btnLogin;


    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void userLogin(String userName, String password) {
        this.txtUserName.click();
        this.txtUserName.sendKeys(userName);
        this.txtPassword.click();
        this.txtPassword.sendKeys(password);
        this.btnLogin.click();
    }
}
