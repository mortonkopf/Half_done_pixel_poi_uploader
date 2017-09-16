package PoiSonic;

/*    */ import java.awt.Point;
/*    */ import java.awt.event.MouseEvent;
import javax.swing.JButton;
/*    */ 
/*    */ public class BrushFrameTool extends BaseFrameTool
/*    */   implements PaletteSelectorListener
/*    */ {
/*    */   private Point downCell;
/*  8 */   private int primaryColor = 0;
/*  9 */   private int secondaryColor = 0;
/*    */ 
/*    */   public BrushFrameTool(PaletteSelector paletteSelector) {
/* 12 */     paletteSelector.addPaletteSelectorListener(this);
/* 13 */     this.primaryColor = paletteSelector.getPrimaryColor();
/* 14 */     this.secondaryColor = paletteSelector.getSecondaryColor();
/*    */   }

    BrushFrameTool(JButton btnBrush) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
/*    */ 
/*    */   protected Point getCell(int x, int y) {
/* 18 */     Animation.Frame frame = this.target.getFrame();
/* 19 */     int w = this.target.getWidth() / frame.getPixelsWide();
/* 20 */     int h = this.target.getHeight() / frame.getPixelsHigh();
/*    */ 
/* 22 */     int s = w < h ? w : h;
/* 23 */     int space = s / 12;
/*    */ 
/* 25 */     if ((x < s * frame.getPixelsWide()) && (y < s * frame.getPixelsHigh())) {
/* 26 */       int distToXBorder1 = x - x / s * s;
/* 27 */       int distToXBorder2 = w - distToXBorder1;
/* 28 */       int distToYBorder1 = y - y / s * s;
/* 29 */       int distToYBorder2 = h - distToYBorder1;
/* 30 */       if ((distToXBorder1 > space) && (distToXBorder2 > space) && (distToYBorder1 > space) && (distToYBorder2 > space))
/*    */       {
/* 32 */         return new Point(x / s, y / s);
/*    */       }
/*    */     }
/* 35 */     return null;
/*    */   }
/*    */ 
/*    */   public void mousePressed(MouseEvent e) {
/* 39 */     if ((e.getButton() == 1) || (e.getButton() == 3))
/* 40 */       this.downCell = getCell(e.getX(), e.getY());
/*    */   }
/*    */ 
/*    */   public void mouseReleased(MouseEvent e)
/*    */   {
/* 45 */     Animation.Frame frame = this.target.getFrame();
/* 46 */     Point upCell = getCell(e.getX(), e.getY());
/* 47 */     if ((upCell != null) && (this.downCell != null) && 
/* 48 */       (upCell.getX() == this.downCell.getX()) && (upCell.getY() == this.downCell.getY())) {
/* 49 */       int i = (int)(upCell.getX() + upCell.getY() * frame.getPixelsWide());
/*    */ 
/* 51 */       if (e.getButton() == 1) {
/* 52 */         frame.setPixel(i, this.primaryColor);
/* 53 */         this.target.repaint();
/* 54 */       } else if (e.getButton() == 3) {
/* 55 */         frame.setPixel(i, this.secondaryColor);
/* 56 */         this.target.repaint();
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void mouseDragged(MouseEvent e)
/*    */   {
/* 63 */     Animation.Frame frame = this.target.getFrame();
/* 64 */     Point dragCell = getCell(e.getX(), e.getY());
/*    */ 
/* 66 */     if (dragCell != null) {
/* 67 */       int i = (int)(dragCell.getX() + dragCell.getY() * frame.getPixelsWide());
/*    */ 
/* 69 */       if ((e.getModifiersEx() & 0x400) == 1024) {
/* 70 */         frame.setPixel(i, this.primaryColor);
/* 71 */         this.target.repaint();
/* 72 */       } else if ((e.getModifiersEx() & 0x1000) == 4096) {
/* 73 */         frame.setPixel(i, this.secondaryColor);
/* 74 */         this.target.repaint();
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void colorChanged(PaletteSelectorEvent e) {
/* 80 */     switch (e.getChangedColor()) { case 1:
/* 81 */       this.primaryColor = e.getColorIndex(); break;
/*    */     case 2:
/* 82 */       this.secondaryColor = e.getColorIndex();
/*    */     }
/*    */   }
/*    */ }

