package com.framework.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.framework.basetest.TestBase;

public class Listeners extends TestBase implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {
		
		printout("********************************TESTING IS STARTED*************************************************");
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		printout("********************************TESTING IS SUCCESS*************************************************");
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		printout("********************************TESTING IS FAILURE*************************************************");
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		printout("********************************TESTING IS SKIPPED*************************************************");
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		printout("********************************TESTING IS FAILED WITH SUCCESS PERCENTAGE*************************************************");
		
	}

	@Override
	public void onStart(ITestContext context) {
		//printout("********************************TESTING IS ON STARTED*************************************************");
		
	}

	@Override
	public void onFinish(ITestContext context) {
		printout("********************************TESTING IS FINISHED*************************************************");
		
	}
	
	
	

}
