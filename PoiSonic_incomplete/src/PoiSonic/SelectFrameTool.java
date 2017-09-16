package PoiSonic;

//jj
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.Image;

public class SelectFrameTool extends BaseFrameTool
{
  private Point startCell;
  private Point endCell;
 


  public void startTool(FramePixelEditor target)
  {
    super.startTool(target);
    target.setCursor(new Cursor(1));
  }

  public void stopTool(FramePixelEditor target) {
    super.stopTool(target);
    target.setCursor(new Cursor(0));
  }

  public void drawOverlay(Graphics g, int dx, int dy) {
    super.drawOverlay(g);

    if (this.startCell != null)
    {
      Animation.Frame frame = this.target.getFrame();
      int w = this.target.getWidth() / frame.getPixelsWide();
      int h = this.target.getHeight() / frame.getPixelsHigh();


       int s = w < h ? w : h;

      Graphics2D g2 = (Graphics2D)g;
  
      Stroke stroke = new BasicStroke(1.0F, 0, 2, 0.0F, new float[] { 8.0F, 8.0F }, 0.0F);

      g2.setStroke(stroke);

      g.setColor(Color.WHITE);
      int minX = s * (Math.min(this.startCell.x, this.endCell.x) + dx);
      int maxX = s * (Math.max(this.startCell.x, this.endCell.x) + dx + 1) - 1;
      int minY = s * (Math.min(this.startCell.y, this.endCell.y) + dy);
      int maxY = s * (Math.max(this.startCell.y, this.endCell.y) + dy + 1) - 1;

      g.drawLine(minX, minY, maxX, minY);
      g.drawLine(maxX, minY, maxX, maxY);
      g.drawLine(minX, maxY, maxX, maxY);
      g.drawLine(minX, minY, minX, maxY);

   //   g.drawImage(image, 0,0,140, 140, null);
    }

     }

  public void offsetSelection(int dx, int dy) {
    this.startCell.translate(dx, dy);
    this.endCell.translate(dx, dy);
    restraintToFrame();
    this.target.repaint();
  }

  private void restraintToFrame() {
    restraintToFrame(this.startCell);
    restraintToFrame(this.endCell);
  }

  private void restraintToFrame(Point p) {
    Animation.Frame frame = this.target.getFrame();
    int w = frame.getPixelsWide();
    int h = frame.getPixelsHigh();

    if (p.x < 0) p.x = 0;
    if (p.x >= w) p.x = (w - 1);
    if (p.y < 0) p.y = 0;
    if (p.y >= h) p.y = (h - 1);
  }

  public Rectangle getSelection()
  {
    if (this.startCell == null) return null;

    int minX = Math.min(this.startCell.x, this.endCell.x);
    int maxX = Math.max(this.startCell.x, this.endCell.x) + 1;
    int minY = Math.min(this.startCell.y, this.endCell.y);
    int maxY = Math.max(this.startCell.y, this.endCell.y) + 1;
    return new Rectangle(minX, minY, maxX - minX, maxY - minY);
  }

  public void mousePressed(MouseEvent e)
  {
    if (e.getClickCount() >= 2) {
      this.startCell = null;
      this.endCell = null;
    } else {
      this.startCell = getCell(e.getX(), e.getY(), true);
      this.endCell = this.startCell;
    }
    this.target.repaint();
  }

  public void mouseDragged(MouseEvent e) {
    if (this.startCell != null) {
      this.endCell = getCell(e.getX(), e.getY(), true);
    }
    this.target.repaint();
  }

  public void mouseReleased(MouseEvent e) {
    this.target.repaint();
  }

  ////////////////////////////////////////////////////////
 

  ///////////////////////////////////////////////////////
}