package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private final String testNoteTitle = "Selenium Test";
    private final String testNoteDescription = "Testing Creating new note using Selenium";

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


}
