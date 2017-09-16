package PoiSonic;

/*    */ import java.util.ArrayList;
import javax.swing.Timer;
/*    */ 
/*    */ public class AnimationPlayer
/*    */   implements Runnable
/*    */ {
/*    */   private Animation animation;
/*  5 */   private boolean running = false;
/*    */   private boolean interrupted;
/*    */   private boolean repeat;
/*    */   private ArrayList<PlayerListener> listeners;
public FrameOrderEditor frameOrderEditor;
public Timer timer;
/*    */ 
/*    */   public AnimationPlayer()
/*    */   {
/* 12 */     this.listeners = new ArrayList();
/*    */   }
/*    */ 
/*    */   public void addListener(PlayerListener listener) {
/* 16 */     this.listeners.add(listener);
/*    */   }
/*    */ 
/*    */   public void removeListener(PlayerListener listener) {
/* 20 */     this.listeners.remove(listener);
/*    */   }
/*    */ 
/*    */   protected void started() {
/* 24 */     for (PlayerListener listener : this.listeners)
/* 25 */       listener.started();
/*    */   }
/*    */ 
/*    */   protected void stopped()
/*    */   {
/* 30 */     for (PlayerListener listener : this.listeners)
/* 31 */       listener.stopped();
/*    */   }
/*    */ 
/*    */   public void play(Animation animation)
/*    */   {
/* 36 */     play(animation, false);
/*    */   }
/*    */ 
/*    */   public void play(Animation animation, boolean repeat) {
/* 40 */     this.animation = animation;
/* 41 */     this.repeat = repeat;
/* 42 */     this.interrupted = false;
/* 43 */     this.running = false;
/*    */ 
/* 45 */     Thread thread = new Thread(this);
/* 46 */     thread.start();
/*    */   }
/*    */ 
/*    */   public boolean isPlaying() {
/* 50 */     return this.running;
/*    */   }
/*    */ 
/*    */   public void stop() {
/* 54 */     this.interrupted = true;
/*    */   }
/*    */ 
/*    */   public void run() {
/* 58 */     this.running = true;
/* 59 */     started();
/*    */ //   timer.start();
/* 61 */     int frameCount = this.animation.getTotalFrames();
/*    */     try
/*    */     {
/* 65 */       while (!this.interrupted) {
/* 66 */         for (int i = 0; (i < frameCount) && (!this.interrupted); i++) {
/* 67 */           Animation.Frame frame = this.animation.getFrame(i);
/* 68 */           this.animation.gotoFrame(i);
///////////////////////////////////////////////////////
        
/* 69 */           Thread.sleep(frame.getTime()*1000);
/*    */         }
/* 71 */         if (this.repeat) continue;
/*    */       }
/*    */     }
/*    */     catch (InterruptedException e) {
/*    */     }
/* 76 */     this.running = false;
/* 77 */     stopped();
           // timer.stop();
/*    */   }
/*    */ }
