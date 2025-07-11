package program.utils;

import java.util.Random;

public class Mesa {
	private Dealer dealer;
	private Jogador[] jogadores;
	private Baralho baralho;
	private Random random;
	
	public Mesa(int numJogadores, String[] nomes) {
		jogadores = new Jogador[4];
		
		for (int i = 0, j = 1; i < 4; i++) {
			if (i < numJogadores) {
				jogadores[i] = new Jogador(false, nomes[i], 100);	
			} else {
				jogadores[i] = new Jogador(true, "Bot" + j++, 100);
				random = new Random();
			}
		}
		dealer = new Dealer();
		baralho= new Baralho();
	}
	
	public Jogador getJogador(int idJogador) {
		return jogadores[idJogador];
	}
	
	public Dealer getDealer() {
		return dealer;
	}
	
	public void newGame(int[] bets) {
		baralho.shuffleBaralho();
		dealer.newGame();
		for (int i = 0; i < 4; i++) {
			jogadores[i].newGame(bets[i]);
		}
		
		Carta carta;
		for (Jogador jog: jogadores) {
			carta = baralho.getNextCarta();
			jog.hit(carta);
			carta = baralho.getNextCarta();
			jog.hit(carta);
		}
		
		carta = baralho.getNextCarta();
		dealer.hit(carta);
		carta = baralho.getNextCarta();
		carta.flipCarta();
		dealer.hit(carta);
	}
	
	public void unflipDealerCarta() {
		dealer.unflipCarta();
	}
	
	public void round1(int[] choises) {
		for (int i = 0; i<4; i++) {
			if (choises[i] == 1) {
				jogadores[i].surrender();
			} else if (choises[i] == 2) {
				jogadores[i].insurence();
			} 
		}
		
		if (dealer.getState() == Mao.enumState.BLACKJACK) {
			unflipDealerCarta();
			end();
		}
	}
	
	public boolean play(int idJogador, int played) {
		
		if (!jogadores[idJogador].isGaming()) {
			return false;
		}
		
		if (jogadores[idJogador].isBot()) {
			played = random.nextInt(1,6);
			
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {}
		}
		
		switch (played) {
		case 1:
			jogadores[idJogador].hit(baralho.getNextCarta());
			break;
		case 2:
			jogadores[idJogador].stand();
			break;
		case 3:
			jogadores[idJogador].doubleDown(baralho.getNextCarta());
			break;
		case 4:
			jogadores[idJogador].split();
			break;
		case 5:
			jogadores[idJogador].surrender();
			break;
		default:
			System.err.println("Opção invalida");
			break;
		}
		
		return true;
	}
	
	public boolean dealerPlay() {
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {}
		
		if (!dealer.isGaming()) {
			return false;
		}
		
		if (dealer.getMao().getMao()[dealer.getMao().getMao().length - 1].getAbsoluteValue() > 21-dealer.getMao().getSize()) {
			dealer.stand();
		} else {
			dealer.hit(baralho.getNextCarta());
		}
		
		return true;
	}
	
	public void end() {
		for (Jogador jog: jogadores) {
			jog.calcResultGame(dealer.getSizeMao());
		}
	}
}
