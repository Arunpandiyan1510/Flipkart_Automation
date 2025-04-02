package flip.org;

import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FlipAutomateTest {

	WebDriver driver;
	WebDriverWait wait;

	@BeforeClass
	public void setup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("https://www.flipkart.com/");
	}

	@Test
	public void addToCartTest() throws InterruptedException {
		// Search for watches
		WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.sendKeys("watches");
		searchBox.submit();

		// Wait for the product link and click it
		WebElement watch = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'KIDS-G-SPORT')]")));
		watch.click();

		// Switch to the new tab
		Set<String> handles = driver.getWindowHandles();
		for (String winHandle : handles) {
			driver.switchTo().window(winHandle);
		}

		// Scroll to "Add to Cart" button
		WebElement addBtn = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Add to cart')]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addBtn);
		Thread.sleep(1000);

		// Click Add to Cart button
		try {
			addBtn.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
		}

		// Wait for "Add Item" button and click
		WebElement addItem = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Add Item')]")));
		addItem.click();
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
