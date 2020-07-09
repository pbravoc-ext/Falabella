package facades.tecnologia;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import base.Fachada;
import pageojects.tecnologia.ConsolasPom;

public class Consolas extends Fachada {

	private ConsolasPom consolas;

	public Consolas(WebDriver driver, ExtentTest test) {
		super(driver, test);
		consolas = new ConsolasPom(driver);
	}

	public void seleccionarProducto() {
		consolas.isInitialized();
		this.CapturaPantalla("Seccion Mueble");
		consolas.abrirFiltroMarca();
		consolas.selectMarca();
		this.CapturaPantalla("Filtro AMERITEX");
		consolas.verPrimerProducto();
		this.CapturaPantalla(consolas.nombreProducto());
		consolas.agregarBolsa();
		this.CapturaPantalla("Agregar a la bolsa");
		consolas.addProductos(3);
		this.CapturaPantalla("Agregar 3 productos");
	}
}
