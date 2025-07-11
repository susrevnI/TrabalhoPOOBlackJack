package program.utils;

public class Carta {
	private String typePlusValue;
	private int absoluteValue;
	private boolean fliped;
	
	public Carta(String tipoPlusValor, int valorAbsoluto) {
		this.typePlusValue = tipoPlusValor;
		this.absoluteValue = valorAbsoluto;
		this.fliped = false;
	}
	
	public String getTypePlusValue() {
		if (fliped) {
			return "##";
		}
		return typePlusValue;
	}
	
	public int getAbsoluteValue() {
		if (fliped) {
			return 0;
		}
		return absoluteValue;
	}
	
	public boolean isFliped() {
		return fliped;
	}
	
	public void flipCarta() {
		fliped = true;
	}
	
	public void unflipCarta() {
		fliped = false;
	}
	
}
