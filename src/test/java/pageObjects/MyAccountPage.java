package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//h2[text()='My Account']")//MyAccount Page heading
	WebElement msgHeading;
	//@FindBy(xpath="ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Logout']")
	@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']")//logout on MYAccount  adde in 6 steps
	WebElement InkLogout;//a[@class='list-group-item'][normalize-space()='Logout']"//div[@class='list-group']//a[text()='logout']
	////ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Logout']
	public boolean isMyAccountPageExists() {
		try {
		return msgHeading.isDisplayed();
		}catch(Exception e)
		{
			return false;
		}
	}
	public void clickLogout() {
		InkLogout.click();
	}
	
	

}
