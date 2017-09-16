package PoiSonic;

/*    */ import java.awt.FlowLayout;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public class AnimationToolbox extends JPanel
/*    */   implements AnimationListener
/*    */ {
    private FrameOrderEditor frameOrderEditor;
/*    */   private AnimationEditor animationEditor;
/*    */   private JLabel lblFrames;
/*    */   private Animation animation;
/*    */ 
/*    */   public AnimationToolbox(AnimationEditor animationEditor)
/*    */   {
/* 12 */     super(new FlowLayout(0));
/*    */ 
/* 14 */   this.animationEditor = animationEditor;
/*    */


/* 16 */     add(new JButton(animationEditor.getAction(1)));
/* 17 */     add(new JButton(animationEditor.getAction(2)));
/* 18 */     add(new JButton(animationEditor.getAction(3)));
/* 19 */     add(new JButton(animationEditor.getAction(8)));


/* 21 */     this.lblFrames = new JLabel("Image: 1/1");
/* 22 */     this.lblFrames.setSize(100, 23);
/* 23 */     add(this.lblFrames);
/*    */   }
/*    */ 
/*    */   public void setAnimation(Animation newAnimation) {
/* 27 */     if (this.animation != null) this.animation.removeAnimationListener(this);
/* 28 */     this.animation = newAnimation;
/* 29 */     this.animation.addAnimationListener(this);
/*    */ 
/* 31 */     updateAnimationInfo();
/*    */   }
/*    */ 
/*    */   private void updateAnimationInfo() {
/* 35 */     this.lblFrames.setText("Image: " + (this.animation.getCurrentFrameNr() + 1) + "/" + this.animation.getTotalFrames());
/*    */   }
/*    */ 
/*    */   public void frameAdded(Animation.Frame newFrame, int newFrameNr, int totalFrames)
/*    */   {
/* 41 */     updateAnimationInfo();
/*    */   }
/*    */ 
/*    */   public void frameSelected(Animation.Frame newFrame) {
/* 45 */     updateAnimationInfo();
/*    */   }
/*    */ 
/*    */   public void frameRemoved(Animation.Frame removedFrame, int totalFrames) {
/* 49 */     updateAnimationInfo();
/*    */   }
/*    */ 
/*    */   public void frameDataChanged(Animation.Frame newFrame)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void frameMoved(Animation.Frame frame, int newFrameNr)
/*    */   {
/*    */   }
/*    */ }

