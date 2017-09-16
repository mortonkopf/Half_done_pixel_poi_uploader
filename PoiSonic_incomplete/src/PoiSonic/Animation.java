package PoiSonic;

//import java.util.ArrayList;
//////////////////////
import javax.swing.ImageIcon;
import java.awt.Image;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Component;
//import java.awt.*;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
//import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.lang.Object;
//import java.awt.Color;
import java.util.*;
//import javax.swing.JTextField;
 ////////////////////////
 public class Animation
 {
  private ArrayList<AnimationListener> listeners;
   protected ArrayList<Animation.Frame> frames;
   protected Animation.Frame currentFrame;
   private boolean unsavedChanges = false;
   private DataFormat dataFormat;
   private int pixelsWide;
   private int pixelsHigh;
   private BaseFrameTool baseFrameTool;
//private FrameToolbox frameToolbox;


   public Animation(int pixelsWide, int pixelsHigh, int dataFormat)
   {
     this.pixelsWide = pixelsWide;
     this.pixelsHigh = pixelsHigh;
     this.dataFormat = DataFormat.newFormatByCode(dataFormat);
     this.listeners = new ArrayList();
     this.frames = new ArrayList();
     addFrame();
     this.currentFrame = ((Animation.Frame)this.frames.get(0));
     this.unsavedChanges = false;


   }

   public int getPixelsWide() {
     return this.pixelsWide;
   }
   ////////////////////////////////////
   public double getPixelsWideD() {
       return this.pixelsWide;
   }
   //////////////////////////////
 
   public int getPixelsHigh() {
     return this.pixelsHigh;
   }
   ////////////////////////////////
   public double getPixelsHighD() {
       return this.pixelsHigh;
   }
   /////////////////////////////////


 
   public int getPixelsTotal() {
     return this.pixelsWide * this.pixelsHigh;
   }
 
   public DataFormat getDataFormat() {
    return this.dataFormat;
  }
 
   public void addAnimationListener(AnimationListener listener) {
     this.listeners.add(listener);
   }
 
   public void removeAnimationListener(AnimationListener listener) {
     this.listeners.remove(listener);
   }
 
   protected void frameAdded(Animation.Frame newFrame, int newFrameNr, int totalFrames) {
     for (AnimationListener listener : this.listeners)
       listener.frameAdded(newFrame, newFrameNr, totalFrames);
   }
 
   protected void frameRemoved(Animation.Frame removedFrame, int totalFrames)
   {
     for (AnimationListener listener : this.listeners)
       listener.frameRemoved(removedFrame, totalFrames);
   }
 
   protected void frameSelected(Animation.Frame newFrame)
   {
     for (AnimationListener listener : this.listeners)
       listener.frameSelected(newFrame);
   }
 
   protected void frameDataChanged(Animation.Frame newFrame)
   {
     for (AnimationListener listener : this.listeners)
       listener.frameDataChanged(newFrame);
   }
 
   protected void frameMoved(Animation.Frame frame, int newFrameNr)
   {
     for (AnimationListener listener : this.listeners)
       listener.frameMoved(frame, newFrameNr);
   }
 
   public Animation.Frame addFrame()
   {
     return addFrame(this.frames.size());
   }
 
   public Animation.Frame addFrame(int index) {
     this.unsavedChanges = true;
     Animation.Frame newFrame = new Animation.Frame();
     this.frames.add(index, newFrame);
     frameAdded(newFrame, index, this.frames.size());
     return newFrame;
   }
 
   public void deleteFrame(Animation.Frame frame) {
     this.unsavedChanges = true;
     int index = this.frames.indexOf(frame);
 
     int currentFrameNr = getCurrentFrameNr();
 
     this.frames.remove(frame);
 
     if (currentFrameNr >= this.frames.size()) {
       currentFrameNr = this.frames.size() - 1;
     }
     gotoFrame(currentFrameNr);
     frameRemoved(frame, this.frames.size());
   }
 
   public int getCurrentFrameNr() {
     return getFrameNr(this.currentFrame);
   }
 
   public void gotoFrame(int frameNr) {
     gotoFrame((Animation.Frame)this.frames.get(frameNr));
   }
 
   public void gotoFrame(Animation.Frame frame) {
     if (frame != this.currentFrame) {
       this.currentFrame = frame;
       frameSelected(this.currentFrame);
     }
   }

 public Animation.Frame getFrame(int i)
/*     */   {
/* 122 */     return (Animation.Frame)this.frames.get(i);
/*     */   }
/*     */ 
/*     */   public int getFrameNr(Animation.Frame frame) {
/* 126 */     return this.frames.indexOf(frame);
/*     */   }
/*     */ 
/*     */   public int getTotalFrames() {
/* 130 */     return this.frames.size();
/*     */   }
/*     */ 
/*     */   public Animation.Frame getCurrentFrame() {
/* 134 */     return this.currentFrame;
/*     */   }
/*     */ 
/*     */   public void setFrameNr(Animation.Frame frame, int newNr) {
/* 138 */     int oldNr = this.frames.indexOf(frame);
/*     */ 
/* 140 */     Animation.Frame selectedFrame = getCurrentFrame();
/*     */ 
/* 142 */     if (oldNr < newNr - 1) {
/* 143 */       this.frames.remove(oldNr);
/* 144 */       this.frames.add(newNr - 1, frame);
/* 145 */       frameMoved(frame, this.frames.indexOf(frame));
/* 146 */     } else if (oldNr > newNr) {
/* 147 */       this.frames.add(newNr, frame);
/* 148 */       this.frames.remove(oldNr + 1);
/* 149 */       frameMoved(frame, this.frames.indexOf(frame));
/*     */     }
/*     */ 
/* 152 */     this.currentFrame = selectedFrame;
/* 153 */     this.unsavedChanges = true;
/*     */   }
/*     */ 
/*     */   public int copyFrame(Animation.Frame frame, int copyFrameNr) {

         Animation.Frame copy = new Animation.Frame(frame);
/* 158 */     this.frames.add(copyFrameNr, copy);
/* 159 */    frameAdded(copy, copyFrameNr, this.frames.size());
/* 160 */    this.unsavedChanges = true;
/* 161 */    return this.frames.indexOf(copy);
/*     */   }
/*     */ 
/*     */   public boolean hasUnsavedChanges() {
/* 165 */     return this.unsavedChanges;
/*     */   }
/*     */ 
/*     */   public void resetUnsavedChanges() {
/* 169 */     this.unsavedChanges = false;
/*     */   }
/*     */ 
/*     */   public void nextFrame() {
/* 173 */     int newFrameNr = (getCurrentFrameNr() + 1) % this.frames.size();
/* 174 */     gotoFrame(newFrameNr);
/*     */   }
/*     */ 
/*     */   public void prevFrame() {
/* 178 */     int newFrameNr = (getCurrentFrameNr() - 1 + this.frames.size()) % this.frames.size();
/* 179 */     gotoFrame(newFrameNr);
/*     */   }


   
   public class Frame implements ActionListener
   {
     public /*private*/ int time;
 //    private int rtime;
     public int[]pixels;
     public Image image = null;
     public int currentFrame;
    public int [][][] array2;
     public int[][][] theArray = null; //changed from private
    public String patternStr = null;
    public Image patternImage = null;
    FrameToolbox frameToolbox;
public int number;
    Integer wide;
    int showtime;
    Integer showT;
    int wd;
public Frame() {
        this(Preferences.getInstance().get("frame.defaultdelay",10000));
           }

    public Frame(int time){

      this.time = time;
      this.pixels = baseFrameTool.target.pixels;
      this.image = baseFrameTool.target.newImage;
      this.currentFrame =this.getcurrentFrame();
      this.array2 = this.copyOf3Dim(array2);
      this.patternStr = this.getpatternStr();
  //    this.rtime = this.getRTime();
      this.patternImage =  this.getpatternImage();
      this.wide = time;
      this.showtime = this.getshowTime();
    
    }

    ////////////////////////////////////////////////////////////////
 
public int[][][] copyOf3Dim(int[][][] array) {
    array = baseFrameTool.target.image;
    int[][][] copy; 
    copy = new int[array.length][][]; 
    for (int i = 0; i < array.length; i++) { 
        copy[i] = new int[array[i].length][]; 
        for (int j = 0; j < array[i].length; j++) { 
            copy[i][j] = new int[array[i][j].length]; 
            System.arraycopy(array[i][j], 0, copy[i][j], 0,  
                array[i][j].length); 
        } 
    } 
    return copy; 
}
/*
public int[] copyOfArray(int[] pixelcopy){
    pixelcopy =baseFrameTool.target.pixels;
    int[] copy1;
    copy1 = new int[pixelcopy.length];
   // for(int i=0; i<pixelcopy.length;i++){
    System.arraycopy(pixelcopy,0, copy1, 0, pixelcopy.length);
   // }
    return copy1;
    }
*/


      public void setcurrentFrame(int currentFrame) {
         this.currentFrame = currentFrame;
     }

     public int getcurrentFrame(){
         return this.currentFrame;
     }

//////////////////////////////////////////////////////////////
    public Frame(Frame org) {
       this.time = org.time;
    //   this.rtime = org.rtime;
       this.pixels = ((int[])(int[])org.pixels.clone());
    this.currentFrame = org.currentFrame;
    this.patternStr = org.patternStr;
    this.patternImage = org.patternImage;
  this.wide = org.time;
  this.showtime = org.showtime;
    }
 
    
     public int getPixelsWide() {
       return Animation.this.pixelsWide;
     }
     
    //this makes a double for the led number and the slice number
     public double getPixelWideD() {
         return Animation.this.pixelsWide;
     }

     public double getPixelHighD() {
         return Animation.this.pixelsHigh;
     }
 
     public int getPixelsHigh() {
       return Animation.this.pixelsHigh;
     }
 
     public int getPixelsTotal() {
       return Animation.this.pixelsWide * Animation.this.pixelsHigh;
     }
 
     public void setPixels(int[] pixels) {
      this.pixels = pixels;
       Animation.this.frameDataChanged(this);
     }
 
     public int[] getPixels() {
       return this.pixels;
     }
      public void setPixel(int i, int pixel) {
       if (this.pixels[i] != pixel) {
         unsavedChanges = true;
         this.pixels[i] = pixel;
         Animation.this.frameDataChanged(this);
       }
     }
      public int getPixel(int i) {
       return this.pixels[i];
     }

        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

     public void setTime(int time) {
       this.time = time;
     }
     public int getTime() {
       return this.time;
     }

   
     public void setshowTime (int showtime) {
         this.showtime = showtime;
     }
     
     public int getshowTime(){
         return this.showtime;
     }

 
     public Animation getAnimation() {
       return Animation.this;
     }

     //for pattern string
       public String getpatternStr(){
         return this.patternStr;
     }
     
     public void setpatternStr(String patternStr){
    this.patternStr = patternStr;
}

public Image getpatternImage(){
    return this.patternImage;
}
public void setpatternImage(Image patternImage){
    this.patternImage = patternImage;
}

   }
 
}
