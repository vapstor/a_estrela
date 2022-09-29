public class App {
    public static void main(String[] args) throws Exception {
        int[][] victoryState = { { 1, 2, 3 },
                                 { 4, 5, 6 },
                                 { 7, 8, 0 } };

        Node node = new Node(victoryState);
        Utils.printNode(node);
    }
}