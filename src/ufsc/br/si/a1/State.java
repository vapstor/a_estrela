package ufsc.br.si.a1;

public class State {
  private String value;
  private double custoTotal;

  public double getCustoTotal() {
    return custoTotal;
  }

  public void setCustoTotal(double custoTotal) {
    this.custoTotal = custoTotal;
  }

  public State(String value) {
    this.value = value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
