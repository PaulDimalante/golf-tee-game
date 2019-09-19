package golfteesolver;

import java.util.List;

public interface GolfTeeSolver {
    //
    //     o               0
    //    o o             1 2
    //   o o o           3 4  5
    //  o o o o        6  7  8  9
    // o o o o o     10 11 12 13 14
    //
    List<Move> solve(Board startingBoard);
    interface Move {
        int getFrom();
        int getTo();
    }
    interface Board {
        enum Piece {
            EMPTY("o"),
            TEE("T");
            private String value;
            Piece(String value) {
                this.value = value;
            }
            @Override
            public String toString() {
                return value;
            }
        }
        Piece get(int location);
    }
}
