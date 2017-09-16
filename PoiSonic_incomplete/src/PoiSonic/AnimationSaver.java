package PoiSonic;


import java.awt.Image;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.Arrays;
import java.lang.Object;
 import java.lang.Integer;
 import java.awt.*;
 import java.awt.image.*;
 import java.awt.event.*;
 import java.io.*;
 import java.net.URL;
 import javax.imageio.*;
 import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.Checkbox;

/*     */ 
/*     */ public class AnimationSaver
/*     */ {
    Animation.Frame frame;
    FrameOrderEditor.FrameTile frameTile;
    String string,hex;
    Image image1;
    BufferedImage bImage;
    LedCodes ledCodes;

/*     */   public static final int CURRENT_FILE_FORMAT = 3;
/*   5 */   public static final String newline = System.getProperty("line.separator");
/*     */ 
/*   7 */   private String lastError = null;
/*     */ 
/*     */   public String getLastError()
/*     */   {
/*  14 */     return this.lastError;
   }
 ////////////////////////////////////////////////////////////////////////////
 /*  public boolean save(Animation animation, String filename) {
     try {
       FileOutputStream fos = new FileOutputStream(filename);
       OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
 
       out.write(3);
       out.write(animation.getPixelsWide());
       out.write(animation.getPixelsHigh());
       out.write(DataFormat.getFormatCode(animation.getDataFormat()));
                for (Animation.Frame frame : animation.frames) {
         out.write(frame.getTime());
         //for (int i = 0; i < animation.getPixelsTotal(); i++) {
         //  out.write(frame.getPixel(i));
         //}
 }
        out.close();
             } catch (FileNotFoundException e) {
       this.lastError = e.getMessage();
       e.printStackTrace();
       return false;
     } catch (IOException e) {
       this.lastError = e.getMessage();
       e.printStackTrace();
       return false;
     }
 
     return true;
   }*/
//////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean save(Animation animation, String filename) {
     try {
      FileWriter fw = new FileWriter(filename);
      String newline = System.getProperty("line.separator");

   //    fw.write(3);
   //    fw.write(animation.getPixelsWide());
   //    fw.write(animation.getPixelsHigh());
   //    fw.write(DataFormat.getFormatCode(animation.getDataFormat()));
   //    fw.write(newline);

         for (Animation.Frame frame : animation.frames) {
         fw.write(Integer.toString(frame.getTime()));
         fw.write(newline);
         if (frame.getpatternStr() != null){
            fw.write(frame.getpatternStr());} else
           {fw.write("null");}
           fw.write(newline);

        //insert array output files here
        //   if (frame.getpatternStr() != null){
        //    fw.write(frame.getpatternStr());} else
        //   {fw.write("null");}
        //   fw.write(newline);

          }
        fw.close();

             } catch (FileNotFoundException e) {
/*  36 */       this.lastError = e.getMessage();
/*  37 */       e.printStackTrace();
/*  38 */       return false;
/*     */     } catch (IOException e) {
/*  40 */       this.lastError = e.getMessage();
/*  41 */       e.printStackTrace();
/*  42 */       return false;
/*     */     }
/*     */
/*  45 */     return true;
/*     */   }
/////////////////////////////////////////////////////////////////////////////////////////////////////

/*     */ 
/*     */   public boolean export(Animation animation, String filename) {
  
/*     */     try {
/*  50 */       String tab = "  ";
/*  51 */       DataFormat dataFormat = animation.getDataFormat();
/*     */ 
/*  53 */       FileWriter fw = new FileWriter(filename);
/*  54 */       BufferedWriter out = new BufferedWriter(fw);

out.write("#if defined(ARDUINO) && ARDUINO >= 100");out.write(newline);
out.write("#include "+"<Arduino.h>");out.write(newline);
out.write("#else");out.write(newline);
out.write("#include "+"<WProgram.h>");out.write(newline);
out.write("#endif");out.write(newline);
/*     */       out.write("#include <FastSPI_LED.h>");
                out.write(newline);
                out.write("#include"+ " <avr/pgmspace.h>>");
                out.write(newline);
                out.write("// This creates an Arduino input file for PoiSonic");
                out.write(newline);
                out.write("// This PoiSonic project is designed for " + animation.getPixelsWide()+" slices " + "using " + animation.getPixelsHigh() + " LEDs");
/*  74 */       out.write(newline);
/*  56 */       out.write("int animationFrames = " + animation.frames.size() + "; //= Number of project images");
/*  57 */       out.write(newline);


              boolean firstFrame = true;
/*  62 */       out.write("int animationDelays[] = { ");
/*  63 */       for (Animation.Frame frame : animation.frames) {
/*  64 */         if (firstFrame) firstFrame = false; else {
/*  65 */           out.write(", ");
/*     */         }
/*  67 */         out.write(Integer.toString(frame.getTime()));
/*     */       }
/*  69 */       out.write(" }; // = Duration of each image");

                out.write(newline);
//out.write(newline);
////////////////////////////////////////////////////////////////////////////////
//    boolean nextFrame = true;
//    out.write("int effect1[] = { ");
//       for (Animation.Frame frame : animation.frames) {
//         if (nextFrame) nextFrame = false; else {
//           out.write(", ");
//         }
//         out.write(Integer.toString(frame.getRTime()));//change this to getboolean for the effect
//       }
//       out.write(" }; // = imageEffect1");
/////////////////////////////////////////////////////////////////////////
         out.write(newline);
        //       out.write("int numberOfLeds = " + animation.getPixelsHigh() + ";");
        //        out.write(newline);
                out.write("int numberOfSlices = " + animation.getPixelsWide()+ ";");
                out.write(newline);
                out.write("int pixelsTotal = " + animation.getPixelsTotal() +";");
                out.write(newline);
                out.write("#define NUM_LEDS " + animation.getPixelsHigh());
                out.write(newline);
out.write("unsigned int Led[NUM_LEDS];");//Led = the strip_colors variable in ws2801 library
out.write(newline);
out.write(newline);
out.write("void setup() {");
out.write(newline);
out.write("typedef uint16_t PROGMEM prog_uint16_t;");
out.write(newline);
out.write(" byte Counter;");
out.write(newline);
out.write(newline);
out.write("  // Turn all LEDs off.");
out.write(newline);
out.write("for(Counter=0;Counter < NUM_LEDS; Counter++)");
  out.write(newline);
    out.write("Led[Counter]=Color(0,0,0);");//(Counter,0,31-Counter);");
  out.write(newline);
out.write("  FastSPI_LED.setLeds(NUM_LEDS);");
out.write(newline);
out.write(" FastSPI_LED.setChipset(CFastSPI_LED::SPI_LPD6803);");
out.write(newline);
out.write("FastSPI_LED.setCPUPercentage(65);");
out.write(newline);
out.write(" FastSPI_LED.init();");
out.write(newline);
out.write("  FastSPI_LED.start();");
out.write(newline);
out.write("  show();");
out.write(newline);
out.write(" }");
 /*               out.write("int dataPin = 2; // 'yellow' wire)");
                out.write(newline);
                out.write("int clockPin = 3; // 'green' wire");
                out.write(newline);
               out.write(newline);
               out.write("LPD6803 strip = LPD6803(30, dataPin, clockPin);");
   */     
  out.write(newline);
               out.write(newline);
               out.write("void show()");
                     out.write(newline);
               out.write("{");
  out.write(newline);
               out.write("  // copy data from Led into the rgb library's output - we need to expand it back out since ");
  out.write(newline);
               out.write("  // the rgb library expects values from 0-255 (because it's more generically focused).");
  out.write(newline);
               out.write("  unsigned char *pData = FastSPI_LED.getRGBData();");
                 out.write(newline);
/////////////////////////////////////////////////////////////////////////////////////////blue green LED selection
        //in here need to place the IF statement...   next line inserted
                 if ((FrameToolbox.select.isSelected())) {
               out.write(" for(int i=0; i < NUM_LEDS; i++) { ");
                 out.write(newline);
               out.write("    int b = ((Led[i] >>10) & 0xFF)*8;");////in original this was b
                 out.write(newline);
               out.write("    int g = ((Led[i] >>5) & 0xFF)*8;");///in original this was g
                 out.write(newline);
               out.write("    int r = (Led[i] & 0xFF)*8; ");
                 out.write(newline);

               out.write(newline);
               out.write("    *pData++ = g;//originally r");//originally r
                 out.write(newline);
               out.write(  "   *pData++ = r;//originally g");// in original this was g
                 out.write(newline);
               out.write("    *pData++ = b;");// in original, this was b
                out.write(newline);
               out.write(" }");
           ////////////////////////////new section/////////////////////////////////////////
                 } else {
               out.write(" for(int i=0; i < NUM_LEDS; i++) { ");
                 out.write(newline);
               out.write("    int b = ((Led[i] >>10) & 0xFF)*8;");
                 out.write(newline);
               out.write("    int g = ((Led[i] >>5) & 0xFF)*8;");
                 out.write(newline);
               out.write("    int r = (Led[i] & 0xFF)*8; ");
                 out.write(newline);

               out.write(newline);
               out.write("    *pData++ = r;");
                 out.write(newline);
               out.write(  "   *pData++ = g;");
                 out.write(newline);
               out.write("    *pData++ = b;");
                out.write(newline);
               out.write(" }");
                ;}
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
               out.write(newline);
               out.write("  FastSPI_LED.show();");
                out.write(newline);
                 out.write("}");
               out.write(newline);

  /////////////////////////////////////////////
/////////////////////////////////////////////////
boolean otherFrame = true;
//out.write(/*"unsigned int array[] PROGMEM= { "*/" ");
    for (Animation.Frame frame : animation.frames) {
         if (otherFrame) otherFrame = false; else {
     //      out.write(newline);
           out.write(newline);
         //  out.write("unsigned int "+("array")+"[] PROGMEM = {"); //array[] PROGMEM = {);
         }
if(frame.pixels !=null){
out.write("unsigned int "+("array")+(Integer.toString(animation.frames.indexOf(frame)))+"[] PROGMEM = {");
for (int p=0;p<animation.getPixelsTotal();p++) 
{
out.write("0x"+
    Integer.toHexString(((((frame.pixels[p] >> 16) & 0xff)/8)<<10)+
    ((((frame.pixels[p] >> 8) & 0xff)/8)<<5)+
    ((frame.pixels[p] ) & 0xff)/8)+
    ", ");
    }
        
  out.write(newline +"};");
    } else {out.write("");
    }
    }
/////////////////////////////////////////////////////////////////////////////////////
out.write(newline);
out.write(newline);

out.write("void loop() {");
                out.write(newline);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
                boolean pattern = true;
/*  62 */  
/*  63 */       for (Animation.Frame frame : animation.frames) {
/*  64 */         if (pattern) pattern = false; else {
/*  65 */          out.write("");
/*     */         }
                    if(frame.pixels != null){
                        out.write("PoiSonic("+(Integer.toString(frame.getTime()))+", array"
                        +(Integer.toString(animation.frames.indexOf(frame)))+");" +newline);}
                    else{
/*  67 */     
                out.write(""+(frame.getpatternStr())+"("+(Integer.toString(frame.getTime()))+","
                        + " "+(Integer.toString(animation.frames.indexOf(frame)))+");"+newline);
                    }}
                out.write(" }");
out.write(newline);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
                out.write(newline);

                out.write("void PoiSonic(unsigned long time, unsigned  int array[]){");out.write(newline);
                 out.write("unsigned long currentTime = millis();");out.write(newline);
                 out.write(" while (millis()< currentTime + (time)) {");out.write(newline);
         
                        out.write("byte Counter;");
                        out.write("");
                                out.write(newline);
                        out.write( " int i;");out.write(newline);
                        out.write( " int x = NUM_LEDS;");out.write(newline);
                        out.write("int a = " +animation.getPixelsWide()+";");out.write(newline);
                        out.write( "  for (i =0;i<a;i++){  ");out.write(newline);
                        out.write(newline);

                       out.write( "Led[Counter+29]= pgm_read_dword((uint16_t)(&(array[i])));");out.write(newline);
                       out.write( "Led[Counter+28]= pgm_read_dword((uint16_t)(&(array[i+(1*a)])));");out.write(newline);
                       out.write( "Led[Counter+27]= pgm_read_dword((uint16_t)(&(array[i+(2*a)])));");out.write(newline);
                       out.write( "Led[Counter+26]= pgm_read_dword((uint16_t)(&(array[i+(3*a)])));");out.write(newline);
                       out.write( "Led[Counter+25]= pgm_read_dword((uint16_t)(&(array[i+(4*a)])));");out.write(newline);
                       out.write( "Led[Counter+24]= pgm_read_dword((uint16_t)(&(array[i+(5*a)])));");out.write(newline);
                       out.write( "Led[Counter+23]= pgm_read_dword((uint16_t)(&(array[i+(6*a)])));");out.write(newline);
                       out.write( "Led[Counter+22]= pgm_read_dword((uint16_t)(&(array[i+(7*a)])));");out.write(newline);
                       out.write( "Led[Counter+21]= pgm_read_dword((uint16_t)(&(array[i+(8*a)])));");out.write(newline);
                       out.write( "Led[Counter+20]= pgm_read_dword((uint16_t)(&(array[i+(9*a)])));");out.write(newline);
                       out.write( "Led[Counter+19]=pgm_read_dword((uint16_t)(&(array[i+(10*a)])));");out.write(newline);
                       out.write( "Led[Counter+18]= pgm_read_dword((uint16_t)(&(array[i+(11*a)])));");out.write(newline);
                       out.write( "Led[Counter+17]= pgm_read_dword((uint16_t)(&(array[i+(12*a)])));");out.write(newline);
                       out.write( "Led[Counter+16]= pgm_read_dword((uint16_t)(&(array[i+(13*a)])));");out.write(newline);
                       out.write( "Led[Counter+15]= pgm_read_dword((uint16_t)(&(array[i+(14*a)])));");out.write(newline);
                       out.write( "Led[Counter+14]= pgm_read_dword((uint16_t)(&(array[i+(15*a)])));");out.write(newline);
                       out.write( "Led[Counter+13]= pgm_read_dword((uint16_t)(&(array[i+(16*a)])));");out.write(newline);
                       out.write( "Led[Counter+12]= pgm_read_dword((uint16_t)(&(array[i+(17*a)])));");out.write(newline);
                       out.write( "Led[Counter+11]= pgm_read_dword((uint16_t)(&(array[i+(18*a)])));");out.write(newline);
                       out.write( "Led[Counter+10]= pgm_read_dword((uint16_t)(&(array[i+(19*a)])));");out.write(newline);
                       out.write( "Led[Counter+9]= pgm_read_dword((uint16_t)(&(array[i+(20*a)])));");out.write(newline);
                       out.write( "Led[Counter+8]= pgm_read_dword((uint16_t)(&(array[i+(21*a)])));");out.write(newline);
                       out.write( "Led[Counter+7]= pgm_read_dword((uint16_t)(&(array[i+(22*a)])));");out.write(newline);
                       out.write( "Led[Counter+6]= pgm_read_dword((uint16_t)(&(array[i+(23*a)])));");out.write(newline);
                       out.write( "Led[Counter+5]= pgm_read_dword((uint16_t)(&(array[i+(24*a)])));");out.write(newline);
                       out.write( "Led[Counter+4]= pgm_read_dword((uint16_t)(&(array[i+(25*a)])));");out.write(newline);
                       out.write( "Led[Counter+3]= pgm_read_dword((uint16_t)(&(array[i+(26*a)])));");out.write(newline);
                       out.write( "Led[Counter+2]= pgm_read_dword((uint16_t)(&(array[i+(27*a)])));");out.write(newline);
                       out.write( "Led[Counter+1]= pgm_read_dword((uint16_t)(&(array[i+(28*a)])));");out.write(newline);
                       out.write( "Led[Counter+0]= pgm_read_dword((uint16_t)(&(array[i+(29*a)])));");out.write(newline);
             
                         out.write("      show();");out.write(newline);
                       out.write( " delay(2);");out.write(newline);
                       out.write("}");out.write(newline);
    ///////////////                   out.write("delay(100);");out.write(newline);
                       out.write( " } }");out.write(newline);
                       out.write(newline);
                                      
out.write(newline);
out.write("/* Helper functions */");
out.write(newline);
out.write("// Create a 15 bit color value from R,G,B");
out.write(newline);
out.write("unsigned int Color(byte r, byte g, byte b)");
out.write(newline);
out.write("{");
out.write(newline);
out.write("  //Take the lowest 5 bits of each value and append them end to end");
out.write(newline);
out.write("return( ((unsigned int)g & 0x1F )<<10 | ((unsigned int)b & 0x1F)<<5 | (unsigned int)r & 0x1F);}");
out.write(newline);
out.write(newline);
/////////////////////////further functions gor here for the standard patterns.
out.write("unsigned int Wheel(byte WheelPos)");out.write(newline);
out.write("{");out.write(newline);
  out.write("byte r,g,b;");out.write(newline);
  out.write("switch(WheelPos >> 5)");out.write(newline);
  out.write("{");out.write(newline);
    out.write("case 0:");out.write(newline);
      out.write("r=31- WheelPos % 32;");  out.write(newline);
      out.write("g=WheelPos % 32;");      out.write(newline);
     out.write(" b=0;");                  out.write(newline);
      out.write("break;"); out.write(newline);
   out.write(" case 1:");out.write(newline);
      out.write("g=31- WheelPos % 32;");  out.write(newline);
     out.write(" b=WheelPos % 32;");      out.write(newline);
      out.write("r=0;");                  out.write(newline);
      out.write("break;"); out.write(newline);
    out.write("case 2:");out.write(newline);
      out.write("b=31- WheelPos % 32;");   out.write(newline);
      out.write("r=WheelPos % 32; ");     out.write(newline);
      out.write("g=0;   ");               out.write(newline);
      out.write("break;"); out.write(newline);
  out.write("}");out.write(newline);
  out.write("return(Color(r,g,b));");out.write(newline);
out.write("}");out.write(newline);
///////////////////////////////////////////////////////////////
  out.write("unsigned int Counter, Counter2, Counter3;");
out.write(newline);
out.write(newline);

out.write("// Show a colour bar going up from 0 to 9");out.write(newline);
out.write("void ColorUp( unsigned int ColourToUse)");out.write(newline);
out.write("{");out.write(newline);
out.write("  byte Counter;");out.write(newline);
out.write("  for(Counter=0;Counter < NUM_LEDS; Counter++)");out.write(newline);
out.write("  {");out.write(newline);
out.write("   Led[Counter]=ColourToUse;");out.write(newline);
out.write("    show();");out.write(newline);
out.write("    delay(2);");out.write(newline);
out.write("  }  ");out.write(newline);
out.write("}");out.write(newline);
out.write(newline);
out.write("// Show a colour bar going down from 9 to 0");out.write(newline);
out.write("void ColorDown( unsigned int ColourToUse)");out.write(newline);
out.write("{");out.write(newline);
out.write("  byte Counter;");out.write(newline);
out.write("  for(Counter=NUM_LEDS;Counter > 0; Counter--)");out.write(newline);
out.write("  {");out.write(newline);
out.write("    Led[Counter-1]=ColourToUse;");out.write(newline);
out.write("    show();");out.write(newline);
out.write("    delay(2);");out.write(newline);
out.write("  }  ");out.write(newline);
out.write("}");out.write(newline);
out.write(newline);
out.write(newline);

///////////////two new patterns//////////////////////////////////////////////////////////////////////
out.write("void Diamond_Pattern(long time, int frame){");out.write(newline);
  out.write("unsigned long currentTime = millis();");out.write(newline);
   out.write("while (millis()< currentTime + (time)) {");out.write(newline);
    out.write("unsigned int Colour =  Color(255,255,255);//brg");out.write(newline);
    out.write("unsigned int purple = Color(125,85,225);//purple");out.write(newline);
    out.write("unsigned int green =Color(0,255,125);//greenish");out.write(newline);
    out.write("unsigned int yellow = Color(255,255,0);");out.write(newline);
    out.write("unsigned int blue = Color (0,125,225);");out.write(newline);
    out.write("unsigned int red = Color(255,0,0);//red");out.write(newline);
    out.write("unsigned int black =Color(0,0,0);//black");out.write(newline);
    out.write("int del =2; int x=0; int y=0; int p=0; int q=0;");out.write(newline);
    out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = black;");out.write(newline);
out.write("Led[0]=Led[29]=yellow;");out.write(newline);
out.write("Led[1]=Led[28]=black;");out.write(newline);
out.write("//1");out.write(newline);
out.write("Led[14]=Led[15]=black;");out.write(newline);
out.write("Led[2]=Led[3]=Led[4]=Led[5]=Led[6]=Led[7]=Led[8]=Led[9]=Led[10]=Led[11]=Led[12]=Led[13]=Led[16]=Led[17]=Led[18]=Led[19]=Led[20]=Led[21]=Led[22]=Led[23]=Led[24]=Led[25]=Led[26]=Led[27]=purple;");out.write(newline);
out.write("show(); delay(del);");out.write(newline);
out.write("//2");out.write(newline);
out.write("Led[13]=Led[16]=black;");out.write(newline);
out.write("Led[3]=Led[4]=Led[5]=Led[6]=Led[7]=Led[8]=Led[9]=Led[10]=Led[11]=Led[18]=Led[19]=Led[20]=Led[21]=Led[22]=Led[23]=Led[24]=Led[25]=Led[26]=black;");out.write(newline);
out.write("Led[12]=Led[17]=purple;");out.write(newline);
out.write("show();delay(del);");out.write(newline);
out.write("//3");out.write(newline);
out.write("Led[12]=Led[17]=black;");out.write(newline);
out.write("Led[4]=Led[5]=Led[6]=Led[7]=Led[8]=Led[21]=Led[22]=Led[23]=Led[24]=Led[25]=blue;");out.write(newline);
out.write("Led[11]=Led[18]=purple;");out.write(newline);
out.write("Led[14]=Led[15]=green; ");out.write(newline);
out.write("show();delay(del);");out.write(newline);
 out.write("//4");out.write(newline);
out.write("while (x<5){{");out.write(newline);
out.write("Led[8-y]=Led[11-y]=Led[18+y]=Led[21+y]=black;");out.write(newline);
out.write("Led[10-y]=Led[19+y]=purple;");out.write(newline);
out.write("Led[13-y]=Led[16+y]=green;");out.write(newline);
out.write("show();delay(del);");out.write(newline);
out.write("}x = x+1; y = y+1;}  ");out.write(newline);
out.write("//9");out.write(newline);
 out.write("Led[6]=Led[23]=black;");out.write(newline);
 out.write("Led[5]=Led[24]=purple;");out.write(newline);
 out.write("Led[8]=Led[21]=green;");out.write(newline);
 out.write("show();delay(del);");out.write(newline);
out.write("//10");out.write(newline);
 out.write("Led[5]=Led[9]=Led[10]=Led[11]=Led[12]=Led[13]=Led[14]=Led[15]=Led[16]=Led[17]=Led[18]=Led[19]=Led[20]=Led[24]=black;");out.write(newline);
out.write(" Led[4]=Led[25]=purple;");out.write(newline);
 out.write("Led[7]=Led[22]=green;");out.write(newline);
 out.write("show();delay(del);");out.write(newline);
 out.write("//11");out.write(newline);
 out.write("Led[4]=Led[25]=black;");out.write(newline);
 out.write("Led[3]=Led[26]=purple;");out.write(newline);
 out.write("Led[6]=Led[23]=green;");out.write(newline);
 out.write("Led[10]=Led[11]=Led[12]=Led[13]=Led[14]=Led[15]=Led[16]=Led[17]=Led[18]=Led[19]=red;");out.write(newline);
 out.write("show();delay(del); ");out.write(newline);
 out.write("//12");out.write(newline);
 out.write("Led[3]=Led[26]=black;");out.write(newline);
 out.write("Led[5]=Led[24]=green;");out.write(newline);
  out.write("show();delay(del);");out.write(newline);
 out.write("//13");out.write(newline);
 out.write("Led[2]=Led[27]=black;");out.write(newline);
 out.write("Led[4]=Led[25]=green;");out.write(newline);
  out.write("show();delay(del);");out.write(newline);
 out.write("//14");out.write(newline);
 out.write("Led[3]=Led[26]=green;");out.write(newline);
  out.write("show();delay(del);");out.write(newline);
out.write("//15");out.write(newline);
out.write("Led[2]=Led[27]=green;");out.write(newline);
 out.write("show();delay(del);");out.write(newline);
 out.write("//16");out.write(newline);
out.write(" delay(del);");out.write(newline);
out.write(" //17");out.write(newline);
 out.write("Led[2]=Led[27]=black;");out.write(newline);
  out.write("show();delay(del);");out.write(newline);
  out.write("//18");out.write(newline);
  out.write("Led[3]=Led[26]=black;");out.write(newline);
   out.write("show();delay(del);");out.write(newline);
 out.write("//19");out.write(newline);
 out.write("Led[4]=Led[25]=black;");out.write(newline);
 out.write("Led[2]=Led[27]=purple;");out.write(newline);
  out.write("show();delay(del);");out.write(newline);
out.write(" //20");out.write(newline);
 out.write("Led[5]=Led[24]=black;");out.write(newline);
 out.write("Led[3]=Led[26]=purple;");out.write(newline);
  out.write("show();delay(del); ");out.write(newline);
out.write(" //21");out.write(newline);
 out.write("Led[3]=Led[6]=Led[26]=Led[23]=Led[9]=Led[10]=Led[11]=Led[12]=Led[13]=Led[14]=Led[15]=Led[16]=Led[17]=Led[18]=Led[19]=Led[20]=black;");out.write(newline);
 out.write("Led[4]=Led[25]=purple;");out.write(newline);
 out.write("show();delay(del); ");out.write(newline);
 out.write("//22");out.write(newline);
 out.write("Led[4]=Led[7]=Led[22]=Led[25]=black;");out.write(newline);
 out.write("Led[5]=Led[24]=purple;");out.write(newline);
 out.write("Led[8]=Led[9]=Led[10]=Led[11]=Led[12]=Led[13]=Led[14]=Led[15]=Led[16]=Led[17]=Led[18]=Led[19]=Led[20]=Led[21]=green;");out.write(newline);
 out.write("show();delay(del); ");out.write(newline);
 out.write("//23");out.write(newline);
 out.write("Led[5]=Led[8]=Led[21]=Led[24]=black;");out.write(newline);
 out.write("Led[6]=Led[23]=purple;");out.write(newline);
 out.write("show();delay(del); ");out.write(newline);
 out.write("//24");out.write(newline);
 out.write("while (p<5) {{");out.write(newline);
 out.write("Led[6+q]=Led[9+q]=Led[20-q]=Led[23-q]=black;");out.write(newline);
 out.write("Led[7+q]=Led[22-q]=purple;");out.write(newline);
 out.write("Led[4+q]=Led[25-q]=blue;");out.write(newline);
 out.write("show();delay(del); ");out.write(newline);
 out.write("} p=p+1; q=q+1;}");out.write(newline);
 out.write("//29");out.write(newline);
 out.write("Led[3]=Led[4]=Led[5]=Led[6]=Led[7]=Led[8]=Led[9]=Led[10]=Led[11]=Led[18]=Led[19]=Led[20]=Led[21]=Led[22]=Led[23]=Led[24]=Led[25]=Led[26]=black;");out.write(newline);
 out.write("Led[13]=Led[14]=Led[15]=Led[16]=black;");out.write(newline);
 out.write("Led[12]=Led[17]=purple;");out.write(newline);
 out.write("show();delay(del);");out.write(newline);
 out.write("//30");out.write(newline);
 out.write("Led[2]=Led[3]=Led[4]=Led[5]=Led[6]=Led[7]=Led[8]=Led[9]=Led[10]=Led[11]=Led[12]=Led[13]=Led[16]=Led[17]=Led[18]=Led[19]=Led[20]=Led[21]=Led[22]=Led[23]=Led[24]=Led[25]=Led[26]=Led[27]=purple;");out.write(newline);
 out.write("show();delay(del);");out.write(newline);
 out.write("}}");out.write(newline);
out.write("//////////////////////////////////////");out.write(newline);
///////////////////////////////////////////////
out.write("void Red_Chevron(long time, int frame){");out.write(newline);
  out.write("unsigned long currentTime = millis();");out.write(newline);
   out.write("while (millis()< currentTime + (time)) {");out.write(newline);
    out.write("unsigned int Colour =  Color(255,255,255);//brg");out.write(newline);
    out.write("unsigned int green =Color(0,255,125);//greenish");out.write(newline);
    out.write("unsigned int yellow = Color(255,255,0);");out.write(newline);
    out.write("unsigned int red = Color(255,0,0);//red");out.write(newline);
    out.write("unsigned int black =Color(0,0,0);//black");out.write(newline);
    out.write("int n =0; int m =0; int p=0; int q=5;");out.write(newline);
    out.write("int r =0; int s =9; int f=0; int e=5;");out.write(newline);
    out.write("int del =2;");out.write(newline);
    out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = black;");out.write(newline);
out.write("Led[0]=Led[29]=yellow;");out.write(newline);
out.write("Led[1]=Led[28]=black;");out.write(newline);
out.write("//1");out.write(newline);
out.write("Led[2]=Led[3]=Led[4]=Led[5]=Led[6]=Led[7]=Led[8]=Led[9]=Led[10]=Led[11]=Led[12]=red;");out.write(newline);
out.write("Led[2]=Led[13]=Led[15]=Led[26]=black;");out.write(newline);
out.write("Led[14]=Led[27]=yellow;");out.write(newline);
out.write("Led[16]=Led[17]=Led[18]=Led[19]=Led[20]=Led[21]=Led[22]=Led[23]=Led[24]=Led[25]=green;");out.write(newline);
out.write("show();delay(del);");out.write(newline);
out.write("//2");out.write(newline);
out.write("Led[13]=red;");out.write(newline);
out.write("Led[3]=Led[14]=Led[16]=black;");out.write(newline);
out.write("Led[15]=yellow;");out.write(newline);
out.write("show();delay(del);");out.write(newline);
out.write("//3");out.write(newline);
out.write("Led[14]=red;");out.write(newline);
out.write("Led[2]=Led[4]=Led[15]=Led[17]=black;");out.write(newline);
out.write("Led[16]=Led[2]=yellow;");out.write(newline);
out.write("show();delay(del);");out.write(newline);
out.write("//4");out.write(newline);
out.write("while (n<3) {{");out.write(newline);
out.write("Led[n+15]=red;");out.write(newline);
out.write("Led[n+2]= Led[n+4]=Led[n+16]=Led[n+18]=black;");out.write(newline);
out.write("Led[n+17]=Led[n+3]=yellow;");out.write(newline);
out.write("show();delay(del);");out.write(newline);
out.write("}n=n+1;}");out.write(newline);
out.write("//7");out.write(newline);
out.write("while (m<8) {{");out.write(newline);
out.write("Led[m+18]=red;");out.write(newline);
out.write("Led[m+5]=Led[m+7]=Led[m+19]=Led[m+21]=black;");out.write(newline);
out.write("Led[m+20]=Led[m+6]=yellow;");out.write(newline);
out.write("Led[m+4]=green;");out.write(newline);
out.write("show();delay(del);");out.write(newline);
out.write("}m=m+1;}");out.write(newline);
out.write("Led[26]=red;");out.write(newline);
out.write("Led[13]=Led[15]=Led[27]=black;");out.write(newline);
out.write("Led[14]=yellow;");out.write(newline);
out.write("Led[12]=green;");out.write(newline);
out.write("show();delay(del);");out.write(newline);
out.write("//16");out.write(newline);
out.write("delay(del);");out.write(newline);
out.write("//17 ");out.write(newline);
out.write("Led[15]=red;");out.write(newline);
out.write("Led[12]=Led[14]=Led[26]=black;");out.write(newline);
out.write("Led[13]=Led[27]=yellow;");out.write(newline);
out.write("show();delay(del);");out.write(newline);
out.write("//18");out.write(newline);
out.write("Led[14]=red;");out.write(newline);
out.write("Led[11]=Led[13]=Led[25]=Led[27]=black;");out.write(newline);
out.write("Led[12]=Led[26]=yellow;");out.write(newline);
out.write("Led[24]=red;");out.write(newline);
out.write("show();delay(del);");out.write(newline);
out.write("//19 ");out.write(newline);
out.write("Led[13]=red;");out.write(newline);
out.write("Led[10]=Led[12]=Led[24]=Led[26]=black;");out.write(newline);
out.write("Led[11]=Led[25]=Led[27]=yellow;");out.write(newline);
out.write("Led[23]=red;");out.write(newline);
out.write("show();delay(del);");out.write(newline);
out.write("//20");out.write(newline);
out.write("Led[12]=red;");out.write(newline);
out.write("Led[9]=Led[11]=Led[23]=Led[25]=black;");out.write(newline);
out.write("Led[10]=Led[24]=yellow;");out.write(newline);
out.write("Led[22]=red;");out.write(newline);
out.write("show();delay(del);");out.write(newline);
out.write("//21 if r=0 and s =9");out.write(newline);
out.write("while (s>0) {{");out.write(newline);
out.write("Led[11-r]=red;");out.write(newline);
out.write("Led[8-r]=Led[10-r]=Led[22-r]=Led[24-r]=black;");out.write(newline);
out.write("Led[9-r]=Led[23-r]=yellow;");out.write(newline);
out.write("Led[25-r]=green;//was 25");out.write(newline);
out.write("Led[1]=black;");out.write(newline);
out.write("show();delay(del);");out.write(newline);
out.write("}s=s-1;");out.write(newline);
out.write("r=r+1;}");out.write(newline);
out.write("}}");out.write(newline);
out.write("/////////");out.write(newline);

////////////////////////////////////////////////////////////////////
out.write("void Rainbow(unsigned long time, int frame) {");out.write(newline);
out.write("//for scrolling rainbnow effect");out.write(newline);
out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
out.write(" while (millis()< currentTime + (time)) {");out.write(newline);

out.write("for(Counter=0; Counter < 96 ; Counter++)");out.write(newline);
out.write("{");out.write(newline);
out.write("Counter3=Counter * 2;");out.write(newline);
out.write("for(Counter2=0; Counter2 < NUM_LEDS; Counter2++)");out.write(newline);
out.write("{");out.write(newline);
out.write("Led[Counter2] = Wheel(Counter3%95);  //There's only 96 colors in this pallette.");out.write(newline);
out.write("Counter3+=(96 / NUM_LEDS);");out.write(newline);
out.write("}");out.write(newline);
out.write("show();");out.write(newline);
out.write("delay(2);");out.write(newline);
out.write("}}}");out.write(newline);
////////////////////////////////////////////////////////////////////////

out.write(newline);
out.write("void Checker(long time, int frame){");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
 out.write("while (millis()< currentTime + (time)) {");out.write(newline);
 out.write("unsigned int Colour =  (Color(random(0,32),random(0,32),random(0,32)));//brg");out.write(newline);
out.write("unsigned int Colour2 =Color(0,0,0);");out.write(newline);
out.write("unsigned int array1[] ={0,1,2,3,4,10,11,12,13,14,20,21,22,23,24};");out.write(newline);
out.write("unsigned int array2[] ={5,6,7,8,9,15,16,17,18,19,25,26,27,28,29};");out.write(newline);
out.write("for( int i=0;i<15;i++)");out.write(newline);
out.write("Led[array1[i]] = Colour;");out.write(newline);
out.write("for( int i=0;i<15;i++)");out.write(newline);
out.write("Led[array2[i]] =Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("delay (25);");out.write(newline);
out.write("for( int i=0;i<15;i++)");out.write(newline);
out.write("Led[array1[i]] = Colour2;");out.write(newline);
out.write("for( int i=0;i<15;i++)");out.write(newline);
out.write("Led[array2[i]] =Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("delay (25);");out.write(newline);
out.write("}}");out.write(newline);
out.write("");out.write(newline);
out.write(newline);
out.write(newline);
/////////////////////////////////////////////////////////////////////////////
out.write(newline);
out.write("void Atomic (long time, int frame){");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
out.write("unsigned int Colour2 = Color(31,0,0);//brg");out.write(newline);
out.write("unsigned int Colour =Color(0,0,0);");out.write(newline);
out.write("unsigned int Colour1 =Color(0,31,31);");out.write(newline);
out.write("int Led1 =0;");out.write(newline);
out.write("int Led2 =29;");out.write(newline);
out.write("int increment =1;");out.write(newline);
out.write("int increment2=1;");out.write(newline);
out.write("while (millis()< currentTime + (time/2)) {");out.write(newline);
out.write("Led[Led1] =Colour1;");out.write(newline);
out.write("Led[Led2] =Colour2;");out.write(newline);
out.write("show(); delay(5);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("if(Led1>29){increment=-1;}");out.write(newline);
out.write("else if(Led1<0){increment=1;}");out.write(newline);
out.write("Led1 = Led1+increment;");out.write(newline);
out.write("if(Led2<0){increment2=1;}");out.write(newline);
out.write("else if(Led2>29){increment2=-1;}");out.write(newline);
out.write("Led2 = Led2+increment2;");out.write(newline);
out.write("}");out.write(newline);
out.write("Led1 =0;");out.write(newline);
out.write("Led2 =29;");out.write(newline);
out.write("increment =1;");out.write(newline);
out.write("increment2=1;");out.write(newline);
out.write("while (millis()< currentTime + ((time/2))+((time/2))) {");out.write(newline);
out.write("Led[Led1] =Colour1;");out.write(newline);
out.write("Led[Led2] =Colour2;");out.write(newline);
out.write("show(); delay(5);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("if(Led1>29){increment=-1;}");out.write(newline);
out.write("else if(Led1<0){increment=1;}");out.write(newline);
out.write("Led1 = Led1+increment;");out.write(newline);
out.write("if(Led2<0){increment=1;}");out.write(newline);
out.write("else if(Led2>29){increment=-1;}");out.write(newline);
out.write("Led2 = Led2+increment;");out.write(newline);
out.write("}}");out.write(newline);
out.write("");out.write(newline);
out.write(newline);
/////////////////////////////////////////////////////////////////////////////////
out.write("void White_Pattern(long time, int frame){");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
out.write("while (millis()< currentTime + (time)) {");out.write(newline);
 out.write("byte Counter;");out.write(newline);
 out.write("for(Counter=10;Counter < NUM_LEDS; Counter++)");out.write(newline);
 out.write("{   Led[Counter]=Color(15,15,15);");out.write(newline);
   out.write("Led[Counter+1]=Color(0,0,0);");out.write(newline);
   out.write("Led[Counter+2] =Color(31,31,31);");out.write(newline);
   out.write("show();");out.write(newline);
out.write("delay(2);");out.write(newline);
out.write("}  ");out.write(newline);
out.write("for(int x = 0; x < (NUM_LEDS-2) ; x++) Led[x] = Color(0,0,0);");out.write(newline);
out.write("show();");out.write(newline);
out.write("// color on way down");out.write(newline);
out.write("byte Counter2;");out.write(newline);
out.write("for(Counter2=NUM_LEDS;Counter2 > 10; Counter2--)");out.write(newline);
out.write("{Led[Counter2-1]=Color(0,0,0);");out.write(newline);
out.write("Led[Counter2-3]=Color(31,31,31);");out.write(newline);
out.write("show();");out.write(newline);
out.write("delay(2);");out.write(newline);
out.write("}  }}");out.write(newline);
out.write("");out.write(newline);
out.write(newline);
////////////////////////////////////////////////////////////////////////////
out.write("void Blue_flower(long time, int frame){");out.write(newline);
 out.write("unsigned long currentTime = millis();");out.write(newline);
 //out.write("unsigned int n=0;");out.write(newline);
 //out.write("int j=0;");out.write(newline);
   out.write("while (millis()< currentTime + (time)) {");out.write(newline);
 out.write(" byte Counter;");out.write(newline);
 out.write(" for(Counter=0;Counter < NUM_LEDS; Counter++)");out.write(newline);
 out.write(" {");out.write(newline);
out.write("   Led[Counter]=Color(0,0,0);");out.write(newline);
out.write("   Led[Counter+2] =Color(0,31,0);");out.write(newline);
    out.write("show();");out.write(newline);
    out.write("delay(2);");out.write(newline);
  out.write("}  ");out.write(newline);
out.write("// Show a colour bar going down from 9 to 0");out.write(newline);
  out.write("byte Counter2;");out.write(newline);
  out.write("for(Counter2=NUM_LEDS;Counter2 > 0; Counter2--)");out.write(newline);
  out.write("{");out.write(newline);
    out.write("Led[Counter2-1]=Color(31,0,0);");out.write(newline);
    out.write("if(Counter2>3){");out.write(newline);
    out.write("Led[Counter2-3]=Color(0,31,0);}");out.write(newline);
    out.write("show();");out.write(newline);
    out.write("delay(2);");out.write(newline);
  out.write("}  ");out.write(newline);
out.write("}}");out.write(newline);
out.write(newline);
    
/////////////////////////////////////////////////////////////////////////
///////////////////////////////new patterns dawn set/////////////////////////////////////////
out.write("/////////////////////////////////Glass");out.write(newline);
out.write("void Glass (long time, int frame){");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
// turn all leds off>
out.write("unsigned int purple =Color(31,31,0);");out.write(newline);
out.write("unsigned int green=Color(0,0,31);");out.write(newline);
out.write("unsigned int blue =Color(31,0,0);");out.write(newline);
//out.write("for(Counter=0;Counter < NUM_LEDS; Counter++)");out.write(newline);
//  out.write("{    for (int i=0;i<31;i++){");out.write(newline);
//   out.write("Led[Counter]=Color(0,0,0);");out.write(newline);
//    out.write("show();}}");out.write(newline);
out.write("while (millis()< currentTime + (time)) {");out.write(newline);
    out.write("float del = 12.00;");out.write(newline);
    out.write("float del2 = 0.05;");out.write(newline);
  out.write("byte n;");out.write(newline);
 out.write("for(n=2;n < 27; n++)");out.write(newline);
 out.write("{ Led[0]=Led[1]=blue;");out.write(newline);
 out.write("Led[n]=Color(0,31,0);//brg");out.write(newline);
   out.write("Led[n+1]= blue;");out.write(newline);
   out.write("Led[n+2]=Color(0,0,0);");out.write(newline);
   out.write("Led[28]=Led[29]=Led[27]=blue;");out.write(newline);
out.write("show();");out.write(newline);
out.write("delay(del);");out.write(newline);
out.write("del=del -0.50;");out.write(newline);
out.write("}");out.write(newline);
out.write("byte n2;");out.write(newline);
out.write("for(n2=27;n2 > 2; n2--)");out.write(newline);
out.write("{  Led[n2]=blue;");out.write(newline);
   out.write("Led[n2+1]=Color(0,0,0);");out.write(newline);
   out.write("Led[n2+2]=purple;");out.write(newline);
   out.write("Led[29]=Led[28]=Led[27] = blue;");out.write(newline);
  out.write("show();");out.write(newline);
out.write("delay(del2);");out.write(newline);
out.write("del2=del2 +0.50;");out.write(newline);
out.write("}");out.write(newline);
out.write("}}");out.write(newline);
out.write("/////////////////////////////////////////raj2");out.write(newline);
out.write("void Raj2 (long time, int frame){");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
//out.write("// turn all leds off>");out.write(newline);
//out.write("for(Counter=0;Counter < NUM_LEDS; Counter++)");out.write(newline);
//  out.write("{    for (int i=0;i<31;i++){");out.write(newline);
//   out.write("Led[Counter]=Color(0,0,0);");out.write(newline);
//    out.write("show();}}");out.write(newline);
out.write("while (millis()< currentTime + (time)) {");out.write(newline);
    out.write("float del = 11.00;");out.write(newline);
    out.write("float del2 = 0.05;");out.write(newline);
  out.write("byte n;");out.write(newline);
 out.write("for(n=0;n < 15; n++)");out.write(newline);
 out.write("{ Led[n]=Color(0,0,0);");out.write(newline);
   out.write("Led[n+1]=Color(0,31,0);");out.write(newline);
   out.write("Led[n+2]=Color(0,0,0);");out.write(newline);
   out.write("Led[n+3]=Led[n+4]=Led[n+5] =Color(31,0,0);");out.write(newline);
   out.write("Led[n+6]=Led[n+7]=Led[n+8] =Color(31,15,0);");out.write(newline);
   out.write("Led[n+9]=Led[n+10]=Led[n+11] =Color(31,31,0);");out.write(newline);
   out.write("Led[n+12]=Led[n+13]=Color(31,31,15);");out.write(newline);
   out.write("Led[n+14] =Color(0,31,0);");out.write(newline);
   out.write("Led[n+15]=Color(0,0,0);");out.write(newline);
out.write("show();");out.write(newline);
out.write("delay(del);");out.write(newline);
out.write("del=del -0.85;");out.write(newline);
out.write("}");out.write(newline);
out.write("byte n2;");out.write(newline);
out.write("for(n2=15;n2 > 0; n2--)");out.write(newline);
out.write("{  Led[n2]=Color(0,0,0);");out.write(newline);
   out.write("Led[n2+1]=Color(0,31,0);");out.write(newline);
   out.write("Led[n2+2]=Color(0,0,0);");out.write(newline);
   out.write("Led[n2+3]=Led[n2+4]=Led[n2+5] =Color(31,0,0);");out.write(newline);
   out.write("Led[n2+6]=Led[n2+7]=Led[n2+8] =Color(31,51,0);");out.write(newline);
   out.write("Led[n2+9]=Led[n2+10]=Led[n2+11] =Color(31,31,0);");out.write(newline);
   out.write("Led[n2+12]=Led[n2+13]=Color(31,31,15);");out.write(newline);
   out.write("Led[n2+14] =Color(0,31,0);");out.write(newline);
   out.write("Led[n2+15] = Color(0,0,0);");out.write(newline);
  out.write("show();");out.write(newline);
out.write("delay(del2);");out.write(newline);
out.write("del2=del2 +0.85;");out.write(newline);
out.write("}");out.write(newline);
out.write("}}");out.write(newline);
out.write("");out.write(newline);
out.write("////////////////////////////////////////////////////raj");out.write(newline);
out.write("void Raj (long time, int frame){");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
//out.write("// turn all leds off>");out.write(newline);
//out.write("for(Counter=0;Counter < NUM_LEDS; Counter++)");out.write(newline);
//  out.write("{    for (int i=0;i<31;i++){");out.write(newline);
//   out.write("Led[Counter]=Color(0,0,0);");out.write(newline);
//    out.write("show();}}");out.write(newline);
out.write("while (millis()< currentTime + (time)) {");out.write(newline);
    out.write("float del = 9.00;");out.write(newline);
    out.write("float del2 = 0.15;");out.write(newline);
  out.write("byte n;");out.write(newline);
 out.write("for(n=0;n < 15; n++)");out.write(newline);
 out.write("{ Led[n]=Color(0,0,0);");out.write(newline);
   out.write("Led[n+1]=Led[n+2]=Color(31,0,0);");out.write(newline);
   out.write("Led[n+3]=Led[n+4]=Led[n+5] =Color(31,15,0);");out.write(newline);
   out.write("Led[n+6]=Led[n+7]=Led[n+8] =Color(31,31,0);");out.write(newline);
   out.write("Led[n+9]=Led[n+10]=Led[n+11] =Color(31,31,15);");out.write(newline);
   out.write("Led[n+12]=Led[n+13]=Led[n+14] =Color(0,31,31);");out.write(newline);
   out.write("Led[n+15]=Color(0,0,0);");out.write(newline);
out.write("show();");out.write(newline);
out.write("delay(del);");out.write(newline);
out.write("del=del -0.65;");out.write(newline);
out.write("}");out.write(newline);
out.write("byte n2;");out.write(newline);
out.write("for(n2=15;n2 > 0; n2--)");out.write(newline);
out.write("{  Led[n2]=Color(0,0,0);");out.write(newline);
   out.write("Led[n2+1]=Led[n2+2]=Color(31,0,0);");out.write(newline);
   out.write("Led[n2+3]=Led[n2+4]=Led[n2+5] =Color(31,15,0);");out.write(newline);
   out.write("Led[n2+6]=Led[n2+7]=Led[n2+8] =Color(31,31,0);");out.write(newline);
   out.write("Led[n2+9]=Led[n2+10]=Led[n2+11] =Color(31,31,15);");out.write(newline);
   out.write("Led[n2+12]=Led[n2+13]=Led[n2+14] =Color(0,31,31);");out.write(newline);
    out.write("Led[n2+15] = Color(0,0,0);");out.write(newline);
  out.write("show();");out.write(newline);
out.write("delay(del2);");out.write(newline);
out.write("del2=del2 +0.65;");out.write(newline);
out.write("}");out.write(newline);
out.write("}}");out.write(newline);
out.write("");out.write(newline);
out.write("///////////////////////////loading///////////////////////////////////////////");out.write(newline);
 out.write("void Loading (long time, int frame){");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
    out.write("for(Counter=0;Counter < NUM_LEDS; Counter++)");out.write(newline);
  out.write("{    for (int i=0;i<30;i++){");out.write(newline);
   out.write("Led[Counter]=Color(0,0,0);");out.write(newline);
    out.write("show();}}");out.write(newline);
out.write("while (millis()< currentTime + (time)) {");out.write(newline);
  out.write("byte Counter;");out.write(newline);
out.write("");out.write(newline);
  out.write("for(Counter=0;Counter < NUM_LEDS; Counter++)");out.write(newline);
  out.write("{");out.write(newline);
    out.write("for (int i=0;i<30;i++){");out.write(newline);
   out.write("Led[Counter]=Color(i,0,0);");out.write(newline);
    out.write("show();");out.write(newline);
    out.write("delay(time/(30*31));");out.write(newline);
  out.write("}");out.write(newline);
 out.write("}");out.write(newline);
out.write("}}");out.write(newline);
out.write("/////////////////////////////////////pulsate");out.write(newline);
out.write("void Pulsate (long time, int frame){");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
 out.write("int i=0;");out.write(newline);
 out.write("byte Counter;");out.write(newline);
 out.write("int fade =1;");out.write(newline);
out.write("");out.write(newline);
out.write("while (millis()< currentTime + (time)) {");out.write(newline);
  out.write("for(Counter=0;Counter < NUM_LEDS; Counter++)");out.write(newline);
  out.write("Led[Counter]=Color(i,0,0);");out.write(newline);
  out.write("show();");out.write(newline);
  out.write("// i=i+1;");out.write(newline);
  out.write("//  delay(time/31);");out.write(newline);
out.write("");out.write(newline);
out.write("if (i >30){fade = -1;}");out.write(newline);
out.write("else if(i<2){fade = 1;}");out.write(newline);
out.write("i=i+fade;");out.write(newline);
      out.write("delay(20);");out.write(newline);
out.write("");out.write(newline);
out.write("}");out.write(newline);
  out.write("}");out.write(newline);
out.write("");out.write(newline);
out.write("/////////////////////////////////////////////////////////////segment");out.write(newline);
out.write("void Segment(unsigned long time, int frame) {");out.write(newline);
out.write("unsigned int Counter;");out.write(newline);
out.write("unsigned int Counter1;");out.write(newline);
out.write("unsigned int Counter2;");out.write(newline);
out.write("unsigned int Counter3;");out.write(newline);
out.write("unsigned int Counter4;");out.write(newline);
out.write("unsigned int x = 2;");out.write(newline);
out.write("unsigned int y = 1;");out.write(newline);
out.write("unsigned int Colour =Color(31,31,31);");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
 out.write("while (millis()< currentTime + (time)) {");out.write(newline);
  out.write("for(Counter=0; Counter < 14 ; Counter++)");out.write(newline);
out.write("{if(x % 2 ==0)");out.write(newline);
out.write("{Led[Counter]=Colour;}");out.write(newline);
out.write("else {Led[Counter]=Color(0,0,0);}}");out.write(newline);
 out.write("for(Counter1=14; Counter1 < 18 ; Counter1++)");out.write(newline);
out.write("{if((x+y) % 2 ==0)");out.write(newline);
out.write("{Led[Counter1]=Colour;}");out.write(newline);
out.write("else {Led[Counter1]=Color(0,0,0);}}");out.write(newline);
 out.write("for(Counter4=18; Counter4 < 22 ; Counter4++)");out.write(newline);
out.write("{if(x % 3 ==0)");out.write(newline);
out.write("{Led[Counter4]=Colour;}");out.write(newline);
out.write("else {Led[Counter4]=Color(0,0,0);}}");out.write(newline);
out.write("for(Counter2=22; Counter2 < 25 ; Counter2++)");out.write(newline);
out.write("{if((x+y) % 3 == 0)");out.write(newline);
out.write("{Led[Counter2]=Colour;}");out.write(newline);
out.write("else{Led[Counter2]=Color(0,0,0);}}");out.write(newline);
out.write("for(Counter2=25; Counter2 < 30 ; Counter2++)");out.write(newline);
out.write("{if(x % 5 == 0)");out.write(newline);
out.write("{Led[Counter2]=Colour;}");out.write(newline);
out.write("else{Led[Counter2]=Color(0,0,0);}}");out.write(newline);
out.write("show();");out.write(newline);
out.write("delay(15);");out.write(newline);
out.write("x = x+y;");out.write(newline);
 out.write("}}");out.write(newline);
 out.write("/////////////////////////////////////////////////////spot lines");out.write(newline);
 out.write("void Spot_line(unsigned long time, int frame) {");out.write(newline);
out.write("unsigned int x;");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
 out.write("while (millis()< currentTime + (time)) {");out.write(newline);
 out.write("Led[5]=Led[12]=Led[13]=Led[18]=Led[19]=Led[20]=Led[26]=Led[27]=Led[28]=Led[29]=Color(31,0,0);");out.write(newline);
 out.write("show();");out.write(newline);
 out.write("delay(10);");out.write(newline);
 out.write("for(x=0;x<30;x++)");out.write(newline);
 out.write("{Led[x]=Color(0,0,0);}");out.write(newline);
 out.write("show();");out.write(newline);
 out.write("delay(30);");out.write(newline);
 out.write("}}");out.write(newline);
 out.write("/////////////////////////////////////////////////////////////b0x");out.write(newline);
out.write("void Box_line(unsigned long time, int frame) {");out.write(newline);
out.write("unsigned int x;");out.write(newline);
out.write("unsigned int Colour = Color(31,0,0);");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
 out.write("while (millis()< currentTime + (time)) {");out.write(newline);
out.write("for(x=0;x<30;x++)");out.write(newline);
 out.write("{Led[x]=Colour;}");out.write(newline);
 out.write("show();");out.write(newline);
 out.write("delay(10);");out.write(newline);
out.write("for(x=0;x<30;x++)");out.write(newline);
 out.write("{Led[x]=Color(0,0,0);}");out.write(newline);
 out.write("show();");out.write(newline);
   out.write("Led[0]=Led[29]=Colour;");out.write(newline);
     out.write("show();");out.write(newline);
      out.write("delay(15);");out.write(newline);
out.write("for(x=0;x<30;x++)");out.write(newline);
 out.write("{Led[x]=Colour;}");out.write(newline);
 out.write("show();");out.write(newline);
 out.write("delay(10);");out.write(newline);
 out.write("for(x=0;x<30;x++)");out.write(newline);
 out.write("{Led[x]=Color(0,0,0);}");out.write(newline);
 out.write("show();");out.write(newline);
 out.write("delay(50);");out.write(newline);
 out.write("}}");out.write(newline);
out.write("");out.write(newline);
 out.write("/////////////////////////////////////////////////////////////////Spiral_70s");out.write(newline);
 out.write("void Spiral_70s(unsigned long time, int frame) {");out.write(newline);
out.write("unsigned int Colour = Color(31,0,0);");out.write(newline);
out.write("unsigned int Colour1 = Color(0,31,0);");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
out.write("unsigned int y=0;");out.write(newline);
out.write("unsigned int n=15;");out.write(newline);
 out.write("while (millis()< currentTime + (time)) {");out.write(newline);
out.write("Led[y]=Color(31,31,31);");out.write(newline);
out.write("Led[y+1]=Led[y+2]=Led[y+3]=Led[y+4]=Led[y+5]=Led[y+6]=Colour;");out.write(newline);
out.write("Led[n]=Color(31,31,31);");out.write(newline);
out.write("Led[n+1]=Led[n+2]=Led[n+3]=Led[n+4]=Led[n+5]=Led[n+6]=Colour1;");out.write(newline);
    out.write("show();");out.write(newline);
  out.write("delay(4);");out.write(newline);
  out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Color(0,0,0);");out.write(newline);
out.write("show();");out.write(newline);
out.write("y=y++;");out.write(newline);
out.write("n=n++;");out.write(newline);
out.write("if (y>26){y=0;");out.write(newline);
out.write("Colour =  (Color(random(0,32),random(0,32),random(0,32)));}//brg");out.write(newline);
out.write("if(n>26) {n=0;");out.write(newline);
out.write("Colour1 =  (Color(random(0,32),random(0,32),random(0,32)));}//brg");out.write(newline);
out.write("}}");out.write(newline);
out.write("");out.write(newline);
out.write("//////////////////////////////////////////////////");out.write(newline);


///////////////////////////////end of dawn set///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
out.write("void Squares(long time, int frame){");out.write(newline);
out.write("  unsigned long currentTime = millis();");out.write(newline);
out.write("   while (millis()< currentTime + (time)) {");out.write(newline);
out.write("    unsigned int Colour2 =  (Color(random(0,32),random(0,32),random(0,32)));//brg");out.write(newline);
out.write("    unsigned int Colour =Color(0,0,0);");out.write(newline);
out.write("    unsigned int Colour1 =(Color(random(0,32),random(0,32),random(0,32)));");out.write(newline);
out.write("    unsigned int Colour3 = (Color(random(0,32),random(0,32),random(0,32)));");out.write(newline);
out.write("    int del =15;");out.write(newline);
out.write("//1");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3]= Colour2;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//2");out.write(newline);
/*out.write("Led[0]=Led[1]=Led[2]=Led[3]= Colour2;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//3");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3]=Colour2;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//4");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3]= Colour2;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//5");out.write(newline);
*/out.write("Led[15]=Led[16]=Led[17]=Led[18]=Led[19]=Led[20]= Colour1;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//6");out.write(newline);
/*out.write("Led[15]=Led[16]=Led[17]=Led[18]=Led[19]=Led[20]= Colour1;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//7");out.write(newline);
out.write("Led[15]=Led[16]=Led[17]=Led[18]=Led[19]=Led[20]= Colour1;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//8");out.write(newline);
out.write("Led[15]=Led[16]=Led[17]=Led[18]=Led[19]=Led[20]= Colour1;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//9");out.write(newline);
out.write("Led[15]=Led[16]=Led[17]=Led[18]=Led[19]=Led[20]= Colour1;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//10");out.write(newline);
out.write("Led[15]=Led[16]=Led[17]=Led[18]=Led[19]=Led[20]= Colour1;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//11");out.write(newline);
*/out.write("Led[10]=Led[11]=Led[12]=Colour3;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//12");out.write(newline);
/*out.write("Led[10]=Led[11]=Led[12]=Colour3;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//13");out.write(newline);
out.write("Led[10]=Led[11]=Led[12]=Colour3;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//14");out.write(newline);
out.write("Led[10]=Led[11]=Led[12]=Colour3;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//15");out.write(newline);
*/out.write("Led[21]=Led[22]=Led[23]=Led[24]=Led[25]=Led[26]=Led[27]=Led[28]= Colour2;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//16");out.write(newline);
/*out.write("Led[21]=Led[22]=Led[23]=Led[24]=Led[25]=Led[26]=Led[27]=Led[28]= Colour2;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//17");out.write(newline);
out.write("Led[21]=Led[22]=Led[23]=Led[24]=Led[25]=Led[26]=Led[27]=Led[28]= Colour2;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//18");out.write(newline);
out.write("Led[21]=Led[22]=Led[23]=Led[24]=Led[25]=Led[26]=Led[27]=Led[28]= Colour2;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//19");out.write(newline);
out.write("Led[21]=Led[22]=Led[23]=Led[24]=Led[25]=Led[26]=Led[27]=Led[28]= Colour2;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//20");out.write(newline);
out.write("Led[21]=Led[22]=Led[23]=Led[24]=Led[25]=Led[26]=Led[27]=Led[28]= Colour2;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//21");out.write(newline);
out.write("Led[21]=Led[22]=Led[23]=Led[24]=Led[25]=Led[26]=Led[27]=Led[28]= Colour2;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//22");out.write(newline);
*/out.write("Led[17]=Led[18]=Led[19]= Colour1;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//23");out.write(newline);
/*out.write("Led[17]=Led[18]=Led[19]= Colour1;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//24");out.write(newline);
out.write("Led[17]=Led[18]=Led[19]= Colour1;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//25");out.write(newline);
*/out.write("Led[5]=Led[6]=Led[7]=Led[8]=Led[9]= Colour1;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//26");out.write(newline);
/*out.write("Led[5]=Led[6]=Led[7]=Led[8]=Led[9]= Colour1;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//27");out.write(newline);
out.write("Led[5]=Led[6]=Led[7]=Led[8]=Led[9]= Colour1;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//28");out.write(newline);
out.write("Led[5]=Led[6]=Led[7]=Led[8]=Led[9]= Colour1;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//29");out.write(newline);
out.write("Led[5]=Led[6]=Led[7]=Led[8]=Led[9]= Colour1;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//30");out.write(newline);
*/out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("}}");out.write(newline);
out.write(newline);
///////////////////////////////////////////////////////////////////////////////
out.write(newline);
out.write("void Spokes(long time, int frame){");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
out.write("unsigned int Colour = Color(0,0,0);");out.write(newline);
out.write("unsigned int del =((time/10)/2);");out.write(newline);
out.write("while (millis()< currentTime + (time)) {");out.write(newline);
   out.write("byte Counter;");out.write(newline);
 out.write("for(Counter=0;Counter < NUM_LEDS; Counter++)");out.write(newline);
 out.write("{    Led[Counter]=Colour;}");out.write(newline);
out.write("show();");out.write(newline);
out.write("delay (7);");out.write(newline);
 out.write("for(Counter=0;Counter < NUM_LEDS; Counter++)");out.write(newline);
 out.write("{    Led[Counter]=Color(0,0,0);}");out.write(newline);
out.write("show();");out.write(newline);
out.write("delay(del);");out.write(newline);
out.write("Colour = (Color(random(0,32),random(0,32),random(0,32)));");out.write(newline);
out.write("del = del*0.92;");out.write(newline);
out.write("}}");out.write(newline);
out.write("");out.write(newline);

//////////////////////////////////////////////////////////////////////////////
out.write(newline);
out.write("void Random_Colour_Dots(long time, int frame)");out.write(newline);
out.write("{");out.write(newline);
 out.write("//scatter colour lines randomly ");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
 out.write("while (millis()< currentTime + (time)) {");out.write(newline);
  out.write("for(int x = 0 ; x < NUM_LEDS ; x++)");out.write(newline);
    out.write("Led[x] = 0;");out.write(newline);
    out.write("delay(5);");out.write(newline);
  out.write("Led[random(0,29)] = (Color(random(0,32),random(0,32),random(0,32)));");out.write(newline);
  out.write("Led[random(0,29)] = (Color(random(0,32),random(0,32),random(0,32)));");out.write(newline);
  out.write("show();");out.write(newline);
out.write("delay (5);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++)");out.write(newline);
    out.write("Led[x] = 0;");out.write(newline);
out.write("delay (1);");out.write(newline);
out.write("}}");out.write(newline);
out.write(newline);
//////////////////////////////////////////////////////////////////////////////////////
out.write("void White_Petal (long time,int frame){");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
out.write("unsigned int Colour =Color(0,0,0);");out.write(newline);
out.write("unsigned int Colour1 =Color(31,31,31);");out.write(newline);
out.write("while (millis()< currentTime + (time)) {");out.write(newline);
 out.write("ColorUp(Color(31,31,31));");out.write(newline);
 out.write("ColorDown(Color(0,0,0));");out.write(newline);
out.write("}");out.write(newline);
out.write("}");out.write(newline);
out.write("");out.write(newline);
out.write(newline);
////////////////////////////////////////////

out.write(newline);
out.write("void null (long time,int frame){");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
out.write("unsigned int Colour =Color(0,0,0);");out.write(newline);
out.write(" while (millis()< currentTime + (time)) {");out.write(newline);
out.write("  for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("   show();");out.write(newline);
out.write("   delay(10);");out.write(newline);
out.write("}}");out.write(newline);
out.write(newline);
out.write(newline);


////////////////////////////////////////////////
out.write("void Atom_Mover (long time, int frame){");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
out.write("unsigned int Colour =Color(0,0,0);");out.write(newline);
out.write("unsigned int Colour1 =Color(31,31,31);");out.write(newline);
out.write("int Led1 =0;int Led29 =29;int Led15 =15;");out.write(newline);
out.write("int increment =1;int increment2=1;int increment3 =1;");out.write(newline);
out.write("while (millis()< currentTime + (time)) {");out.write(newline);
out.write("Led[Led1]=Led[Led29]=Colour1;");out.write(newline);
out.write("show(); delay(2);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("if(Led1>29){increment=-1;}");out.write(newline);
out.write("else if(Led1<0){increment=1;}");out.write(newline);
out.write("Led1 = Led1+increment;");out.write(newline);
out.write("if(Led29<0){increment2=1;}");out.write(newline);
out.write("else if(Led29>29){increment2=-1;}");out.write(newline);
out.write("Led29 = Led29+increment2;");out.write(newline);
out.write("}");out.write(newline);
out.write("Led1 =0;Led29 =29;");out.write(newline);
out.write("increment =1; increment2=1; increment3=1;");out.write(newline);
out.write("}");out.write(newline);
out.write(newline);
//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////
out.write(newline);
out.write("void Chaser(long time, int frame){");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
out.write("unsigned int Colour2 = Color(31,0,0);//brg");out.write(newline);
out.write("unsigned int Colour =Color(0,0,0);");out.write(newline);
out.write("unsigned int Colour1 =Color(0,31,31);");out.write(newline);
out.write("int Led1 =0;");out.write(newline);
out.write("int Led2 =29;");out.write(newline);
out.write("int increment =1;");out.write(newline);
out.write("int increment2=1;");out.write(newline);
out.write("while (millis()< currentTime + (time)) {");out.write(newline);
out.write("Led[Led1] =Colour1;");out.write(newline);
out.write("Led[Led2] =Colour2;");out.write(newline);
out.write("show(); delay(1);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour;");out.write(newline);
out.write("show();");out.write(newline);
out.write("if(Led1>29){increment=-1;}");out.write(newline);
out.write("else if(Led1<0){increment=1;}");out.write(newline);
out.write("Led1 = Led1+increment;");out.write(newline);
out.write("if(Led2<0){increment2=1;}");out.write(newline);
out.write("else if(Led2>29){increment2=-1;}");out.write(newline);
out.write("Led2 = Led2+increment2;");out.write(newline);
out.write("}");out.write(newline);
out.write("Led1 =0;");out.write(newline);
out.write("Led2 =29;");out.write(newline);
out.write("increment =1;");out.write(newline);
out.write("increment2=1;");out.write(newline);
out.write("}");out.write(newline);
out.write(newline);
out.write(newline);
/////////////////////////////////////////////////////////////////////////////////////
out.write("void Count_In(long time, int frame){");out.write(newline);
out.write("unsigned int Colour =Color(0,5,0);");out.write(newline);
out.write("unsigned int Blank = Color(0,0,0);");out.write(newline);
out.write("unsigned int del =750;");out.write(newline);
out.write("for( int i=0;i<30;i++)");out.write(newline);
out.write("Led[i] = Blank;");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3] =Colour; show();delay(250);");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3] = Blank; show();delay(del);");out.write(newline);
out.write("Led[0]=Led[1]=Led[2] =Colour; show(); delay(250);");out.write(newline);
out.write("Led[0]=Led[1]=Led[2] = Blank; show(); delay(del);");out.write(newline);
out.write("Led[0]=Led[1] =Colour; show(); delay(250);");out.write(newline);
out.write("Led[0]=Led[1] = Blank; show(); delay(del);");out.write(newline);
out.write("unsigned long currentTime = millis();");out.write(newline);
out.write("while (millis()< currentTime + (((time)))-5000) {");out.write(newline);
out.write("Led[0] =Colour; show(); delay(250);");out.write(newline);
out.write("Led[0] = Blank; show(); delay(del);");out.write(newline);
out.write("}");out.write(newline);
out.write("Led[0]=Led[1] =Colour; show(); delay(250);");out.write(newline);
out.write("Led[0]=Led[1] = Blank; show(); delay(del);");out.write(newline);
out.write("Led[0]=Led[1] =Colour; show(); delay(250);");out.write(newline);
out.write("Led[0]=Led[1] = Blank; show(); delay(del);");out.write(newline);
out.write("}");out.write(newline);
out.write("");out.write(newline);
out.write(newline);

/////////////////////////////////////////////////////////////////////////////////////////////////
out.write("");out.write(newline);
out.write("void Spiral(long time, int frame){");out.write(newline);
out.write("  unsigned long currentTime = millis();");out.write(newline);
out.write("   while (millis()< currentTime + (time)) {");out.write(newline);
out.write("    unsigned int Colour =  Color(255,255,255);//brg");out.write(newline);
out.write("    unsigned int Colour2 =Color(0,0,0);");out.write(newline);
out.write("    int del =2;");out.write(newline);
out.write("//1");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3]=Led[4]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//2");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//3");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//4");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//5");out.write(newline);
out.write("Led[0]=Led[1]=Colour; show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//6");out.write(newline);
out.write("Led[0]=Led[1]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//7");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[20]=Led[22]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//8");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[19]=Led[20]=Led[21]=Led[22]=Led[23]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//9");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[17]=Led[18]=Led[19]=Led[21]=Led[22]=Led[23]=Led[24]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//10");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[16]=Led[17]=Led[18]=Led[19]=Led[23]=Led[24]=Led[25]=Led[26]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//11");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[15]=Led[16]=Led[17]=Led[25]=Led[26]=Led[27]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//12");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3]=Led[15]=Led[16]=Led[25]=Led[26]=Led[27]=Led[28]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//13");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3]=Led[4]=Led[14]=Led[15]=Led[27]=Led[28]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//14");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3]=Led[4]=Led[13]=Led[14]=Led[27]=Led[28]=Led[29]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//15");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3]=Led[4]=Led[5]=Led[13]=Led[14]=Led[20]=Led[21]=Led[22]=Led[28]=Led[29]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//16");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3]=Led[4]=Led[5]=Led[6]=Led[13]=Led[14]=Led[20]=Led[21]=Led[22]=Led[23]=Led[28]=Led[29]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//17");out.write(newline);
out.write("Led[0],Led[1],Led[2],Led[3],Led[4],Led[5],Led[6],Led[7],Led[14],Led[21]=Led[22]=Led[23]=Led[23]=Led[29]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//18");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3]=Led[4]=Led[5]=Led[6]=Led[7]=Led[8]=Led[14]=Led[15]=Led[21]=Led[22]=Led[23]=Led[28]=Led[29]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//19");out.write(newline);
out.write("Led[0]=Led[2]=Led[3]=Led[4]=Led[5]=Led[6]=Led[7]=Led[8]=Led[15]=Led[16]=Led[17]=Led[20]=Led[21]=Led[28]=Led[29]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//20");out.write(newline);
out.write("Led[0]=Led[3]=Led[4]=Led[5]=Led[6]=Led[7]=Led[8]=Led[9]=Led[17]=Led[18]=Led[19]=Led[20]=Led[28]=Led[29]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//21");out.write(newline);
out.write("Led[0]=Led[4]=Led[5]=Led[6]=Led[7]=Led[8]=Led[9]=Led[10]=Led[11]=Led[27]=Led[28]=Led[29]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//22");out.write(newline);
out.write("Led[0]=Led[5]=Led[6]=Led[7]=Led[8]=Led[9]=Led[10]=Led[11]=Led[12]=Led[27]=Led[28]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//23");out.write(newline);
out.write("Led[0]=Led[6]=Led[7]=Led[8]=Led[9]=Led[10]=Led[11]=Led[12]=Led[13]=Led[24]=Led[25]=Led[26]=Led[27]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//24");out.write(newline);
out.write("Led[0]=Led[1]=Led[7]=Led[8]=Led[9]=Led[10]=Led[11]=Led[12]=Led[13]=Led[14]=Led[15]=Led[22]=Led[23]=Led[24]=Led[25]=Led[26]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//25");out.write(newline);
out.write("Led[0]=Led[1]=Led[9]=Led[10]=Led[11]=Led[12]=Led[13]=Led[14]=Led[15]=Led[16]=Led[17]=Led[20]=Led[21]=Led[22]=Led[23]=Led[24]=Led[25]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//26");out.write(newline);
out.write("Led[0]=Led[1]=Led[11]=Led[12]=Led[13]=Led[14]=Led[15]=Led[16]=Led[17]=Led[18]=Led[17]=Led[20]=Led[21]=Led[22]=Led[23]=Led[24]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//27");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[13]=Led[14]=Led[15]=Led[16]=Led[17]=Led[18]=Led[19]=Led[20]=Led[21]=Led[22]=Led[23]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//28");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[15]=Led[16]=Led[17]=Led[18]=Led[19]=Led[20]=Led[21]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//29");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("//30");out.write(newline);
out.write("Led[0]=Led[1]=Led[2]=Led[3]=Led[4]= Colour;show();delay(del);");out.write(newline);
out.write("for(int x = 0 ; x < NUM_LEDS ; x++) Led[x] = Colour2;");out.write(newline);
out.write("show();");out.write(newline);
out.write("}}");out.write(newline);
out.write(newline);



////////////////////////////////////////////////////////////////////////////////////////////
     /////////////////////////////////////////////////////////////////////////////////////////////////

/*     */ 
/* 102 */       out.close();
/*     */     } catch (FileNotFoundException e) {
/* 104 */       this.lastError = e.getMessage();
/* 105 */       e.printStackTrace();
/* 106 */       return false;
/*     */     } catch (IOException e) {
/* 108 */       this.lastError = e.getMessage();
/* 109 */       e.printStackTrace();
/* 110 */       return false;
/*     */     }
/*     */ 
/* 113 */     return true;
/*     */   }
/*     */
///////////////////////////////////////////////////////////////////////////////////////////
/*   public Animation load(String filename) {
     Animation animation = null;
     try {
       FileInputStream fis = new FileInputStream(filename);
       InputStreamReader in = new InputStreamReader(fis, "UTF-8");
      
       int time = -1;
       boolean skipTimeRead = false;
 
      int fileFormat = in.read();
       if (fileFormat == 3) {
         int width = in.read();
         int height = in.read();
         int paletteCode = in.read();
         animation = new Animation(width, height, paletteCode);
       } else if (fileFormat == 2) {
         int width = in.read();
         int height = in.read();
         animation = new Animation(width, height, 1);
       }
       else if (new File(filename).length() % 66L == 0L) {
         animation = new Animation(8, 8, 1);
         time = fileFormat;
         fileFormat = 1;
         skipTimeRead = true;
       } else {
         this.lastError = "Unable to read file. Wrong file version.";
         return null;
       }
        while (true)
       {
         if (skipTimeRead) skipTimeRead = false; else
           time = in.read();
        if (time < 0)
           break;
     //    int[] pixels = new int[animation.getPixelsTotal()];
     //    for (int i = 0; i < pixels.length; i++)
    //      pixels[i] = in.read();
///////also need to add the image values for patterns.
         Animation.Frame frame = animation.new Frame ();
         frame.setTime(time);
      //   frame.setPixels(pixels);
        animation.frames.add(frame);
       }
       animation.frames.remove(0);
       animation.gotoFrame(0);
 
       in.close();
     } catch (FileNotFoundException e) {
       this.lastError = e.getMessage();
       e.printStackTrace();
   animation = null;
    } catch (IOException e) {
       this.lastError = e.getMessage();
       e.printStackTrace();
       animation = null;
     }
     return animation;
   }*/
/////////////////////////////////////////////////////////////////////////////////
   public Animation load(String filename) {
     Animation animation = null;
     FrameToolbox frameToolbox =null;
         int width = 150;
         int height = 30;
         int paletteCode = 2;
    try {
  Scanner sin = new Scanner(new FileReader(filename));
  /////////////////////////////////////

  //////////////////////////////////////////////////////////
  animation = new Animation(width, height, paletteCode);
  
    while ( sin.hasNextLine() ){
      String time = sin.nextLine();
      String patternStr = sin.nextLine();
   //   String timeT = time.trim();
   //   String patternStrT = patternStr.trim();

      Animation.Frame frame = animation.new Frame ();
      frame.setTime(Integer.parseInt(time));
      frame.setpatternStr(patternStr);

       ImageIcon icon = createImageIcon("images/"  +patternStr + ".gif");
       Image imageP = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
       frame.setpatternImage(imageP);

      animation.frames.add(frame);
       }

       animation.frames.remove(0);
       animation.gotoFrame(0);
       sin.close();
     } catch (FileNotFoundException e) {
       this.lastError = e.getMessage();
       e.printStackTrace();
   animation = null;
    } catch (IOException e) {
       this.lastError = e.getMessage();
       e.printStackTrace();
       animation = null;
     }
     return animation;
   }

///////////////////////////////////////////////////////////////////
/*private static Object resizeArray (Object oldArray, int newSize) {
   int oldSize = java.lang.reflect.Array.getLength(oldArray);
   Class elementType = oldArray.getClass().getComponentType();
   Object newArray = java.lang.reflect.Array.newInstance(
         elementType,newSize);
   int preserveLength = Math.min(oldSize,newSize);
   if (preserveLength > 0)
      System.arraycopy (oldArray,0,newArray,0,preserveLength);
   return newArray; }

}*/
////////////////////////////////////////////////////////
/** Returns an ImageIcon, or null if the path was invalid. */
protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = LedCodes.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
  // protected ImageIcon createImageIcon(String path,
  //                                         String description) {
  //  java.net.URL imgURL = getClass().getResource(path);
  //  if (imgURL != null) {
  //      return new ImageIcon(imgURL, description);
  //  } else {
  //      System.err.println("Couldn't find file: " + path);
  //      return null;
  //  }
//}
}
