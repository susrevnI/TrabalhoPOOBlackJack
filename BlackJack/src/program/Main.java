package program;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int numJogadores = 0, i = 0;
		
		while(true) {
			System.out.print("Número de jogadores: ");
			numJogadores = sc.nextInt();
			
			if (numJogadores < 1 || numJogadores > 4) {
				System.err.println("Número deve estar entre 1 e 4 (inclusive)");
				continue;
			}
			
			break;
		}
		
		sc.nextLine();
		
		String[] nomes = new String[numJogadores];
		while (i < numJogadores) {
			System.out.print("Nome jogador "+(i+1)+": ");
			nomes[i] = sc.nextLine().trim();
			if (nomes[i].equals("")) {
				System.err.println("Nome vazio");
				continue;
			}
			i++;
		}
		
		System.out.println();
		
		Jogo jogo = new Jogo(numJogadores, nomes);
		while (jogo.jogo(sc)) ;
		sc.close();
	}
}
