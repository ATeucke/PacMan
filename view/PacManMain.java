package prog2.project5.view;

import static prog2.project5.enums.Direction.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.text.Format;
//import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.Timer;
import prog2.project5.autoplay.ActorController;
import prog2.project5.autoplay.ControllerFactory;
import prog2.project5.autoplay.GhostAutoPlayer;
import prog2.project5.autoplay.newPacManAutoPlayer;
import prog2.project5.enums.ActorType;
import prog2.project5.game.Board;
import prog2.project5.game.FieldInfo;
import prog2.project5.game.GameInfo;
import prog2.project5.game.GameObserverAdpater;
import prog2.project5.game.GhostInfo;
import prog2.project5.game.PacManGame;
import prog2.project5.testutil.TestUtil;
import prog2.project5.testutil.TestUtil.TestControllerFactory;

/**
 * BoardView provides a basic view for the pacMan game, and buttons to
 * manipulate the game.
 */
public class PacManMain {

    private static final String[] DEFAULT_BOARD = { "############################", "#------------##------------#", "#-####-#####-##-#####-####-#", "#X####-#####-##-#####-####X#", "#-####-#####-##-#####-####-#", "#--------------------------#", "#-####-##-########-##-####-#", "#-####-##-########-##-####-#", "#------##----##----##------#", "######-#####-##-#####-######", "######-#####-##-#####-######", "######-##----------##-######", "######-##-####-###-##-######", "######-##-####-###-##-######", "----------##GGGG##----------", "######-##-########-##-######", "######-##-########-##-######", "######-##----------##-######", "######-##-########-##-######", "######-##-########-##-######", "#------------##------------#", "#-####-#####-##-#####-####-#", "#-####-#####-##-#####-####-#", "#X--##--------P-------##--X#", "###-##-##-########-##-##-###", "###-##-##-########-##-##-###", "#------##----##----##------#", "#-##########-##-##########-#", "#-##########-##-##########-#", "#--------------------------#", "############################" };
    private static final String[] NO_GHOST_BOARD = { "############################", "#------------##------------#", "#-####-#####-##-#####-####-#", "#X####-#####-##-#####-####X#", "#-####-#####-##-#####-####-#", "#--------------------------#", "#-####-##-########-##-####-#", "#-####-##-########-##-####-#", "#------##----##----##------#", "######-#####-##-#####-######", "######-#####-##-#####-######", "######-##----------##-######", "######-##-####-###-##-######", "######-##-####-###-##-######", "----------########----------", "######-##-########-##-######", "######-##-########-##-######", "######-##----------##-######", "######-##-########-##-######", "######-##-########-##-######", "#------------##------------#", "#-####-#####-##-#####-####-#", "#-####-#####-##-#####-####-#", "#X--##--------P-------##--X#", "###-##-##-########-##-##-###", "###-##-##-########-##-##-###", "#------##----##----##------#", "#-##########-##-##########-#", "#-##########-##-##########-#", "#--------------------------#", "############################" };
    private static final String[] TEST_BOARD = { "#########","#P------#","#-------#","#-------#","#-------#","#########"};
    private static final String[] DEBUG_BOARD = { "----", "GP-G" };
    private static ImageIcon cherry; 
	private static ImageIcon banana ;
	private static ImageIcon orange ;
	private static ImageIcon strawberry ;
	
	
	
	/** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path,
                                               String description) {
        java.net.URL imgURL = PacManComponent.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    /**
	 * Starts a new Pac-Man game. If parameter -a is given the game is started
	 * in auto player mode.
	 * 
	 * @param args
	 *            the command line arguments.
	 */
    public static void main(String[] args) {
        boolean autoPlay = false;
        if (args.length > 0) {
            String arg = args[0];
            if (arg.startsWith("-a")) {
                autoPlay = true;
            }
        }
        createBoardView(autoPlay);
    }

    /**
	 * TimerTask that triggers the step method of the Pac-Man game.
	 */
    private static class PacManTask extends TimerTask {

        private PacManGame game;
        private GameInfo gameInfo;
      final  private Timer timer;
        private  Graphics g ;
        private Component comp;

        public PacManTask(PacManGame game, final Timer timer, final Component comp) {
           this.game=game;
           this.comp=comp;
           this.timer=timer;
           this.gameInfo = game.getGameInfo();
           game.addObserver(new GameObserverAdpater(){
                    public void gameOver() {
                    ((PacManComponent)comp).newpaint();
        			timer.stop();//.cancel();
        	    	}
        		});
         }
      
        @Override
        public void run() {
        	g = ((PacManComponent)comp).getGraphics();
        	game.step();
        	((PacManComponent)comp).newpaint();	

        	
            
            return ;
        }
    }

    /**
	 * Creates a view for the board.
	 * 
	 * @param autoPlay
	 * 
	 * @param game
	 *            the game to create the view for
	 */
    public static void createBoardView(boolean autoPlay) {
    	
        final Board board = Board.parse(DEFAULT_BOARD); //TODO 
       //final Board board = Board.parse(TEST_BOARD);
    	//Board board = Board.parse(NO_GHOST_BOARD);
        final ControlPlayer controlPlayer = new ControlPlayer();
        ControllerFactory cf = getControllerFactory(controlPlayer, autoPlay);
        final PacManGame game = new PacManGame(board, cf);
        final JFrame frame = new JFrame("2521829 | P A C T O R  - G A M E");
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        frame.setBackground(Color.BLACK);
        final PacManComponent pacManComponent = new PacManComponent(game.getGameInfo());
        controlPlayer.setGame(game);
        controlPlayer.setComponent(pacManComponent);
       
        ActionListener action = new ActionListener() {
        	
        	int deathstep;
        	boolean go;
        	public void actionPerformed(ActionEvent evt) {
        		game.addObserver(new GameObserverAdpater(){
//                	@Override
                	public void pacManDied() {
                		deathstep=17;
                	}
//                	@Override
                	public void gameOver() {
                		go=true;
                	}
//                	@Override
                	public void nextStage() {
                		deathstep=board.getNumberOfRows()+1;
                	}

        		});
                
            	Graphics g ;
            	g = ((PacManComponent)pacManComponent).getGraphics();
            	if(deathstep<=0&&!go) game.step();
            	else deathstep--;
            	((PacManComponent)pacManComponent).newpaint();	
              return ;
            }
        };
         final Timer timer = new Timer(75,action);//was ();
        if(autoPlay) {
        	game.addObserver(new GameObserverAdpater(){
//            	
            	public void gameOver() {
                ((PacManComponent)pacManComponent).newpaint();
    			timer.stop();//.cancel();
    	    	}});
        	timer.start();
        }
        else{
        game.addObserver(new GameObserverAdpater(){
        	int tot;
//        	@Override
        	public void nextStage() {
        		tot=2;
        		//timer.stop();
        	}
//        	@Override
        	public void stepDone() {
        		if(tot>0){tot--;
        		if(tot==0)timer.stop();}
        		
        	}
//        	@Override
        	public void pacManDied() {
        		tot=2;
        		//timer.stop();
        	}
        	public void gameOver() {
            ((PacManComponent)pacManComponent).newpaint();
			timer.stop();//.cancel();
	    	}});}
        //timer.setInitialDelay(1000);
        //timer.start();//new
        //TimerTask tt = new PacManTask(game, timer, pacManComponent);
        //timer.schedule(tt, 1000, 75);
        JPanel rightPanel = createRightPanel(game, controlPlayer,timer,frame,pacManComponent);
        cp.add(pacManComponent, BorderLayout.CENTER);
        cp.add(rightPanel, BorderLayout.LINE_END);
        if (!autoPlay) {
            MyKeyListener keyListener = getKeyListener(controlPlayer,timer,game);
            pacManComponent.setFocusable(true);
            addKeyListenerToAllComponents(keyListener, cp);
        }
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static MyKeyListener getKeyListener(final ControlPlayer controlPlayer, Timer timer, PacManGame game) {
        return new MyKeyListener(controlPlayer,timer,game) ;
    }

    /**
	 * Returns a suitable {@link ControllerFactory}
	 * 
	 * @param controlPlayer
	 *            a ControlPlayer instance to control Pac-Man.
	 * @param autoPlayMode
	 *            indicates whether game is in autoplayer mode.
	 * @return a suitable {@link ControllerFactory}
	 */
    private static ControllerFactory getControllerFactory(final ControlPlayer controlPlayer, boolean autoPlayMode) {
        ControllerFactory cf;
        if (autoPlayMode) {
            cf = new ControllerFactory() {

             //   @Override
                public ActorController getGhostController(GameInfo info, GhostInfo ghost) {
                    return new GhostAutoPlayer(info,ghost);
                }

              //  @Override
                public ActorController getPacManController(GameInfo info) {
                    return new newPacManAutoPlayer(info);
                }
            };
        } else {
            cf = new ControllerFactory() {

               // @Override
                public ActorController getGhostController(GameInfo info, GhostInfo ghost) {
                    return new GhostAutoPlayer(info,ghost);
                	
                	//return new TestUtil.SimpleAutoPlayer(UP);
                }

             //   @Override
                public ActorController getPacManController(GameInfo info) {
                    return controlPlayer;
                }
            };
        }
        return cf;
    }

    /**
	 * Create a panel with direction buttons and info about the game.
	 * 
	 * @param game
	 *            the game to create the buttons for.
     * @param timer 
     * @param frame 
     * @param pacManComponent 
	 * 
	 */
    private static JPanel createRightPanel(final PacManGame game, ControlPlayer controlPlayer, final Timer timer, JFrame frame, PacManComponent pacManComponent) {
        
    	 cherry = createImageIcon("CherryBonus.gif", "a Cherry");
         banana  = createImageIcon("BananaBonus.gif", "a Banana");
		  orange  =createImageIcon("OrangeBonus.gif", "an Orange");
		  strawberry  = createImageIcon("StrawberryBonus.gif", "a Strawberry");
		 
    	JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.BLACK);
        BoxLayout layout = new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS);
       // GridBagLayout layout = new GridBagLayout();
       // GridLayout layout = new GridLayout(3,2,0,0);
        rightPanel.setLayout(layout);
        JPanel allButtonPanel = new JPanel();
        allButtonPanel.setLayout(new BoxLayout(allButtonPanel, BoxLayout.PAGE_AXIS));
        allButtonPanel.setBackground(Color.BLACK);
        JButton rightButton = getRightButton(controlPlayer);
        JButton leftButton = getLeftButton(controlPlayer);
        JButton downButton = getDownButton(controlPlayer);
        JButton upButton = getUpButton(controlPlayer);
        JButton restartButton = getRestartButton(timer,frame);
        JButton autoButton = getautoButton(timer,frame);
        JButton pauseButton = getPauseButton(timer,frame,game,pacManComponent,controlPlayer);
        JPanel directionButtonPanel = new JPanel();
        JPanel bonusButtonPanel = new JPanel();
        directionButtonPanel.setBackground(Color.BLACK);
        bonusButtonPanel.setBackground(Color.BLACK);
        directionButtonPanel.setLayout(new GridLayout(2, 3));
        bonusButtonPanel.setLayout(new GridLayout(2, 3));
        directionButtonPanel.add(new JLabel(""));
        directionButtonPanel.add(upButton);
        directionButtonPanel.add(new JLabel(""));
        directionButtonPanel.add(leftButton);
        directionButtonPanel.add(downButton);
        directionButtonPanel.add(rightButton);
         bonusButtonPanel.add(new JLabel(""));
        bonusButtonPanel.add(pauseButton);
        bonusButtonPanel.add(new JLabel(""));
        bonusButtonPanel.add(restartButton);
        JLabel restart =new JLabel("      Restart");
        restart.setForeground(Color.YELLOW);
        bonusButtonPanel.add(restart);
        bonusButtonPanel.add(autoButton);
        allButtonPanel.add(directionButtonPanel);
        allButtonPanel.add(bonusButtonPanel);
       rightPanel.add(allButtonPanel);
       
        //Score
        final JPanel scorepanel = new JPanel();
        scorepanel.setBackground(Color.BLACK);
        final JLabel score =new JLabel();
       // score.setPreferredSize(new Dimension(15*20,2*20));
        score.setForeground(Color.YELLOW);
        score.setText("Score  :   " + game.getScore());
        score.setFont(new Font(null, 0,15)); //20));
        scorepanel.add(score);
        game.addObserver(new GameObserverAdpater(){
//        	@Override
        	public void stepDone() {
        		score.setText("Score  :  " + game.getScore());
        		//scorepanel.add(score);
        	}
        });
       
        
        //level
        final JPanel levelpanel = new JPanel();
        levelpanel.setBackground(Color.BLACK);
        final JLabel level =new JLabel();
        level.setForeground(Color.YELLOW);
        level.setText("Level :" +game.getStage());
        levelpanel.add(level);
        final JProgressBar bar = new JProgressBar();
        levelpanel.add(bar);
        game.addObserver(new GameObserverAdpater(){
//        	@Override
        	public void nextStage() {
        		level.setText("Level: " +game.getStage());
                levelpanel.add(level);
        	}
        	public void stepDone() {
        		bar.setValue((game.getPacDots() *100) / game.getBoardInfo().getPacDotsOnStart());
        		levelpanel.add(bar);
        	}
        });
        
        //Life
        final JPanel lifepanel = new JPanel();
        lifepanel.setBackground(Color.BLACK);
      //  lifepanel.setPreferredSize(new Dimension(15*20,20));
        final LifeComponent lives = new LifeComponent(game.getGameInfo());
        //final JLabel life =new JLabel();
        //life.setText("Life:" + game.getLives());
        lifepanel.add(lives);
        game.addObserver(new GameObserverAdpater(){
//        	@Override
        	public void pacManDied() {
        		//life.setText("Life:" + game.getLives());
                //lifepanel.add(life);
        		lives.repaint();
        	}
        });
        
      //ExtraItem
        final JPanel extrapanel = new JPanel();
        extrapanel.setBackground(Color.BLACK);
        extrapanel.setPreferredSize(new Dimension(32,32));
        final JLabel extra = new JLabel();
        extrapanel.add(extra);
        game.addObserver(new GameObserverAdpater(){
//        	//@Override
        	public void extraItemPlaced(Point p) {
        		//ExtraItem
        		if (game.getBoard().getField(p.x, p.y).getExtraItem() ==null) return;
				switch(game.getBoard().getField(p.x, p.y).getExtraItem())
					{
					case CHERRY:extra.setIcon(cherry); 
						break; 
					case BANANA:extra.setIcon(banana);  
					break; 
					case ORANGE: extra.setIcon(orange); 
					break; 
					case STRAWBERRY:extra.setIcon(strawberry); 
					break; 
					}
			}

//        	@Override
        	public void extraItemVanished() {
        		extra.setIcon(null);
        	}
//        	@Override
        	public void nextStage() {
        		extra.setIcon(null);
        	}
        	
        });
        
        //benachrichtigung
        final JLabel pause =new JLabel("PRESS SPACE or 'P'ause");
        pause.setForeground(Color.YELLOW);
        pause.setFont(new Font(null, 0, 10));
        pause.setPreferredSize(new Dimension(130,32));
        pause.setHorizontalAlignment(SwingConstants.CENTER);
        game.addObserver(new GameObserverAdpater(){
        	//	@Override
        	public void nextStage() {
        		pause.setText("PRESS SPACE or 'P'ause");
        	}

//        	@Override
        	public void pacManDied() {
        		pause.setText("PRESS SPACE or 'P'ause");
        	}
//        	@Override
        	public void stepDone() {
        	if(timer.isRunning())pause.setText("");
        	else pause.setText("PRESS SPACE or 'P'ause");
        	}
        });
        rightPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
       rightPanel.add(new  JSeparator());
        rightPanel.add(scorepanel);
       rightPanel.add(new  JSeparator());
       rightPanel.add(levelpanel);
        rightPanel.add(new  JSeparator());
        rightPanel.add(lifepanel);
        rightPanel.add(new  JSeparator());
        rightPanel.add(extrapanel);
        rightPanel.add(new  JSeparator());
        rightPanel.add(pause);
       
       /* rightPanel.add(score);
        rightPanel.add(lifepanel);
        rightPanel.add(levelpanel);
        rightPanel.add(extra);
        rightPanel.add(directionButtonPanel);
        rightPanel.add(bonusButtonPanel);*/
        
        return rightPanel; 
    }

    private static JButton getRightButton(final ControlPlayer player) {
        JButton button = new JButton("D-right");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                player.setDirection(RIGHT);return ;
            }
        });
        return button;
    }

    private static JButton getLeftButton(final ControlPlayer player) {
        JButton button = new JButton("A-left");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	player.setDirection(LEFT);
                return ;
            }
        });
        return button;
    }

    private static JButton getDownButton(final ControlPlayer player) {
        JButton button = new JButton("S-down");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	player.setDirection(DOWN);
                return ;
            }
        });
        return button;
    }

    private static JButton getUpButton(final ControlPlayer player) {
        JButton button = new JButton("W-up");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	player.setDirection(UP);
                return ;
            }
        });
        return button;
    }

    private static JButton getRestartButton(final Timer timer, final JFrame frame) {
        JButton button = new JButton("Play");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	timer.stop();//.cancel();
            	frame.dispose();
                createBoardView(false);
                return ;
            }
        });
        return button;
    }
    private static JButton getautoButton(final Timer timer, final JFrame frame) {
        JButton button = new JButton("Auto");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	timer.stop();//cancel();
            	frame.dispose();
                createBoardView(true);
                return ;
            }
        });
        return button;
    }
    private static JButton getPauseButton(final Timer timer, final JFrame frame, final PacManGame game, final PacManComponent pacManComponent, final ControlPlayer controlPlayer) {
        JButton button = new JButton("'P'ause");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(!timer.isRunning()){
            		game.resetLastStepInvocation();
            		timer.restart();
            	}
				else{ 
					timer.stop();
					timer.setInitialDelay(0);
				}
				 return ;
            }
        });
        return button;
    }
    /**
	 * Adds KeyListener to all sub components of the given Container.
	 * 
	 * @param listener
	 *            the listener to add.
	 * @param parent
	 *            the parent container.
	 */
    private static void addKeyListenerToAllComponents(KeyListener listener, Container parent) {
        for (Component cmp : parent.getComponents()) {
            if (cmp instanceof Container) addKeyListenerToAllComponents(listener, (Container) cmp);
            cmp.addKeyListener(listener);
        }
    }
}