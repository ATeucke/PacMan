package prog2.project5.view;

import static java.awt.event.KeyEvent.*;
import static prog2.project5.enums.Direction.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import prog2.project5.game.PacManGame;

/**
 * Key listener that allows to move Pac-Man.
 */
public class MyKeyListener extends KeyAdapter {

    /**
	 * The control player to delegate the moves.
	 */
    ControlPlayer c;
    Timer timer ;
    PacManGame game;
    /**
	 * Creates a new MyKeyListener with given control player.
	 * 
	 * @param c
	 *            the control player to delegate the moves.
	 */
    public MyKeyListener(ControlPlayer c,Timer timer,PacManGame game) {
        this.c=c;
        this.timer=timer;
        this.game=game;
    }

    /**
	 * Sets the direction for the control player when a corresponding key was
	 * pressed (VK_UP, VK_LEFT ...).
	 * 
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
        case VK_UP : c.setDirection(UP);break;
        case VK_LEFT : c.setDirection(LEFT);break;
        case VK_RIGHT : c.setDirection(RIGHT);break;
        case VK_DOWN : c.setDirection(DOWN);break;
        case VK_W : c.setDirection(UP);break;
        case VK_A : c.setDirection(LEFT);break;
        case VK_D: c.setDirection(RIGHT);break;
        case VK_S : c.setDirection(DOWN);break;
        case VK_P : if(!timer.isRunning()){
    		game.resetLastStepInvocation();
    		timer.restart();
    	}
		else{ 
			timer.stop();
			timer.setInitialDelay(0);
		}break;
        case VK_SPACE : if(!timer.isRunning()){
    		game.resetLastStepInvocation();
    		timer.restart();
    	}else{ 
			timer.stop();
			timer.setInitialDelay(0);
		}
		break;
        default : break;
        }
    	return ;
    }
    
}