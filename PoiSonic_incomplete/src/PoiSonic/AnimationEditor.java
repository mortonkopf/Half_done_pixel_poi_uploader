package PoiSonic;

/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*     */ import java.util.Hashtable;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.KeyStroke;
//////////////////////////
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JLabel;
//import java.util.Timer;
 import java.util.TimerTask;
 import java.util.Date;
 import java.text.SimpleDateFormat;
 import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;


/*     */ public class AnimationEditor extends JPanel
/*     */   implements PlayerListener
/*     */ {
/*     */   public static final int ACTION_FRAME_ADD = 1;
/*     */   public static final int ACTION_FRAME_INSERT = 2;
/*     */   public static final int ACTION_FRAME_DELETE = 3;
/*     */   public static final int ACTION_FRAME_NEXT = 4;
/*     */   public static final int ACTION_FRAME_PREV = 5;
/*     */   public static final int ACTION_FRAME_COPY = 6;
/*     */   public static final int ACTION_FRAME_MOVE = 7;
/*     */   public static final int ACTION_ANIMATION_PLAY = 8;
/*     */   public static final int ACTION_ANIMATION_ROTATE90 = 9;
/*     */   public static final int ACTION_ANIMATION_ROTATE180 = 10;
/*     */   public static final int ACTION_ANIMATION_ROTATE270 = 11;
/*     */   public static final int ACTION_ANIMATION_FLIP_H = 12;
/*     */   public static final int ACTION_ANIMATION_FLIP_V = 13;

////////////////////////////////////
     //       public static final int ACTION_ANIMATION_UPDATER = 15;
//////////////////////////////////////
/*     */   private Main main;
/*     *///private final JScrollPane orderScrollPane;
            private AnimationToolbox animationToolbox;
/*     */   private FrameOrderEditor frameOrderEditor;
/*     */   private Animation animation;
//private Animation.Frame frame;
/*     */   private AnimationPlayer animationPlayer;
  private AnimationEditor animationEditor;
/*     */   private Hashtable<Integer, AbstractAction> actions;
public WaveformPanelContainer wfpc;
//            private FrameToolbox frameToolbox;
//            private FramePixelEditor framePixelEditor;
//private SelectFrameTool selectFrameTool;
public JScrollPane orderScrollPane;//changed from private//
private JPanel jpanel;
public int showMins = 5;
public double minM;
public int ST;
      int a=0,b=50,c=0,d=0;
///////////////////////////////////
public Animation.Frame frame;


      ///////////////////////////

   public AnimationEditor(Main main)
   {
    super(new BorderLayout());
     this.main = main;
 
     this.animationPlayer = new AnimationPlayer();
     this.animationPlayer.addListener(this);
 
     this.actions = new Hashtable();
     createActions(main);
 
     this.animationToolbox = new AnimationToolbox(this);
     add(this.animationToolbox, "South");

     this.frameOrderEditor = new FrameOrderEditor(this, (GhostGlassPane)main.getGlassPane());
    /*JScrollPane*/ orderScrollPane = new JScrollPane(this.frameOrderEditor, 21, 32);
     this.frameOrderEditor.setScrollPane(orderScrollPane);

     this.showMins = showMins;
////////////////////////////////////////////////////////////////////






////////////////////////////////////////////////////////////////////////
     ////for the audio position indicator
     jpanel = new JPanel(){

      public void paintComponent(Graphics g) {
        super.paintComponent(g);


        Rectangle r = orderScrollPane.getBounds();

        minM = r.width/showMins;

        for (int i = 0; i < r.width; i += minM) {
          g.drawLine(r.x + i, 0, r.x + i, 6);
                   }
        g.setColor(Color.red);
        g.drawLine(a,b,c,d);
        g.drawLine(a+1,b,c+1,d);
    }
};

jpanel.setBackground(Color.LIGHT_GRAY);
	   JLabel label = new JLabel("Audio Channel ");
           label.setPreferredSize(new Dimension(100,40));
           label.setBackground(Color.red);
           jpanel.setBorder(BorderFactory.createLineBorder(Color.black));
	   jpanel.add(label);

//////////////////////////////////////////////////////////////////
JLabel columnheader = new JLabel() {

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Rectangle r = orderScrollPane.getBounds();//g.getClipBounds();
        minM = r.width/showMins;
        int mark = (int) minM;
        int mark2 = (int) (minM * 0.20);
        int mark3 =(int) (minM * 0.40);
        int mark4 =(int) (minM * 0.60);
        int mark5 = (int) (minM * 0.80);

        for (int i = 0; i < r.width; i += minM) {
          g.drawLine(r.x + i, 0, r.x + i, 6);//min marker
         g.drawLine(r.x+i+mark,0,r.x+i+mark,5);
        // g.drawLine(r.x+i+(mark/2),0,r.x+i+(mark/2),6);//30 sec marker
          g.drawLine(r.x+i+mark2,0,r.x+i+mark2,5);//20 sec marker
           g.drawLine(r.x+i+mark3,0,r.x+i+mark3,5);//40 sec marker
           g.drawLine(r.x+i+mark4,0,r.x+i+mark4,5);//60 sec marker
           g.drawLine(r.x+i+mark5,0,r.x+i+mark5,5);//80 sec marker
           g.drawString("" + (r.x + i/mark)+" m", r.x + i - 8, 15);
        }
      }
      public Dimension getPreferredSize() {
        return new Dimension((int) 25,//this.orderScrollPane.getWidth(),
            25);
              }
          };

    columnheader.setOpaque(true);

  //  orderScrollPane.setColumnHeaderView(columnheader);
    orderScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    orderScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


 // add(jpanel,"North");
  add(orderScrollPane);
  
      }
/////////////////////////////////////////////////////////////////////////

/*     */ 
/*     */   protected AbstractAction getAction(int actionType) {
/*  51 */     return (AbstractAction)this.actions.get(Integer.valueOf(actionType));
/*     */   }
/*     */ 
/*     */   private void createActions(Main main) {
/*  55 */     JRootPane rootPane = main.getRootPane();
/*  56 */     InputMap im = rootPane.getInputMap(2);
/*  57 */     ActionMap am = rootPane.getActionMap();
/*     */

/*  60 */     AbstractAction actionAdd = new AbstractAction("Add") {

/*     */       public void actionPerformed(ActionEvent e) {
/*  62 */         AnimationEditor.this.addFrame();
//////////////////////////////////////////////////////////////////////////
      
                frameOrderEditor.revalidate();
/*     */       }
/*     */     };
/*  65 */     actionAdd.putValue("ShortDescription", "Add an image to the end of the sequence");
/*  66 */     this.actions.put(Integer.valueOf(1), actionAdd);
/*     */

//////////////////////////////////////////////
     AbstractAction actionInsert = new AbstractAction("Insert") {
       public void actionPerformed(ActionEvent e) {
         AnimationEditor.this.insertFrame(AnimationEditor.this.animation.getCurrentFrameNr());
       }
     };
     actionInsert.putValue("ShortDescription", "Insert an image before the current");
     Object insertMapKey = actionInsert.getValue("Name");
     im.put(KeyStroke.getKeyStroke(155, 0), insertMapKey);
     am.put(insertMapKey, actionInsert);
     this.actions.put(Integer.valueOf(2), actionInsert);
/*     */ 
/*  81 */     AbstractAction actionDelete = new AbstractAction("Delete") {
/*     */       public void actionPerformed(ActionEvent e) {
/*  83 */         AnimationEditor.this.deleteFrame(AnimationEditor.this.animation.getCurrentFrame());
/*     */       }
/*     */     };
/*  86 */     actionDelete.putValue("ShortDescription", "Delete the selected image");
/*  87 */     Object deleteMapKey = actionDelete.getValue("Name");
/*  88 */     im.put(KeyStroke.getKeyStroke(127, 0), deleteMapKey);
/*  89 */     am.put(deleteMapKey, actionDelete);
/*  90 */     this.actions.put(Integer.valueOf(3), actionDelete);
/*     */ 
/*  93 */     AbstractAction actionNextFrame = new AbstractAction("Next image") {
/*     */       public void actionPerformed(ActionEvent e) {
/*  95 */         AnimationEditor.this.animation.nextFrame();
/*     */       }
/*     */     };
/*  98 */     Object nextMapKey = actionNextFrame.getValue("Name");
/*  99 */     im.put(KeyStroke.getKeyStroke(39, 512), nextMapKey);
/* 100 */     am.put(nextMapKey, actionNextFrame);
/* 101 */     this.actions.put(Integer.valueOf(4), actionNextFrame);
/*     */ 
/* 104 */     AbstractAction actionPrevFrame = new AbstractAction("Prev image") {
/*     */       public void actionPerformed(ActionEvent e) {
/* 106 */         AnimationEditor.this.animation.prevFrame();
/*     */       }
/*     */     };
/* 109 */     Object prevMapKey = actionPrevFrame.getValue("Name");
/* 110 */     im.put(KeyStroke.getKeyStroke(37, 512), prevMapKey);
/* 111 */     am.put(prevMapKey, actionPrevFrame);
/* 112 */     this.actions.put(Integer.valueOf(5), actionPrevFrame);
/*     */ 
/* 114 */     AbstractAction copyAction = new TileCopyMoveAction(true);
/* 115 */     this.actions.put(Integer.valueOf(6), copyAction);
/*     */ 
/* 117 */     AbstractAction moveAction = new TileCopyMoveAction(false);
/* 118 */     this.actions.put(Integer.valueOf(7), moveAction);
/*     */ 
/*     AbstractAction actionPlay = new AbstractAction("Play/Stop") {
       public void actionPerformed(ActionEvent e) {
         if (AnimationEditor.this.animationPlayer.isPlaying()){
           AnimationEditor.this.animationPlayer.stop();timer1.stop();}
         else{
           AnimationEditor.this.animationPlayer.play(AnimationEditor.this.animation, true);
                    timer1.start();
       }
 
    }};
     actionPlay.putValue("ShortDescription", "Play or stop the sequence");
     Object playMapKey = actionPlay.getValue("Name");
     im.put(KeyStroke.getKeyStroke(10, 512), playMapKey);
     am.put(playMapKey, actionPlay);
     this.actions.put(Integer.valueOf(8), actionPlay);
 */
 //////////////////////////////////
    }

/*     */   public void setAnimation(Animation animation) {
/* 153 */     this.animation = animation;
/* 154 */     this.animationToolbox.setAnimation(animation);
/* 155 */     this.frameOrderEditor.setAnimation(animation);
/*     */   }
/*     */ 
/*     */   public void addFrame() {
/* 159 */     Animation.Frame frame = this.animation.addFrame();
/* 160 */     this.animation.gotoFrame(frame);
/*     */   }
/*     */ 
/*     */   public void insertFrame(int insertBefore) {
/* 164 */     Animation.Frame frame = this.animation.addFrame(insertBefore);
/* 165 */     this.animation.gotoFrame(frame);
/* 166 */     this.frameOrderEditor.focusFrame(frame);
/*     */   }
/*     */ 
/*     */   public void deleteFrame(Animation.Frame frame) {
/* 170 */     if (this.animation.getTotalFrames() > 1)
/* 171 */       this.animation.deleteFrame(frame);
/*     */     else
/* 173 */       JOptionPane.showMessageDialog(this, "There needs to be at least one image.", "PoiSonic", 2);
/*     */   }
/*     */ 
/*     */   private void setActionsEnabled(boolean enabled)
/*     */   {
/* 179 */     getAction(1).setEnabled(enabled);
/* 180 */     getAction(2).setEnabled(enabled);
/* 181 */     getAction(3).setEnabled(enabled);
/* 182 */     getAction(4).setEnabled(enabled);
/* 183 */     getAction(5).setEnabled(enabled);
/* 184 */     getAction(6).setEnabled(enabled);
/* 185 */     getAction(7).setEnabled(enabled);
/*     */   }
/*     */ 
/*     */   public void started()
/*     */   {
/* 191 */     setActionsEnabled(false);
/*     */   }
/*     */ 
/*     */   public void stopped() {
/* 195 */     setActionsEnabled(true);
/*     */   }

////////////////////////////////////////////////////////////////////////////////////////////

 

/////////////////////////////////////////////////////////////////////////////////////////////
 
/*     */ }


