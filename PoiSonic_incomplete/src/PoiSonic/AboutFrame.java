package PoiSonic;
//import javax.swing.*;
//import java.awt.*;
import java.awt.Color;
//import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.net.URL;
import javax.swing.JTextArea;
import javax.swing.*;

/*    */ 
public class AboutFrame extends JDialog
{

    public AboutFrame(Frame owner)
   {
     setLayout(null);
     setTitle("About PoiSonic");
     URL imageUrl = getClass().getResource("PoiSonicon.gif");
            if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            setIconImage(icon.getImage());
            setPreferredSize(new Dimension(600, 250));
        //    setBounds(new Rectangle(500, 500, 500, 500));
            setSize(600,250);
     setResizable(false);
/*    */
     }
     setSize(600,350);
     Point p = owner.getLocation();
     setLocation(p.x + (owner.getWidth() - getWidth()) / 2, p.y + (owner.getHeight() - getHeight()) / 2);
/*    */ 

 AboutFrame.Logo logo = new AboutFrame.Logo();
     logo.setLocation(12, 16);
     add(logo);

  
     JLabel title = new JLabel("PoiSonic");
     title.setSize(200, 26);
     title.setLocation(180, 16);
     title.setFont(new Font("Arial", 1, 18));
     getContentPane().add(title);

     JLabel version = new JLabel("Version 1.5");
     version.setSize(200, 16);
     version.setLocation(180, 40);
     add(version);
 
     JLabel date = new JLabel("Nov - 2012");
     date.setSize(200, 16);
     date.setLocation(180, 55);
     date.setBackground(Color.BLUE);
     add(date);
 
     JLabel name = new JLabel("built by Marius, ");
            // + "using work by Chris van Marle and Jan Paul Posma ");
     name.setSize(350, 16);
     name.setLocation(180, 75);
     name.setBackground(Color.BLUE);
     add(name);

     JLabel name3 = new JLabel ("to do:");//("http://janpaulposma.nl/pimpmybike-biking-at-night-with-style");
     name3.setSize(300, 16);
     name3.setLocation(180, 95);
     name3.setBackground(Color.BLUE);
    // add(name3);

     JLabel name2 = new JLabel ("to do: port out, timeline,");
     name2.setSize(340, 16);
     name2.setLocation(180, 95);
     name2.setBackground(Color.BLUE);
     add(name2);
          JLabel name6 = new JLabel ("audio, add blank frame...");
     name6.setSize(300, 16);
     name6.setLocation(180, 105);
     name6.setBackground(Color.BLUE);
     add(name6);

JTextArea textArea = new JTextArea(
     "updates: frame time changed to millisec, null pattern now "+
    "shows as blank time in display, April - save and open now function, " +
  "May - repaired spokes/ blue flower hangup, new (dawn set) patterns, " +
   "Aug - converted to 150 pixel width for Teensy 2++, "
   + "\n Oct - changes to this about box (more legible). app minimum size " +
   "reduced for smaller screens, hooky blue green led issue resolved with selector button" +
   ", set show timeline has been removed, button for select Music has been removed," +//, folder prefs menu item added."//in main frame min size set from 750 to 650, and in animation editor audio scale commented out
"two new patterns added (chevron and diamond "// enter new text here
   );
Color ncolor = new Color(240,240,240);
textArea.setSize(350, 150);
textArea.setLocation(180, 125);
textArea.setFont(new Font("Times",Font.PLAIN, 10));//("Serif", Font.ITALIC, 14));
textArea.setLineWrap(true);
textArea.setBackground(ncolor);//(Color.LIGHT_GRAY);
textArea.setWrapStyleWord(true);
add(textArea);

     JButton close = new JButton("Close");
     close.setBounds(12,250, 100, 26);
     close.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         AboutFrame.this.setVisible(false);
       }
     });
     add(close);
   }
class Logo extends JComponent
   {
     private Color[] Colors;
     private int[][] Logo = { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 1, 1, 1, 1 }, { 0, 0, 0, 1, 1, 1, 1, 1 } };

     public Logo()
     {
       setSize(100, 100);
       this.Colors = new Color[] { new Color(0, 75, 128), new Color(240, 240, 240), new Color(186, 0, 0), new Color(240, 0, 0) };
     }

     public void paint(Graphics g)
     {
       g.setColor(new Color (240, 240, 240));
       g.fillRect(0, 0, getWidth(), getHeight());

       int w = getWidth() / 8;
       int h = getHeight() / 8;

       int s = w < h ? w : h;
       int space = s / 12;

       for (int y = 0; y < 8; y++)
         for (int x = 0; x < 8; x++) {
           g.setColor(this.Colors[this.Logo[y][x]]);
           g.fillOval(s * x + space, s * y + space, s - 2 * space, s - 2 * space);
         }
     }
   }
}
