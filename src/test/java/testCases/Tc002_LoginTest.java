package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class Tc002_LoginTest extends BaseClass {
@Test(groups= {"Sanity","Master"})
public void Verify_Login() 
{
	logger.info("****Strting Tc_002_LoginTest******");
	try {
	//HomePage
	HomePage hp=new HomePage(driver);	
	hp.clickMyAccount();
	hp.clickLogin();
	//Loginpage
	LoginPage lp=new LoginPage(driver);
	lp.setEmail(p.getProperty("Email"));
	lp.setPassword(p.getProperty("Password"));
	lp.clickLogin();
	
	//MyAccount
	MyAccountPage macc=new MyAccountPage(driver);
	boolean targetPage=macc.isMyAccountPageExists();
	
	//Assert.assertEquals(targetPage,true,"Test Failed");
	Assert.assertTrue(targetPage);
	}
	catch(Exception e) {
		Assert.fail();
	}
	logger.info("*****Finished Tc_002_LoginTest****");
}
	
}
