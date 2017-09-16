package PoiSonic;


import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.net.URL;
import javax.swing.*;
 import java.awt.*;
 import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FrameToolbox extends JPanel
  implements AnimationListener, PaletteSelectorListener
{
//    private FrameOrderEditor.FrameTile frameTile;
  private CoreJavaSound coreJavaSound;
  private WaveformDisplaySimulator wfds;
 public FrameOrderEditor.FrameTile frameTile;
  public AnimationEditor animationEditor;
  /*private*/ public FrameOrderEditor frameOrderEditor;
  private SizeSelector sizeSelector;
  private PaletteSelector paletteSelector;
  public FramePixelEditor framePixelEditor;
  public JFormattedTextField txtFrameTime;
  private JTextField numberLEDS;
  private JTextField numberSLICES;
  public JSlider startBar;
  public JSlider thBar;
  private JButton importImage;
  private JButton updatebutton;
  private JButton clearImage;
public Animation animation;
  private Animation.Frame frame;
  private BrushFrameTool brushFrameTool;
  private DragFrameTool dragFrameTool;
  private SelectFrameTool selectFrameTool;
  private FrameTool currentTool = null;
  public JTextField ShowTime;
  private JLabel cF;
    public int nCirInteger = 255;
   public int nLedsInteger = 32;
    public double nCirdDouble = 255;
    public double nLeddDouble = 32;
    public int currentFrame;
    public int[][][] image;
public String sliceStr;
public String ledStr;
public JComboBox combo;
public JTextField txt;
public JLabel picLabel;
public LedCodes ledCodes;
public JLabel picture;
public String str;
public String patternStr;
public static Image patternImage;
public ImageIcon icon;
public Integer amount;
public AnimationEditor panel;
public Integer showtime;
public static JCheckBox select;

  public FrameToolbox(FramePixelEditor framePixelEditor) {
    
      this.framePixelEditor = framePixelEditor;
    framePixelEditor.setFrameToolbox(this);

        TitledBorder title1;
        title1 = (BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Control Panel"));
        setBorder(title1);

    add(new JLabel ("Duration of image"));
    add(new JLabel("Time(millisec):"));

    this.txtFrameTime = new JFormattedTextField(40);
    this.txtFrameTime.setColumns(5);
    this.txtFrameTime.addActionListener(new FrameToolbox.FrameTimeTextListener(this));
//    this.txtFrameTime.addActionListener(new FrameToolbox.FOEvalidate(this));
    add(this.txtFrameTime);



////////////////////////////////////////////////////////////////////////////
    //add (new Label ("Hooky poi select"));
    add(new JLabel  ("                     "));
    select = new JCheckBox("Hooky poi select");
    add(select);//add lines in to saver file .... if select = 1, then swap rgb order

 //   add (new JLabel ("_____________________"));
  //  add(new JLabel  ("                     "));
/*
    add(new JLabel ("Set show timeline     "));
   add(new JLabel("Time (secs)"));

    this.ShowTime = new JTextField("300", 5);
    this.ShowTime.addActionListener(new FrameToolbox.ShowTimeTextListener (this));
   add(this.ShowTime);
*/
     add (new JLabel ("________________________"));
   //  add(new JLabel ("                     "));
    this.cF = new JLabel("Start position: 0");
   // add(cF);
 //   add (new JSlider());
    this.startBar = new JSlider();
    this.startBar.setMaximum(255);
    this.startBar.setMinimum(0);
    this.startBar.setValue(0);
    this.startBar.addChangeListener(new FrameToolbox.startBarCl(this));
//add (startBar);
 //////////////////////////////////////////////////////////////////////////////////////////////////
String[] patternStrings = //ledCodes.petStrings;
{
     
     "Select_a_Pattern",//item 0
     "Diamond_Pattern",
     "Red_Chevron",
     "Count_In",//item 1
     "Rainbow",//item 2
     "Squares",
     "Checker",//item 3
     "Random_Colour_Dots",//item 4
     "Spiral",
     "Blue_flower",
     "Spokes",
     "Atomic",
     "Chaser",
     "Atom_Mover",
     "White_Petal",
     "White_Pattern",
     "//dawn patterns below",
     "Glass",
     "Raj2",
     "Raj",
     "Loading",
     "Pulsate",
     "Segment",
     "Spot_line",
     "Box_line",
     "Spiral_70s"

        };

 /////////////////////////////////////////////////////////////////////////////////////////////////////
   combo = new JComboBox(patternStrings);
   combo.setBackground(Color.gray);
   combo.setForeground(Color.red);
   combo.setSelectedIndex(0);
  this.combo.addActionListener(new FrameToolbox.picUpdater(this));
  this.combo.addActionListener(new FrameToolbox.patternUpdater(this));
 
   this.txt = new JTextField(10);
   this.combo.setPreferredSize(new Dimension (120,25));
   add(txt);
   add(combo);
   combo.addItemListener(new ItemListener(){
   public void itemStateChanged(ItemEvent ie){
    str = (String)combo.getSelectedItem();
   txt.setText(str);
    }    }
   );

      //  add(new JLabel ("                   "));
        picture = new JLabel();
        picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateLabel(patternStrings[combo.getSelectedIndex()]);
        picture.setPreferredSize(new Dimension(100,100));
        updateImage(patternStrings[combo.getSelectedIndex()]);
        add(picture);

////////////////////////////////////////////////////////////////////////////////////////////////////
     add (new JLabel ("________________________"));
    this.importImage = new JButton("Select Image");
   this.importImage.addActionListener(new FrameToolbox.openAL(this));
   add(this.importImage);

 //       add (new JLabel ("________________________"));
    this.importImage = new JButton("Select Music");
   this.importImage.addActionListener(new FrameToolbox.openSound(this));
 //  add(this.importImage);

    this.thBar = new JSlider();
    JLabel threshold = new JLabel("Threshold");
//   threshold.setBounds(20, 105, 120, 15);
//    panel.add(threshold);

//////////////////////////////////////////////////////////////////////////////////////////////
    this.paletteSelector = new PaletteSelector();
    this.paletteSelector.addPaletteSelectorListener(this);
 //   add(this.paletteSelector);
    this.brushFrameTool = new BrushFrameTool(this.paletteSelector);

    this.selectFrameTool = new SelectFrameTool();
    this.dragFrameTool = new DragFrameTool(this.selectFrameTool);

  }




  public void setAnimation(Animation newAnimation) {
    if (this.animation != null) this.animation.removeAnimationListener(this);
    this.animation = newAnimation;
    this.animation.addAnimationListener(this);
    this.frame = this.animation.getCurrentFrame();
    this.framePixelEditor.setFrame(this.frame);
    this.paletteSelector.setDataFormat(this.animation.getDataFormat());
    this.framePixelEditor.setDataFormat(this.animation.getDataFormat());
    this.txtFrameTime.setText(Integer.toString(this.frame.getTime()));
    ///////////below line added for rotation time
//    this.txtRotationTime.setText(Integer.toString(this.frame.getRTime()));
             //added for pattern chooser
    this.txt.setText(this.frame.getpatternStr());
    this.patternImage =this.frame.getpatternImage();
    validate();
  }


  public Dimension getPreferredSize() {
    return new Dimension(150, 300);
  }

  private void setCurrentTool(FrameTool tool) {
    if (this.currentTool != null) this.currentTool.stopTool(this.framePixelEditor);
    this.currentTool = tool;
    this.currentTool.startTool(this.framePixelEditor);
  }

  public void colorChanged(PaletteSelectorEvent e) {
    setCurrentTool(this.brushFrameTool);
  }

  public void drawOverlay(Graphics g) {
   this.selectFrameTool.drawOverlay(g, this.dragFrameTool.getDx(), this.dragFrameTool.getDy());

        }


 public void frameAdded(Animation.Frame newFrame, int newFrameNr, int totalFrames)
  {
  }

  public void frameSelected(Animation.Frame newFrame)
  {
    this.txtFrameTime.setText(Integer.toString(newFrame.getTime()));
    /////////////below line added for rotation time
//    this.txtRotationTime.setText(Integer.toString(newFrame.getRTime()));
    this.txt.setText(newFrame.getpatternStr());
     this.frame = newFrame;
    this.framePixelEditor.setFrame(this.frame);
  }

  public void frameRemoved(Animation.Frame removedFrame, int totalFrames)
  {
  }

  public void frameDataChanged(Animation.Frame newFrame) {
  }

  public void frameMoved(Animation.Frame frame, int newFrameNr) {
  }

  class FrameTimeTextListener implements ActionListener {
    private FrameToolbox frameToolbox;
    public FrameTimeTextListener(FrameToolbox frameToolbox) {
      this.frameToolbox = frameToolbox;
    }
    public void actionPerformed(ActionEvent e) {
      this.frameToolbox.animation.getCurrentFrame().setTime(Integer.parseInt(((JTextField)e.getSource()).getText()));

    }
  }

  class ShowTimeTextListener implements ActionListener {
      private FrameToolbox frameToolbox;

      public ShowTimeTextListener (FrameToolbox frameToolbox){
          this.frameToolbox = frameToolbox;
      }

      public void actionPerformed (ActionEvent e) {
       this.frameToolbox.animation.getCurrentFrame().setshowTime(Integer.parseInt(((JTextField)e.getSource()).getText()));
this.frameToolbox.animationEditor.showMins =Integer.parseInt(((JTextField)e.getSource()).getText());
      }
  }
  
   class startBarCl implements ChangeListener  {
       private FrameToolbox frameToolbox;
       
       public startBarCl(FrameToolbox frameToolbox)
    {
        this.frameToolbox = frameToolbox;
    }
    public void stateChanged(ChangeEvent arg0)
    {
     this.frameToolbox.framePixelEditor.repaint();
    }
  }
///////////////////////////////////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////////
  public class openSound implements ActionListener   {
        private FrameToolbox frameToolbox;

    public openSound(FrameToolbox frameToolbox) {
     this.frameToolbox = frameToolbox;
     }
    public void actionPerformed(ActionEvent e)
            {
                this.frameToolbox.coreJavaSound.openFile();
               // this.frameToolbox.wfds.openFile();
            }
  }
  //////////////////////////////////////////////////////////////

  public class openAL implements ActionListener   {
        private FrameToolbox frameToolbox;
 
    public openAL(FrameToolbox frameToolbox) {
     this.frameToolbox = frameToolbox;
     }
    public void actionPerformed(ActionEvent e)
            {
                this.frameToolbox.framePixelEditor.openPicture();
                this.frameToolbox.framePixelEditor.updatePicture();
               
           }
  }


  public class thChange implements ChangeListener {
    private FrameToolbox frameToolbox;

    public thChange(FrameToolbox frameToolbox)    {
        this.frameToolbox = frameToolbox;
    }

    public void stateChanged(ChangeEvent arg0)
    {
          this.frameToolbox.framePixelEditor.updatePicture();
    }
    }

protected void updateLabel(String name) {
        ImageIcon icon = createImageIcon("images/" +name + ".gif");
        this.picture.setIcon(icon);
        if (icon != null) {
            this.picture.setText(null);
        } else {
            this.picture.setText("Image not found");
        }
      }

protected void updateImage(String name){
    ImageIcon icon = createImageIcon("images/" + name + ".gif");
    Image imageP = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
       if (icon != null) {
           this.patternImage = imageP;  } 
           else {   this.patternImage = null;
           System.err.println("Couldn't find file: " + name);
             }
        }

protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = LedCodes.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

        public class picUpdater implements ActionListener {
            private FrameToolbox frameToolbox;

            public picUpdater(FrameToolbox frameToolbox){
                this.frameToolbox = frameToolbox;
            }
            public void actionPerformed(ActionEvent e){
        JComboBox cb = (JComboBox)e.getSource();
        String petName = (String)cb.getSelectedItem();
        updateLabel(petName);


            this.frameToolbox.animation.getCurrentFrame().setpatternStr(petName);
                   }
    }

        public class patternUpdater implements ActionListener {
            private FrameToolbox frameToolbox;

            public patternUpdater(FrameToolbox frameToolbox){
                this.frameToolbox = frameToolbox;
            }
            public void actionPerformed(ActionEvent e){
            JComboBox cb = (JComboBox)e.getSource();
            String patternStr = (String)cb.getSelectedItem();
            updateImage(patternStr);

                this.frameToolbox.animation.getCurrentFrame().setpatternImage(patternImage);
      }    }


     public void setshowTime(Integer showtime) {
         this.showtime = showtime;
     }

     public Integer getshowTime(){

         if(this.showtime == null){
         return 10;} else{return this.showtime;
     }
    }



}

