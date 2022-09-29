import java.util.Arrays;

class Utils {
  public static void printNode(Node node) {
    System.out.println(Arrays.deepToString(node.getTable())
        .replaceAll("],", "]," + System.getProperty("line.separator")));
  }
}
