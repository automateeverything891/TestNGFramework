package com.framework.basetest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.framework.contants.VariableConstants;
import com.framework.frameworkutils.Keywords;
import com.framework.frameworkutils.Xls_Reader;
import com.framework.mail.SendMail;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase implements VariableConstants {

	public  WebDriver driver;

	public Properties prop = null;

	public FileInputStream filein = null;

	public Xls_Reader xls = new Xls_Reader(
			System.getProperty("user.dir") + "\\src\\main\\com\\com\\framework\\testdata\\TestSuite1.xlsx");

	public static ExtentReports report;

	public static ExtentTest extentTest;

	public static Logger log = Logger.getLogger(Keywords.class);

	// String browserName = "Chrome";

	static {


		report = new ExtentReports(System.getProperty("user.dir") + "\\src\\main\\com\\com\\framework\\report\\extentreport.html",true);

		PropertyConfigurator.configure("log4j.properties");

		try {
			report.addSystemInfo("Host Name", InetAddress.getLocalHost().getHostName())
					.addSystemInfo("USER NAME", "TESTING TEAM").addSystemInfo("PROJECT NAME", "FREE CRM");

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String openBrowser(String browserName) {

		printout("OPENING THE GIVEN BROWSER !!!!!!!!");

		if (browserName.equalsIgnoreCase("Chrome")) {

			WebDriverManager.chromedriver().setup();

			driver = new ChromeDriver();

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} else if (browserName.equalsIgnoreCase("Firefox")) {

			WebDriverManager.firefoxdriver().setup();

			driver = new FirefoxDriver();
			
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} else if (browserName.equalsIgnoreCase("Edge")) {

			WebDriverManager.edgedriver().setup();

			driver = new EdgeDriver();

		} else {

			throw new SkipException("DRIVER IS NOT MATCHED>>>>>>> PLEASE CHECK BROWSER SETUP..");

		}

		return "Pass";
	}

	public String navigateURL(String URL) {

		printout("NAVIGATING TO THE GIVEN URL!!!!!!!!");
		driver.get(URL);

		return "Pass";
	}

	@BeforeMethod
	public void beforeMethod(Method result) {

		extentTest = report.startTest(result.getName());
	}

	public void loadproperties() throws IOException {

		try {
			prop = new Properties();
			filein = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\com\\com\\framework\\pompage\\loginpage.properties");
			prop.load(filein);
			filein = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\com\\com\\framework\\pompage\\calendar.properties");
			prop.load(filein);
			filein = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\com\\com\\framework\\pompage\\dashboard.properties");
			prop.load(filein);
			filein = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\com\\com\\framework\\pompage\\contacts.properties");
			prop.load(filein);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public  WebElement getLocator(String locator) throws Exception {
		String[] split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];

		if (locatorType.toLowerCase().equals("id"))
			return driver.findElement(By.id(locatorValue));
		else if (locatorType.toLowerCase().equals("name"))
			return driver.findElement(By.name(locatorValue));
		else if ((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
			return driver.findElement(By.className(locatorValue));
		else if ((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
			return driver.findElement(By.className(locatorValue));
		else if ((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
			return driver.findElement(By.linkText(locatorValue));
		else if (locatorType.toLowerCase().equals("partiallinktext"))
			return driver.findElement(By.partialLinkText(locatorValue));
		else if ((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
			return driver.findElement(By.cssSelector(locatorValue));
		else if (locatorType.toLowerCase().equals("xpath"))
			return driver.findElement(By.xpath(locatorValue));
		else
			throw new Exception("Unknown locator type '" + locatorType + "'");
	}

	public  List<WebElement> getLocators(String locator) throws Exception {
		String[] split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];

		if (locatorType.toLowerCase().equals("id"))
			return driver.findElements(By.id(locatorValue));
		else if (locatorType.toLowerCase().equals("name"))
			return driver.findElements(By.name(locatorValue));
		else if ((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
			return driver.findElements(By.className(locatorValue));
		else if ((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
			return driver.findElements(By.className(locatorValue));
		else if ((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
			return driver.findElements(By.linkText(locatorValue));
		else if (locatorType.toLowerCase().equals("partiallinktext"))
			return driver.findElements(By.partialLinkText(locatorValue));
		else if ((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
			return driver.findElements(By.cssSelector(locatorValue));
		else if (locatorType.toLowerCase().equals("xpath"))
			return driver.findElements(By.xpath(locatorValue));
		else
			throw new Exception("Unknown locator type '" + locatorType + "'");
	}

	public WebElement getWebElement(String locator) throws Exception {
		return getLocator(prop.getProperty(locator));

	}

	public List<WebElement> getWebElements(String locators) throws Exception {
		return getLocators(prop.getProperty(locators));
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {

		getStatus(result);

		report.endTest(extentTest);
		report.flush();
	}

	@AfterClass
	public void endReport() {
		
		SendMail.custom_Mail();
	}
	private void getStatus(ITestResult result) {

		if (result.getStatus() == ITestResult.SUCCESS) {
			
			xls.setCellDataInparticularCell(result.getMethod().getMethodName(), "Test Data", "Status", "PASS");
			
		} else if (result.getStatus() == ITestResult.FAILURE) {
			
			xls.setCellDataInparticularCell(result.getMethod().getMethodName(), "Test Data", "Status", "FAIL");
			extentTest.log(LogStatus.ERROR, result.getName() + "test is failed " + result.getThrowable());
			extentTest.log(LogStatus.FAIL,
					result.getName() + "test is failed " + extentTest.addScreenCapture(catureScreen()));
			
		} else if (result.getStatus() == ITestResult.SKIP) {
			
			xls.setCellDataInparticularCell(result.getMethod().getMethodName(), "Test Data", "Status", "SKIP");
			extentTest.log(LogStatus.SKIP, result.getName() + "test is skip " + result.getThrowable());

		} else if (result.getStatus() == ITestResult.STARTED) {
			extentTest.log(LogStatus.INFO, result.getName() + " Test is Started");
		}
	}

	public void printout(String value) {

		log.info(value);
		extentTest.log(LogStatus.INFO, value);
	}

	public String catureScreen() {

		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleformat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			destFile = new File(System.getProperty("user.dir") + "\\src\\main\\com\\com\\framework\\report\\"
					+ simpleformat.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.getAbsolutePath();
	}

}
