package ufsc.br.si.a1;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Utils {
  public final static int SUCCESS_STATUS = 0;
  public final static int ERROR_STATUS = -1;
  public final static State finalState = new State("123456780");

  public static boolean isValidState(String initialState) {
    return initialState.length() == 9
        && initialState.contains("1")
        && initialState.contains("2")
        && initialState.contains("3")
        && initialState.contains("4")
        && initialState.contains("5")
        && initialState.contains("6")
        && initialState.contains("7")
        && initialState.contains("8")
        && initialState.contains("0");
  }

  public static boolean isFinalState(State state) {
    return state.getValue().contentEquals(finalState.getValue());
  }

  public static void print(String str) {
    System.out.println(str);
  }

  public static void showDialog(String title, String message) {
    JFrame frame = new JFrame("");
    frame.setAlwaysOnTop(true);
    JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    frame.dispose();
  }

  public static void printFinalState() {
    print("Achou!");
    showDialog("Solução encontrada", "O melhor caminho foi encontrado!");
  }

  public static double roundDouble(double cTotal) {
    return new BigDecimal(cTotal).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

}
