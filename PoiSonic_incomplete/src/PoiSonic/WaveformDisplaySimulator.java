/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PoiSonic;



import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.io.*;

public class WaveformDisplaySimulator {



  // public static void main(String[] args) {
 //       try {
    public static void openFile(){
try{
 //   public openFile(String[]args){
    JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream (new FileInputStream (f)));
                   WaveformPanelContainer container = new WaveformPanelContainer();
            container.setAudioToDisplay(audioInputStream);
       //  try {
            CoreJavaSound s = new CoreJavaSound (f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        //    JFrame frame = new JFrame("Waveform Display Simulator");
       //     frame.setBounds(200,200, 500, 350);

           // File file = new File(args[0]);
         //   AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream (new FileInputStream (f)));

        //    WaveformPanelContainer container = new WaveformPanelContainer();
        //    container.setAudioToDisplay(audioInputStream);

        //    frame.getContentPane().setLayout(new BorderLayout());
        //    frame.getContentPane().add(container, BorderLayout.CENTER);

       //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      //      frame.show();
        //    frame.validate();
        //    frame.repaint();


     //   } catch (Exception e){
     //       e.printStackTrace();
      //  }
    }

//}

/*import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.io.*;

public class WaveformDisplaySimulator {

    public static void main(String[] args) {
        try {

            JFrame frame = new JFrame("Waveform Display Simulator");
            frame.setBounds(200,200, 500, 350);

            File file = new File(args[0]);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream (new FileInputStream (file)));

            WaveformPanelContainer container = new WaveformPanelContainer();
            container.setAudioToDisplay(audioInputStream);

            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().add(container, BorderLayout.CENTER);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.show();
            frame.validate();
            frame.repaint();


        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

/*   Swing Hacks
 *   Tips and Tools for Killer GUIs
 * By Joshua Marinacci, Chris Adamson
 *   First Edition June 2005
 *   Series: Hacks
 *   ISBN: 0-596-00907-0
 *   http://www.oreilly.com/catalog/swinghks/
 */


