package barcodenavio;

import java.util.Random;
import java.util.Scanner;

public class Campo {
    static String[] MENSAGEM_ERRO = new String[]{"Tudo certo", "O barco nao cabe no lugar escolhido!", "Ja existe um barco na posicao escolhida!"};
    String[][] campo = new String[10][10];
    String modoJogo = "nao definido";
    boolean autoPos;
    boolean jogador;
    boolean vsJogador;
    int numJogador;


    public void iniciarCampo() {
        for(int i = 0; i < 10; ++i) {
            for(int j = 0; j < 10; ++j) {
                campo[i][j] = "\ud83c\udf0a";
            }
        }

    }

    public void mostrarCampo() {
        System.out.println("\n         | 01 | 02 | 03 | 04 | 05 | 06 | 07 | 08 | 09 | 10 |\n");

        for(int i = 0; i < 10; ++i) {
            System.out.print("| " + (char)(65 + i) + " |    ");

            for(int j = 0; j < 10; ++j) {
                if (j != 9) {
                    System.out.print("| " + campo[i][j] + " ");
                } else {
                    System.out.println("| " + campo[i][j] + " |");
                }
            }
        }

        System.out.println();
    }

    public void mostrarCampoEscondido() {
        System.out.println("\n         | 01 | 02 | 03 | 04 | 05 | 06 | 07 | 08 | 09 | 10 |\n");

        for(int i = 0; i < 10; ++i) {
            for(int j = 0; j < 10; ++j) {
                if (j == 0) {
                    System.out.print("| " + (char)(65 + i) + " |    ");
                }

                if (campo[i][j].equals("\ud83d\udee5️")) {
                    if (j != 9) {
                        System.out.print("| \ud83c\udf0a ");
                    } else {
                        System.out.println("| \ud83c\udf0a |");
                    }
                } else if (j != 9) {
                    System.out.print("| " + campo[i][j] + " ");
                } else {
                    System.out.println("| " + campo[i][j] + " |");
                }
            }
        }

        System.out.println();
    }

    public boolean direcaoBarco() {
        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.println("Horizontal ou vertical? (v ou h tambem funciona)");
            switch (scan.next().toLowerCase()) {
                case "horizontal":
                case "h":
                    return true;
                case "vertical":
                case "v":
                    return false;
            }
        }
    }

    public int linhaBarco() {
        Scanner scan = new Scanner(System.in);
        int linha = 0;

        do {
            if (linha < 0 || linha >= 10) {
                System.out.println("A linha precisa ser uma letra de A a J!\n");
            }

            System.out.print("Linha (letra): ");
            linha = scan.next().toUpperCase().charAt(0) - 65;
            System.out.println();
        } while(linha < 0 || linha >= 10);

        return linha;
    }

    public int colunaBarco() {
        Scanner scan = new Scanner(System.in);
        int coluna = 0;

        do {
            if (coluna < 0 || coluna >= 10) {
                System.out.println("A coluna precisa ser um numero de 1 a 10!\n");
            }

            System.out.print("Coluna(numero): ");
            coluna = scan.nextInt() - 1;
            System.out.println();

        } while(coluna < 0 || coluna >= 10);

        return coluna;
    }

    public boolean temBarcoAinda() {
        for(int i = 0; i < 10; ++i) {
            for(int j = 0; j < 10; ++j) {
                if (campo[i][j].equals("\ud83d\udee5️")) {
                    return true;
                }
            }
        }
        return false;
    }

    public int testarBarco(boolean horizontal, int tamanho, int linha, int coluna) {
        if (horizontal && coluna + tamanho - 1 >= 10) {
            return 1;
        } else if (!horizontal && linha + tamanho - 1 >= 10) {
            return 1;
        } else {
            for(int i = 0; i < tamanho; ++i) {
                if (horizontal && campo[linha][coluna + i].equals("\ud83d\udee5️")) {
                    return 2;
                }

                if (!horizontal && campo[linha + i][coluna].equals("\ud83d\udee5️")) {
                    return 2;
                }
            }

            return 0;
        }
    }

    public void salvarBarco(boolean horizontal, int tamanho, int linha, int coluna) {
        for(int i = 0; i < tamanho; ++i) {
            if (horizontal) {
                campo[linha][coluna + i] = "\ud83d\udee5️";
            }

            if (!horizontal) {
                campo[linha + i][coluna] = "\ud83d\udee5️";
            }
        }

    }

    public void posicionarCampo() {
        iniciarCampo();
        int contagemBarco = 1;
        int linhaSelec;
        int colunaSelec;
        byte tamanho;
        boolean horizontal;
        if (!autoPos) {
            System.out.println("Jogador " + numJogador + ": Comece o posicionamento dos barcos...");

            while(contagemBarco <= 10) {
                String nomeBarco;
                mostrarCampo();
                if (contagemBarco == 1) {
                    nomeBarco = "Barcasso (4 espacos)";
                    System.out.println("Posicione o " + nomeBarco);
                    tamanho = 4;
                } else if (contagemBarco >= 2 && contagemBarco <= 3) {
                    nomeBarco = "B.R Marolinha maluca (3 espacos)";
                    System.out.println("Posicione um " + nomeBarco + ", falta " + (4 - contagemBarco));
                    tamanho = 3;
                } else if (contagemBarco >= 4 && contagemBarco <= 6) {
                    nomeBarco = "Canoa de pau a pique com um canhao de pirata e varios indiozinhos (2 espacos)";
                    System.out.println("Posicione uma " + nomeBarco + ", falta " + (7 - contagemBarco));
                    tamanho = 2;
                } else {
                    nomeBarco = "Jetski equipado com uma metralhadora .50 (1 espaco)";
                    System.out.println("Posicione um " + nomeBarco + ", falta " + (11 - contagemBarco));
                    tamanho = 1;
                }

                horizontal = direcaoBarco();
                linhaSelec = linhaBarco();
                colunaSelec = colunaBarco();
                int resultadoTeste = testarBarco(horizontal, tamanho, linhaSelec, colunaSelec);
                if (resultadoTeste == 0) {
                    salvarBarco(horizontal, tamanho, linhaSelec, colunaSelec);
                    ++contagemBarco;
                } else {
                    System.out.println("Erro " + resultadoTeste + ":\n" + MENSAGEM_ERRO[resultadoTeste] + "\n");
                }
            }

            Scanner scan = new Scanner(System.in);
            System.out.println("\nEsses são seus barcos...");
            System.out.println("Envie qualquer COISA para continuar");
            scan.next();
        } else {
            Random rand = new Random();
            Scanner scan = new Scanner(System.in);

            while(contagemBarco <= 10) {

                if (contagemBarco == 1) {
                    tamanho = 4;
                } else if (contagemBarco >= 2 && contagemBarco <= 3) {
                    tamanho = 3;
                } else if (contagemBarco >= 4 && contagemBarco <= 6) {
                    tamanho = 2;
                } else {
                    tamanho = 1;
                }

                horizontal = rand.nextBoolean();
                 do{
                    if (horizontal) {
                        linhaSelec = rand.nextInt(0, 10);
                        colunaSelec = rand.nextInt(0, 11 - tamanho);
                    } else {
                        linhaSelec = rand.nextInt(0, 11 - tamanho);
                        colunaSelec = rand.nextInt(0, 10);
                    }
                }
                while(testarBarco(horizontal, tamanho, linhaSelec, colunaSelec) != 0);
                contagemBarco++;
                salvarBarco(horizontal, tamanho, linhaSelec, colunaSelec);
            }

            if (jogador) {
                mostrarCampo();
                System.out.println("\nEsses são seus barcos...");
                System.out.println("Envie qualquer COISA para continuar");
                scan.next();
            }
        }
    }

    public void tiroInimigo() {
        if (vsJogador) {
            if (numJogador == 1) {
                System.out.println("\nVez do jogador 2:");
            } else {
                System.out.println("\nVez do jogador 1:");
            }
            mostrarCampoEscondido();
        } else {
            System.out.println("\nVez do bot");
        }

        boolean continuarAtirando = true;
        int Acertounalinha = 10;
        int Acertounacoluna = 10;
        while(continuarAtirando) {
            if (!temBarcoAinda()) {
                System.out.print("Todos os barcos foram EXPLODIDOS");
                if (numJogador == 2) {
                    System.out.println(", aguarde o outro jogador...");
                } else {
                    System.out.println();
                }

                return;
            }

            int BalaNaLinha;
            int BalaNaColuna;

            if (vsJogador) {
                BalaNaLinha = linhaBarco();
                BalaNaColuna = colunaBarco();
            } else if (modoJogo.equals("dificil") && Acertounacoluna != 10 && Acertounalinha != 10) {
                BalaNaLinha = Acertounalinha;
                BalaNaColuna = Acertounacoluna;
            } else {
                Random rand = new Random();
                BalaNaColuna = rand.nextInt(0, 10);
                BalaNaLinha = rand.nextInt(0, 10);
            }

            switch (campo[BalaNaLinha][BalaNaColuna]) {
                case "\ud83c\udf0a":
                    campo[BalaNaLinha][BalaNaColuna] = "\ud83c\udf00";
                    if (vsJogador) {
                        System.out.println("Você acertou sua bala na água... \ud83d\ude25");
                    } else {
                        System.out.println("O ROBO ACERTOU NA ÁGUA KKKKKKKK! \ud83e\udd23");
                    }
                    continuarAtirando = false;
                    break;
                case "\ud83c\udf00":
                case "\ud83d\udca5":
                    if (vsJogador) {
                        System.out.println("BURRO, ACERTOU NO MESMO LUGAR!!! Vai de novo...");
                    }
                    break;
                case "🛥️":
                    campo[BalaNaLinha][BalaNaColuna] = "\ud83d\udca5";
                    if (vsJogador) {
                        System.out.println("VOCÊ ACERTOU UM PEDAÇO DE BARCO!!! \ud83d\ude18");
                    } else {
                        System.out.println("O ROBO ACERTOU UM DOS SEUS BARCOS! \ud83d\ude32");
                    }
                    if (!vsJogador && modoJogo.equals("dificil")) {
                        boolean encontrouHorizontal = false;
                        boolean encontrouVertical = false;

                        int i;
                        for(i = 1; i < 4; ++i) {
                            if (BalaNaColuna - i >= 0 && campo[BalaNaLinha][BalaNaColuna - i].equals("\ud83d\udee5️")) {
                                Acertounalinha = BalaNaLinha;
                                Acertounacoluna = BalaNaColuna - i;
                                encontrouHorizontal = true;
                                break;
                            }

                            if (BalaNaColuna - i < 0) {
                                break;
                            }
                        }

                        if (!encontrouHorizontal) {
                            for(i = 1; i < 4; ++i) {
                                if (BalaNaColuna + i < 10 && campo[BalaNaLinha][BalaNaColuna + i].equals("\ud83d\udee5️")) {
                                    Acertounalinha = BalaNaLinha;
                                    Acertounacoluna = BalaNaColuna + i;
                                    encontrouHorizontal = true;
                                    break;
                                }

                                if (BalaNaColuna + i >= 10 || !campo[BalaNaLinha][BalaNaColuna + i].equals("\ud83d\udca5")) {
                                    break;
                                }
                            }
                        }

                        if (!encontrouHorizontal) {
                            for(i = 1; i < 4; ++i) {
                                if (BalaNaLinha - i >= 0 && campo[BalaNaLinha - i][BalaNaColuna].equals("\ud83d\udee5️")) {
                                    Acertounalinha = BalaNaLinha - i;
                                    Acertounacoluna = BalaNaColuna;
                                    encontrouVertical = true;
                                    break;
                                }

                                if (BalaNaLinha - i < 0 || !campo[BalaNaLinha - i][BalaNaColuna].equals("\ud83d\udca5")) {
                                    break;
                                }
                            }

                            if (!encontrouVertical) {
                                for(i = 1; i < 4; ++i) {
                                    if (BalaNaLinha + i < 10 && campo[BalaNaLinha + i][BalaNaColuna].equals("\ud83d\udee5️")) {
                                        Acertounalinha = BalaNaLinha + i;
                                        Acertounacoluna = BalaNaColuna;
                                        encontrouVertical = true;
                                        break;
                                    }

                                    if (BalaNaLinha + i >= 10 || !campo[BalaNaLinha + i][BalaNaColuna].equals("\ud83d\udca5")) {
                                        break;
                                    }
                                }
                            }

                            if (!encontrouVertical) {
                                Acertounalinha = 10;
                                Acertounacoluna = 10;
                            }
                        }
                    }
            }

            if (continuarAtirando && vsJogador) {
                mostrarCampoEscondido();
            }
        }

    }
}
