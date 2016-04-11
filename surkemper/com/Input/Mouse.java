package surkemper.com.Input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

	public static int mouseX = -1;
	public static int mouseY = -1;
	public static int mouseB = -1;
	private static boolean dragged = false;

	public void mouseDragged(MouseEvent arg0) {
		mouseX = arg0.getX();
		mouseY = arg0.getY();
		System.out.println(mouseX);
	}

	public void mouseMoved(MouseEvent arg0) {
		// mouseX = arg0.getX();
		// mouseY = arg0.getY();
		// System.out.println(mouseX);

	}
	
	public static boolean dragged(){
		return dragged;
	}

	public void mouseClicked(MouseEvent arg0) {
		// if (arg0.getButton() == 1){
		// mouseX = arg0.getX();
		// mouseY = arg0.getY();
		// }
		//
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		dragged = true;
	}

	public void mouseReleased(MouseEvent arg0) {
		dragged = false;
	}

}
