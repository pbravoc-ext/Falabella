package testng;

import static org.testng.Assert.assertEquals;

import java.text.ParseException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.ExtentTest;

import base.TestFuncional;
import suites.test.ComprarProducto;
import utils.DateUtil;

public class AutomatizacionFuncional  extends TestFuncional {

	public static final String numeroDeEjecucion = DateUtil.fecha();
	public static String constante_id_suite = "BS2 Test";
	
	@BeforeSuite
	public void startReport() {
	}

	public AutomatizacionFuncional() {
		super("Automatizacion Funcional", numeroDeEjecucion, constante_id_suite);
	}
	
	@Test(priority = 1)
	public void variacion1() throws ParseException {
		WebDriver driver = this.getDriver();
		ExtentTest test = this.startTest("Automatizacion Funcional");
		ComprarProducto comprarProducto = new ComprarProducto(driver, test);
		assertEquals(comprarProducto.logica(), "OK");
		
		this.endTest();
		driver.close();
		driver.quit();
	}
}
	