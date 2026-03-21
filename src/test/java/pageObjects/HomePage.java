package pageObjects;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class HomePage extends BasePage{
	
	public HomePage(WebDriver driver) 
	{
		super(driver);
	}
	
	
	@FindBy(xpath="//span[normalize-space()='My Account']")
	WebElement InkMyAccount;
	@FindBy(xpath="//a[normalize-space()='Register']")
	WebElement InkRegister;
	@FindBy(linkText = "Login")
	//@FindBy(xpath="//a[normalize-space()='Login']")
	WebElement linkLogin;
	
	
	public void clickMyAccount()      
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(InkMyAccount)).click();
		} catch(Exception e) {
			// fallback to JS click if element is obscured or not interactable
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", InkMyAccount);
		}
	}
	public void clickRegister() 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(InkRegister)).click();
		} catch(Exception e) {
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", InkRegister);
		}
		
	}
	public void clickLogin() 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(linkLogin)).click();
		} catch(Exception e) {
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", linkLogin);
		}
	}
	
	
	
}