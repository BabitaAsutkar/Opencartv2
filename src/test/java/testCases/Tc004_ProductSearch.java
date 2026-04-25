package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.productMeetingTheSearchCriteriaPage;
import testBase.BaseClass;

public class Tc004_ProductSearch extends BaseClass  {
	
	  @Test(groups={"Regression","Master"}, invocationCount=1)
	  void verify_Search_Filter() {
		  logger.info("*****Strating Tc004 ProductSearch *******");
		  try {
			  HomePage hp=new HomePage(driver);
			  
			  hp.clickSearch();
			  logger.info("click on the search placeholder");
			  hp.setSearchProduct(p.getProperty("SearchKeyword"));
			  logger.info("Click on the search button");
			  hp.clickSearchButton();
			  
			  
			productMeetingTheSearchCriteriaPage pmtscp=new productMeetingTheSearchCriteriaPage(driver);
			boolean targetPage=pmtscp.isProductFound();
				
				//Assert.assertEquals(targetPage,true,"Test Failed");
				//Assert.assertTrue(targetPage);
				}
				catch(Exception e) {
					 Assert.fail("Test failed due to exception: " + e.getMessage(), e);
				}
		  logger.info("*****Finished Tc_004_ProductSearch****");  
			
		  }
			
				 
		  
	  }


