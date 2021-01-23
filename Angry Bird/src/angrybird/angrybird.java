package angrybird;
// Angry Birds template provided by Mr. David

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class angrybird extends JPanel {
	
	// how big is the screen...
	private final int W_WIDTH = 900, W_HEIGHT = 600;
	
	// how hard is the game...or how long do you want to play
	private final int NUM_ENEMIES = 5;
	
	// are you playing on earth or moon...emmm
	private final double GRAVITY = .4;
	private int[] enemyX = {500, 450, 760, 600, 300};
	private int[] enemyY = {330, 330, 200, 200, 450};
	private final int LAUNCHERDIAM = 55, ENEMYDIAM = 80;
	private final int OBSTACLEDIAM = 150;
	
	
	// how do you know that it's a angry bird game?
	// in fact, I wanted to make a laser gun game, but...the gravity doesn't work then.
	// below are the variables
	private Image cannongun;	
	private Image background;
	private Image aliens;
	private Image obstacle;
	private Image cannonball;
	private int startX, startY;
	private int bounceup = 5;
	private boolean hitobY = false;
	private boolean win = false;
	private boolean hitob = false;
	private boolean birddead = false;
	private int bounce = 2;
	private int[] obstacleX = {600, 600, 470, 730};
	private int[] obstacleY = {300, 430, 430, 300};
	private int pausetime = 500;
	private int lives = 8;
	private boolean move = true;
	private boolean gameover = false;
	private Boolean[] dead = {false, false, false, false, false};
	private int score = 0;
	private int fall = 10;
	private double SpeedX = 0, SpeedY = 0;
	private double birdX = 100, birdY = 300;
	private int birdstartX = 100, birdstartY = 300;
	private int launchstartX = birdstartX - 20;
	private int imagewh = 150;
	private int launchheight = imagewh + 200;
	private boolean isgravityon = false;
	private int popupX = 500;
	private int popupY = 150;
	private int scoreX = 100;
	private int scoreY = 200;
	private int livesY = 150;
	private int winX = 400;
	private int winY = 300;
	
	
	//so I am gonna to draw the images of the shooting stuff and the target
	public void setup() {
		try {
			cannonball = ImageIO.read(new File("cannon ball.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			cannongun  = ImageIO.read(new File("cannon gun.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			background = ImageIO.read(new File("background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			aliens = ImageIO.read(new File("aliens.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			obstacle = ImageIO.read(new File("obstacle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// so here is the gravity effect
	public void moveBird() {
		if (move == true) {
			birdX += SpeedX;
			birdY += SpeedY;
		
		
			if (isgravityon)
				SpeedY += GRAVITY;
		
		}
	//Since the ball can fly forever, I set a limit for it, so it can come back.
		if (birdY > W_HEIGHT + pausetime) {
			birddead = true;
			birdX = birdstartX;
			birdY = birdstartY;
			
			lives -= 1;
			move = false;
			hitob = false;
			birddead = false;
		}
	// cannon ball exploded!
		if (birdY < -W_HEIGHT) {
			birddead = true;
			birdX = birdstartX;
			birdY = birdstartY;
			
			lives -= 1;
			move = false;
			hitob = false;
			birddead = false;
		}
		if (birdX > W_WIDTH + pausetime) {
			birddead = true;
			birdX = birdstartX;
			birdY = birdstartY;
			
			lives -= 1;
			move = false;
			hitob = false;
			birddead = false;
		}
	
		// if enemies are dead, they falls
		for (int i = 0; i < enemyY.length; i++) {
			if (dead[i] == true) {
				enemyY[i]+= fall;
			}
		}
		
		if (hitob == true && birddead == false) {
			birdX -= SpeedX * bounce;
			birdY += fall;
			
		}
		if (hitobY == true && birddead == false) {
			
			isgravityon = false;
			SpeedY -= bounceup;
		
			hitobY = false;
			
		}
		
	
		
	}
	
	// check for any collisions between your 'bird' and the enemies.
	// if there is a collision, address it
	public void checkHits() {
		// check for collisions on the birds and the enemy and the bird and the obstacle's left and upper side.
	
	for (int i = 0; i < enemyX.length; i++) {
		if (distance(birdX, birdY, enemyX[i], enemyY[i]) <= ENEMYDIAM/2 + LAUNCHERDIAM/2) {
			
			if (dead[i] == false) {
				score ++;
			}
			dead[i] = true;		
		}
	}
	//object hit detection for bird and side of obstacles, will bounce off the side
	for (int j = 0; j < obstacleX.length; j++) {
		if (birdY > obstacleY[j] && birdY <= obstacleY[j] + imagewh && birdX > obstacleX[j] - imagewh) {
			hitob = true;
		
		}
	}
	

	for (int j = 0; j < obstacleX.length; j++) {
		if (birdX > obstacleX[j] && birdX <= obstacleX[j] + imagewh && birdY > obstacleY[j] - imagewh) {
			hitobY = true;
			
		}
	}
}

	// distance formula basically copied from math
	private double distance(double birdX, double birdY, int enemyX, int enemyY) {
		double without = Math.pow((enemyX-birdX), 2) + Math.pow((enemyY - birdY), 2);
		without = Math.sqrt(without);
		
		return without;

	
	}
	
	// what you want to happen at the moment when the 
	// mouse is first pressed down.
	public void mousePressed(int mouseX, int mouseY) {
		// what happens when mouse is pressed, initialize two variables.
		
		startX = mouseX;
		startY = mouseY;
		
	}
	
	// what you want to happen when the mouse button is released
	public void mouseReleased(int mouseX, int mouseY) {
		// when mouse is released the bird is launched in the opposite direction of the drag.
	if (gameover == false && win == false) {
		int distDraggedX = mouseX - startX;
		int distDraggedY = mouseY - startY;
		SpeedX = -distDraggedX/10.0;
		SpeedY = -distDraggedY/10.0;
		
		isgravityon = true;
		move = true;
	}
	}
	
	// draws everything in our project - the enemies, your 'bird', 
	// and anything else that is present in the game
	public void paint(Graphics g) {
		// draws a white background
		g.drawImage(background, 0, 0, W_WIDTH, W_HEIGHT, null);
		g.drawRect(0, 0, W_WIDTH, W_HEIGHT);
		
		g.drawImage(cannongun, launchstartX, birdstartY+130, imagewh, launchheight-200, null);
		// all of my image drawn
		g.drawImage(cannonball,(int)birdX+80, (int)birdY+140, imagewh-70, imagewh-70, null);
		
		//draws all the obstacles
		for (int j = 0; j < obstacleX.length; j++) {
			g.drawImage(obstacle, obstacleX[j], obstacleY[j], imagewh, imagewh, null);
		}
		// draws all the enemies
		for (int i = 0; i < enemyX.length; i++) {
			g.drawImage(aliens, enemyX[i], enemyY[i]+30, imagewh-70, imagewh-70, null);
		}
		
		
		
		// these are scores and lives for the game drawn and special
		//popups for each score phase.
		Font f = new Font("Arial", Font.BOLD, 25);
		g.setFont(f);
		g.setColor(Color.white);
		g.drawString("score: " + score, scoreX, scoreY);
		g.drawString("lives: " + lives, scoreX, livesY);
		// if socre is one beginners luck pops up
		if (score == 1) {
			
			Font a = new Font("Serif", Font.BOLD, 25);
			g.setFont(a);
			g.setColor(Color.green);
			g.drawString("nice job", popupX, popupY);
		
		}
		// if score is two then two in a role pops up
		if (score == 2) {
			
			Font a = new Font("Serif Bold", Font.BOLD, 25);
			g.setFont(a);
			g.setColor(Color.green);
			g.drawString("wow, what a nice shot", popupX, popupY);
		
		}
		// if the score is four then almost pops up
		if (score == 4) {
			
			Font a = new Font("Arial", Font.BOLD, 25);
			g.setFont(a);
			g.setColor(Color.green);
			g.drawString("Hhh, you will become a great artillery!", popupX, popupY);
		
		}
		// is score is five then the player won
		if (score == 5) {
			win = true;
			Font v = new Font("Serif Bold", Font.BOLD, 400);
			g.setColor(Color.green);
			g.drawString("Human Wins!!!", winX, winY);
		}
		
		// if lives is 0 then game is over
		if (lives == 0) {
			gameover = true;
			Font v = new Font("Serif Bold", Font.BOLD, 400);
			g.setColor(Color.red);
			g.drawString("GAME OVER", winX, winY);
			
		}
		
		
	}
	
	
	// ************** DON'T TOUCH THE BELOW CODE ********************** //
	
	public void run() {
		while (true) {
			moveBird();
			checkHits();
			repaint();
			
			try {Thread.sleep(20);} 
			catch (InterruptedException e) {}
		}
	}
	
	public angrybird() {
		setup();
		
		JFrame frame = new JFrame();
		frame.setSize(W_WIDTH,W_HEIGHT);
		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				angrybird.this.mousePressed(e.getX(),e.getY());	
			}
			public void mouseReleased(MouseEvent e) {
				angrybird.this.mouseReleased(e.getX(),e.getY());
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		frame.add(this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		run();
	}
	public static void main(String[] args) {
		new angrybird();
	}
}