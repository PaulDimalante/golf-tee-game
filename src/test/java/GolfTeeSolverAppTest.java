import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GolfTeeSolverAppTest {
    @Test
    public void initialBoardShouldHave13Tees() {
        GolfTeeSolverApp app = new GolfTeeSolverApp();
        GolfTeeSolverApp.Board board;
        board = app.getBoard();
        int result = board.getRemainingPieces();
        assertEquals(13, result);
    }
}
