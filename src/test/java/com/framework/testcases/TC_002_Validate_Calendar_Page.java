package com.framework.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.framework.basetest.TestBase;
import com.framework.frameworkutils.Keywords;
import com.framework.frameworkutils.TestUtils;

public class TC_002_Validate_Calendar_Page extends TestBase {

	Hashtable<String, String> data = null;
	@Test(priority = 2)
	public void tC_002_Validate_Calendar_Page() throws Exception {
		
		
		if(!TestUtils.isTestcasesExecutable(this.getClass().getSimpleName(), xls)) {
			
			throw new SkipException("Test Cases execution mode is set by No..!!!!!!!!!!!!");
		}
		
		Keywords k = new Keywords();
		k.executeKeywords(this.getClass().getSimpleName(), data);
		
	}

}
