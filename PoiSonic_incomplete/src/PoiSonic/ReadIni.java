
package PoiSonic;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Properties;
import javax.swing.JOptionPane;

class ReadIni
{
  public String doit()
  {
    String tempstring = "COM1";
    try {
      Properties p = new Properties();
      p.load(new FileInputStream("comport.ini"));
      tempstring = p.getProperty("DBcomport");
    }
    catch (Exception e) {
      JOptionPane.showMessageDialog(null,
        e.toString(),
        "Ini warning",
        2);
      System.out.println(e);
    }
    return tempstring;
  }
}