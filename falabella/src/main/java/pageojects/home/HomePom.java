package pageojects.home;

import org.openqa.selenium.WebDriver;

import base.PageObject;

public class HomePom extends PageObject {

	public HomePom(WebDriver driver) {
		super(driver);
	}

	private final String xpath_cerrar_modal_inicio = "/html/body/div[5]/div[2]/div/div[1]";
	private final String xpath_icon_categoria = "//*[@id='hamburgerMenu']/div[1]";
	private final String xpath_cat_DecoHogar = "//*[@id='item-5']/div[1]";
	private final String xpath_tec_Muebles = "//*[@id='header']/nav/div[1]/div/div[1]/div/section[1]/div/section[6]/div/div/ul[1]/div[2]/li[5]";
	
	public boolean isInitialized() {
		return this.xpathExist("//*[@id='header']/nav");
//		return this.isElementDisplayed("//*[@id='header']/nav");
	}
	
	public void cerrarModal() {
		if(this.xpathExist(this.xpath_cerrar_modal_inicio)) {
			this.loadElementByXpath(this.xpath_cerrar_modal_inicio).click();
		}
	}
	
	public void menuCategorias() {
		this.loadElementByXpath(this.xpath_icon_categoria).click();
		
	}
	
	public void itemDecoHogar() {
		this.loadElementByXpath(this.xpath_cat_DecoHogar).click();
	}
	
	public void decMuebles() {
		this.loadElementByXpath(this.xpath_tec_Muebles).click();
	}
}
