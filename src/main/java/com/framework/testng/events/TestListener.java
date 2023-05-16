package com.framework.testng.events;


import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.framework.utils.MyScreenRecorder;




public class TestListener implements ITestListener {

	public void onTestStart(ITestResult result) {
		try {
			MyScreenRecorder.startRecording(result.getName());
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	public void onTestSuccess(ITestResult result) {
		try {
			MyScreenRecorder.stopRecording();
			Thread.sleep(3000);
			 List<File> files = Arrays.asList(new File(System.getProperty("user.dir") + File.separator + "recordings").listFiles());
			 for (File file : files) {
				 if(file.getAbsolutePath().contains(result.getName())) {
					 new File(file.getAbsolutePath()).delete();
				 }
			}
		
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	public void onTestFailure(ITestResult result) {
		try {
			MyScreenRecorder.stopRecording();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}
