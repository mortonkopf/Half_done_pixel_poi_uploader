
package PoiSonic;
import java.awt.Container;
     import java.awt.Dimension;
      import java.awt.Rectangle;
      import java.awt.Toolkit;
      import java.awt.event.ActionEvent;
      import java.awt.event.ActionListener;
      import java.awt.event.WindowAdapter;
      import java.awt.event.WindowEvent;
      import java.io.File;
      import java.io.IOException;
import java.awt.TrayIcon;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ import javax.swing.filechooser.FileNameExtensionFilter;
//imports

import java.net.URL;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;

//insert
// second insert for menu bar
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JMenuBar;
////
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;

//imports finish here


public class Main extends JFrame {


  public JFrame baseGridFrame;
//added
  public JMenuItem fileOpen;
  public JMenuBar menuBar;
  public JMenu menu, submenu;
  public JMenuItem menuItem;
  public JTextArea output;
  public JScrollPane scrollPane;
  public JSeparator separator;
  public Container box;
   private FramePixelEditor framePixelEditor;
public TrayIcon trayIcon;

   private FrameToolbox frameToolbox;
   private Animation animation;
   private LiveMenu liveMenu;

private AnimationEditor animationEditor;

  ////////////////////////////////////////////////////////////
   //create the JFrame

 public Main()
/*     */   {
              Dimension minSize = new Dimension(650, 650);
/*     */
/*  21 */     setTitle("PoiSonic");
/*  22 */     setMinimumSize(minSize);
/*     */     
            //    setLayout(new GridBagLayout());
            //    GridBagConstraints gBC = new GridBagConstraints();


/*  24 */     Rectangle windowLoc = new Rectangle();
/*  25 */     windowLoc.x = Preferences.getInstance().get("window.left", 40);
/*  26 */     windowLoc.y = Preferences.getInstance().get("window.top", 40);
/*  27 */     windowLoc.width = Preferences.getInstance().get("window.width", minSize.width);
/*  28 */     windowLoc.height = Preferences.getInstance().get("window.height", minSize.height);
/*  29 */     setBounds(windowLoc);
/*     */
/*  31 */     URL imageUrl = getClass().getResource("PoiSonicon.gif");
/*  32 */     if (imageUrl != null) {
/*  33 */       ImageIcon icon = new ImageIcon(imageUrl);
/*  34 */       setIconImage(icon.getImage());
/*     */     }
/*     */
/*  37 */     GhostGlassPane glassPane = new GhostGlassPane();
/*  38 */     setGlassPane(glassPane);


/*  40 */     this.framePixelEditor = new FramePixelEditor();
/*  41 */     getContentPane().add(this.framePixelEditor);
/*     */
            /////////////////////////////////////////////////////////////////////////////////////////////////////
 /* 46 */     this.frameToolbox = new FrameToolbox(this.framePixelEditor);
/*  47 */     getContentPane().add(this.frameToolbox, "West");

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*  43 */     this.animationEditor = new AnimationEditor(this);
/*  44 */     getContentPane().add(this.animationEditor, "South");
/*     */
/*  46 */  //   this.frameToolbox = new FrameToolbox(this.framePixelEditor);
/*  47 */  //   getContentPane().add(this.frameToolbox, "West");
     //adding panels into frame
 
                setJMenuBar(createMenu(this.animationEditor));

/*  51 */     setDefaultCloseOperation(0);
/*  52 */     addWindowListener(new WindowAdapter() {
/*     */       public void windowClosing(WindowEvent e) {
/*  54 */         Main.this.Exit();
/*     */       }
/*     */     });
/*  66 */     setAnimation(createPreferredAnimation());
/*     */   }
/*     *//////////////end of public main/////////////////////


   private JMenuBar createMenu(AnimationEditor animationEditor) {
     JMenuBar menuBar = new JMenuBar();

     JMenu menuFile = new JMenu("File");
     menuFile.setMnemonic(70);
     menuBar.add(menuFile);

     JMenuItem fileNew = new JMenuItem("New");
     fileNew.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
     fileNew.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         if (Main.this.confirmUnsavedChanges()) {
           SizeSelector sizeSelector = new SizeSelector(Main.this);
           int result = sizeSelector.showDialog();
           if (result == 0) {
             Main.this.setAnimation(new Animation(sizeSelector.getCols(), sizeSelector.getRows(), sizeSelector.getFormatCode()));
             Main.this.validate();
           }
         }
       }
     });
 ////   menuFile.add(fileNew);
/*     */
/*  92 */     JMenuItem fileOpen = new JMenuItem(new Main.FileOpenAction(this));
            fileOpen.setText("Open");
                menuFile.add(fileOpen);

/*  95 */     JMenuItem fileSave = new JMenuItem(new Main.FileSaveAction(this));
/*  96 */   fileSave.setText("Save");
            menuFile.add(fileSave);
/*     */
/*  98 */     menuFile.add(new JSeparator());
/*     */
/* 100 */     JMenuItem fileExport = new JMenuItem(new Main.FileExportAction(this));
/* 101 */   fileExport.setText("Export");
            menuFile.add(fileExport);
/*     */
/* 103 */     menuFile.add(new JSeparator());
/*     */
/* 105 */     JMenuItem about = new JMenuItem("About");
/* 106 */     about.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 108 */         new AboutFrame(Main.this).setVisible(true);
/*     */       }
/*     */     });
/* 111 */     menuFile.add(about);
       menuFile.add(new JSeparator());
/////////////////////////////////////////////////////////////////for preferences of folders open file, save, etc
/*   final JFrame frame = new JFrame ("dialogue");
    JMenuItem prefs = new JMenuItem("Prefs");
     prefs.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
              int res = JOptionPane.showConfirmDialog(frame
                ,"Open the PoiSonic Preferences file for editing" +
                "\n"+
                "\n (only works after Save, Open & Export have been used a first time)"
                ,"Set your home folders", JOptionPane.WARNING_MESSAGE
                , JOptionPane.OK_CANCEL_OPTION);
if (res == JOptionPane.OK_OPTION) try {
//Process p=Runtime.getRuntime().exec("notepad prefs.txt" ); //enter file path
Process p=Runtime.getRuntime().exec("notepad dist" + File.separatorChar +"prefs.txt");

} catch (Exception ee) {
ee.printStackTrace();
    }
              }    });
    menuFile.add(prefs);
    menuFile.add(new JSeparator());
*/
///////////////////////////////////////////////////////////////////
/* 113 */ //    menuFile.add(new JSeparator());
/*     */
/* 115 */     JMenuItem fileExit = new JMenuItem("Exit");
/* 116 */     fileExit.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 117 */     fileExit.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 119 */         Main.this.Exit();
/*     */       }
/*     */     });
/* 122 */     menuFile.add(fileExit);
/*     */
/* 124 */     JMenu menuAnimation = new JMenu("Animation");
/* 125 */     menuAnimation.setMnemonic(65);
/* 126 */ //    menuBar.add(menuAnimation);

/*     */
/* 143 */     menuBar.add(this.liveMenu = new LiveMenu());
/*     */
/* 145 */     return menuBar;
/*     */   }
/*     */////////////////end of menu setting///////////////////

///////////////////////////////////////////////////////////////////
   private void Exit() {
     if (confirmUnsavedChanges()) {
       try {
         Rectangle windowLoc = getBounds();
         Preferences.getInstance().set("window.left", windowLoc.x);
         Preferences.getInstance().set("window.top", windowLoc.y);
         Preferences.getInstance().set("window.width", windowLoc.width);
         Preferences.getInstance().set("window.height", windowLoc.height);

         Preferences.getInstance().save();
       } catch (IOException e) {
         showError("Error saving preferences: " + e.getMessage());
       }
       System.exit(0);
     }
   }

   public void setAnimation(Animation animation) {
     this.animation = animation;
     this.animationEditor.setAnimation(animation);
     this.frameToolbox.setAnimation(animation);
     this.liveMenu.setAnimation(animation);
   }

   public Animation getAnimation() {
     return this.animation;
   }

   private Animation createPreferredAnimation() {
     int prefWidth = Preferences.getInstance().get("animation.width", 150);
     int prefHeight = Preferences.getInstance().get("animation.height", 30);
     int prefDataFormat = Preferences.getInstance().get("animation.dataformat", 1);
     return new Animation(prefWidth, prefHeight, prefDataFormat);
   }

   private boolean confirmUnsavedChanges() {
     if (this.animation.hasUnsavedChanges()) {
       int result = JOptionPane.showConfirmDialog(this, "There are unsaved changes, do you want to save these?", "PoiSonic - hold on a minute", 1, 2);

       switch (result) {
       case 0:
         return saveChanges();
       case 1:
         return true;
       case 2:
         return false;
       }
     }
     return true;
   }

/*     */   public static void main(String[] args) {
/*     */     try {
/* 201 */       Preferences.instanceFromFile("prefs.txt");
/*     */     } catch (IllegalStateException e) {
/* 203 */       e.printStackTrace();
/*     */     } catch (IOException e) {
/* 205 */       e.printStackTrace();
/*     */     }
/*     */
/* 208 */     Preferences.getInstance().setFilename("prefs.txt");
/*     */     try
/*     */     {
/* 211 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*     */     } catch (ClassNotFoundException e) {
/* 213 */       e.printStackTrace();
/*     */     } catch (InstantiationException e) {
/* 215 */       e.printStackTrace();
/*     */     } catch (IllegalAccessException e) {
/* 217 */       e.printStackTrace();
/*     */     } catch (UnsupportedLookAndFeelException e) {
/* 219 */       e.printStackTrace();
/*     */     }
/*     */
/* 222 */     new Main().setVisible(true);
/*     */   }
/*     */
/*     */   private void showError(String message) {
/* 226 */     JOptionPane.showMessageDialog(this, message, "PoiSonic", 0);
/*     */   }
/*     */
/*     */   private boolean saveChanges() {
/* 230 */     String wd = Preferences.getInstance().get("directories.file.save", System.getProperty("user.dir"));
/* 231 */     SaveFileChooser fc = new SaveFileChooser(wd);
/* 232 */     FileNameExtensionFilter filter = new FileNameExtensionFilter("Animation files (.led)", new String[] { "led" });
/*     */
/* 234 */     fc.addChoosableFileFilter(filter);
/* 235 */     fc.setDialogType(1);
/* 236 */     fc.setDialogTitle("Save Animation File");
/*     */
/* 238 */     int rc = fc.showDialog(null, "Save");
/* 239 */     if (rc == 0) {
/* 240 */       Preferences.getInstance().set("directories.file.save", fc.getCurrentDirectory().toString());
/* 241 */       File file = fc.getSelectedFile();
/* 242 */       String filename = file.getAbsolutePath();
/* 243 */       AnimationSaver saver = new AnimationSaver();
/* 244 */       if (saver.save(this.animation, filename))
/* 245 */         this.animation.resetUnsavedChanges();
/*     */       else {
/* 247 */         showError(saver.getLastError());
/*     */       }
/*     */
/* 250 */       return true;
/*     */     }
/*     */

/* 253 */     return false;
/*     */   }
/*     */
/*     */   class FileExportAction extends AbstractAction
/*     */   {
/*     */     Main main;
/*     */
/*     */     public FileExportAction(Main main)
/*     */     {
/* 315 */       super();
/* 316 */       putValue("ShortDescription", "Export project to .ino arduino file");
/* 317 */       putValue("MnemonicKey", Integer.valueOf(88));
/* 318 */       putValue("AcceleratorKey", KeyStroke.getKeyStroke(88, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 319 */       this.main = main;
/*     */     }
/*     */
/*     */     public void actionPerformed(ActionEvent e) {
/* 323 */       String wd = Preferences.getInstance().get("directories.file.export", System.getProperty("user.dir"));
/* 324 */       SaveFileChooser fc = new SaveFileChooser(wd);
/* 325 */       FileNameExtensionFilter filter = new FileNameExtensionFilter("Header files (.ino)", new String[] { "ino" });
/*     */
/* 327 */       fc.addChoosableFileFilter(filter);
/* 328 */       fc.setDialogType(1);
/* 329 */       fc.setDialogTitle("Export PoiSonic Project File");
/*     */
/* 331 */       int rc = fc.showDialog(null, "Export");
/* 332 */       if (rc == 0) {
/* 333 */         Preferences.getInstance().set("directories.file.export", fc.getCurrentDirectory().toString());
/* 334 */         File file = fc.getSelectedFile();
/* 335 */         String filename = file.getAbsolutePath();
/* 336 */         AnimationSaver exporter = new AnimationSaver();
/* 337 */         if (!exporter.export(this.main.animation, filename))
/* 338 */           Main.this.showError(exporter.getLastError());
/*     */       }
/*     */     }
/*     */   }
/*     */
/*     */   class FileSaveAction extends AbstractAction
/*     */   {
/*     */     Main main;
/*     */
/*     */     public FileSaveAction(Main main)
/*     */     {
/* 299 */       super();
/* 300 */       putValue("ShortDescription", "Save this project");
/* 301 */       putValue("MnemonicKey", Integer.valueOf(83));
/* 302 */       putValue("AcceleratorKey", KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 303 */       this.main = main;
/*     */     }
/*     */
/*     */     public void actionPerformed(ActionEvent e) {
/* 307 */       Main.this.saveChanges();
/*     */     }
/*     */   }
/*     */
/*     */   class FileOpenAction extends AbstractAction
/*     */   {
/*     */     Main main;
/*     */
/*     */     public FileOpenAction(Main main)
/*     */     {
/* 260 */       super();
/*     */
/* 262 */       putValue("ShortDescription", "Open a PoiSonic project");
/* 263 */       putValue("MnemonicKey", Integer.valueOf(79));
/* 264 */       putValue("AcceleratorKey", KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 265 */       this.main = main;
/*     */     }
/*     */
/*     */     public void actionPerformed(ActionEvent e) {
/* 269 */       if (Main.this.confirmUnsavedChanges()) {
/* 270 */         String wd = Preferences.getInstance().get("directories.file.open", System.getProperty("user.dir"));
/* 271 */         JFileChooser fc = new JFileChooser(wd);
/* 272 */         FileNameExtensionFilter filter = new FileNameExtensionFilter("Poisonic files (.led)", new String[] { "led" });
/*     */
/* 274 */         fc.addChoosableFileFilter(filter);
/* 275 */         fc.setDialogType(0);
/* 276 */         fc.setDialogTitle("Open PoiSonic Project File");
/*     */
/* 278 */         int rc = fc.showDialog(null, "Open");
/* 279 */         if (rc == 0) {
/* 280 */           Preferences.getInstance().set("directories.file.open", fc.getCurrentDirectory().toString());
/* 281 */           File file = fc.getSelectedFile();
/* 282 */           String filename = file.getAbsolutePath();
/* 283 */           AnimationSaver loader = new AnimationSaver();
/* 284 */           Animation animation = loader.load(filename);
/* 285 */           if (animation != null)
/* 286 */             this.main.setAnimation(animation);
/*     */           else
/* 288 */             Main.this.showError(loader.getLastError());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

