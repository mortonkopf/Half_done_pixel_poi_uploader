package PoiSonic;

/*     */ import java.awt.Component;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;

/*     */ 
/*     */ public class SizeSelector extends JDialog
/*     */ {
/*   7 */   private int dialogResult = 1;
/*     */   public /*private*/ JTextField txtRows;
/*     */   public /*private*/ JTextField txtCols;
/*     */   private JComboBox cmbFormat;
            public JButton btnOk;
            private FrameToolbox frameToolbox;
            private Animation animation;
            private FramePixelEditor framePixelEditor;
/*     */ public String colsStr;
            public double colsStrDouble;
            public double rowsStrDouble;
            public String rowsStr;
            public int rowsStrInteger;
            public int colsStrInteger;

            public int nCirInteger = 150;
            public double nCirdDouble = 150;
            public int nLedsInteger = 30;
            public double nLeddDouble =30;

/*     */   public SizeSelector(Frame owner)
/*     */   {
    
/*  13 */     super(owner, true);
/*  14 */     setTitle("Select Size");
/*  15 */     setResizable(false);
/*     */  
/*  17 */     Action actionCancel = new AbstractAction("Cancel") {
/*     */       public void actionPerformed(ActionEvent e) {
/*  19 */         SizeSelector.this.buttonClicked(2);
/*     */       }
/*     */     };
/*  22 */     KeyStroke keyStroke = KeyStroke.getKeyStroke(27, 0);
/*     */ 
/*  24 */     Object actionMapKey = actionCancel.getValue("Name");
/*  25 */     InputMap im = getRootPane().getInputMap(2);
/*  26 */     ActionMap am = getRootPane().getActionMap();
/*  27 */     im.put(keyStroke, actionMapKey);
/*  28 */     am.put(actionMapKey, actionCancel);
/*     */ 
/*  30 */     GridBagLayout gridBag = new GridBagLayout();
/*  31 */     JPanel panel = new JPanel(gridBag);
/*  32 */     Border border = new EmptyBorder(8, 8, 8, 8);
/*  33 */     panel.setBorder(border);
/*  34 */     add(panel);
/*     */ 
/*  37 */     GridBagConstraints labelConstraints = new GridBagConstraints();
/*  38 */     labelConstraints.anchor = 18;
/*  39 */     labelConstraints.fill = 1;
/*  40 */     labelConstraints.weightx = 0.0D;
/*  41 */     labelConstraints.insets = new Insets(5, 5, 5, 5);
/*     */ 
/*  43 */     GridBagConstraints textConstraints = new GridBagConstraints();
/*  44 */     textConstraints.anchor = 18;
/*  45 */     textConstraints.fill = 0;
/*  46 */     textConstraints.weightx = 1.0D;
/*  47 */     textConstraints.gridwidth = 0;
/*  48 */     textConstraints.insets = labelConstraints.insets;
/*     */ 
/*  50 */     GridBagConstraints okConstraints = new GridBagConstraints();
/*  51 */     okConstraints.anchor = 14;
/*  52 */     okConstraints.fill = 0;
/*  53 */     okConstraints.weightx = 0.0D;
/*  54 */     okConstraints.weighty = 1.0D;
/*  55 */     okConstraints.gridwidth = -1;
/*  56 */     okConstraints.gridheight = 0;
/*  57 */     okConstraints.insets = labelConstraints.insets;
/*     */ 
/*  59 */     FocusAdapter selectOnFocus = new FocusAdapter() {
/*     */       public void focusGained(FocusEvent e) {
/*  61 */         Component component = e.getComponent();
/*  62 */         if ((component instanceof JTextField)) ((JTextField)component).selectAll();
/*     */       }
/*     */     };
/*  68 */     JLabel label = new JLabel("Number of slices:");
/*  69 */     panel.add(label);
/*  70 */     gridBag.setConstraints(label, labelConstraints);
/*     */ 
/*  72 */     this.txtCols = new JTextField(Integer.toString(Preferences.getInstance().get("animation.width", 150)), 3);
/*  73 */     this.txtCols.addFocusListener(selectOnFocus);
////////////////////////////////////add second listener here to parseint to double for the ncird//////////////////
              this.txtCols.addActionListener (new SizeSelector.numberSLICESAL(this));

/*  74 */     panel.add(this.txtCols);
/*  75 */     gridBag.setConstraints(this.txtCols, textConstraints);
/*     */ 
/*  79 */     label = new JLabel("LED number:");
/*  80 */     panel.add(label);
/*  81 */     gridBag.setConstraints(label, labelConstraints);
/*     */ 
/*  83 */     this.txtRows = new JTextField(Integer.toString(Preferences.getInstance().get("animation.height", 30)), 3);
/*  84 */     this.txtRows.addFocusListener(selectOnFocus);
              this.txtRows.addActionListener (new SizeSelector.NumberLEDSAL(this));

  //        rowsStr = txtRows.getText();
  //      rowsStrDouble = Double.parseDouble(rowsStr);
  //      rowsStrInteger = Integer.parseInt(rowsStr);
//this.txtRows.addActionListener(new test());
////////////////////////////////////add second listener here to parseint to double for the ncird//////////////////
//              this.txtRows.addActionListener(new SizeSelector.NumberLEDSAL(framePixelEditor/*frameToolbox*/));
//              this.txtRows.addActionListener(new SizeSelector.numberLEDS(frameToolbox));
//////////////////////////////////////////////////////////
                panel.add(this.txtRows);
/*  86 */     gridBag.setConstraints(this.txtRows, textConstraints);
/*     */ 
/*  90 */     label = new JLabel("Format:");
/*  91 */    // panel.add(label);
/*  92 */     gridBag.setConstraints(label, labelConstraints);
/*     */ 
/*  94 */     DataFormatDescription[] dataFormats = DataFormat.getDataFormats();
/*  95 */     this.cmbFormat = new JComboBox(dataFormats);
/*  96 */   //  panel.add(this.cmbFormat);
/*  97 */     gridBag.setConstraints(this.cmbFormat, textConstraints);
/*     */ 
/*  99 */     int preferredDataFormat = Preferences.getInstance().get("animation.dataformat", 1);
/* 100 */     for (int i = 0; i < dataFormats.length; i++) {
/* 101 */       if (dataFormats[i].getCode() == preferredDataFormat) {
/* 102 */         this.cmbFormat.setSelectedItem(dataFormats[i]);
/* 103 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 109 */     JButton btnCancel = new JButton(actionCancel);
/* 110 */     panel.add(btnCancel);
/* 111 */     gridBag.setConstraints(btnCancel, okConstraints);
/*     */ 
/* 118 */     okConstraints.gridwidth = -1;
/* 119 */     okConstraints.weightx = 1.0D;
/*     */ 
/* 121 */     JButton btnOk = new JButton("OK");
/* 122 */     panel.add(btnOk);
/* 123 */     gridBag.setConstraints(btnOk, okConstraints);
/* 124 */     btnOk.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 126 */         SizeSelector.this.buttonClicked(0);
                 }   /*     */      
/*     */     });
            btnOk.addActionListener (new SizeSelector.numberSLICESAL(this));
            btnOk.addActionListener (new SizeSelector.NumberLEDSAL(this));
/* 130 */
            addWindowListener(new WindowAdapter() {
/*     */       public void windowClosing(WindowEvent e) {
/* 132 */         SizeSelector.this.buttonClicked(2);
/*     */       }
/*     */     });
/* 142 */     getRootPane().setDefaultButton(btnOk);
/* 143 */     pack();
/*     */ 
/* 145 */     Point p = owner.getLocation();
/* 146 */     setLocation(p.x + (owner.getWidth() - getWidth()) / 2, p.y + (owner.getHeight() - getHeight()) / 2);
/*     */   }
/*     */ 
/*     */   private void buttonClicked(int option) {
/* 150 */     if (option == 0) {
/* 151 */       savePreferences();
/*     */     }
/*     */ 
/* 154 */     this.dialogResult = option;
/* 155 */     setVisible(false);
/*     */   }
/*     */ 
/*     */   public int showDialog() {
/* 159 */     setVisible(true);
/* 160 */     return this.dialogResult;
/*     */   }
/*     */ 
/*     */   public int getRows() {
/* 164 */     return Integer.parseInt(this.txtRows.getText());
/*     */   }
/*     */ 
/*     */   public int getCols() {
/* 168 */     return Integer.parseInt(this.txtCols.getText());
/*     */   }
/*     */ 
/*     */   public int getFormatCode() {
/* 172 */     return ((DataFormatDescription)this.cmbFormat.getSelectedItem()).getCode();
/*     */   }
/*     */ 
/*     */   private void savePreferences() {
/* 176 */     Preferences.getInstance().set("animation.width", getCols());
/* 177 */     Preferences.getInstance().set("animation.height", getRows());
/* 178 */     Preferences.getInstance().set("animation.dataformat", getFormatCode());
/*     */   }
///////////////////////////for nCird and Leds values////////////////////////////////////////////////
/////////////////other listeners from controlpanel///////////////////
/*
class test implements ActionListener {
      public test()
    { 
      } 
          public void actionPerformed (ActionEvent e)
      {
              
          System.out.println("test worked");
        
      }

    }
*/
/*  class numberLEDS implements ActionListener {
      private FrameToolbox frameToolbox;

      public numberLEDS (FrameToolbox frameToolbox){
          this.frameToolbox = frameToolbox;
      }

      public void actionPerformed (ActionEvent e) {
        this.frameToolbox.animation.getCurrentFrame().setnumberLEDS (Integer.parseInt(((JTextField)e.getSource()).getText()));
        }
  }
*/  class NumberLEDSAL implements ActionListener {
          private SizeSelector sizeSelector;

      public NumberLEDSAL(SizeSelector sizeSelector)
    {
          this.sizeSelector = sizeSelector;
      }
          public void actionPerformed (ActionEvent e)
      {
             String rowsStr = txtRows.getText();
              nLeddDouble = Double.parseDouble(rowsStr);
              nLedsInteger = Integer.parseInt(rowsStr);

     repaint();
        }
          
    }
    class numberSLICESAL implements ActionListener {
          private SizeSelector sizeSelector;

      public numberSLICESAL(SizeSelector sizeSelector)
    {
          this.sizeSelector = sizeSelector;
      }
          public void actionPerformed (ActionEvent e)
      {
              colsStr = txtCols.getText();
             /*framePixelEditor.*/nCirdDouble = Double.parseDouble(colsStr);
             /* framePixelEditor.*/nCirInteger = Integer.parseInt(colsStr);

   //           this.sizeSelector.framePixelEditor.repaint();
      }
    }
}

  /////////////////////////////////////////////////////////




////////////////////////////////////////////////////////////////////////////



 

