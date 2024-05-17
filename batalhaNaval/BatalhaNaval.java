package batalhaNaval;

import java.util.Scanner;

public class BatalhaNaval {

    public static void main(String[] args) {
        //criando um objeto pra cada jogador
        Scanner scan = new Scanner(System.in);
        Campo campoUm = new Campo();
        campoUm.jogador = true;
        campoUm.numJogador = 1;
        Campo campoDois = new Campo();
        campoDois.vsJogador = true;
        campoDois.numJogador = 2;

        int numJogadores;
        do {
            System.out.println("1 ou 2 jogadores?");
            numJogadores = scan.nextInt();
            switch (numJogadores) {
                case 1:
                    campoDois.jogador = false;
                    campoDois.autoPos = true;
                    campoUm.vsJogador = false; // adversario do campoUm é um robo (jogador = false)
                    campoDois.posicionarCampo();
                    char dificuldade;
                    do{
                        System.out.println("Escolha um modo:");
                        System.out.println("F - Facil");
                        System.out.println("D - Dificil");
                        dificuldade = scan.next().toLowerCase().charAt(0);
                    }
                    while(dificuldade != 'f' && dificuldade != 'd');
                    if(dificuldade == 'f')
                        campoDois.modoJogo = "facil";
                    else
                        campoDois.modoJogo = "dificil";
                    break;

                case 2:
                    campoDois.jogador = true;
                    campoUm.vsJogador = true;
                    break;
                default:
                    System.out.println("Numero de jogadores invalido");
            }
        } while(numJogadores != 1 && numJogadores != 2);

        for(int i = 1; i <= numJogadores; ++i) {
            System.out.println("Jogador " + i + ": Posicionar automatico? (s ou n)");
            char posAuto = scan.next().toLowerCase().charAt(0);
            if (i == 1) {
                switch (posAuto) {
                    case 's' -> campoUm.autoPos = true;
                    case 'n' -> campoUm.autoPos = false;
                    default -> System.out.println("Escreva sim ou nao APENAS.");
                }
                campoUm.posicionarCampo();
            } else {
                switch (posAuto) {
                    case 'n' -> campoDois.autoPos = false;
                    case 's' -> campoDois.autoPos = true;
                    default -> System.out.println("Escreva sim ou nao APENAS.");
                }
                campoDois.posicionarCampo();
            }
        }

        while(campoUm.temBarcoAinda() && campoDois.temBarcoAinda()) {
            campoDois.tiroInimigo();
            campoUm.tiroInimigo();
        }

        if (!campoDois.temBarcoAinda() && !campoUm.temBarcoAinda()) {
            System.out.println("Empate \ud83d\ude0b ");
            campoUm.mostrarCampoEscondido();
            campoDois.mostrarCampoEscondido();
        } else if (!campoUm.temBarcoAinda()) {
            System.out.println("Jogador 2 ganhou \ud83d\ude0e");
            campoUm.mostrarCampoEscondido();
        } else {
            System.out.println("Jogador 1 ganhou \ud83d\ude0e");
            campoDois.mostrarCampoEscondido();
        }

    }
}
