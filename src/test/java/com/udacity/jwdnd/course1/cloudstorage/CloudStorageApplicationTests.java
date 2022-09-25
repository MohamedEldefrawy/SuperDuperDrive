package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import page.HomePage;
import page.LoginPage;
import page.SignupPage;

import java.io.File;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @Value("${HOST}")
    private String HOST;
    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get(HOST + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get(HOST + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }


    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get(HOST + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() {
        // Create a test account
        doMockSignUp("Redirection", "Test", "RT", "123");

        // Check if we have been redirected to the log in page.
        Assertions.assertEquals(HOST + this.port + "/login", driver.getCurrentUrl());
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Test", "UT", "123");
        doLogIn("UT", "123");

        // Try to access a random made-up URL.
        driver.get(HOST + this.port + "/some-random-page");
        Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
    }


    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("uploadButton"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

    }

    @Test
    public void testUnAuthorizedUserRole() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        driver.get(HOST + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        driver.get(HOST + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        Assertions.assertTrue(driver.getPageSource().contains("Login"));

        driver.get(HOST + this.port + "/login");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        driver.get(HOST + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        Assertions.assertTrue(driver.getPageSource().contains("Login"));
    }

    @Test
    public void testAuthorizationFlow() {
        var signUpPage = new SignupPage(driver);
        var loginPage = new LoginPage(driver);
        var homePage = new HomePage(driver);
        var webDriverWait = new WebDriverWait(driver, 2);
        createUserAndLogin(webDriverWait, loginPage, signUpPage, "testAuth", "testAuthFlow", "auth", "auth");
        Assertions.assertTrue(driver.getPageSource().contains("Upload a New File"));
        homePage.logout();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        driver.get(HOST + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        Assertions.assertTrue(driver.getPageSource().contains("Login"));
    }

    @Test
    public void testCredentialCreation() {
        var webDriverWait = new WebDriverWait(driver, 2);
        var loginPage = new LoginPage(driver);
        var homepage = new HomePage(driver);
        var signUpPage = new SignupPage(driver);

        createUserAndLogin(webDriverWait, loginPage, signUpPage, "testCredentialCreation", "testCredentialCreationFlow", "CredentialCreation", "CredentialCreation");
        homepage.navCredentials();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewCredential")));
        homepage.openAddNewCredentialModal();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        homepage.createNewCredential();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        homepage.navCredentials();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewCredential")));
        Assertions.assertTrue(driver.getPageSource().contains(homepage.getUrl()));
    }

    @Test
    public void testEditCredential() {
        var webDriverWait = new WebDriverWait(driver, 2);
        var loginPage = new LoginPage(driver);
        var homepage = new HomePage(driver);
        var signUpPage = new SignupPage(driver);

        createUserAndLogin(webDriverWait, loginPage, signUpPage, "testCredentialEdit", "testCredentialEditFlow", "CredentialEdit", "CredentialEdit");
        homepage.navCredentials();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewCredential")));
        homepage.openAddNewCredentialModal();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        homepage.createNewCredential();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        homepage.navCredentials();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewCredential")));

        var btnEdit = driver.findElement(By.id("btnEdit-1"));
        homepage.openEditCredential(btnEdit);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        homepage.editCredential();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        homepage.navCredentials();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewCredential")));
        Assertions.assertTrue(driver.getPageSource().contains(homepage.getUrl() + "edit/"));
    }

    public void testDeleteCredential() {
        var webDriverWait = new WebDriverWait(driver, 2);
        var loginPage = new LoginPage(driver);
        var homepage = new HomePage(driver);
        var signUpPage = new SignupPage(driver);

        createUserAndLogin(webDriverWait, loginPage, signUpPage, "testCredentialDelete", "testCredentialDeleteFlow", "CredentialDelete", "CredentialDelete");
        homepage.navCredentials();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewCredential")));
        homepage.openAddNewCredentialModal();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        homepage.createNewCredential();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        homepage.navCredentials();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewCredential")));

        var btnDelete = driver.findElement(By.id("btnDelete-2"));
        homepage.deleteCredential(btnDelete);
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        homepage.navCredentials();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewCredential")));
        Assertions.assertFalse(driver.getPageSource().contains(homepage.getUrl()));

    }

    @Test
    public void testNoteCreation() {
        var webDriverWait = new WebDriverWait(driver, 2);
        var loginPage = new LoginPage(driver);
        var homepage = new HomePage(driver);
        var signUpPage = new SignupPage(driver);

        createUserAndLogin(webDriverWait, loginPage, signUpPage, "testNoteCreation", "testNoteCreationFlow", "noteCreation", "noteCreation");
        homepage.navToNotes();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewNote")));
        homepage.openAddNewNoteModal();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        homepage.createNewNote();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        homepage.navToNotes();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewNote")));
        Assertions.assertTrue(driver.getPageSource().contains(homepage.getTestNoteTitle()));
    }

    @Test
    public void testNoteEdit() {
        var webDriverWait = new WebDriverWait(driver, 2);
        var loginPage = new LoginPage(driver);
        var homepage = new HomePage(driver);
        var signUpPage = new SignupPage(driver);

        createUserAndLogin(webDriverWait, loginPage, signUpPage, "testEditNote", "testEditNoteFlow", "editNote", "editNote");
        homepage.navToNotes();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewNote")));
        homepage.openAddNewNoteModal();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        homepage.createNewNote();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        homepage.navToNotes();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewNote")));

        var btnEdit = driver.findElement(By.id("btnEdit-2"));
        homepage.openEditNoteModal(btnEdit);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        homepage.editNote();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        homepage.navToNotes();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewNote")));
        Assertions.assertTrue(driver.getPageSource().contains(homepage.getTestNoteTitle() + " edited"));
    }

    @Test
    public void testNoteDelete() {
        var webDriverWait = new WebDriverWait(driver, 2);
        var loginPage = new LoginPage(driver);
        var homepage = new HomePage(driver);
        var signUpPage = new SignupPage(driver);

        createUserAndLogin(webDriverWait, loginPage, signUpPage, "testDeleteNote", "testDeleteNoteFlow", "deleteNote", "deleteNote");
        homepage.navToNotes();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewNote")));
        homepage.openAddNewNoteModal();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        homepage.createNewNote();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        homepage.navToNotes();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewNote")));

        WebElement btnDelete = driver.findElement(By.id("btnDelete-1"));
        homepage.deleteNote(btnDelete);
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        homepage.navToNotes();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewNote")));
        Assertions.assertFalse(driver.getPageSource().contains(homepage.getTestNoteTitle()));
    }

    private void createUserAndLogin(WebDriverWait webDriverWait, LoginPage loginPage, SignupPage signUpPage, String firstname, String lastName, String userName, String password) {
        driver.get(HOST + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        signUpPage.createUser(firstname, lastName, userName, password);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        loginPage.userLogin(userName, password);
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }


}
