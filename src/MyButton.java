/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.*;

/**
 *
 * @author nishant
 */
public class MyButton extends JButton {
    
    private int[] coOrd = new int[2];
    private boolean clicked = false;
    public MyButton(int x, int y) {
        coOrd[0] = x;
        coOrd[1] = y;
    }
    void Click() {
        clicked = true;
    }
    void unClick() {
        clicked = false;
    }
    boolean checkClick() {
        return clicked;
    }
    int[] getCoords() {
        return coOrd;
    }
}
