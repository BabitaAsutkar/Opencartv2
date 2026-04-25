package testBase;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Optional;




public class BaseClass {
	
   public static WebDriver driver;
   public Logger logger; //log4j 
   public Properties p;
        
	     @BeforeClass(groups= {"Master","Sanity","Regression",/*"DataDriven"*/})
	     @Parameters({"os","browser"})
		public void setup(@Optional("windows") String os, @Optional("chrome") String br) throws IOException
		{
			 //loading config.properties file
			 FileReader file=new FileReader("./src//test//resources//config.properties");
			 p=new Properties();
			 p.load(file);
			 
			 
			 logger=LogManager.getLogger(this.getClass());//log4j2
			 
			 // for a remote environment selenium grid
			 String executionEnv = p.getProperty("execution_env").trim().toLowerCase();
			 
			 if(executionEnv.equals("remote"))
			 {
			      DesiredCapabilities capabilities=new DesiredCapabilities();
			      //OS
			      if(os.equalsIgnoreCase("windows"))
			      {
				  capabilities.setPlatform(Platform.WIN11);
			      }
			      else if(os.equalsIgnoreCase("mac"))
			      {
				  capabilities.setPlatform(Platform.MAC);
			      }
			      else if(os.equalsIgnoreCase("linux"))
			      {
				  capabilities.setPlatform(Platform.LINUX);
			      }
			      else
			      {
				  System.out.println("No matching os");
				  return;
			      }
			 
			  
		  //browser
			      switch(br.toLowerCase()) 
			      {
			      case "chrome" :capabilities.setBrowserName("chrome"); break;
			      case "edge" :capabilities.setBrowserName("MicrosoftEdge"); break;
			      case "firefox" :capabilities.setBrowserName("firefox"); break;
			     default :  System.out.println("No matching browser"); return;
			      }
			      // FIX: removed stray extra quote that caused a malformed URL
			      driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);

		  logger.info("Remote WebDriver initialized for Selenium Grid");
	 }
		else if(executionEnv.equals("local"))
	    {
		  //local execution 	  
		  switch(br.toLowerCase())
			     {
			     case "chrome" : driver=new ChromeDriver(); break;
			     case "edge"  : driver=new EdgeDriver(); break;
			     case "firefox"  : driver=new FirefoxDriver(); break;
			     default: System.out.println("Invalid browser name.."); return;
			     }
		  logger.info("Local WebDriver initialized - Browser: " + br);
	    }
	 //local stop
		  
		 driver.manage().deleteAllCookies();
		 driver.manage().window().maximize();
		 
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		 
		 driver.get(p.getProperty("appURL"));//reading url form config.properties file
		
		}
	     @AfterClass(groups= {"Master","Sanity","Regression",/*"DataDriven"*/})
		public void tearDown() 
		{
			driver.quit();
		}
	     
	   
		public String randomeString()
	     {
			String generatedstring=RandomStringUtils.randomAlphabetic(5);
			return generatedstring;
		 }
		
		public String randomeNumber()
		{
			String generatednumber=RandomStringUtils.randomNumeric(10);
			return generatednumber;
		}
		public String randomeAlphaNumber()
		{
			String generatedstring=RandomStringUtils.randomAlphabetic(3);
			String generatednumber=RandomStringUtils.randomNumeric(3);
			return (generatednumber+"*"+generatedstring);
		}
		
		public String captureScreen(String tname) throws IOException {
			String timeStamp= new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			
			TakesScreenshot takesScreenshot=(TakesScreenshot) driver;
			File sourceFile=takesScreenshot.getScreenshotAs(OutputType.FILE);
			
			String targetFilePath=System.getProperty("user.dir")+"\\reports\\"+tname+"_"+ timeStamp +".png";
			File targetFile=new File(targetFilePath);
			
			sourceFile.renameTo(targetFile);
			
			return targetFilePath;
			
		}
		
	     

}