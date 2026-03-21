package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import java.time.Duration;

public class AccountRegistrationPage extends BasePage{
	
		WebDriver driver;
		
		public AccountRegistrationPage(WebDriver driver) {
			super(driver);
			this.driver = driver;
		}
		
		@FindBy(xpath="//input[@id='input-firstname']")
		WebElement txtFirstname;
		
		@FindBy(xpath="//input[@id='input-lastname']")
		WebElement txtLastname;
		
		@FindBy(xpath="//input[@id='input-email']")
		WebElement txtEmail;
		
		@FindBy(xpath="//input[@id='input-telephone']")
		WebElement txtTelephone;

		@FindBy(xpath="//input[@id='input-password']")
		WebElement txtPassword;
		
		@FindBy(xpath="//input[@id='input-confirm']")
		WebElement txtConfirmPassword;
		
		@FindBy(xpath="//input[@name='agree']")
		WebElement chkPolicy;
		
		@FindBy(xpath="//input[@value='Continue']")
		WebElement btnContinue;
		
		@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
		WebElement msgConfirmation;
		public void setFirstName(String fname) {
			txtFirstname.sendKeys(fname);
			
		}
		

		public void setLastName(String lname) {
			txtLastname.sendKeys(lname);
			
		}
		

		public void setEmail(String email) {
			txtEmail.sendKeys(email);
			
		}
		public void setTelephone(String tel) {
			txtTelephone.sendKeys( tel);
			
		}

		public void setPassword(String password) {
			txtPassword.sendKeys( password);
			
		}
		
		public void setConfirmPassword(String password) {
			txtConfirmPassword.sendKeys( password);
			
		}
		
		public void setPrivacyPolicy() {
			chkPolicy.click();
			
		}
		public void clickContinue() {
			// wait until continue button is clickable and click - more reliable than direct click
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
		}
		
		
		public String getConfirmationMsg() {
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        // wait specifically for the exact H1 text we expect for a successful registration
		        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[normalize-space()='Your Account Has Been Created!']")));
		        return msg.getText().trim();
		    }catch(Exception e) {
		        return "";
		    }
		}
}
		





