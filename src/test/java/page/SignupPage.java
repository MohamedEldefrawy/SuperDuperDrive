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


    public void createUser(String firstname, String lastName, String userName, String password) {
        this.txtFirstName.click();
        this.txtFirstName.sendKeys(firstname);

        this.txtLastName.click();
        this.txtLastName.sendKeys(lastName);

        this.txtUserName.click();
        this.txtUserName.sendKeys(userName);

        this.txtPassword.click();
        this.txtPassword.sendKeys(password);

        this.btnSignup.click();
    }

}
