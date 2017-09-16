package PoiSonic;

import java.awt.*;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.SwingUtilities;
//import javax.swing.JTextField;
import javax.swing.JLabel;

 public class FrameOrderEditor extends JPanel
   implements AnimationListener
 {
 //  private FramePixelEditor framePixelEditor;
   public static final int TILE_SPACING = 0;//2;
   private AnimationEditor animationEditor;
   private Animation animation;
   private GhostGlassPane glassPane;
   private JScrollPane scrollPane;
   public int thumbWidth= 80;
   public int thumbHeight = 140;
 //  private JTextField txtFrameTime;
 //  private FrameToolbox frameToolbox;
 // private Animation.Frame frame;
  public Component selectedComponent;
  public int selectedFrame;
public JLabel imageLabel =null;
BaseFrameTool baseFrameTool;
public Font a;
public int minM;
public Integer wide;
public int wd;
public int grad;
     
     public FrameOrderEditor(AnimationEditor animationEditor, GhostGlassPane glassPane) {
       super(new FlowLayout(0, 0,2));//2, 2));
       this.animationEditor = animationEditor;
         this.glassPane = glassPane;
         this.minM = (int) animationEditor.minM;
         

     }
    public void setScrollPane(JScrollPane scrollPane) {
     this.scrollPane = scrollPane;
      }
 
    public void setAnimation(Animation newAnimation) {
    if (this.animation != null) this.animation.removeAnimationListener(this);
    this.animation = newAnimation;
    this.animation.addAnimationListener(this);

     removeAll();
     int frames = this.animation.getTotalFrames();
     for (int i = 0; i < frames; i++) {
       add(new FrameOrderEditor.FrameTile(this.animation.getFrame(i)));
     }
     }
 
   public void focusFrame(Animation.Frame frame) {
     getTileForFrame(frame).requestFocusInWindow();
   }

 ////////////set the frame border and colour for selected frame/////////////////////////////////// 
   public void paint(Graphics g) {
     super.paint(g);

     int selectedFrame = this.animation.getCurrentFrameNr();
     Component selectedComponent = getComponent(selectedFrame);
     //////////////////////////////////////////

     //////////////////////////////////////////

     g.setColor(Color.RED);
     g.drawRect(selectedComponent.getX() - 2, selectedComponent.getY() - 2, selectedComponent.getWidth() + 3, selectedComponent.getHeight() + 3);
  //   g.drawRect(selectedComponent.getX() - 1, selectedComponent.getY() - 1, selectedComponent.getWidth() + 1, selectedComponent.getHeight() + 1);
//g.drawLine(selectedComponent.getX() - 2, selectedComponent.getY() - 2, selectedComponent.getX() - 2, selectedComponent.getHeight() + 2);
g.drawLine(selectedComponent.getX() - 1, selectedComponent.getY() - 2, selectedComponent.getX() - 1, selectedComponent.getHeight() + 2);
g.drawLine(selectedComponent.getX() -3, selectedComponent.getY() - 2, selectedComponent.getX() - 3, selectedComponent.getHeight() + 2);
revalidate();
repaint();
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*     */ 
/*     */   private void scrollTo(Animation.Frame frame) {
/*  66 */     FrameOrderEditor.FrameTile tile = getTileForFrame(frame);
/*  67 */     Rectangle bounds = tile.getBounds();
/*  68 */     bounds.grow(4, 4);
/*  69 */     scrollRectToVisible(bounds);
/*     */   }
/*     */ 
/*     */   private void draggingTile(FrameOrderEditor.FrameTile tile, Point dragPoint) {
/*  73 */     SwingUtilities.convertPointToScreen(dragPoint, tile);
/*  74 */     SwingUtilities.convertPointFromScreen(dragPoint, this);
/*  75 */     scrollRectToVisible(new Rectangle((int)(dragPoint.getX() - tile.getWidth() / 2), (int)(dragPoint.getY() - tile.getHeight() / 2), tile.getWidth(), tile.getHeight()));
/*     */   }
/*     */ 
/*     */   private void updateOrder(FrameOrderEditor.FrameTile tile, MouseEvent e) {
/*  79 */     Point releasePoint = e.getPoint();
/*  80 */     SwingUtilities.convertPointToScreen(releasePoint, tile);
/*  81 */     SwingUtilities.convertPointFromScreen(releasePoint, this);
/*     */ 
/*  83 */     int newIndex = (int)((releasePoint.getX() - 2.0D) / (tile.getWidth() + 2) + 0.5D);
/*  84 */     if (newIndex > this.animation.getTotalFrames()) newIndex = this.animation.getTotalFrames();
/*     */ 
/*  86 */     TileCopyMoveAction copyAction = (TileCopyMoveAction)this.animationEditor.getAction(6);
/*  87 */     TileCopyMoveAction moveAction = (TileCopyMoveAction)this.animationEditor.getAction(7);
/*     */ 
/*  89 */     copyAction.setFrame(tile.frame);
/*  90 */     copyAction.setNewIndex(newIndex);
/*     */ 
/*  92 */     moveAction.setFrame(tile.frame);
/*  93 */     moveAction.setNewIndex(newIndex);
/*     */ 
/*  95 */     if (e.getButton() == 3) {
/*  96 */       JPopupMenu contextMenuMove = new JPopupMenu("Tile");
/*  97 */       contextMenuMove.add(copyAction);
/*  98 */       contextMenuMove.add(moveAction);
/*  99 */       contextMenuMove.show(this, releasePoint.x, releasePoint.y);
/*     */     }
/* 101 */     else if (e.getButton() == 1) {
/* 102 */       if ((e.getModifiers() & 0x2) == 2)
/* 103 */         copyAction.actionPerformed(null);
/*     */       else
/* 105 */         moveAction.actionPerformed(null);
/*     */     }
/*     */   }
/*     */ ///////////////////////////////////////////////////////////

/*     */   private /*final*/ FrameOrderEditor.FrameTile getTileForFrame(Animation.Frame frame)//final inserted
/*     */   {
/* 111 */     int components = getComponentCount();
/*     */ 
/* 113 */     for (int i = 0; i < components; i++) {
/* 114 */       Component component = getComponent(i);
/* 115 */       if (((component instanceof FrameOrderEditor.FrameTile)) && (((FrameOrderEditor.FrameTile)component).frame == frame)) {
/* 116 */         return (FrameOrderEditor.FrameTile)component;
/*     */       }
/*     */     }
/* 119 */     throw new IllegalArgumentException("Unknown frame");
/*     */   }
/*     */ 
/*     */   public void frameAdded(Animation.Frame newFrame, int newFrameNr, int totalFrames)
/*     */   {
/* 126 */     add(new FrameOrderEditor.FrameTile(newFrame), newFrameNr);
/* 127 */  
/*     */   }
/*     */ 
/*     */   public void frameSelected(Animation.Frame newFrame) {
/* 131 */
              repaint();
/* 132 */     scrollTo(newFrame);
/*     */   }
/*     */ 
/*     */   public void frameRemoved(Animation.Frame removedFrame, int totalFrames) {
/* 136 */     remove(getTileForFrame(removedFrame));
/* 137 */     getTileForFrame(this.animation.getCurrentFrame()).requestFocusInWindow();
/* 138 */    
/*     */   }
/*     */ 
/*     */   public void frameDataChanged(Animation.Frame newFrame) {
/* 142 */     repaint();
/*     */   }
/*     */ 
/*     */   public void frameMoved(Animation.Frame frame, int newFrameNr) {
/* 146 */     FrameOrderEditor.FrameTile tile = getTileForFrame(frame);
/* 147 */     setComponentZOrder(tile, newFrameNr);
/* 148 */     scrollTo(frame);
/*     */   }
 ///////
  /*final*/ class FrameTile extends JComponent /*final inserted*/
/*     */     implements MouseListener, MouseMotionListener, KeyListener
/*     */   {
/*     */     Animation.Frame frame;
              BaseFrameTool baseFrameTool;
              FrameToolbox frameToolbox;
/* 155 */     private boolean dragging = false;
             

/////////////////////////////////////////////////////////////////////////////////////
         public FrameTile(Animation.Frame frame) {
/* 1///////58 */       this.frame = frame;
                     //  if (wide == null){thumbWidth = frame.time*7/2;}
                     //  else{
                     //   thumbWidth = 4*wide*grad/60/2;
                     //       }
                 //       thumbWidth = 90;
//////////////////////////////////////////////////////////////////////////////////////////
/* 159 */       setPreferredSize(new Dimension(/*thumbWidth*/FrameOrderEditor.this.thumbWidth,
                 FrameOrderEditor.this.thumbHeight
                 ));

             
/* 161 */       setFocusable(true);
/* 163 */       addMouseListener(this);
/* 164 */       addMouseMotionListener(this);
/* 165 */       addKeyListener(this);

                revalidate();
                 }

   public int[][][]privatearray = null;

  public void paint(Graphics g) {
      super.paint(g);
     int x = FrameOrderEditor.this.animationEditor.showMins;
     Rectangle r = FrameOrderEditor.this.animationEditor.orderScrollPane.getBounds();
     grad = r.width/x;
     wide = this.frame.getTime()/1000;
     int wd =wide*grad/60;

      g.setColor(Color.DARK_GRAY);
       
       Graphics2D g2 = (Graphics2D)g;
       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

       if(wide ==null){ g2.fillRect(0, 0, getWidth(), getHeight());
       } else { g2.fillRect(0, 0, wd, getHeight());   }
     
       repaint();
       g.setColor(Color.black);
       g.drawLine(0, 0, 0, getHeight());

        g.drawImage(this.frame.image,0,0,40,40,null);
        //below if for text of pattern style
        a = new Font("TimesRoman",Font.BOLD,12);
        g.setFont(a );
        g.setColor(Color.red);

     if(this.frame.patternImage ==null){
           g.drawString("no pattern",10,100);
       } else {
          g.drawImage(this.frame.patternImage,0,110,30,30,null );
       }
        revalidate();
        repaint();
        g.dispose();
    }

/*     */     private void updateGlassPaneImage(boolean copy) {
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e) {
/* 196 */       requestFocusInWindow();
/*     */ 
/* 198 */       if ((e.getButton() == 1) || (e.getButton() == 3))
/* 199 */         FrameOrderEditor.this.animation.gotoFrame(this.frame);
/*     */     }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e)
/*     */     {
/* 204 */       if (!this.dragging) {
/* 205 */         this.dragging = true;
/*     */ 
/* 207 */         BufferedImage image = new BufferedImage(getWidth(), getHeight(), 2);
/* 208 */         Graphics g = image.getGraphics();
/* 209 */         paint(g);
       /////////////////////////////////////////////////

    //////////////////////////////////////////////////////
/*     */ 
/* 211 */         FrameOrderEditor.this.glassPane.setImage(image);
/* 212 */         FrameOrderEditor.this.glassPane.repaint();
/*     */ 
/* 214 */         Point p = (Point)e.getPoint().clone();
/* 215 */         SwingUtilities.convertPointToScreen(p, this);
/* 216 */         SwingUtilities.convertPointFromScreen(p, FrameOrderEditor.this.glassPane);
/*     */ 
/* 218 */         FrameOrderEditor.this.glassPane.setPoint(p);
/* 219 */         FrameOrderEditor.this.glassPane.setCopyIcon((e.getModifiers() & 0x2) == 2);
/* 220 */         FrameOrderEditor.this.glassPane.setVisible(true);
/*     */       }
/*     */ 
/* 223 */       FrameOrderEditor.this.draggingTile(this, e.getPoint());
/*     */ 
/* 225 */       Point p = (Point)e.getPoint().clone();
/* 226 */       SwingUtilities.convertPointToScreen(p, this);
/* 227 */       SwingUtilities.convertPointFromScreen(p, FrameOrderEditor.this.glassPane);
/*     */ 
/* 229 */       FrameOrderEditor.this.glassPane.setPoint(p);
/* 230 */       FrameOrderEditor.this.glassPane.repaint();
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e) {
/* 234 */       if (this.dragging) {
/* 235 */         this.dragging = false;
/* 236 */         FrameOrderEditor.this.glassPane.setVisible(false);
/* 237 */         FrameOrderEditor.this.updateOrder(this, e);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void keyPressed(KeyEvent e) {
/* 242 */       if ((this.dragging) && (e.getKeyCode() == 17))
/* 243 */         FrameOrderEditor.this.glassPane.setCopyIcon(true);
/*     */     }
/*     */ 
/*     */     public void keyReleased(KeyEvent e)
/*     */     {
/* 248 */       if ((this.dragging) && (e.getKeyCode() == 17))
/* 249 */         FrameOrderEditor.this.glassPane.setCopyIcon(false);
/*     */     }
/*     */ 
/*     */     public void keyTyped(KeyEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseMoved(MouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseClicked(MouseEvent e)
/*     */     {
/*     */     }

        public void actionPerformed(ActionEvent e) {
          
        }
   }
 }

