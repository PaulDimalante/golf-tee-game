import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GolfTeeSolverAppTest {
    @Test
    public void move2to7ShouldReturn2to7() {
        GolfTeeSolver.Move move = new GolfTeeSolverApp().new Move(2,7);
        int resultFrom = move.getFrom();
        int resultTo = move.getTo();
        assertEquals(2, resultFrom);
        assertEquals(7, resultTo);
    }

    @Test
    public void getShouldReturnEmptyWhenLocationIs1() {
        GolfTeeSolver.Board board = new GolfTeeSolverApp().new Board();
        GolfTeeSolver.Board.Piece result = board.get(1);
        assertEquals(GolfTeeSolver.Board.Piece.EMPTY, result);
    }

    @Test
    public void getShouldReturnTeeWhenLocationIs2() {
        GolfTeeSolver.Board board = new GolfTeeSolverApp().new Board();
        GolfTeeSolver.Board.Piece result = board.get(2);
        assertEquals(GolfTeeSolver.Board.Piece.TEE, result);
    }
}
