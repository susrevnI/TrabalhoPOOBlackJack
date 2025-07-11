package program;

import java.util.Scanner;
import java.util.Random;

import program.utils.Mesa;

public class Jogo {
	private Mesa mesa;
	Random random;
	
	public Jogo(int numJogadores, String[] nomes) {
		mesa = new Mesa(numJogadores, nomes);
		random = new Random();
	}
	
	public boolean jogo(Scanner sc) {
		int[] bets = new int[4];
		int i = 0, bet = 0;
		
		while (i<4) {
			if (mesa.getJogador(i).isBot()) {
				bets[i] = random.nextInt(mesa.getJogador(i++).getFichas());
				continue;
			}
			
			System.out.print("Aposta "+mesa.getJogador(i).getNome()+" (jogador "+(i+1)+"): ");
			bet = sc.nextInt();
			
			if (bet<0 || bet > mesa.getJogador(i).getFichas()) {
				System.err.println("Valor deve estar entre 0 e o total de fichas.");
				continue;
			}
			
			bets[i++] = bet;
		}
		
		mesa.newGame(bets);
		printDealerPlusJogadores();
		
		i = 0;
		int[] choises = new int[4];
		while (i<4) {
			if (mesa.getJogador(i).isBot()) {
				choises[i++] = random.nextInt(0, 3);
				continue;
			}
			
			System.out.print(mesa.getJogador(i).getNome()+" (jogador "+(i+1)+"):\n   0: NOTHING\n   1: SURRENDER\n   2: INSURENCE\n:");
			int choise = sc.nextInt();
			
			if (choise != 0 && choise != 1 && choise != 2) {
				System.err.println("Valor deve ser 1 ou 2.");
				continue;
			}
			
			choises[i++] = choise;
		}
		
		mesa.round1(choises);
		printDealerPlusJogadores();
		
		i = 0;
		int choise = 0;
		while (i<4) {
			while (true){
				if (!mesa.getJogador(i).isBot()) {
					System.out.print(mesa.getJogador(i).getNome()+"(Jogador "+(i+1)+"):\n   1: HIT\n   2: STAND\n   3: DOUBLEDOWN\n   4: SPLIT\n   5: SURRENDER\n:");
					choise = sc.nextInt();
				}
				
				mesa.play(i, choise);
				
				if (!mesa.getJogador(i).isGaming()) {
					break;
				}
				printDealerPlusJogadores();
			}
			
			i++;
		}
		
		mesa.unflipDealerCarta();
		printDealerPlusJogadores();
		
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {}
		
		while (mesa.dealerPlay()) {
			printDealerPlusJogadores();
		}
		
		mesa.end();
		
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {}
		
		printDealerPlusJogadores();
		
		System.out.print("[Proxima rodada]");
		sc.nextLine();
		
		return true;
	}
	
	public void printDealerPlusJogadores() {
		System.out.println();
		System.out.println(mesa.getDealer());
		System.out.println();
		for (int i = 0; i < 4; i++) {
			System.out.println(mesa.getJogador(i));
		}
		System.out.println();
	}
}

