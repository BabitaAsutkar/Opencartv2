package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;



public class Tc003_loginDDT extends BaseClass {
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="Datadriven")//specify the data provider class the present in utilities package
	//getting data provider from different class
    public void verify_loginDDT(String Email,String Password,String exp) throws InterruptedException
    {
     logger.info("****starting Tc_003_LoginDDT*******");
     
     try {
		//Home page
    	HomePage hp=new HomePage(driver);
    	hp.clickMyAccount();
    	hp.clickLogin();
    	
    	//Loginpage
    	LoginPage lp=new LoginPage(driver);
    	lp.setEmail(Email);
    	lp.setPassword(Password);
    	lp.clickLogin();
    	
    	//MyAccount
    	MyAccountPage macc=new MyAccountPage(driver);
    	boolean targetPage=macc.isMyAccountPageExists();
    	//to check 
    	/* Data is valid -login success-test cases-logout
    	 data is valid - login failed -test fail
    	 
    	 *data is invalid -login success-test fail
    	 data is valid -login failed -test pass
    	 */
    	if(exp.equalsIgnoreCase("Valid")) 
    	{
    	   if(targetPage==true)
    	   {
    		   macc.clickLogout();
    		   Assert.assertTrue(true);
    		  
    	   }
    	   else
    	   {
    		   Assert.assertTrue(false);
    	   }
        }
    	if(exp.equalsIgnoreCase("Invalid"))
    	{
    		if(targetPage==true)
     	   {
    			   macc.clickLogout();
     		   Assert.assertTrue(false);
     		
     	   }
     	   else
     	   {
     		   Assert.assertTrue(true);
     	   }
    		
    		
    	}
     }catch(Exception e) 
     {
    	 Assert.fail();
     }
     Thread.sleep(3000);
    	 logger.info("****finished Tc_003_LoginDDT*******");
    }
    

}
