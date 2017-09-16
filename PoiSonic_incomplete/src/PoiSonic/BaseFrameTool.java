package PoiSonic;



import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Image;

public class BaseFrameTool
  implements FrameTool, MouseListener, MouseMotionListener
{
  protected static FramePixelEditor target;

  public void startTool(FramePixelEditor target)
  {
    this.target = target;
    target.addMouseListener(this);
    target.addMouseMotionListener(this);
   
  }

  public void stopTool(FramePixelEditor target) {
    this.target = target;
    target.removeMouseListener(this);
    target.removeMouseMotionListener(this);
  }
///////////////////////////////////////////////////////////////////
  public void drawOverlay(Graphics g) {

  }

 protected Point getCell(int x, int y, boolean limitAtBorders) {
    Animation.Frame frame = this.target.getFrame();
 
    int w = this.target.getWidth() / frame.getPixelsWide();
    int h = this.target.getHeight() / frame.getPixelsHigh();

    int s = w < h ? w : h;
// this means, s equal w if w less than h is true, otherwise s equals h

    int px = x / s;
    int py = y / s;

    if (limitAtBorders) {
      if (px < 0) px = 0;
      if (py < 0) py = 0;
      if (px >= frame.getPixelsWide()) px = frame.getPixelsWide() - 1;
      if (py >= frame.getPixelsHigh()) py = frame.getPixelsHigh() - 1;
    }

    return new Point(px, py);
 //  
  }

 /////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////
  public void mousePressed(MouseEvent e)
  {
  }

  public void mouseReleased(MouseEvent e)
  {
  }

  public void mouseDragged(MouseEvent e)
  {
  }

  public void mouseClicked(MouseEvent e)
  {
  }

  public void mouseEntered(MouseEvent e)
  {
  }

  public void mouseExited(MouseEvent e)
  {
  }

  public void mouseMoved(MouseEvent e)
  {
  }
}