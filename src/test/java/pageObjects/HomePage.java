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
	@FindBy(xpath="//input[@placeholder='Search']")
	WebElement InkSearch;
	@FindBy(xpath="//button[@class=\"btn btn-default btn-lg\"]")
	WebElement InkSearchButton; 
	@FindBy(xpath="//a[normalize-space()=\"Qafox.com\"]")
	WebElement InkLogo;
	
	
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
	
	public void clickSearch()
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(InkSearch)).click();
		} catch(Exception e) {
			
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", InkSearch);
		}
	}
	public void setSearchProduct(String searchProductName)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			WebElement search = wait.until(ExpectedConditions.visibilityOf(InkSearch));
			try {
				search.clear();
			} catch (Exception ex) {
				// ignore if clear isn't supported
			}
			if (searchProductName != null && !searchProductName.isEmpty()) {
				search.sendKeys(searchProductName);
			}
		} catch(Exception e) {
			// fallback to JS click to focus if visibility failed
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", InkSearch);
		}
		
	}
	
	public void clickSearchButton()
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.visibilityOf(InkSearchButton));
			wait.until(ExpectedConditions.elementToBeClickable(InkSearchButton)).click();
		} catch(Exception e) {
			
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", InkSearchButton);
		}
	}
	
	public void clickLogo()      
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(InkLogo)).click();
		} catch(Exception e) {
			
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", InkLogo);
		}
	}
	
	
	
	
}