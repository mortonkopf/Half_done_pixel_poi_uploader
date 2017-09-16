package PoiSonic;

/*    */ import java.awt.event.ActionEvent;
/*    */ import javax.swing.AbstractAction;
/*    */ 
/*    */ public class RotateFlipAction extends AbstractAction
/*    */ {
/*    */   public static final int ROTATE90 = 1;
/*    */   public static final int ROTATE180 = 2;
/*    */   public static final int ROTATE270 = 3;
/*    */   public static final int FLIP_H = 4;
/*    */   public static final int FLIP_V = 5;
/*    */   private int action;
/*    */   private Main main;
/*    */ 
/*    */   public RotateFlipAction(int action, Main main)
/*    */   {
/* 15 */     this.action = action;
/* 16 */     this.main = main;
/*    */ 
/* 18 */     switch (action) { case 1:
/* 19 */       putValue("Name", "Rotate 90°"); break;
/*    */     case 2:
/* 20 */       putValue("Name", "Rotate 180°"); break;
/*    */     case 3:
/* 21 */       putValue("Name", "Rotate 270°"); break;
/*    */     case 4:
/* 22 */       putValue("Name", "Flip horizontally"); break;
/*    */     case 5:
/* 23 */       putValue("Name", "Flip vertically"); }
/*    */   }
/*    */ 
/*    */   public void actionPerformed(ActionEvent e)
/*    */   {
/* 28 */     Animation animation = this.main.getAnimation();
/*    */ 
/* 31 */     int newW = animation.getPixelsWide();
/* 32 */     int newH = animation.getPixelsHigh();
/*    */ 
/* 34 */     if ((this.action == 1) || (this.action == 3)) {
/* 35 */       int temp = newH;
/* 36 */       newH = newW;
/* 37 */       newW = temp;
/*    */     }
/*    */ 
/* 40 */     Animation flipped = new Animation(newW, newH, animation.getDataFormat().getFormatCode());
/*    */ 
/* 43 */     int frames = animation.getTotalFrames();
/* 44 */     int w = flipped.getPixelsWide();
/* 45 */     int h = flipped.getPixelsHigh();
/*    */ 
/* 48 */     for (int i = 0; i < frames; i++) {
/* 49 */       Animation.Frame frame = flipped.addFrame();
/* 50 */       Animation.Frame orgFrame = animation.getFrame(i);
/*    */ 
/* 52 */       for (int y = 0; y < h; y++) {
/* 53 */         for (int x = 0; x < w; x++)
/*    */         {
/*    */           int translated;
/* 55 */           switch (this.action) { case 1:
/* 56 */             translated = rotate90(w, h, x, y); break;
/*    */           case 2:
/* 57 */             translated = rotate180(w, h, x, y); break;
/*    */           case 3:
/* 58 */             translated = rotate270(w, h, x, y); break;
/*    */           case 4:
/* 59 */             translated = flipH(w, h, x, y); break;
/*    */           case 5:
/* 60 */             translated = flipV(w, h, x, y); break;
/*    */           default:
/* 61 */             throw new IllegalArgumentException("Unknown rotation or flip action");
/*    */           }
/*    */ 
/* 64 */           frame.setPixel(y * w + x, orgFrame.getPixel(translated));
/*    */         }
/*    */       }
/*    */     }
/* 68 */     flipped.deleteFrame(flipped.getFrame(0));
/*    */ 
/* 70 */     this.main.setAnimation(flipped);
/*    */   }
/*    */ 
/*    */   private int rotate90(int w, int h, int x, int y) {
/* 74 */     return (w - x - 1) * h + y;
/*    */   }
/*    */ 
/*    */   private int rotate180(int w, int h, int x, int y) {
/* 78 */     return (h - y - 1) * w + (w - x - 1);
/*    */   }
/*    */ 
/*    */   private int rotate270(int w, int h, int x, int y) {
/* 82 */     return x * h + (h - y - 1);
/*    */   }
/*    */ 
/*    */   private int flipH(int w, int h, int x, int y) {
/* 86 */     return y * w + (w - x - 1);
/*    */   }
/*    */ 
/*    */   private int flipV(int w, int h, int x, int y) {
/* 90 */     return (h - y - 1) * w + x;
/*    */   }
/*    */ }
