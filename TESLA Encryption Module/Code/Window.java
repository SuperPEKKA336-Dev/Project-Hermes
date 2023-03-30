/* @Project: Project Hermes */
/* @File: Window.java */
/* @Author: SuperPEKKA336 */
/* @Version: 0.1.1 Pre-alpha */
/* @PatchNotes:  */
/* @Updated: 03/30/2023 */

/* ================================================== */

/* Imports */
import java.awt.Dimension;

public class Window
{
  private static void frameSetup()
  {
    Variables.mainFrame.setSize(Variables.screenLength / 3, Variables.screenWidth / 3);
    Variables.mainFrame.setLocation(Variables.screenLength / 3, Variables.screenWidth / 3);
    Variables.mainFrame.setMinimumSize(new Dimension(Variables.screenLength / 3, Variables.screenWidth /3));
    Variables.mainFrame.setMaximumSize(Variables.SCREEN);

    Variables.mainCanvas.setSize(Variables.mainFrame.getWidth(), Variables.mainFrame.getHeight());
    
    Variables.mainFrame.add(Variables.mainCanvas);
    Variables.mainFrame.pack();

    Variables.mainFrame.setVisible(true);
  }
}

/* ================================================== */