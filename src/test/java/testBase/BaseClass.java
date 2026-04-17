package testBase;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.beust.jcommander.Parameter;

public class BaseClass {

// static so ExtentReportManager can access without inheritance
public static WebDriver driver; 

public Logger logger;
public Properties p;
	
	@BeforeClass(groups= {"Sanity", "Regression", "Master"})
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws IOException {

		// loading config.properties 
		FileReader file= new FileReader(System.getProperty("user.dir") 
				+ "//src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		logger = LogManager.getLogger(this.getClass());	
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) 
		{	
			// set OS
			String platformName;
			
			if(os.equalsIgnoreCase("windows"))
			{
				platformName = "windows 11";
			}
			else if(os.equalsIgnoreCase("linux"))
			{
				platformName = "linux";
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				platformName = "mac";
			}
			else
			{
				logger.error("No matching OS found: " + os);
				return; 
			}
				
		// set browser
		switch (br.toLowerCase()) {
		case "chrome":
			driver = new RemoteWebDriver(
					URI.create("http://localhost:4444").toURL(),
					new ChromeOptions());
			break;
		case "firefix":
			driver= new RemoteWebDriver(
					URI.create("http://localhost:4444").toURL(),
					new FirefoxOptions());
			break;
		case "edge": 
			driver = new RemoteWebDriver(
					URI.create("http://localhost:4444").toURL(),
					new EdgeOptions());
			break;
		default:
			logger.error("Invalid browser: " + br);
			return;
		}
	}
		
		 if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
	            switch(br.toLowerCase()) {
	                case "chrome":
	                    WebDriverManager.chromedriver().setup();
	                    driver = new ChromeDriver();
	                    break;
	                case "firefox":
	                    WebDriverManager.firefoxdriver().setup();
	                    driver = new FirefoxDriver();
	                    break;
	                case "edge":
	                    WebDriverManager.edgedriver().setup();
	                    driver = new EdgeDriver();
	                    break;
	                default:
	                    logger.error("Invalid browser name: " + br);
	                    return;
	            }
	        }
		 
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL")); 
		driver.manage().window().maximize();
		logger.info("Browser launched: " + br + " | URL: " + p.getProperty("appURL"));
	}

	
	@AfterClass(groups= {"Sanity", "Regression", "Master"})
	public void tearDown() throws InterruptedException {
		driver.quit();
	}
	
	
	// generates random alphabetic string of length 5
	public String randomString() {
		return RandomStringUtils.secure().nextAlphabetic(5);
	}
	
	// generates random numeric string of length 10
	public String randomNumber() {
		return RandomStringUtils.secure().nextNumeric(10);
	}
	
	// generates random alphanumeric password with special character
	public String randomAlphaNumeric() {
		String letters=RandomStringUtils.secure().nextAlphanumeric(3);
		String numbers=RandomStringUtils.secure().nextNumeric(3);
		return letters+"#"+numbers;
	}
	
	// takes screenshot and saves to screenshots folder
	public String captureScreen(String tname) {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File sourceFile=	ts.getScreenshotAs(OutputType.FILE);
		String targetFilePath= System.getProperty("user.dir") + "\\screenshots\\"+ 
		tname+ " " + timeStamp + ".png";
		File targetFile= new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}
}
