package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.productMeetingTheSearchCriteriaPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class Tc005_ProductSearchDDT extends BaseClass {
	@Test(dataProvider="SearchData",dataProviderClass=DataProviders.class,groups="Datadriven")
	
	public void verify_SearchProductDDT(String searchKeyword,String ExpectedResult,String Exp) {
		HomePage hp = new HomePage(driver);
		productMeetingTheSearchCriteriaPage pmscp = new productMeetingTheSearchCriteriaPage(driver);
		try {
		 // Do not click logo at the start - start from whatever page the application opens to

		 // Clear/set search
		 hp.clickSearch();
		 hp.setSearchProduct(searchKeyword == null ? "" : searchKeyword);
		  logger.info("Click on the search button for keyword: " + searchKeyword);
		  hp.clickSearchButton();
	 

		 // Special handling for empty search field
		 if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
			 // Depending on application behavior, empty search might show all products or show a validation.
			 // We'll check for either a product list present or a no-match/validation message.
			 boolean foundProducts = pmscp.isProductFound();
			 boolean noMatch = pmscp.isNoMatchFoundMessageDisplayed();

			 // Accept either behavior but assert that the page responded (not stuck)
			 Assert.assertTrue(foundProducts || noMatch, "Empty search produced neither product list nor a no-match message.");
			 logger.info("Empty-search behavior: foundProducts=" + foundProducts + ", noMatch=" + noMatch);
			 return;
		 }

		 // For non-empty searchKeyword, use expected classification from Exp column (Valid/Invalid)
		 boolean found = pmscp.isProductFound();
		 boolean noMatch = pmscp.isNoMatchFoundMessageDisplayed();

		 if (Exp.equalsIgnoreCase("Valid")) {
			 Assert.assertTrue(found, "Expected products for keyword '" + searchKeyword + "' but none were found. NoMatchVisible=" + noMatch);
		 } else if (Exp.equalsIgnoreCase("Invalid")) {
			 Assert.assertTrue(noMatch || !found, "Expected no products for invalid keyword '" + searchKeyword + "' but product(s) were found.");
		 } else {
			 // If Exp column contains other guidance, default to requiring the UI to respond
			 Assert.assertTrue(found || noMatch, "Search did not return a recognizable response for keyword: " + searchKeyword);
		 }

	 } catch (Exception e) {
		 Assert.fail("Test failed due to exception: " + e.getMessage(), e);
	 } finally {
		 // After each data-driven iteration, click logo to navigate back to home for the next iteration
		 try {
			 hp.clickLogo();
		 } catch (Exception e) {
			 logger.warn("Could not click logo to return home: " + e.getMessage());
		 }
	 }
	 logger.info("*****Finished Tc005_SearchProductDDT****");  
  
  }

	// NOTE: The following two tests were originally included in this class and run in the same TestNG
	// suite; they perform empty-search validations (one explicit and one repeated 7x). That caused
	// the search field to be empty many times when the user expected only the Excel-driven sequence.
	// To keep the suite behavior aligned with the Excel data-driven run, these empty-search tests are
	// disabled by default — enable them intentionally when you want to run empty-search scenarios.

	// New single-run test to validate explicit empty-search behavior
	@Test(groups={"Sanity","EmptySearch"}, enabled=false)
	public void verify_EmptySearchShowsNoProductPresent() {
		HomePage hp = new HomePage(driver);
		productMeetingTheSearchCriteriaPage pmscp = new productMeetingTheSearchCriteriaPage(driver);
		try {
			// Do not click logo before searching; start from current landing page
			hp.clickSearch();
			// pass empty string to setSearchProduct which now clears the field
			hp.setSearchProduct("");
			logger.info("Performing empty search to validate 'no product present' message");
			hp.clickSearchButton();
			// User requested: "if that product is not prsent then system show anproduct is not present"
			// We'll assert that the application displays a no-match message. If your app shows all products
			// instead, change this assertion to match the desired behavior.
			boolean noMatch = pmscp.isNoMatchFoundMessageDisplayed();
			Assert.assertTrue(noMatch, "Expected 'product is not present' / no-match message when searching empty, but it was not displayed.");
			logger.info("Empty-search no-match message visible: " + noMatch);
		} catch (Exception e) {
			Assert.fail("Empty-search test failed due to exception: " + e.getMessage(), e);
		} finally {
			// After this verification, return to home
			try {
				hp.clickLogo();
			} catch (Exception e) {
				logger.warn("Could not click logo after empty-search: " + e.getMessage());
			}
		}
	}

	// New data-driven empty search test that runs 7 times using EmptySearchData provider
	@Test(dataProvider = "EmptySearchData", dataProviderClass = DataProviders.class, groups = {"Sanity","EmptySearch"}, enabled=false)
	public void verify_EmptySearchRepeated(String emptyKeyword) {
		HomePage hp = new HomePage(driver);
		productMeetingTheSearchCriteriaPage pmscp = new productMeetingTheSearchCriteriaPage(driver);
		try {
			hp.clickSearch();
			// pass the empty keyword (should be an empty string)
			hp.setSearchProduct(emptyKeyword);
			hp.clickSearchButton();
			boolean noMatch = pmscp.isNoMatchFoundMessageDisplayed();
			Assert.assertTrue(noMatch, "Expected no-match message for empty search iteration, but it was not displayed.");
			logger.info("Empty-search iteration result: noMatch=" + noMatch);
		} catch (Exception e) {
			Assert.fail("Empty-search repeated test failed due to exception: " + e.getMessage(), e);
		} finally {
			try {
				hp.clickLogo();
			} catch (Exception e) {
				logger.warn("Could not click logo after empty-search iteration: " + e.getMessage());
			}
		}
	}
}