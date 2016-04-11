package surkemper.com.Main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import surkemper.com.Input.Mouse;

public class RayCreator extends Canvas {

	// unsere frisch gebackene Komponente
	private NaivePaintingComponent paintingComponent = new NaivePaintingComponent();
	public static int amountCount;
	public JFrame frame;
	private int lineColor = 0;
	private int backColor = 0;
	private int position = 0;
	public static BufferedImage savedImage;
	public static Graphics2D graphic;
	private final int HEIGHT = 500;
	private final int WIDTH = 1000;
	public static boolean freeHandOn = false;

	/**
	 * Im Konstrukor wird die übliche Arbeit erledigt um den JFrame zu öffnen
	 * und die Komponenten zu initialisieren
	 */
	public RayCreator() {
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		amountCount = 1;
		// einen JFrame erzeugen
		frame = new JFrame("Farbenspiel");
		// ein hübsches Layout setzen
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		// dafür sorgen das das Programm beendet wird wenn man das 'X' anklickt
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// eine JComponent hat keine Ahnung davon was man auf ihr zeichnen
		// möchte.
		// Der LayoutManager hat also keine Möglichkeit die passende Größe für
		// unser
		// Objekt festzustellen und würde von (0,0) ausgehen.
		// Daher helfen wir etwas nach und setzen die gewünschte Größe händisch
		paintingComponent.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		// unsere Komponente wird mittig im JFrame plaziert
		frame.add(paintingComponent, BorderLayout.CENTER);

		// in den unteren Bereich des Frames packen wir einige
		// Steuerelemente die wir der Übersicht wegen in einer
		// eigenen Methode erstellen und initialisieren
		frame.add(createControls(), BorderLayout.SOUTH);

		// der Frame enthält nun alle benötigten Komponenten
		// und kann nun seine minimale Größe berechnen
		frame.pack();
		frame.requestFocus();
		// und noch den Frame sichtbar machen und zentrieren
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	// public static BufferedImage getScreenShot(Component component) {
	//
	// BufferedImage image = new BufferedImage(component.getWidth(),
	// component.getHeight(), BufferedImage.TYPE_INT_RGB);
	// // call the Component's paint method, using
	// // the Graphics object of the image.
	// component.paint(image.getGraphics()); // alternately use .printAll(..)
	// return image;
	// }

	/**
	 * hier wird ein JPanel erzeugt auf das wir alle Steuerelemente legen
	 * 
	 * @return ein JPanel das alle Steuerelemente enthält
	 */
	private Component createControls() {
		// ein einfaches FlowLayout soll für unser Beispiel genügen
		final JPanel panel = new JPanel(new FlowLayout());

		// Ein Array mit den 3 Grundfarben wird erstellt und in
		// eine Combobox übergeben.
		// damit können wir später die Farbe der Zeichnung bestimmen
		String[] colors = { "Zufall", "Blau", "Rot" };

		final JComboBox lineColorBox = new JComboBox(colors);
		String[] positionArray = { "Zentral", "Zufall", "Feuerwerk" };
		final JComboBox positionBox = new JComboBox(positionArray);
		String[] colors2 = { "Schwarz", "Zufall", "Blau", "Rot" };
		final JComboBox backgroundColorBox = new JComboBox(colors2);

		panel.add(new JLabel("Farbe Linien :"));
		panel.add(lineColorBox);
		panel.add(new JLabel("Farbe Hintergrund :"));
		panel.add(backgroundColorBox);
		panel.add(new JLabel("Muster :"));
		panel.add(positionBox);

		// Als nächstes ein Array mit Shapes (Figuren).
		// Der Einfachheit halber setzen wir die Position und Größe
		// für alle Objekte fest.
		// Die toString Methode wird hier überschrieben damit die Auswahl
		// in der Combobox besser lesbar ist.

		final JTextField amount = new JTextField(10);

		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				amount.requestFocus();
			}
		});

		panel.add(new JLabel("Menge :"));
		panel.add(amount);

		// als letztes noch ein Button mit dem die gewählte Figur gezeichnet
		// wird
		JButton paintNow = new JButton("Zeichnen");
		panel.add(paintNow);
		paintNow.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {
					amountCount = Integer.parseInt(amount.getText().trim());
					if (amountCount > 0) {
						if (lineColorBox.getSelectedItem().equals("Blau")) {
							lineColor = 1;
						} else if (lineColorBox.getSelectedItem().equals("Rot")) {
							lineColor = 2;
						} else {
							lineColor = 0;
						}

						if (backgroundColorBox.getSelectedItem().equals(
								"Schwarz")) {
							backColor = 0;
						} else if (backgroundColorBox.getSelectedItem().equals(
								"Blau")) {
							backColor = 1;
						} else if (backgroundColorBox.getSelectedItem().equals(
								"Rot")) {
							backColor = 2;
						} else if (backgroundColorBox.getSelectedItem().equals(
								"Zufall")) {
							backColor = 3;
						}

						if (positionBox.getSelectedItem().equals("Zentral")) {
							position = 0;
						} else if (positionBox.getSelectedItem().equals(
								"Zufall")) {
							position = 1;
						} else if (positionBox.getSelectedItem().equals(
								"Feuerwerk")) {
							position = 2;
						}
						paintingComponent.drawNow(lineColor, backColor,
								position);
					}

				} catch (NumberFormatException the_input_string_isnt_an_integer) {
					// ask the user to try again

				}
			}
		});
		JButton saveButton = new JButton("Speichern");
		panel.add(saveButton);
		saveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("Save clicked");
				paintingComponent.saveImage();
				// savedImage = getScreenShot(frame);
				// savedImage = getImage(paintingComponent);
				/*
				 * try { Robot robot = new Robot();
				 * 
				 * // // Capture screen from the top left in 200 by 200 pixel //
				 * size. // BufferedImage bufferedImage = robot
				 * .createScreenCapture(new Rectangle(new Dimension( 1000,
				 * 500)));
				 * 
				 * // // The captured image will the writen into a file called
				 * // screenshot.png // File imageFile = new
				 * File("screenshot.png"); ImageIO.write(bufferedImage, "png",
				 * imageFile); } catch (AWTException e1) { e1.printStackTrace();
				 * } catch (IOException e2) { e2.printStackTrace(); }
				 */

				// try {
				// ImageIO.write(savedImage, "BMP", new File("filename.bmp"));
				// } catch (IOException e1) {
				// e1.printStackTrace();
				// }
				//
				// }
			}
		});

		JButton freeHandButton = new JButton("Freihand zeichnen");
		panel.add(freeHandButton);
		freeHandButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int lineCol = 0;
				int backCol = 0;
				if (lineColorBox.getSelectedItem().equals("Blau")) {
					lineColor = 1;
				} else if (lineColorBox.getSelectedItem().equals("Rot")) {
					lineColor = 2;
				} else {
					lineColor = 0;
				}

				if (backgroundColorBox.getSelectedItem().equals("Schwarz")) {
					backColor = 0;
				} else if (backgroundColorBox.getSelectedItem().equals("Blau")) {
					backColor = 1;
				} else if (backgroundColorBox.getSelectedItem().equals("Rot")) {
					backColor = 2;
				} else if (backgroundColorBox.getSelectedItem()
						.equals("Zufall")) {
					backColor = 3;
				}
				if (freeHandOn == false)freeHandOn = true;
				else freeHandOn = false;

				paintingComponent.drawFreeHand(lineCol, backCol, position);
				System.out.println("Drwan");
			}
		});
		return panel;
	}

	public void drawLine(int x, int y, Graphics g) {
		if (x >= 0 && y >= 0)
			g.drawLine(WIDTH / 2, HEIGHT / 2, x, y);
	}

	public BufferedImage getImage(Component c) {
		BufferedImage bi = null;
		try {
			bi = new BufferedImage(c.getWidth(), c.getHeight(),
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = bi.createGraphics();
			c.print(g2d);
			g2d.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bi;
	}

	public static void main(String[] args) {
		new RayCreator();
	}
}
