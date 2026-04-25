package utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

public class DataProviders {
	//DataProvider1
	@DataProvider(name="LoginData")
	public static String[][]  getData() throws IOException
	{
		// " . " represent the current project folder
	   
		String path=System.getProperty("user.dir")+"\\testData\\Opencart_LoginData.xlsx";//taking xl=excel file from testData
		
	   ExcelUtility xlutil=new ExcelUtility(path);//creating anobject XLUTIlity
	   
	   int totalrows=xlutil.getRowCount("Sheet1");
	   int totalcols=xlutil.getCellCount("Sheet1",1);
	   
	   System.out.println("[DataProviders] Loading LoginData from: " + path + " rows=" + totalrows + " cols=" + totalcols);
	   
	   String logindata[][]=new String[totalrows][totalcols];//created for two dimension array which can store
	   
	   for(int i=1;i<=totalrows;i++)   //1 //read the data from xl storing in two dimension array
	   {
		   for(int j=0; j<totalcols;j++)//0  1 is rows j is col
            {
		     logindata[i-1][j]=xlutil.getCellData("Sheet1", i, j);//1,0  
	          }
	   }
	   return logindata;  //returning two dimension array
	
	
	}
	//Data provider2
	@DataProvider(name="SearchData")
	public static String[][] getSearchTestData() throws IOException {
		String path = System.getProperty("user.dir")+"\\testData\\Search_TestData.xlsx";
		ExcelUtility xlutil = new ExcelUtility(path);
		
		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1", 1);
		
		System.out.println("[DataProviders] Loading SearchData from: " + path + " rows=" + totalrows + " cols=" + totalcols);
		
		// Use a list to collect only non-empty rows (skip blank rows)
		List<String[]> rows = new ArrayList<>();
		for (int i = 1; i <= totalrows; i++) {
			// read first column (assumed to be searchKeyword) and check if empty
			String firstCell = xlutil.getCellData("Sheet1", i, 0);
			boolean isRowEmpty = (firstCell == null) || (firstCell.trim().isEmpty());
			if (isRowEmpty) {
				// skip rows where search keyword is empty
				continue;
			}
			String[] rowData = new String[totalcols];
			for (int j = 0; j < totalcols; j++) {
				rowData[j] = xlutil.getCellData("Sheet1", i, j);
			}
			rows.add(rowData);
		}
		
		// Convert to String[][] as required by TestNG DataProvider
		String[][] searchData = new String[rows.size()][];
		for (int i = 0; i < rows.size(); i++) {
			searchData[i] = rows.get(i);
		}
		return searchData;
	}
	// New DataProvider: 7 empty search rows for repeated empty-search validation
	@DataProvider(name = "EmptySearchData")
	public static Object[][] getEmptySearchData() {
		int repeat = 7; // run 7 times
		Object[][] data = new Object[repeat][1];
		for (int i = 0; i < repeat; i++) {
			data[i][0] = ""; // empty search keyword
		}
		return data;
	}
	//DataProvider3

}