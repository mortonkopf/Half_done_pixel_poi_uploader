package PoiSonic;
/*
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
*/
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.JFileChooser;
import java.awt.image.PixelGrabber;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.imageio.*;
import javax.imageio.ImageIO;
import java.awt.image.WritableRaster;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;


 public class FramePixelEditor extends JComponent
   implements MouseListener
 {

 //     private AnimationEditor animationEditor;
     private Animation.Frame frame;
   private DataFormat dataFormat;
   private FrameToolbox frameToolbox; /* = null;*/
public SizeSelector sizeSelector;
private Animation animation;
public FramePixelEditor framePixelEditor;

   public FramePixelEditor() {
     setFocusable(true);
 
     addMouseListener(this);
   }
 
   public void setFrame(Animation.Frame frame) {
     this.frame = frame;
     repaint();
   }
    
   public Animation.Frame getFrame() {
     return this.frame;
   }
  
  public void setDataFormat(DataFormat dataFormat) {
     this.dataFormat = dataFormat;
   }
 
   public void setFrameToolbox(FrameToolbox toolbox) {
     this.frameToolbox = toolbox;
     }


/////////////////////new graphic////////////////////////////////

  public double nLeddDouble = 255;
  public int nLedsInteger =255;
  public double nCirdDouble = 255;//30
  public int nCirInteger = 255;//30
  public static int currentFrame; /////////static removed
  public static int[][][] image; ///////////static added //null added

  public static Image img = Toolkit.getDefaultToolkit().createImage("PoiSonicon.gif"); // null;
  public static Image m_image =  null;
  public int w;
  public int h;
  public static int[] pixels =null;
public static Image newImage = null;
public int[][][] imagexx;
public BufferedImage bImg;
public static Image smallImage;
public static Image transImage;
public int leds;
public int cirs;
     {
      this.image = new int[this.nCirInteger /*+ 1*/][this.nLedsInteger/* + 1*/][3];
  
    }

   
//////////////////////////////////////////////////////////////////////////////////////////////////
  boolean openPicture()
  {
    JFileChooser fc = new JFileChooser();

    String filename = null;
    MediaTracker mt = new MediaTracker(this);
    String[] filefilters = { "JPG", "GIF", "PNG" };
    fc.setFileFilter(new ExampleFileFilter(filefilters, "Supported Filetypes"));
    int fd = fc.showOpenDialog(this);
    if (fd == 0) {
      filename = fc.getSelectedFile().getPath();
      repaint();
      this.m_image = this.img;
    }
    else {
      return true;
    }System.out.println("Opening file: " + filename);
    this.smallImage = Toolkit.getDefaultToolkit().createImage(filename);//changed from this.img//changed from getImage
    
    this.leds = 30;
    this.cirs =150;
    this.img =smallImage.getScaledInstance(cirs,leds,Image.SCALE_SMOOTH);

     ///////////////////////////
    mt.addImage(this.img, 0);
    try
    {
      mt.waitForAll();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
///////////////////////////////
    this.newImage = Toolkit.getDefaultToolkit().createImage(filename);

    ///////////////////////////////////////////////////////

//line below changed to add scaled instnce.
    this.m_image = this.img;
    this.w = this.m_image.getWidth(this);
    this.h = this.m_image.getHeight(this);
    if (this.w > 0)
    {
      this.frameToolbox.thBar.addChangeListener(new thChange());
      this.frameToolbox.thBar.setEnabled(true);
    }
    else
    {
      this.frameToolbox.thBar.setEnabled(false);
    }
    this.pixels = new int[(this.w + 1) * (this.h + 1)];
    System.out.println(this.w);
    System.out.println(this.h);
    PixelGrabber pixelGrabber = new PixelGrabber(this.m_image, 0, 0, this.w, this.h, this.pixels, 0,this.w);
    try {
      pixelGrabber.grabPixels();
    } catch (InterruptedException e) {
      System.err.println("interrupted waiting for pixels!");
      System.exit(0);
    }

     return false;

  }

 ///////////////////////////////this section distributes the colours/////////////////
  public void updatePicture()
  {  
    int meanVal = (minVal(this.pixels) + maxVal(this.pixels)) / 2;
    System.out.println("Meanval: " + meanVal);

    for (int i = 0; i <this.frame.getPixelsWide()/*nCirInteger*/; i++)
    {
      for (int j = 0; j < this.frame.getPixelsHigh()/*nLedsInteger*/; j++)
      {
        this.image[i][j][this.currentFrame] =
//          ((this.pixels[rad2ind(i, j, this.w, this.h)] & 0xFF) / 64 |
//          (this.pixels[rad2ind(i, j, this.w, this.h)] >> 8 & 0xFF) / 32 << 2 |
//          (this.pixels[rad2ind(i, j, this.w, this.h)] >> 16 & 0xFF) / 32 << 5);
         ((this.pixels[j*w+i] & 0xFF) / 64 |
          (this.pixels[j*w+i] >> 8 & 0xFF) / 32 << 2 |
          (this.pixels[j*w+i] >> 16 & 0xFF) / 32 << 5);
   }
      }
 repaint();
  }

  public int minVal(int[] arr)
  {
    int tempVal = this.frame.getPixelsWide()/*nCirInteger*/ -1;
    for (int i = 0; i < arr.length; i++)
    {
      if (arr[i] >= tempVal) continue; tempVal = arr[i];
    }
    System.out.println("Minval: " + tempVal);

    return tempVal;
  }

  public int maxVal(int[] arr)
  {
    int tempVal = 0;
    for (int i = 0; i < arr.length; i++)
    {
      if (arr[i] <= tempVal) continue; tempVal = arr[i];
    }
    System.out.println("Maxval: " + tempVal);

    return tempVal;
  }

  public int rad2ind(int cir, int rad, int w, int h)
  {
    int centerx = w / 2;
    int centery = h / 2;
    int x = centerx + (int)(Math.cos(cir /this.frame.getPixelWideD()/*nCirdDouble*/ * 2.0D * 3.141592653589793D) * (0.1D * centerx + rad / this.frame.getPixelHighD()/*nLeddDouble*/ * (centerx * 0.9D)));
    int y = centery + (int)(Math.sin(cir /this.frame.getPixelWideD()/*nCirdDouble*/ * 2.0D * 3.141592653589793D) * (0.1D * centery + rad / this.frame.getPixelHighD()/*nLeddDouble*/ * (centery * 0.9D)));
    return y * w + x;

  }

//////////////////////////////////this section puts the pixels in two semicircles for painting locations///////////////////////////////////////
   public void paint(Graphics g)
   {
       super.paint(g);

       Graphics2D g2 = (Graphics2D)g;
       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       g2.setColor(Color.darkGray);
       g2.fillRect(0, 0, getWidth(), getHeight());
////////////////////the below four lines added for new grid of dots//////////
       int w = getWidth() / this.frame.getPixelsWide();
       int h = getHeight() / this.frame.getPixelsHigh();
       int s = w < h ? w : h;
       int space = s / 6;
       int dot2 =4;//
        int z =0;//gap between images in rotation
///////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////
        /*
    for (double ii = 0.0D; ii < this.frame.getPixelsHigh(); ii += 1.0D)
    {
      for (double jj = 0.0D; jj < this.frame.getPixelsWide(); jj += 1.0D)
      {
        g2.setColor(Color.black);

        //  new Color((this.image[(int)jj][(int)ii][this.currentFrame] >> 5 & 0x7) * 32,
        //  (this.image[(int)jj][(int)ii][this.currentFrame] >> 2 & 0x7) * 32,
        //  (this.image[(int)jj][(int)ii][this.currentFrame] << 1 & 0x6) * 32));
      double x = Math.cos(jj / this.frame.getPixelsWide() * 2.0D * 3.14D) * (4.0D * ii + 35.0D) + getWidth()/2;
       double y = Math.sin(jj / this.frame.getPixelsWide() * 2.0D * 3.14D) * (4.0D * ii + 35.0D) + getHeight()/2;

       g2.fillOval((int)x, (int)y, 4, 4);
        }
  */

      for (int i = 0; i < this.frame.getPixelsHigh(); i++)
          for (int j = 0; j < this.frame.getPixelsWide(); j++) {
           g2.setColor(
             new Color((this.image[(int)j][(int)i][this.currentFrame] >> 5 & 0x7) * 32,
          (this.image[(int)j][(int)i][this.currentFrame] >> 2 & 0x7) * 32,
          (this.image[(int)j][(int)i][this.currentFrame] << 1 & 0x6) * 32));

          double x =-Math.sin((double)j / (frame.getPixelsWide()+z) * 2.0D * 3.14D/4) * (/*5.0D**/ 4.3D* (double)i - 35.0D*5.5D) + getWidth()/2;
          double y =Math.cos((double)j / (frame.getPixelsWide()+z) * 2.0D * 3.14D/4) * (/*5.0D**/ 4.3D* (double)i - 35.0D*5.5D) + getHeight()/2;
          double a =Math.sin((double)j / (frame.getPixelsWide()+z) * 2.0D * 3.14D/4) * (/*5.0D **/ 4.3D*(double)i - 35.0D*5.5D) + getWidth()/2;
          double b =1-Math.cos((double)j / (frame.getPixelsWide()+z) * 2.0D * 3.14D/4) * (/*5.0D **/ 4.3D*(double)i - 35.0D*5.5D) + getHeight()/2;
          double c =1- Math.cos((double)j / (frame.getPixelsWide()+z) * 2.0D * 3.14D/4) * (/*5.0D **/ 4.3D*(double)i - 35.0D*5.5D) + getWidth()/2;
          double d =1-Math.sin((double)j / (frame.getPixelsWide()+z) * 2.0D * 3.14D/4) * (/*5.0D **/4.3D* (double)i - 35.0D*5.5D) + getHeight()/2;
          double e = Math.cos((double)j / (frame.getPixelsWide()+z) * 2.0D * 3.14D/4) * (/*5.0D **/4.3D* (double)i - 35.0D*5.5D) + getWidth()/2;
          double f = Math.sin((double)j / (frame.getPixelsWide()+z) * 2.0D * 3.14D/4) * (/*5.0D **/4.3D* (double)i - 35.0D*5.5D) + getHeight()/2;

    //      g2.fillOval(s * j + space, s * i + space, s - 2 * space, s - 2 * space);
            g2.fillOval((int)x,(int)y, dot2,dot2);
            g2.fillOval((int)a,(int)b, dot2,dot2);
            g2.fillOval((int)c,(int)d, dot2,dot2);
            g2.fillOval((int)e,(int)f, dot2,dot2);
  
    }
       repaint();
     }

/////////////////////////////////////////////////////////////////////////

  public class thChange
    implements ChangeListener
  {
    public thChange()
    {
    }

    public void stateChanged(ChangeEvent arg0)
    {
      FramePixelEditor.this.updatePicture();
      }
     }

 //////////////////////////////////////////////////////////////
   public void mousePressed(MouseEvent e)
   {
     requestFocusInWindow();
   }
 
   public void mouseReleased(MouseEvent e)
   {
   }
 
   public void mouseClicked(MouseEvent e)
  {
   }
 
   public void mouseEntered(MouseEvent e)
   {
   }
 
   public void mouseExited(MouseEvent e)
   {
   }



 }

