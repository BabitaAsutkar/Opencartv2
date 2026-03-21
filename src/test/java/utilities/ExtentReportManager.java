package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager  implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	
	public void onStart(ITestContext TestContext) {
		 /*SimpleDataFormate df=new SimpleDataFormat("yyyy.MM.dd.HH.mm.ss");
		   Date dt=new Date();
		  String currentdatetimestamp=df.format(dt)*/
		
		String timeStamp =new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp
		repName = "Test-Report-"+timeStamp+".html";
		sparkReporter=new ExtentSparkReporter(".\\reports\\"+repName); //Specify location of the report
		
		sparkReporter.config().setDocumentTitle("opencart automation report"); //title of the report
		sparkReporter.config().setReportName("opencart functional testing"); //name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application","Opencart");
		extent.setSystemInfo("Module","Admin");
		extent.setSystemInfo("Sub Module","Customers");
		extent.setSystemInfo("User Name",System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		
		String os=TestContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser= TestContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups =TestContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups",includedGroups.toString());
		}
	}
	
	public void onTestSuccess(ITestResult result) {
		test =extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS,result.getName()+"got successfully excuted");
		}
	
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, result.getName()+"got failed");
		test.log(Status.INFO, result.getThrowable());
		
		try {
			String imgPath= new BaseClass().captureScreen(result.getName());
			File f=new File(imgPath);
			test.addScreenCaptureFromPath(f.getName());
			//test.addScreenCaptureFromPath(imgPath);
			}
		catch(IOException e1) 
		  {
			e1.printStackTrace();
		   }
	}
		
		public void onTestSkipped(ITestResult result) {
			test=extent.createTest(result.getTestClass().getName());
			test.assignCategory(result.getMethod().getGroups());
			test.log(Status.SKIP,result.getName()+"got skipped");
			test.log(Status.INFO,result.getThrowable().getMessage());
	}
	
       public void onFinish(ITestContext testContext) {
    	   extent.flush();
    	   String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
    	   File extentReport =new File(pathOfExtentReport);
    	   
    	   try {
    		   Desktop.getDesktop().browse(extentReport.toURI());
    		  }catch(IOException e) {
    			  e.printStackTrace();
    		  }
    	   
    	   
    	  /*  try{
    	    	URL url= new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
    	    
    	    //create the emailmessage 
    	    ImageHtmlEmail email=new ImageHtmlEmail();
    	    email.setDataSourceResolver(new DataSourceUrlResolver(url));
    	    email.setHostName("smtp.googlemail.com");
    	    email.setSmtpPort(465);
    	    email.setAuthenticator(new DefaultAuthenticator("babitasutkar100@gmail.com","pasword"));
    	    email.setSSLOnConnect(true); 
    	    email.setFrom("babitasutkar100@gmail.com");//Sender 
    	    email.setSubject("test result" );
    	    email.setMsg("Please find Attached Reports.....");
    	    email.addTo("chaitaliasutkar@gmail.com"); //receiver 
    	    email.attach(url,"extent report","Please check report..."); 
    	    email.send();//send the email
    	    }
    	    catch(Exception e){
    	    e.printStackTrace();
    	   
    	   
       }*/
	}
	

}
