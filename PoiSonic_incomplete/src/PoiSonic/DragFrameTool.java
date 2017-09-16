package PoiSonic;

/*    */ import java.awt.Cursor;
/*    */ import java.awt.Point;
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.event.MouseEvent;
/*    */ 
/*    */ public class DragFrameTool extends BaseFrameTool
/*    */ {
/*    */   private SelectFrameTool selectFrameTool;
/*    */   int[] originalPixels;
/*    */   Point downPoint;
/*    */   Point dragPoint;
/*    */ 
/*    */   public DragFrameTool(SelectFrameTool selectFrameTool)
/*    */   {
/*  8 */     this.selectFrameTool = selectFrameTool;
/*    */   }
/*    */ 
/*    */   public void startTool(FramePixelEditor target) {
/* 12 */     super.startTool(target);
/* 13 */     target.setCursor(new Cursor(13));
/*    */   }
/*    */ 
/*    */   public void stopTool(FramePixelEditor target) {
/* 17 */     super.stopTool(target);
/* 18 */     target.setCursor(new Cursor(0));
/*    */   }
/*    */ 
/*    */   public void mousePressed(MouseEvent e)
/*    */   {
/* 25 */     Animation.Frame frame = this.target.getFrame();
/* 26 */     if (e.getButton() == 1) {
/* 27 */       this.downPoint = getCell(e.getX(), e.getY(), true);
/* 28 */       this.originalPixels = frame.getPixels();
/*    */     }
/*    */   }
/*    */ 
/*    */   public int getDx() {
/* 33 */     if ((this.downPoint != null) && (this.dragPoint != null))
/* 34 */       return this.dragPoint.x - this.downPoint.x;
/* 35 */     return 0;
/*    */   }
/*    */ 
/*    */   public int getDy() {
/* 39 */     if ((this.downPoint != null) && (this.dragPoint != null))
/* 40 */       return this.dragPoint.y - this.downPoint.y;
/* 41 */     return 0;
/*    */   }
/*    */ 
/*    */   public void mouseDragged(MouseEvent e) {
/* 45 */     Animation.Frame frame = this.target.getFrame();
/* 46 */     int pixelsW = frame.getPixelsWide();
/* 47 */     int pixelsH = frame.getPixelsHigh();
/*    */ 
/* 49 */     this.dragPoint = getCell(e.getX(), e.getY(), false);
/* 50 */     int dx = this.dragPoint.x - this.downPoint.x;
/* 51 */     int dy = this.dragPoint.y - this.downPoint.y;
/*    */ 
/* 53 */     Rectangle selection = this.selectFrameTool.getSelection();
/* 54 */     if (selection == null) selection = new Rectangle(0, 0, pixelsW, pixelsH);
/*    */ 
/* 56 */     int[] newPixels = new int[this.originalPixels.length];
/* 57 */     int startX = Math.max(0, selection.x + dx);
/* 58 */     int startY = Math.max(0, selection.y + dy);
/* 59 */     int maxX = Math.min(pixelsW, selection.x + selection.width + dx);
/* 60 */     int maxY = Math.min(pixelsH, selection.y + selection.height + dy);
/*    */ 
/* 62 */     if (selection != null) {
/* 63 */       newPixels = (int[])(int[])this.originalPixels.clone();
/* 64 */       for (int y = selection.y; y < selection.y + selection.height; y++) {
/* 65 */         for (int x = selection.x; x < selection.x + selection.width; x++) {
/* 66 */           newPixels[(y * pixelsW + x)] = 0;
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 71 */     for (int y = startY; y < maxY; y++) {
/* 72 */       for (int x = startX; x < maxX; x++) {
/* 73 */         newPixels[(y * pixelsW + x)] = this.originalPixels[((y - dy) * pixelsW + x - dx)];
/*    */       }
/*    */     }
/*    */ 
/* 77 */     frame.setPixels(newPixels);
/* 78 */     this.target.repaint();
/*    */   }
/*    */ 
/*    */   public void mouseReleased(MouseEvent e) {
/* 82 */     if ((this.selectFrameTool.getSelection() != null) && (this.downPoint != null) && (this.dragPoint != null)) {
/* 83 */       this.selectFrameTool.offsetSelection(this.dragPoint.x - this.downPoint.x, this.dragPoint.y - this.downPoint.y);
/*    */     }
/*    */ 
/* 86 */     this.downPoint = null;
/* 87 */     this.dragPoint = null;
/*    */   }
/*    */ }

