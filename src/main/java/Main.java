import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main me = new Main();
        Scanner scanner = new Scanner(System.in);
        GolfTeeSolver solver = new GolfTeeSolverImpl();
        GolfTeeSolver.Board board = new GolfTeeSolverImpl().new Board();
        List<GolfTeeSolver.Move> moves = solver.solve(board);
        me.displayBoard(board);
        for(GolfTeeSolver.Move move : moves) {
            System.out.println("from="+move.getFrom()+"; to="+move.getTo());
            //String str = scanner.nextLine();
        }

    }
    private void displayBoard(GolfTeeSolver.Board board) {
        System.out.println("===========================================================");
        int pos = 0;
        for(int r=0; r<5; r++) {
            for(int c=0; c<=r; c++) {
                System.out.print(board.get(pos).toString());
            }
            System.out.println();
        }
    }
    private GolfTeeSolver.Board updateBoard(GolfTeeSolver.Board board, GolfTeeSolver.Move move) {
        int moveFrom = move.getFrom();
        int moveTo = move.getTo();
        GolfTeeSolver.Board newBoard = new GolfTeeSolverImpl().new Board();
        for(int i=0; i<15;i++) {
            if(i == moveFrom) {

            }
        }
        return newBoard;
    }
}