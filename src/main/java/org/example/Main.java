package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Main {


    private static final String PATIENT_NAME = "SharmaCC";
    private static final String PATIENT_AGE = "31";
    private static final String PATIENT_PH_NO = "9876543212";
    private static final long WAIT_SECONDS = 30;
    private static final String USER_NAME = "scott";
    private static final String PASS_WORD = "scott";
    private static final String DR_NAME = "Dr.Abi Sd";
    private static final String ADMISSION_TYPE = "Out Patient";
    private static final long THREAD_SECONDS = 3000;


    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");


        // Initialize WebDriver
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://18.215.63.38:8095/#/auth/login");

        // Open Google

        // Find the search box and enter a search query
        loggingFunction(driver);

        Thread.sleep(THREAD_SECONDS);

        locationSelected(driver);

        Thread.sleep(THREAD_SECONDS);

        System.out.println("Logging in...");

        patientRegister(driver);


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        createAppointment(driver);


        checkingAppoinment(driver);

        addPrescription(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));

        boolean isWelcomeFound = false;
        int attempts = 0;
        int maxAttempts = 10; // Adjust as needed

        do {
            try {
                WebElement welcomeText = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//li/span[contains(text(),'Welcome')]")
                ));

                if (welcomeText.isDisplayed()) {
                    System.out.println("Login successful! 'Welcome' text found.");
                    isWelcomeFound = true;
                    // Call the next function after login
                }
            } catch (TimeoutException e) {
                System.out.println("Retrying... 'Welcome' text not found yet.");
            }

            attempts++;
            Thread.sleep(3000); // Wait for 3 seconds before retrying
        } while (!isWelcomeFound && attempts < maxAttempts);


        if (isWelcomeFound) {
            pharmacyBill(driver);
        }
    }

    private static void pharmacyBill(ChromeDriver driver) throws InterruptedException {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement menuButton = driver.findElement(By.id("mega-menu-nav-btn"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", menuButton);

        System.out.println("Clicked");


        // Wait for the "Current Admissions" link to become clickable

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));

// Wait for the overlay/loader to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("page-loader-wrapper")));

        System.out.println("Wait loader complete");

// Now wait for "Create Appointment" link to be clickable
        WebElement openPharmacyTap = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),' Pharmacy')]"))
        );

// Click the element
        openPharmacyTap.click();


        // Wait for the row containing "SharmaA"
        WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//tr[td/span[contains(text(),'" + PATIENT_NAME + "')]]")
        ));

// Find the "Bill" button inside the same row
        WebElement billButton = row.findElement(By.xpath(".//button[@title='Bill']"));

// Wait until the button is clickable, then click it
        wait.until(ExpectedConditions.elementToBeClickable(billButton)).click();

        System.out.println("Bill button clicked successfully for patient SharmaA.");


        // Wait for the "Generate Bill" button to be present
        WebElement generateBillButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(),'Generate Bill')]")
        ));

// Wait for the button to be clickable and then click it
        wait.until(ExpectedConditions.elementToBeClickable(generateBillButton)).click();

        System.out.println("Generate Bill button clicked successfully.");


        WebElement payButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Pay')]")
        ));

// Scroll to the button (if needed)
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", payButton);
        Thread.sleep(1000); // Small delay to ensure visibility

// Click the button
        payButton.click();

        System.out.println("Pay button clicked successfully.");


    }

    private static void addPrescription(ChromeDriver driver) {
        try {
            Thread.sleep(THREAD_SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement menuButton = driver.findElement(By.id("mega-menu-nav-btn"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", menuButton);

        System.out.println("Clicked");


        // Wait for the "Current Admissions" link to become clickable

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));

// Wait for the overlay/loader to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("page-loader-wrapper")));

        System.out.println("Wait loader complete");

// Now wait for "Create Appointment" link to be clickable
        WebElement viewAdmission = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Current Admissions')]"))
        );

// Click the element
        viewAdmission.click();


        try {

            WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//td[span[contains(text(),'" + PATIENT_NAME + "')]]/parent::tr")
            ));

// Step 2: Find and click the dropdown inside this row
            WebElement dropdownIcon = row.findElement(By.xpath(".//span[contains(@class,'ti-angle-double-down')]"));
            wait.until(ExpectedConditions.elementToBeClickable(dropdownIcon)).click();
            System.out.println("Dropdown icon clicked successfully.");
            Thread.sleep(2000); // Ensure dropdown loads

// Step 3: Now locate "Prescription" inside the SAME ROW
            WebElement prescriptionOption = row.findElement(By.xpath(".//span[contains(text(),'Prescription')]"));
            wait.until(ExpectedConditions.elementToBeClickable(prescriptionOption)).click();
            System.out.println("Clicked on 'Prescription' successfully!");

            Thread.sleep(2000);
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("current-admission-prescribedAdd")
            ));
            addButton.click();
            System.out.println("Clicked on 'Add New' button successfully!");


            // Locate the input field and type the medicine name
            WebElement medicineInput = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@placeholder='Enter Medicine']")
            ));
            medicineInput.sendKeys("Sulphasala");

// Wait for the autocomplete options to load
            Thread.sleep(2000); // Adjust if necessary

// Select the correct option from the dropdown
            WebElement selectedOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//mat-option//span[contains(text(),'Sulphasalazine Tablet  50 50 Tablets')]")
            ));
            selectedOption.click();

            System.out.println("Selected 'Sulphasalazine Tablet 50 50 Tablets' successfully!");
// Locate the quantity input field using its attributes
            WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@type='number' and @title='Quantity']")
            ));

// Clear any existing value and enter "10"
            quantityInput.clear();
            quantityInput.sendKeys("10");

            System.out.println("Entered quantity: 10");

            // Locate the "Save & Close" button using its class and text
            WebElement saveCloseButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(), 'Save & Close')]")
            ));

// Click the button
            saveCloseButton.click();

            System.out.println("Clicked 'Save & Close' button successfully.");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void checkingAppoinment(ChromeDriver driver) throws InterruptedException {
        System.out.println("Wait over. Proceeding with next steps...");

        Thread.sleep(THREAD_SECONDS);
        WebElement menuButton = driver.findElement(By.id("mega-menu-nav-btn"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", menuButton);

        System.out.println("Clicked");


        // Wait for the "Current Admissions" link to become clickable

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));

// Wait for the overlay/loader to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("page-loader-wrapper")));

        System.out.println("Wait loader complete");

// Now wait for "Create Appointment" link to be clickable
        WebElement createAppointmentLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'View Appointments')]"))
        );

// Click the element
        createAppointmentLink.click();


        try {
            // Locate the row containing 'SharmaE'
            WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//td[span[contains(text(),'" + PATIENT_NAME + "')]]/parent::tr")
            ));

            // Find the "Check In" button in the same row
            WebElement checkInButton = row.findElement(By.xpath(".//button[@title='Check In']"));

            // Wait and Click the button
            wait.until(ExpectedConditions.elementToBeClickable(checkInButton)).click();

            System.out.println("Successfully clicked 'Check In' button for SharmaE.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


    }

    private static void patientRegister(ChromeDriver driver) {

        try {
            Thread.sleep(THREAD_SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));

        WebElement menuButton = driver.findElement(By.id("mega-menu-nav-btn"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", menuButton);


        // Wait for the "Current Admissions" link to become clickable

        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));

// Wait for the overlay/loader to disappear
        wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.className("page-loader-wrapper")));

// Now wait for "Patient Registration" link to be clickable
        WebElement patientRegistrationLink = wait1.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Patient Registration')]"))
        );

// Click the element
        patientRegistrationLink.click();


        System.out.println("Successfully clicked on 'New Patient loaded'!");


        // Wait for the input field to be visible
        WebDriverWait waitFirstName = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));
        WebElement firstNameField = waitFirstName.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@formcontrolname='firstName']")));

// Fill the input field with a name
        firstNameField.sendKeys(PATIENT_NAME);


        WebElement ageField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@formcontrolname='age']")));

// Fill the input field with a numeric value (e.g., 25)
        ageField.sendKeys(PATIENT_AGE);


        // Wait for the phone number input field to be visible
        WebElement phoneNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@formcontrolname='phoneNumber']")));

// Fill the input field with a valid phone number (e.g., 9876543210)
        phoneNumberField.sendKeys(PATIENT_PH_NO);


        List<WebElement> genderRadios = driver.findElements(By.xpath("//input[@formcontrolname='gender' and @value='Male']"));

        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.xpath("//input[@formcontrolname='gender' and @value='Male']"));
        js1.executeScript("arguments[0].click();", element);


        // Step 1: Click the MatSelect dropdown to open the options
        WebElement matSelect = wait.until(ExpectedConditions.elementToBeClickable(By.id("mat-select-0")));
        matSelect.click();

        // Step 2: Wait for the options to be visible
        WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(), 'Chennai')]")
        ));

        // Step 3: Click on the option to select it
        cityOption.click();

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Submit')]")));
        submitButton.click();

    }

    private static void locationSelected(ChromeDriver driver) {
        //locations eelct
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));
        WebElement locationDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@title='Location']")));

// Wait for the option to be visible before selecting
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@title='Location']//option[text()='Navaur branch']")));

        Select select = new Select(locationDropdown);
        select.selectByVisibleText("Navaur branch");


// After the wait, find the dropdown and select the option

        WebDriverWait waitSubmit = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));
        WebElement proceedButton = waitSubmit.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Proceed Next']")));

        proceedButton.click();
    }

    private static void loggingFunction(ChromeDriver driver) {
        WebElement usernameField = driver.findElement(By.id("signin-email"));

        // Enter the username
        usernameField.sendKeys(USER_NAME);
        WebElement passwordField = driver.findElement(By.id("signin-password"));
        passwordField.sendKeys(PASS_WORD);


        // Submit the login

        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();

    }

    private static void createAppointment(ChromeDriver driver) throws InterruptedException {


        try {
            Thread.sleep(THREAD_SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement menuButton = driver.findElement(By.id("mega-menu-nav-btn"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", menuButton);

        System.out.println("Clicked");


        // Wait for the "Current Admissions" link to become clickable

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));

// Wait for the overlay/loader to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("page-loader-wrapper")));

        System.out.println("Wait loader complete");

// Now wait for "Create Appointment" link to be clickable
        WebElement createAppointmentLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Create Appointment')]"))
        );

// Click the element
        createAppointmentLink.click();


// Locate the input field
        WebElement patientCodeInput = wait.until(
                ExpectedConditions.elementToBeClickable(By.name("patientCode"))
        );

// Enter text in the input field
        patientCodeInput.sendKeys(PATIENT_NAME);

// Wait for the autocomplete dropdown to appear and select an option
//        WebElement autocompleteOption = wait.until(
//                ExpectedConditions.elementToBeClickable(By.xpath("//mat-option//span[contains(text(),'"+PATIENT_NAME+"')]"))
//        );
//        autocompleteOption.click();

        List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//mat-option")));
        for (WebElement option : options) {
            if (option.getText().contains(PATIENT_NAME)) {
                option.click();
                break;
            }
        }


        WebElement purposeDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("select[formcontrolname='purpose']")
        ));

        // Select "Out Patient"
        Select select = new Select(purposeDropdown);
        select.selectByVisibleText(ADMISSION_TYPE);

        // Verify selection
        WebElement selectedOption = select.getFirstSelectedOption();
        System.out.println("Selected Purpose of Visit: " + selectedOption.getText());


        WebElement selectDoctorId = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("select[formcontrolname='doctorId']")
        ));

        // Select "Out Patient"
        Select selectDr = new Select(selectDoctorId);
        selectDr.selectByVisibleText(DR_NAME);
        // Optional: Verify selection

        WebElement saveButton = driver.findElement(By.id("saveNdCloseAp"));
        saveButton.click();

    }
}