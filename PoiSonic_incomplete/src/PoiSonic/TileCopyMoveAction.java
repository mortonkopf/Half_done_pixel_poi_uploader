package PoiSonic;

/*    */ import java.awt.event.ActionEvent;
/*    */ import javax.swing.AbstractAction;
/*    */ 
/*    */ public class TileCopyMoveAction extends AbstractAction
/*    */ {
/*    */   private Animation animation;
/*    */   private Animation.Frame frame;
/*    */   private int newIndex;
/*    */   private boolean copy;
/*    */ 
/*    */   public TileCopyMoveAction(boolean copy)
/*    */   {
/* 11 */     putValue("Name", copy ? "Copy" : "Move");
/* 12 */     this.copy = copy;
/*    */   }
/*    */ 
/*    */   public void setFrame(Animation.Frame frame) {
/* 16 */     this.frame = frame;
/* 17 */     this.animation = frame.getAnimation();
/*    */   }
/*    */ 
/*    */   public void setNewIndex(int newIndex) {
/* 21 */     this.newIndex = newIndex;
/*    */   }
/*    */ 
/*    */   public void actionPerformed(ActionEvent e) {
/* 25 */     if (this.copy) {
/* 26 */       int index = this.animation.copyFrame(this.frame, this.newIndex);
/* 27 */       this.animation.gotoFrame(index);
/*    */     } else {
/* 29 */       this.animation.setFrameNr(this.frame, this.newIndex);
/*    */     }
/*    */   }
/*    */ }
