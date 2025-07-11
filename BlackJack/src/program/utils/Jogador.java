package program.utils;

public class Jogador {
	
	private boolean bot;
	private String nome;
	private int fichas;
	private Mao[] maos;
	
	public Jogador(boolean bot, String nome, int fichas) {
		super();
		this.bot = bot;
		this.nome = nome;
		this.fichas = fichas;
		maos = new Mao[2];
	}
	
	public String getNome() {
		return nome;
	}
	
	public boolean isBot() {
		return bot;
	}
	
	public int getFichas() {
		return fichas;
	}
	
	public void newGame(int bet) {
		maos = new Mao[2];
		maos[0] = new Mao(bet);
		fichas -= bet;
	}
	
	public boolean isGaming() {
		if (maos[0].getState() == Mao.enumState.GAMING || (maos[1] != null && maos[1].getState() == Mao.enumState.GAMING)) {
			return true;
		}
		
		return false;
	}
	
	public void hit(Carta carta) {
		if (maos[0].getState() == Mao.enumState.GAMING) {
			maos[0].hit(carta);
		} else if (maos[1] != null && maos[1].getState() == Mao.enumState.GAMING) {
			maos[1].hit(carta);
		}
	}
	
	public void stand() {
		if (maos[0].getState() == Mao.enumState.GAMING) {
			maos[0].stand();
		} else if (maos[1] != null && maos[1].getState() == Mao.enumState.GAMING) {
			maos[1].stand();
		}
	}
	
	public void doubleDown(Carta carta) {
		if (maos[0].getState() == Mao.enumState.GAMING) {
			maos[0].doubleDown(carta);
		} else if (maos[1] != null && maos[1].getState() == Mao.enumState.GAMING) {
			maos[1].doubleDown(carta);
		}
	}
	
	public void split() {
		if (!maos[0].isSplitering()) {
			return;
		}
		
		maos[1] = new Mao(maos[0].getBet());
		maos[1].setSplited();
		fichas -= maos[0].getBet();
		
		Carta carta = maos[0].split();
		maos[1].hit(carta);
	}
	
	public void surrender() {
		if (maos[0].getState() == Mao.enumState.GAMING) {
			maos[0].surrender();
		} else if (maos[1] != null && maos[1].getState() == Mao.enumState.GAMING) {
			maos[1].surrender();
		}
	}
	
	public void insurence() {
		if (maos[0].getState() == Mao.enumState.GAMING && !maos[0].isInsurence()) {
			maos[0].buyInsurence();
		} else if (maos[1] != null && maos[1].getState() == Mao.enumState.GAMING && !maos[1].isInsurence()) {
			maos[1].buyInsurence();
		}
	}
	
public Mao.enumState[] getState(){
		return new Mao.enumState[] {maos[0].getState(), maos[1].getState()};
	}
	
	public void calcResultGame(int valueMaoDealer) {
		if (maos[0].getState() != Mao.enumState.BUSTED) {
			if (maos[0].getState() == Mao.enumState.SURRENDER) {
				fichas += maos[0].getBet()/2;
			} else if (maos[0].getState() == Mao.enumState.BLACKJACK) {
				fichas += maos[0].getBet() * 3 / 2;
			} else if (maos[0].getSize() > valueMaoDealer) {
				fichas += maos[0].getBet() * 2;
			} else if (maos[0].getSize() == valueMaoDealer) {
				fichas += maos[0].getBet();
			}
		}
		if (maos[1] != null && maos[1].getState() != Mao.enumState.BUSTED) {
			if (maos[1].getState() == Mao.enumState.SURRENDER) {
				fichas += maos[1].getBet()/2;
			} else if (maos[1].getState() == Mao.enumState.BLACKJACK) {
				fichas += maos[1].getBet() * 3 / 2;
			} else if (maos[1].getSize() > valueMaoDealer) {
				fichas += maos[1].getBet() * 2;
			} else if (maos[1].getSize() == valueMaoDealer) {
				fichas += maos[1].getBet();
			}
		}
	}

	@Override
	public String toString() {
		if (maos[1] == null) {
			return nome + "[ Fichas[" + fichas + "] Mão[" + maos[0].toString("PLAYER", 0) + "] ]";
		} else {
			return nome + "[ Fichas[" + fichas + "] Mãos[" + maos[0].toString("PLAYER", 1) + " | " + maos[1].toString("PLAYER", 2) + "] ]";
		}
	}	
}
