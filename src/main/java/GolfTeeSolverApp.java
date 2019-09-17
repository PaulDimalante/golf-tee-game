import java.util.ArrayList;
import java.util.List;

public class GolfTeeSolverApp implements GolfTeeSolver {
    private List<GolfTeeSolver.Move> moves = new ArrayList<GolfTeeSolver.Move>();

    public class Move implements GolfTeeSolver.Move {
        private int moveFrom;
        private int moveTo;

        public Move() {
        }

        public Move(int moveFrom, int moveTo) {
            this.moveFrom = moveFrom;
            this.moveTo = moveTo;
        }

        @Override
        public int getFrom() {
            return this.moveFrom;
        }
        @Override
        public int getTo() {
            return this.moveTo;
        }
    }

    public class Board implements GolfTeeSolver.Board {
        private String[] board = {"0","T","T","T","T","T","T","T","T","T","T","T","T","T","T"};

        @Override
        public Piece get(int location) {
            if(location != 1) return Piece.TEE;
            return Piece.EMPTY;
        }
    }

    @Override
    public List<GolfTeeSolver.Move> solve(GolfTeeSolver.Board startingBoard) {
        this.moves.add(new Move());
        return this.moves;
    }

}
