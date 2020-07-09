package base;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import utils.UtilesExtentReport;

public class Fachada {

	private WebDriver driver;
	private ExtentTest test;

	public Fachada(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public ExtentTest getTest() {
		return test;
	}

	public String getTestName() {
		return test.getTest().getName();
	}

	public void CapturaPantalla(String strDescripcion) {
		UtilesExtentReport.captura(this.getDriver(), this.getTestName(), this.getTest(), strDescripcion);
	}
	
	public void CapturaPantallaError(String strDescripcion) {
		UtilesExtentReport.capturaError(this.getDriver(), this.getTestName(), this.getTest(), strDescripcion);
	}

}
