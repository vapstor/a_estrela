package ufsc.br.si.a1;

import java.util.Arrays;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println(
                "Digite o estado inicial, inserindo 0 no lugar do espaço vazio. (Exemplo: estado final é 123456780)");

        Scanner sc = new Scanner(System.in);
        try {
            String input = sc.nextLine();
            while (!Utils.isValidState(input)) {
                System.out.println("Estado inválido, insira novamente. (Exemplo: estado final é 123456780)");
                input = sc.nextLine();
            }
            Utils.print("Iniciando Busca...");

            // Cria Obj com estado inicial
            State initialState = new State(input);

            // // Busca melhor caminho (com o menor custo).
            // double custoTotal = 0.0;
            // getCustoTotalDoEstado(initialState);
            
            State finalState = realizaTroca(initialState, '0', '8');
            Utils.showDialog("Pecas fora do lugar", String.valueOf(qtdPecasForaDoLugar(finalState)));

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(Utils.ERROR_STATUS);
        } finally {
            sc.close();
            System.exit(Utils.SUCCESS_STATUS);
        }
    }

    private static State realizaTroca(State oldState, char first, char second) {
        State newState = new State(oldState.getValue());
        char[] values = newState.getValue().toCharArray();
        for (int i = 0; i < values.length-1; i++) {
            if(values[i] == first || values[i] == second) {
                if(values[i] == first) {
                    int idxSegundo = newState.getValue().indexOf(second);
                    values[i] = second;
                    values[idxSegundo] = first;
                } else {
                    int idxPrimeiro = newState.getValue().indexOf(first);
                    values[i] = first;
                    values[idxPrimeiro] = second;
                }
                break;
            }
        }
        newState.setValue(String.valueOf(values));
        return newState;
    }

    private static void getCustoTotalDoEstado(State state) {
        int pecasForaDoLugar = qtdPecasForaDoLugar(state);
        double custoTotal = 0.0;
        boolean isColumn = true;
        if (isColumn) {
            int[] firstColumn = getColumn(state, 0);
            int[] secondColumn = getColumn(state, 1);
            int[] thirdColumn = getColumn(state, 2);
            custoTotal =+ getCustoOrdenacao(firstColumn);
            custoTotal =+ getCustoOrdenacao(secondColumn);
            custoTotal =+ getCustoOrdenacao(thirdColumn);
        } else {
            int[] firstLine = getLine(state, 0);
            int[] secondLine = getLine(state, 3);
            int[] thirdLine = getLine(state, 6);
            custoTotal =+ getCustoOrdenacao(secondLine);
            custoTotal =+ getCustoOrdenacao(secondLine);
            custoTotal =+ getCustoOrdenacao(thirdLine);
        }
        buscaHeuristica(null);

    }

    private static int[] getColumn(State state, int pos) {
        int[] column = {
                Character.getNumericValue(state.getValue().charAt(pos)),
                Character.getNumericValue(state.getValue().charAt(pos + 3)),
                Character.getNumericValue(state.getValue().charAt(pos + 6))
        };
        return column;
    }

    private static int[] getLine(State state, int pos) {
        int[] column = {
                Character.getNumericValue(state.getValue().charAt(pos)),
                Character.getNumericValue(state.getValue().charAt(pos + 1)),
                Character.getNumericValue(state.getValue().charAt(pos + 2))
        };
        return column;
    }

    private static void buscaHeuristica(State state) {
        if (Utils.isFinalState(state)) {
            Utils.printFinalState();
        } else {
            // Calcula custo do estado
            double cTotal = Utils.roundDouble(getHeuristica(state) * (state.getValue().length() * 0.1)); // Dica: usar
                                                                                                         // 10% para nao
                                                                                                         // ultrapassar
                                                                                                         // o pior caso.
            state.setCustoTotal(cTotal);
            Utils.print("Valor: " + state.getCustoTotal());

            // Verifica filhos

            //
        }
    }

    private static int qtdPecasForaDoLugar(State state) {
        int pecasForaLugar = 9;
        for (int i = 0; i < state.getValue().length(); i++) {
            if (isPosCorreta(state.getValue().charAt(i), i)) {
                pecasForaLugar--;
            }
        }
        return pecasForaLugar;
    }

    private static boolean isPosCorreta(char c, int position) {
        return Utils.finalState.getValue().charAt(position) == c;
    }

    private static double getCustoOrdenacao(int[] numbers) {
        // take count as all elements are correctly placed
        int count = numbers.length;
        // Traverse array from end
        for (int i = numbers.length - 1; i >= 0; i--) {
            // If current item is at its correct position,
            // decrement the count
            // range is 1 to n so every arr[i] should have value i+1
            // 1 at 0 index, 2 at 1 index........
            if (numbers[i] == count)
                count--;
        }
        return count;
    }

    private static boolean isTroca(int first, int second) {
        return first > second;
    }

    private static int getHeuristica(State state) {
        // int qtdTrocas = 0;
        Utils.print("length " + state.getValue().length());

        return getSimpleQtdTrocas(state);

        // for (int posAtual = 0; posAtual < state.getValue().length(); posAtual++) {
        // qtdTrocas += getQtdTrocas(posAtual, state);
        // }
        // return qtdTrocas;
    }

    private static int getSimpleQtdTrocas(State state) {
        int qtdTrocasSimples = 0;
        for (int i = 0; i < state.getValue().length(); i++) {
            char correctValue = Utils.finalState.getValue().charAt(i);
            char comparisonValue = state.getValue().charAt(i);
            if (comparisonValue != correctValue) {
                qtdTrocasSimples++;
            }
        }
        return qtdTrocasSimples;
    }

    // Get quantidade de trocas.
    private static int getQtdTrocas(int posAtual, State state) {
        int qtdTrocas = 0;
        int posComparador = 0;
        int firstValue = Character.getNumericValue(state.getValue().charAt(posAtual));

        while (posComparador < state.getValue().length()) {
            if (posAtual != posComparador) {
                int secondValue = Character.getNumericValue(state.getValue().charAt(posComparador));

                if (isTroca(firstValue, secondValue)) {
                    qtdTrocas++;
                }
            }
            posComparador++;
        }
        return qtdTrocas;
    }

    /*
     * int custo = 0;
     * // Primeira Linha
     * char _1linha1char = state.getValue().charAt(0);
     * char _1linha2char = state.getValue().charAt(1);
     * char _1linha3char = state.getValue().charAt(2);
     * 
     * // Ordenação Horizontal 1 Linha
     * if (isTroca(_1linha1char, _1linha2char)) {
     * custo++;
     * }
     * if (isTroca(_1linha2char, _1linha3char)) {
     * custo++;
     * }
     * 
     * // Segunda Linha
     * char _2linha1char = state.getValue().charAt(3);
     * char _2linha2char = state.getValue().charAt(4);
     * char _2linha3char = state.getValue().charAt(5);
     * 
     * // Terceira Linha
     * char _3linha1char = state.getValue().charAt(6);
     * char _3linha2char = state.getValue().charAt(7);
     * char _3linha3char = state.getValue().charAt(8);
     */
}