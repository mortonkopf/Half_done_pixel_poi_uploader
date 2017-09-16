
package PoiSonic;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Properties;

class WriteIni
{
  public void doit(String comport)
  {
    try
    {
      Properties p = new Properties();
      p.put("DBcomport", comport);
      FileOutputStream out = new FileOutputStream("comport.ini");
      p.store(out, "");
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
  }
}