package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	//DataProvider1
	@DataProvider(name="LoginData")
	public String[][]  getData() throws IOException
	{
		// "." represent the current project folder
	   String path=".\\testData\\Opencart_LoginData.xlsx";//taking xl=excel file from testData
	   ExcelUtility xlutil=new ExcelUtility(path);//creating anobject XLUTIlity
	   
	   int totalrows=xlutil.getRowCount("Sheet1");
	   int totalcols=xlutil.getCellCount("Sheet1",1);
	   
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
	//DataProvider3

}
