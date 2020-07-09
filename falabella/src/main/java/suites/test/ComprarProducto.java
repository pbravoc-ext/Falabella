package suites.test;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import base.Variacion;
import facades.home.Home;
import facades.tecnologia.Consolas;

public class ComprarProducto extends Variacion {

	public ComprarProducto(WebDriver driver, ExtentTest test) {
		super(driver, test);
		// TODO Auto-generated constructor stub
	}

	public String logica() {

		Home home = this.iniciar();
		assertTrue(home.isInitialized());
		home.menuCategoria();
		home.itemDecoHogar();
		home.decMuebles();
		
		Consolas consol = new Consolas(this.getDriver(), this.getTest());
		consol.seleccionarProducto();
		return "OK";
	}
	
}
