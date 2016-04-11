package surkemper.com.Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import surkemper.com.Input.Mouse;

class NaivePaintingComponent extends JComponent {
	private Shape shape;
	private Color c;
	public static Random random = new Random();
	private int randCol;
	private int backColor;
	private int[] colorHolderBlue = { 0x1D84F2, 0x594BF2, 0x3248D9, 0xA9D1F5,
			0x86D1DB };
	private int[] colorHolderRed = { 0xDB1F1F, 0xFA1E29, 0xAD131B, 0xFA7F86,
			0xFC4949 };
	private BufferedImage bi;
	private int[][] firePos = { { random.nextInt(1000), random.nextInt(500) },
			{ random.nextInt(1000), random.nextInt(500) },
			{ random.nextInt(1000), random.nextInt(500) }, };

	private void resetPos() {
		for (int i = 0; i < 3; i++) {
			firePos[i][0] = random.nextInt(1000);
			for (int k = 0; k < 2; k++) {
				firePos[i][1] = random.nextInt(500);
			}

		}

	}

	public void drawNow(int lineCol, int backCol, int position) {
		bi = new BufferedImage(this.getSize().width, this.getSize().height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = (Graphics2D) bi.getGraphics();
		Graphics2D g2dScreen = (Graphics2D) getGraphics();
		clearScreen(g2d);
		if (backCol == 0) {
			backColor = 0x000000;
		} else if (backCol == 1) {
			backColor = colorHolderBlue[0];
		} else if (backCol == 2) {
			backColor = colorHolderRed[1];
		} else {
			backColor = random.nextInt(0xffffff);
		}
		g2d.setColor(new Color(backColor));
		drawBackground(g2d);

		for (int i = 0; i < RayCreator.amountCount; i++) {
			int randX = random.nextInt(1000);
			int randY = random.nextInt(500);

			if (lineCol == 1) {
				randCol = colorHolderBlue[i % 5];
			} else if (lineCol == 2) {
				randCol = colorHolderRed[i % 5];
			} else {
				randCol = random.nextInt(0xffffff);
			}

			g2d.setColor(new Color(randCol));

			if (position == 0) {
				drawLineZentral(randX, randY, g2d);
			}
			if (position == 1) {
				drawLineRandom(g2d);
			}
			if (position == 2) {
				drawLineFirework(g2d);
			}

			/*
			 * try { //TimeUnit.MILLISECONDS.sleep(2000
			 * /RayCreator.amountCount); } catch (InterruptedException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); }
			 */
			// /Random punkte...

		}
		resetPos();
		paint(g2d);
		g2d.dispose();
		g2dScreen.drawImage(bi, null, 0, 0);

	}

	public void drawFreeHand(int lineCol, int backCol, int position) {
		bi = new BufferedImage(this.getSize().width, this.getSize().height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = (Graphics2D) bi.getGraphics();
		Graphics2D g2dScreen = (Graphics2D) getGraphics();
		clearScreen(g2d);
		if (backCol == 0) {
			backColor = 0x000000;
		} else if (backCol == 1) {
			backColor = colorHolderBlue[0];
		} else if (backCol == 2) {
			backColor = colorHolderRed[1];
		} else {
			backColor = random.nextInt(0xffffff);
		}
		g2d.setColor(new Color(backColor));
		drawBackground(g2d);

		if (lineCol == 1) {
			randCol = colorHolderBlue[random.nextInt(5)];
		} else if (lineCol == 2) {
			randCol = colorHolderRed[random.nextInt(5)];
		} else {
			randCol = random.nextInt(0xffffff);
		}
		while (RayCreator.freeHandOn) {
			g2d.setColor(new Color(randCol));
			drawLineZentral(Mouse.mouseX, Mouse.mouseY, g2d);
			paint(g2d);
			g2d.dispose();
			g2dScreen.drawImage(bi, null, 0, 0);
		}

	}

	// protected void paintComponent(Graphics arg0) {
	// System.out.println("USED");
	// }

	public void drawBackground(Graphics g) {
		g.fillRect(0, 0, 1000, 500);
	}

	public void saveImage() {
		try {
			int number = loadInt();
			ImageIO.write(bi, "png", new File("farbenspiel_" + number + ".png"));
			saveInt(number + 1);

		} catch (Exception e) {
		}

	}

	public int loadInt() throws IOException {
		File fil = new File("number.txt");
		if (!fil.exists() || fil.isDirectory()) {
			saveInt(0);
			System.out.println("file does not exist");
		}
		FileReader inputFil = null;
		inputFil = new FileReader(fil);
		BufferedReader in = new BufferedReader(inputFil);

		int number;
		String gesamt = "";

		String s = null;
		s = in.readLine();

		while (s != null) {
			gesamt += s;
			System.out.println(gesamt);
			s = in.readLine();
		}
		number = Integer.parseInt(gesamt);

		in.close();
		return number;
	}

	public void saveInt(int number) throws IOException {
		PrintWriter writer = new PrintWriter("number.txt");
		writer.println("" + number);
		writer.close();
	}

	public void setColor(Color c) {
		this.c = c;
	}

	public void drawLineZentral(int x, int y, Graphics g) {
		if (x >= 0 && y >= 0 && x < 1000 && y < 500)
			while (Mouse.dragged()) {
				g.drawLine(500, 250, x, y);
				x = Mouse.mouseX;
				y = Mouse.mouseY;
			}
	}

	public void drawLineRandom(Graphics g) {
		g.drawLine(random.nextInt(1000), random.nextInt(500),
				random.nextInt(1000), random.nextInt(500));
	}

	public void drawLineFirework(Graphics g) {
		// zeichne fw bei mouse click
		int counter = random.nextInt(3);

		g.drawLine(firePos[counter][0], firePos[counter][1],
				random.nextInt(1000), random.nextInt(500));
	}

	public void clearScreen(Graphics g) {
		g.clearRect(0, 0, 1000, 500);

	}
}