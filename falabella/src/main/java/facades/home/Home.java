package facades.home;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import base.Fachada;
import pageojects.home.HomePom;
import utils.UtilesSelenium;

public class Home extends Fachada {

	private HomePom home;

	public Home(WebDriver driver, ExtentTest test) {
		super(driver, test);
		home = new HomePom(driver);
	}

	public boolean isInitialized() {
		return home.isInitialized();
	}
	
	public void cerrarModal() {
		UtilesSelenium.waitForLoad(this.getDriver());
		home.cerrarModal();
		this.CapturaPantalla("Pagina Inicial");
	}
	
	public void menuCategoria() {
		home.menuCategorias();
		this.CapturaPantalla("Menu Categoria");
	}
	
	public void itemDecoHogar() {
		home.itemDecoHogar();
		this.CapturaPantalla("Seccion Tecnologias");
	}
	
	public void decMuebles() {
		home.decMuebles();
	}

}
