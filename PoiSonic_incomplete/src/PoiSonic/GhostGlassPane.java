package PoiSonic;

/*    */ import java.awt.AlphaComposite;
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Point;
/*    */ import java.awt.image.BufferedImage;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public class GhostGlassPane extends JPanel
/*    */ {
/*    */   private AlphaComposite composite1;
/*    */   private AlphaComposite composite2;
/* 11 */   private BufferedImage dragged = null;
/* 12 */   private Point location = new Point(0, 0);
/* 13 */   private boolean showCopyIcon = false;
/*    */ 
/*    */   public GhostGlassPane()
/*    */   {
/* 17 */     setOpaque(false);
/* 18 */     this.composite1 = AlphaComposite.getInstance(3, 0.5F);
/* 19 */     this.composite2 = AlphaComposite.getInstance(3, 1.0F);
/*    */   }
/*    */ 
/*    */   public void setImage(BufferedImage dragged)
/*    */   {
/* 24 */     this.dragged = dragged;
/*    */   }
/*    */ 
/*    */   public void setCopyIcon(boolean show) {
/* 28 */     if (show != this.showCopyIcon) {
/* 29 */       this.showCopyIcon = show;
/* 30 */       repaint();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setPoint(Point location)
/*    */   {
/* 36 */     this.location = location;
/*    */   }
/*    */ 
   public void paintComponent(Graphics g)
   {
     if (this.dragged == null) {
       return;
     }
     int imageX = (int)(this.location.getX() - this.dragged.getWidth(this) / 2);
     int imageY = (int)(this.location.getY() - this.dragged.getHeight(this) / 2);
 
     Graphics2D g2 = (Graphics2D)g;
     g2.setComposite(this.composite1);
     g2.drawImage(this.dragged, imageX, imageY, null);

  if (this.showCopyIcon) {
    g2.setComposite(this.composite2);
    g2.setColor(Color.WHITE);
    int copyBoxLeft = imageX + this.dragged.getWidth(this) - 10;
    int copyBoxTop = imageY + this.dragged.getHeight(this) - 10;
    g2.fillRect(copyBoxLeft, copyBoxTop, 10, 10);
    g2.setColor(Color.BLACK);
   g2.drawRect(copyBoxLeft, copyBoxTop, 10, 10);
    g2.drawLine(copyBoxLeft + 5, copyBoxTop + 2, copyBoxLeft + 5, copyBoxTop + 8);
    g2.drawLine(copyBoxLeft + 2, copyBoxTop + 5, copyBoxLeft + 8, copyBoxTop + 5);
  }
   }
}
