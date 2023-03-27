import java.awt.Canvas;
import java.awt.Graphics;

class Drawing extends Canvas
{
    public void paint(Graphics g)
    {
        g.drawRect(0, 0, TESLA_Encryption.screenLength, TESLA_Encryption.screenWidth);
    }
}