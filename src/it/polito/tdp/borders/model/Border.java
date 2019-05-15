package it.polito.tdp.borders.model;

public class Border {
	
	private Country primoPaese;
	private Country secondoPaese;
	
	public Border(Country primoPaese, Country secondoPaese) {
		super();
		this.primoPaese = primoPaese;
		this.secondoPaese = secondoPaese;
	}

	public Country getPrimoPaese() {
		return primoPaese;
	}

	public void setPrimoPaese(Country primoPaese) {
		this.primoPaese = primoPaese;
	}

	public Country getSecondoPaese() {
		return secondoPaese;
	}

	public void setSecondoPaese(Country secondoPaese) {
		this.secondoPaese = secondoPaese;
	}
	
	

}
