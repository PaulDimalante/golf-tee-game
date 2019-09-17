import java.util.List;

public class GolfTeeSolverApp implements GolfTeeSolver {
    public class Move implements GolfTeeSolver.Move {
        private int moveFrom;
        private int moveTo;

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
        @Override
        public Piece get(int location) {
            return Piece.EMPTY;
        }
    }

    @Override
    public List<GolfTeeSolver.Move> solve(GolfTeeSolver.Board startingBoard) {
        return null;
    }

}
