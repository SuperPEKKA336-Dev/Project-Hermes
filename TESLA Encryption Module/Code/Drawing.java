/* @Project: Project Hermes */
/* @File: Drawing.java */
/* @Author: SuperPEKKA336 */
/* @Version: 0.1.1 Pre-alpha */
/* @PatchNotes:  */
/* @Updated: 04/03/2023 */

/* ================================================== */

/* Imports */
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;

class Drawing extends Canvas
{
  public void paint(Graphics g)
  {
    try
    {
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, Variables.mainFrame.getWidth(), Variables.mainFrame.getHeight());

      Font blackOpsOne = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/BlackOpsOne.ttf"));

      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(blackOpsOne.deriveFont(40f));
      g.setFont(blackOpsOne.deriveFont(40f));

      int length = (int)g.getFontMetrics().getStringBounds("T.E.S.L.A. Encryption Module", g).getWidth();

      g.setColor(Color.CYAN);
      g.drawString("T.E.S.L.A. Encryption Module", (Variables.screenLength / 3 - length) / 2, 50);
    }
    catch(Exception e)
    {
      
    }
  }
}

/* ================================================== */