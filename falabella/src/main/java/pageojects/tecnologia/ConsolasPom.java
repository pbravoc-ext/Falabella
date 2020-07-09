package pageojects.tecnologia;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import base.PageObject;
import utils.UtilesSelenium;

public class ConsolasPom extends PageObject {

	private final String xpath_cerrar_alert_inicio = "//*[@id='acc-alert-close']";
	private final String xpath_filtrar_marca = "//*[@id=\"testId-Accordion-Marca\"]";
	private final String xpath_select_primera_marca = "//*[@id=\"testId--desktop-container\"]/div/ul/li[8]/div/ul/li[1]";
	private final String xpath_boton_ver_producto_1 = "//*[@id=\"testId-Pod-action-11032321\"]";
	private final String xpath_boton_agregar_a_la_bolsa = "//*[@id=\"buttonForCustomers\"]/button";
	private final String xpath_nombre_producto = "//*[@id='__next']/div/section/div[1]/div[1]/div[2]/section[2]/div[1]/div[2]/h1/div";
	private final String xpath_cantidad = "//*[@id='__next']/div/div/div/div/div/div/div[2]/div[1]/div/div[1]/div/div[3]/div/div";
	private final String xpath_boton_mas = "//*[@id=\"__next\"]/div/div/div/div/div/div/div[2]/div/div/div/div/div[3]/div/button[2]";
	private final String xpath_boton_menos = "//*[@id='__next']/div/div/div/div/div/div/div[2]/div[1]/div/div[1]/div/div[3]/div/button[1]";

	public ConsolasPom(WebDriver driver) {
		super(driver);
	}

	public boolean isInitialized() {
		if (this.xpathExist(this.xpath_cerrar_alert_inicio)) {
			this.loadElementByXpath(this.xpath_cerrar_alert_inicio).click();
		}
		return this.xpathExist("//*[@id='header']/nav");
	}

	public void abrirFiltroMarca() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, 700);");
		this.loadElementByXpath(this.xpath_filtrar_marca).click();
	}

	public void selectMarca() {
		this.loadElementByXpath(this.xpath_select_primera_marca).click();
		UtilesSelenium.waitForLoad(driver, 10);
	}

	public String nombreProducto() {
		return this.loadElementByXpath(this.xpath_nombre_producto).getAttribute("data-name").toString();
	}

	public void verPrimerProducto() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, -700);");
		this.loadElementByXpath(this.xpath_boton_ver_producto_1).click();
		UtilesSelenium.waitForLoad(driver);
	}

	public void agregarBolsa() {
		this.loadElementByXpath(this.xpath_boton_agregar_a_la_bolsa).click();
		UtilesSelenium.waitForLoad(driver);
	}

	public int getCantidad() {
		return Integer.parseInt(this.loadElementByXpath(this.xpath_cantidad).getText());
	}
	
	public void addProducto() {
		this.loadElementByXpath(this.xpath_boton_mas).click();
		UtilesSelenium.waitForLoad(driver);
	}
	
	public void restProducto() {
		this.loadElementByXpath(this.xpath_boton_menos).click();
		UtilesSelenium.waitForLoad(driver);
	}
	
	public void addProductos(int x) {
		int cantidad = this.getCantidad();
		while (cantidad < x) {
			this.addProducto();
			cantidad = this.getCantidad();
		}
	}
}
