package program.utils;

import java.util.Random;

public class Baralho {
	private Carta[] baralho;
	private int cartasRestantes;
	
	public Baralho() {
		baralho = new Carta[52];
		cartasRestantes = baralho.length;
		
		String[] tipos = new String[] {"♣","♦","♥","♠"}; 
		String[] valores = new String[] {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
		
		for (int i = 0; i < valores.length; i++) {
			for (int j = 0; j < tipos.length; j++) {
				if (i < 10)
					baralho[j*valores.length+i] = new Carta(valores[i].concat(tipos[j]),i+1);
				else
					baralho[j*valores.length+i] = new Carta(valores[i].concat(tipos[j]),10);
			}
		}
	}
	
	public void shuffleBaralho() {
		cartasRestantes = baralho.length;
		
		Random random = new Random();
        for (int i = baralho.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            Carta temp = baralho[i];
            baralho[i] = baralho[index];
            baralho[index] = temp;
        }
	}
	
	public Carta getNextCarta() /*throws BaralhoVazioException*/ {
		/*if (cartasRestantes == 0) {
			throw new BaralhoVazioException("Baralho sem cartas");
		}*/
		
		return baralho[52 - (cartasRestantes--)];
	}
}
