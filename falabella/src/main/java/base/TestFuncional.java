package base;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import constantes.NavegadorConstantes;
import utils.DateUtil;
import utils.Resource;

public class TestFuncional {

	private String Id;
	private String resultadoTests = "OK";
	private String nombreFlujo;
	private ExtentReports extentReport;
	private List<WebDriver> listDriver;

	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

	public TestFuncional(String nombreFlujo, String numeroDeEjecucion, String idSuite) {
		this.extentReport = new ExtentReports(System.getProperty("user.dir") + "/test-output/Falabella-" + nombreFlujo
				+ "-" + numeroDeEjecucion + ".html", true);
		this.extentReport.addSystemInfo("Encoding", "ISO-8859-1");
		this.extentReport.addSystemInfo("Host Name", "Automatizacion FALABELLA")
				.addSystemInfo("Environment", "https://www.falabella.com/falabella-cl/").addSystemInfo("User Name", "BS2");
		this.extentReport.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
		listDriver = new ArrayList<>();
		this.nombreFlujo = "Flujo " + nombreFlujo;
		this.Id = idSuite;

	}

	public WebDriver getDriver() {
		System.setProperty(NavegadorConstantes.DRIVER_CHROME, Resource.getProperty("path.webdriver.chrome"));
		ChromeOptions chromeOptions = new ChromeOptions();

		if (Resource.getProperty("chromeoptions.arguments") != null
				&& !Resource.getProperty("chromeoptions.arguments").equals("")) {
			String[] listaDeOpciones = Resource.getProperty("chromeoptions.arguments").split(";");

			chromeOptions.addArguments(listaDeOpciones);
		}

		chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		WebDriver driver = new ChromeDriver(chromeOptions);
		try {
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
		} catch (Exception e) {
			System.out.println("No pudo Maximizar la ventana ");
		}

		driver.get(Resource.getProperty("url.falabella"));

		this.listDriver.add(driver); // los agrega a una lista para poder luego repasar esa lista y cerrar los
										// webdriver que hayan quedado abiertos desde @AfterClass

		return driver;
	}

	public ExtentReports getExtentReport() {

		return this.extentReport;
	}

	@BeforeClass
	public void setUp() throws SQLException {

	}

	@AfterClass
	public void tearDown() throws SQLException {
		for (WebDriver driver : listDriver) {
			// si existe URL es que aun esta abierto el webdriver
			if (!driver.toString().contains("(null)")) {
				driver.close();
				driver.quit();

			}
		}

	}

	@AfterMethod
	public void afterEachTest(ITestResult result) throws SQLException {
		System.out.println(this.getTest().getTest().getName());
		if (result.getStatus() == ITestResult.FAILURE) {
			this.getTest().log(LogStatus.FAIL, result.getThrowable());
			resultadoTests = "NOK";
		} else if (result.getStatus() == ITestResult.SKIP) {
			this.getTest().log(LogStatus.SKIP, "Test Saltado " + result.getThrowable());
		} else {
			this.getTest().log(LogStatus.PASS, "Test Pasado", DateUtil.fechaReporte());
		}
		this.getExtentReport().endTest(this.getTest());
		this.getExtentReport().flush();
	}

	public ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public void endTest() {
		extentReport.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));

	}

	public ExtentTest startTest(String testName) {
		return startTest(testName, "");
	}

	public ExtentTest startTest(String testName, String desc) {
		ExtentTest test = extentReport.startTest(testName, desc);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
		return test;
	}

}