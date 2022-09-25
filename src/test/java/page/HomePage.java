package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private final String testNoteTitle = "Selenium Test";
    private final String testNoteDescription = "Testing Creating new note using Selenium";

    private final String url = "https://www.google.com.eg/";
    private final String userName = "credentialUser";
    private final String password = "credentialUser";

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getTestNoteTitle() {
        return testNoteTitle;
    }

    public String getTestNoteDescription() {
        return testNoteDescription;
    }

    @FindBy(id = "logout")
    private WebElement btnLogout;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotes;

    @FindBy(id = "btnAddNewNote")
    private WebElement btnAddNewNote;

    @FindBy(id = "note-title")
    private WebElement txtNoteTitle;

    @FindBy(id = "note-description")
    private WebElement txtNoteDescription;

    @FindBy(id = "noteSubmit")
    private WebElement btnNoteSubmit;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentials;
    @FindBy(id = "btnAddNewCredential")
    private WebElement btnAddNewCredential;
    @FindBy(id = "credential-url")
    private WebElement txtUrl;
    @FindBy(id = "credential-username")
    private WebElement txtUserName;
    @FindBy(id = "credential-password")
    private WebElement txtPassword;
    @FindBy(id = "credentialSubmit")
    private WebElement btnCredentialSubmit;


    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        this.btnLogout.click();
    }

    public void navToNotes() {
        this.navNotes.click();
    }

    public void openAddNewNoteModal() {
        this.btnAddNewNote.click();
    }

    public void createNewNote() {
        this.txtNoteTitle.click();
        this.txtNoteTitle.sendKeys(this.testNoteTitle);
        this.txtNoteDescription.click();
        this.txtNoteDescription.sendKeys(this.testNoteDescription);
        this.btnNoteSubmit.submit();
    }

    public void deleteNote(WebElement btnDelete) {
        btnDelete.click();
    }

    public void openEditNoteModal(WebElement btnEdit) {
        btnEdit.click();
    }

    public void editNote() {
        this.txtNoteTitle.click();
        this.txtNoteTitle.sendKeys(" edited");
        this.txtNoteDescription.click();
        this.txtNoteDescription.sendKeys(" edited");
        this.btnNoteSubmit.submit();
    }

    public void navCredentials() {
        this.navCredentials.click();
    }

    public void openAddNewCredentialModal() {
        this.btnAddNewCredential.click();
    }

    public void createNewCredential() {
        this.txtUrl.click();
        this.txtUrl.sendKeys(this.url);
        this.txtUserName.click();
        this.txtUserName.sendKeys(this.userName);
        this.txtPassword.click();
        this.txtPassword.sendKeys(this.password);
        this.btnCredentialSubmit.submit();
    }

    public void openEditCredential(WebElement btnEdit) {
        btnEdit.click();
    }

    public void editCredential() {
        this.txtUrl.click();
        this.txtUrl.sendKeys("edit/");
        this.txtUserName.click();
        this.txtUserName.sendKeys("Edit");
        this.txtPassword.click();
        this.txtPassword.sendKeys("Edit");
        this.btnCredentialSubmit.submit();
    }

    public void deleteCredential(WebElement btnDelete) {
        btnDelete.click();
    }

}
