package ecommercePackage;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.Set;

public class EcommerceAutomation {

	public static void main(String[] args) {
		// Set the path to ChromeDriver
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\U1087082\\eclipse-workspace\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--password-store=basic");

		// Create a Chrome driver instance
		WebDriver driver = new ChromeDriver(options);

		// Navigate to the website
		driver.get("https://oyn-adminportal-qc-demo.salmonsky-1edff179.westeurope.azurecontainerapps.io");

		// Maximize the browser window
		driver.manage().window().maximize();

		// Find and fill in the login form
		WebElement usernameField = driver.findElement(By.id("mat-input-0"));
		WebElement passwordField = driver.findElement(By.id("mat-input-1"));
		WebElement checkbox = driver.findElement(By.id("mat-mdc-checkbox-1-input"));

		usernameField.sendKeys("store@admin.com");
		passwordField.sendKeys("P@ssw0rd");
		checkbox.click();

		// Click on the login button
		WebElement loginButton = driver
				.findElement(By.xpath("//button[@class='block primary' and contains(text(), 'Sign In')]"));
		loginButton.click();

		// Explicit wait for the home page to load

		Duration duration = Duration.ofSeconds(60);
		WebDriverWait wait = new WebDriverWait(driver, duration);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("My Dashboard")));

		// Verify if the login is successful by checking if the user is redirected to
		// the home page
		String homePageTitle = driver.getTitle();
		System.out.println("Title of the Home Page: " + homePageTitle);

		// close chrome pop-up window

		String mainWindowHandle = driver.getWindowHandle();
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String handle : allWindowHandles) {
			if (!handle.equals(mainWindowHandle)) {
				driver.switchTo().window(handle);
				driver.close();
				break;
			}

		}

		driver.switchTo().window(mainWindowHandle);

		// Click on user account
		WebElement parentElement = driver.findElement(By.xpath("//div[@class='user']"));
		parentElement.click();
		// select logout from drop down
		WebElement logoutOption = driver
				.findElement(By.xpath("//ul[@class='dropdown']/li[contains(text(), 'Logout')]"));
		logoutOption.click();

		// Explicit wait for the login page to appear
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Sign In']")));

		// Verify if the user is successfully logged out by checking if the login page
		// is displayed again

		String loginPageTitle = driver.getTitle();
		if (loginPageTitle.equals("Admin | Login")) {
			System.out.println("Logout successful. User is on the Login page.");
		} else {
			System.out.println("Logout unsuccessful. User is not on the Login page.");
		}

		// Close the browser
		driver.quit();
	}
}
