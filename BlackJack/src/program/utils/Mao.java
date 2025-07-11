package program.utils;

import java.util.ArrayList;
import java.util.List;

public class Mao {
	private List<Carta> mao, maoSemAs, maoComAs;
	private int sizeMao, bet;
	public static enum enumState {
		GAMING, BLACKJACK, STANDED, BUSTED, SURRENDER
	}
	private enumState state;
	private boolean insurence;
	private boolean splited;
	
	public Mao(int bet) {
		this.bet = bet;
		mao = new ArrayList<>();
		maoSemAs = new ArrayList<>();
		maoComAs = new ArrayList<>();
		sizeMao = 0;
		state = enumState.GAMING;
		insurence = false;
		splited = false;
	}
	
	private void somar() {
		int soma = 0;
		
		for (Carta carta: maoSemAs) {
			soma += carta.getAbsoluteValue();
		}
		
		for (int i = 0, j = maoComAs.size(); i < maoComAs.size(); i++, j--)
		{
			if (soma + i + 11*j <= 21)
			{
				soma += i + 11*j;
				break;
			}
			
			if (i+1 == maoComAs.size())
				soma += maoComAs.size();
		}
		
		sizeMao = soma;
	}
	
	public int getSize() {
		return sizeMao;
	}
	
	public void hit(Carta carta) {
		mao.add(carta);
		
		if (carta.getAbsoluteValue() == 1)
			maoComAs.add(carta);
		else
			maoSemAs.add(carta);
		
		somar();
		
		if (sizeMao == 21) {
			state = enumState.BLACKJACK;
		} else if (sizeMao > 21) {
			state = enumState.BUSTED;
		}
	}
	
	public boolean isSplitering() {
		if (splited)
			return false;
		
		if (mao.get(0).getAbsoluteValue() == mao.get(1).getAbsoluteValue())
			return true;
		return false;
	}
	
	public void setSplited() {
		splited = true;
	}
	
	public Carta split() {
		splited = true;
		Carta carta = mao.remove(1);
		
		if (maoComAs.size() != 0) {
			maoComAs.remove(1);
		} else {
			maoSemAs.remove(1);
		}
		
		return carta;
	}
	
	public void stand() {
		state = enumState.STANDED;
	}
	
	public void surrender() {
		bet /= 2;
		state = enumState.SURRENDER;
	}
	
	public void buyInsurence() {
		bet *= 2;
		insurence = true;
	}
	
	public boolean isInsurence() {
		return insurence;
	}
	
	public void doubleDown(Carta carta) {
		bet *= 2;
		hit(carta);
		state = enumState.STANDED;
	}
	
	public void unflipCarta() {
		for (Carta c: maoComAs) {
			c.unflipCarta();
		}
		for (Carta c: maoSemAs) {
			c.unflipCarta();
		}
		for (Carta c: mao) {
			c.unflipCarta();
		}
		
		somar();
	}
	
	public int getBet() {
		return bet;
	}
	
	public Carta[] getMao() {
		Object[] old = mao.toArray();
		Carta[] c = new Carta[old.length];
		
		for (int i = 0; i < old.length; i++) {
			c[i] = (Carta) old[i];
		}
		
		return c;
	}
	
	public enumState getState() {
		return state;
	}

	public String toString(String type, int numMao) {
		String s;
		if (numMao == 1) {
			s = "Mão 1: ";
		} else if (numMao == 2) {
			s = "Mão 2: ";
		} else {
			s = "Mão: ";
		}
		
		if (state == enumState.BLACKJACK) {
			s = s.concat("BLACKJACK ");
		} else if (state == enumState.BUSTED) {
			s = s.concat("BUSTED ");
		} else if (state == enumState.GAMING) {
			s = s.concat("GAMING ");
		} else if (state == enumState.STANDED) {
			s = s.concat("STANDED ");
		} else if (state == enumState.SURRENDER) {
			s = s.concat("SURRENDER ");
		}
		
		if (splited) {
			s = s.concat("SPLITED ");
		}
		
		if (insurence) {
			s = s.concat("INSURENCE ");
		}
		
		if (!type.equals("DEALER")) {
			s = s.concat("Bet[").concat(String.valueOf(bet)).concat("] ");
			s = s.concat("Size[").concat(String.valueOf(sizeMao)).concat("] Cartas[");
		} else {
			if (mao.get(1).isFliped()) {
				s = s.concat("Size[").concat(String.valueOf(sizeMao-mao.get(1).getAbsoluteValue())).concat("] Cartas[");
			} else {
				s = s.concat("Size[").concat(String.valueOf(sizeMao)).concat("] Cartas[");
			}
		}
		
		for (int i = 0; i < getMao().length; i++) {
			if (i+1 == getMao().length) {
				s = s.concat(getMao()[i].getTypePlusValue()).concat("]");
				break;
			}
			s = s.concat(getMao()[i].getTypePlusValue()).concat(" ");
		}
		
		return s;
	}
	
	
}
