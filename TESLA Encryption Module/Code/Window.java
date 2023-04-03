/* @Project: Project Hermes */
/* @File: Window.java */
/* @Author: SuperPEKKA336 */
/* @Version: 0.1.1 Pre-alpha */
/* @PatchNotes:  */
/* @Updated: 04/03/2023 */

/* ================================================== */

/* Imports */
import java.awt.Dimension;

class Window
{
  public static void frameSetup()
  {
    Variables.mainFrame.setSize(Variables.screenLength / 3, Variables.screenWidth / 3);
    Variables.mainFrame.setLocation(Variables.screenLength / 3, Variables.screenWidth / 3);
    Variables.mainFrame.setMinimumSize(new Dimension(Variables.screenLength / 3, Variables.screenWidth /3));
    Variables.mainFrame.setMaximumSize(Variables.SCREEN);

    Variables.mainCanvas.setSize(Variables.mainFrame.getWidth(), Variables.mainFrame.getHeight());

    Variables.mainFrame.setIconImage(Variables.TESLA_LOGO.getImage());
    
    Variables.mainFrame.add(Variables.mainCanvas);
    Variables.mainFrame.pack();

    Variables.mainFrame.createImage(100, 100);

    Variables.mainFrame.setVisible(true);
  }
}

/* ================================================== */