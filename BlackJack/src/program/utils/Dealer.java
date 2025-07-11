package program.utils;

public class Dealer {
	private Mao mao;
	
	public Dealer() {
		mao = new Mao(0);
	}
	
	public void newGame() {
		mao = new Mao(0);
	}
	
	public boolean isGaming() {
		if (mao.getState() == Mao.enumState.GAMING) {
			return true;
		}
		
		return false;
	}
	
	public void hit(Carta carta) {
		mao.hit(carta);
	}
	
	public void stand() {
		mao.stand();
	}
	
	public void unflipCarta() {
		mao.unflipCarta();
	}
	
	public int getSizeMao() {
		return mao.getSize();
	}
	
	public Mao getMao() {
		return mao;
	}
	
	public Mao.enumState getState() {
		return mao.getState();
	}

	@Override
	public String toString() {
		return "Dealer [ Mão[" + mao.toString("DEALER", 0) + "] ]";
	}
	
	
}
