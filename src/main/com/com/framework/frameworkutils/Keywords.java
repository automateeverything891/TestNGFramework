package com.framework.frameworkutils;

import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.framework.basetest.TestBase;
import com.framework.contants.VariableConstants;
import com.relevantcodes.extentreports.LogStatus;

public class Keywords extends TestBase{
	
	public String testCasesName = "";
	
	public void executeKeywords(String testcases, Hashtable<String, String> data) throws Exception {

		this.testCasesName = testcases;
		loadproperties();
		
		System.out.println("Executable test cases is :" + testcases);

		String keyword = null;
		String objectkeys = null;
		String datakeys = null;

		for (int rNum = 2; rNum <= xls.getRowCount("Test Steps"); rNum++) {
			if (testcases.equalsIgnoreCase(xls.getCellData("Test Steps", "TCID", rNum))) {
				keyword = xls.getCellData("Test Steps", "Keyword", rNum);
				objectkeys = xls.getCellData("Test Steps", "Object", rNum);
				datakeys = xls.getCellData("Test Steps", "Data", rNum);

				System.out.println(keyword + "--" + objectkeys + "--" + datakeys);

				String result = null;
				switch (keyword) {
				
				case "openBrowser":
					result = openBrowser(objectkeys);
					break;

				case "navigateURL":
					result = navigateURL(objectkeys);
					break;

				case "verifyTitle": 
					result = verifyTitle(datakeys); 
					break;
				case "click":
					result = click(objectkeys);
					break;

				case "enter":
					if (data != null)
						result = enter(objectkeys, data.get(datakeys));
					else
						result = enter(objectkeys, datakeys);
					break;
				
				  case "verifyText": if (data != null) 
					  result = verifyText(objectkeys,data.get(datakeys)); 
				  else 
					  result = verifyText(objectkeys, datakeys); 
				  break;
				  case "verifyMultipleText":
					  result = verifyMultipleText(objectkeys,data.get(datakeys)); 
				  break;
				  case "searchAndSelect":
					  result = searchAndSelect(objectkeys,datakeys);
					  break;
				  case "selectDropdown": 
					  result = selectDropdown(objectkeys, datakeys); 
					  break;
				  case "waitForTextPresent":
					  result = waitForTextPresent(objectkeys, datakeys);
				  	  break;
				  case "waitForElementPresent":
					  result = waitForElementPresent(objectkeys);
				  	  break;
				case "close":
					result = close();
					break;
				case "selectWithDIVclass":
						result = selectWithDIVclass(objectkeys, datakeys);
				  	  break;
				case "selectcombobox":
					result = selectcombobox(objectkeys, datakeys);
				  	  break;
				case "sleep":
					result = sleep(datakeys);
					break;
				default:
					System.out.println("Not Matching Keyword :" + keyword);
					break;
				}

				if (result != null) {
					if (result.equalsIgnoreCase("Pass"))
						xls.setCellData1("Test Steps", "Status", (rNum - 1), "Executed");
					else
						xls.setCellData1("Test Steps", "Status", (rNum - 1), "Not Executed");
				}

			}

		}

	}


	private String enter(String objectkeys, String string) throws Exception {
		
		printout("ENTERINNG THE GIVEN FIELD!!!!!!!!");
		getWebElement(objectkeys).sendKeys(string);
		
		return "Pass";
	}

	private String click(String objectkeys) throws Exception {

		printout("CLICKING ON GIVEN FIELD!!!!!!!!");
		getWebElement(objectkeys).click();
		
		return "Pass";
	}
	
	private String verifyTitle(String datakeys) {
		
		printout("VERIFYING THE TITLE OF THE PAGE !!!!!!!!");
		Assert.assertEquals(driver.getTitle(), datakeys);
		
		return "Pass";
	}
	
	private String verifyText(String objectkeys, String string) throws Exception {
		
		printout("VERIFYING THE ELEMENT TEXT !!!!!!!!");
		Assert.assertEquals(getWebElement(objectkeys).getText(), string);
		return "Pass";
	}

	private String selectDropdown(String objectkeys, String datakeys) throws Exception {
		
		printout("SELECTING THE GIVEN DROP DOWN FIELDS !!!!!!!!");
		Select select = new Select(getWebElement(objectkeys));
		select.selectByValue(datakeys);
		
		return "Pass";
	}
	
	
	
	private String searchAndSelect(String objectkeys, String datakeys) throws Exception {
		printout("SEARCH AND SELECT THE FIELDS !!!!!!!!");

		String str = null;
		String str1 = null;
		if(objectkeys.contains("\\|")) {
			 str = objectkeys.split("\\|")[0];
			 str1 = objectkeys.split("\\|")[1];
		}
		getWebElement(str).click();
		getWebElement(str).sendKeys(datakeys);
		List<WebElement> listofelement = getWebElements(str1);
		
		for(WebElement element:listofelement) {
			
			if(element.getText().equalsIgnoreCase(datakeys)) {
				element.click();
			}
		}
		
		return "Pass";
	}

	private String waitForTextPresent(String objectkeys, String datakeys) throws Exception {
		
		printout("WAITING FOR THE GIVEN ELEMENT TEXT PRESENT !!!!!!!!");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.textToBePresentInElement(getWebElement(objectkeys), datakeys));
		
		return "Pass";
	}
	

	private String waitForElementPresent(String objectkeys) throws Exception {

		printout("WAITING FOR THE GIVEN ELEMENT PRESENT !!!!!!!!");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(getWebElement(objectkeys)));
		
		return "Pass";
	}

	private String verifyMultipleText(String objectkeys, String string) throws Exception {
		printout("VERIFY MULTIPLE TEXT METHOD IS CALLING !!!!!!!!");
		if(objectkeys.contains("|")) {
			
			String[] strarr = objectkeys.split("\\|");
			for(int i=0; i<strarr.length;i++) {
			
				if(getWebElements(strarr[i]).size()!=0) {
	
					Assert.assertEquals(getWebElement(strarr[i]).getText(), string);
					return "PASS";
				}
			}
		}
		System.out.println("RETRUN FAILS");
		return "FAIL";
	}

	private String selectWithDIVclass(String objectkeys, String datakeys) throws Exception {
		
		if(objectkeys.contains("|")) {
			
		getWebElement(objectkeys.split("|")[0]).click();
		
		List<WebElement> listofelement = getWebElements(objectkeys.split("|")[1]);
		
		for(WebElement element : listofelement) {
			if(element.getText().equalsIgnoreCase(datakeys)) {
				element.click();
			}
		}
		}
		
		return "PASS";
	}
	private String selectcombobox(String objectkeys, String datakeys) throws Exception {
		
		getWebElement(objectkeys).sendKeys(datakeys+Keys.ENTER);
		return "PASS";
	}
	private String sleep(String datakeys) {
		try {
			Thread.sleep(Long.parseLong(datakeys));
		} catch (NumberFormatException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	public String close() {
		
		printout("CLOSEING THE OPENDED BROWSER !!!!!!!!");
		
		if(driver!=null) {
			driver.quit();
		}
		
		return "Pass";
	}

	
}
