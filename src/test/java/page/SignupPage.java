package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement txtFirstName;
    @FindBy(id = "inputLastName")
    private WebElement txtLastName;
    @FindBy(id = "inputUsername")
    private WebElement txtUserName;
    @FindBy(id = "inputPassword")
    private WebElement txtPassword;
    @FindBy(id = "buttonSignUp")
    private WebElement btnSignup;


    public SignupPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }


    public void createUser() {
        this.txtFirstName.click();
        this.txtFirstName.sendKeys("Mohamed");

        this.txtLastName.click();
        this.txtLastName.sendKeys("Ahmed");

        this.txtUserName.click();
        this.txtUserName.sendKeys("admin");

        this.txtPassword.click();
        this.txtPassword.sendKeys("admin");

        this.btnSignup.click();
    }

}
