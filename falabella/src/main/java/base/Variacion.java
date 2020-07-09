package base;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import facades.home.Home;

public class Variacion {

	private WebDriver driver;
	private ExtentTest test;
	
	public Variacion(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
	}
	
	public Home iniciar() {
		Home home = new Home(this.driver, this.test);
		home.cerrarModal();
		return home;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public ExtentTest getTest() {
		return test;
	}
}