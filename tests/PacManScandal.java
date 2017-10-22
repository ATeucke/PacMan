package prog2.project5.tests;

import java.awt.Point;
import org.junit.Test;
import prog2.project5.autoplay.ActorController;
import prog2.project5.autoplay.ControllerFactory;
import prog2.project5.enums.ActorType;
import prog2.project5.game.Board;
import prog2.project5.game.GameInfo;
import prog2.project5.game.GameObserver;
import prog2.project5.game.GhostInfo;
import prog2.project5.game.PacManGame;
import prog2.project5.testutil.TestUtil.SimpleAutoPlayer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static prog2.project5.enums.Direction.*;

public class PacManScandal {
        
        private int derTod = 0;

        public static ControllerFactory getSimpleControllerFactory() {
                final ActorController pacManAuto = new SimpleAutoPlayer(LEFT);
                final ActorController ghostAuto = new SimpleAutoPlayer(RIGHT);
                return new ControllerFactory() {
                       // @Override
                        public ActorController getGhostController(GameInfo info,
                                        GhostInfo ghost) {
                                return ghostAuto;
                        }

                      //  @Override
                        public ActorController getPacManController(GameInfo info) {
                                return pacManAuto;
                        }
                };
        }
        @Test(timeout = 5000)
        public void stageComplete() {
        Board board = Board.parse(new String[] { "G-P" });
                PacManGame pmg = new PacManGame(board,getSimpleControllerFactory());
                derTod = 0;
                pmg.addObserver(new GraveDigger());
                assertEquals("beginnt bei 0L S1L", 0L, pmg.getScore());
                pmg.step(362L);
                assertEquals("Bitter ist der Tod, aber PacMan lebt noch? S1L",1, derTod);
                assertEquals("ohne dass in diesen Zug noch Punkte vergeben werden? S1L", 201L, pmg.getScore());
        }
        
        @Test(timeout = 5000)
        public void schrecklicherTest() {
        Board board = Board.parse(new String[] { "-G-P" });
                PacManGame pmg = new PacManGame(board,getSimpleControllerFactory());
                derTod = 0;
                pmg.addObserver(new GraveDigger());
                assertEquals("beginnt bei 0L S2L", 0L, pmg.getScore());
                pmg.step(362L);
                assertEquals("Bitter ist der Tod, aber PacMan lebt noch? S2L",1, derTod);
                assertEquals("ohne dass in diesen Zug noch Punkte vergeben werden? S2L", 1L, pmg.getScore());
        }
        
        @Test(timeout = 5000)
        public void gameOver() {
        Board board = Board.parse(new String[] { "G-P" });
                PacManGame pmg = new PacManGame(board,getSimpleControllerFactory());
                derTod = 0;
                pmg.addObserver(new GraveDigger());
                assertEquals("Am Anfang 0 Punkte S3L", 0L, pmg.getScore());
                pmg.step(362L);
                assertEquals("-1 Leben S3L",1, derTod);
                pmg.step(362L);
                assertEquals("-2 Leben S3L",2, derTod);
                assertEquals("ohne dass in diesen Zug noch Punkte vergeben werden? S3L1", 302L, pmg.getScore());
                pmg.step(362L);
                assertEquals("-3 Leben S3L",3, derTod);
                assertTrue(pmg.isGameOver());
                assertEquals("verrechnet S3L",303L, pmg.getScore());
        }
        
        @Test(timeout = 5000)
        public void copyGameOver() {
        Board board = Board.parse(new String[] { "G-P" });
                PacManGame pmg = new PacManGame(board,getSimpleControllerFactory());
                derTod = 0;
                pmg.addObserver(new GraveDigger());
                assertEquals("Am Anfang 0 Punkte S4L", 0L, pmg.getScore());
                pmg.step(362L);
                assertEquals("-1 Leben S4L",1, derTod);
                pmg.step(362L);
                assertEquals("-2 Leben S4L",2, derTod);
                assertEquals("ohne dass in diesen Zug noch Punkte vergeben werden? S4L1", 302L, pmg.getScore());
                pmg.step(362L);
                assertTrue(pmg.isGameOver());
                assertEquals("verrechnet S4L", 303L, pmg.getScore());
        }
        
        
        
        private class GraveDigger implements GameObserver {

           //     @Override
                public void actorRemoved(ActorType actortype, int x, int y) {}

            //    @Override
                public void actorSet(ActorType actortype, int x, int y) {}

             //   @Override
                public void endPowerPelletMode() {}

            //    @Override
                public void extraItemPlaced(Point p) {}

           //     @Override
                public void extraItemVanished() {}

              //  @Override
                public void gameOver() {}

         //       @Override
                public void nextStage() {}

          //      @Override
                public void pacManDied() {
                derTod++;               
                }

            //    @Override
                public void startPowerPelletMode() {}

            //    @Override
                public void stepDone() {}       
        }
}