package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;


public class Tc001_AccountRegistrationTest extends BaseClass{
   
     @Test(groups={"Regression","Master"}, invocationCount=1)
	void verify_account_registration() 
	{
       logger.info("*****Strating Tc001 AccountingRegistrationTest*******");
       try
       {
        HomePage hp=new HomePage(driver);
        
         hp.clickMyAccount();
         logger.info("Clicked on my account link");
         hp.clickRegister();
         logger.info("Clicked on Register link");
        
        AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
        logger.info("Providing the customer deatails");
        regpage.setFirstName(randomeString().toUpperCase());
        regpage.setLastName(randomeString().toUpperCase());
        regpage.setEmail(randomeString()+"@"+ "gmail.com");
        regpage.setTelephone(randomeNumber());
               String password=randomeAlphaNumber();
        
        regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		String confmsg = regpage.getConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!")) {
			Assert.assertTrue(true);
		}
		else {
			 logger.error("Test failed - confirmation message was: " + confmsg);
             logger.debug("Debug logs.");
            Assert.fail("Unexpected confirmation message: '" + confmsg + "'");
		}
		//Assert.assertEquals(confmsg,"Your Account Has Been Created!");
		
	
       }
       catch(Exception e)
       {
           logger.error("Exception during test execution: ", e);
           Assert.fail(e.getMessage());
       }
       finally {
           // reset session so repeated invocation start fresh
           try {
               driver.manage().deleteAllCookies();
               driver.get(p.getProperty("appURL"));
           } catch(Exception ignore) {}
       }
       logger.info("Finish Tc001 AccountRegistrationTest..");
	}
     
    
	
	
	
}