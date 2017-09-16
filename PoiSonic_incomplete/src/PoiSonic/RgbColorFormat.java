package PoiSonic;
    import java.nio.*; 
import java.net.*; 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
import java.util.*;//Arrays;
/*     */ 
/*     */ public class RgbColorFormat extends DataFormat
/*     */ {
/*   5 */   public static final Color[] COLORS = { new Color(128, 128, 128), new Color(250, 0, 0), new Color(0, 250, 0), new Color(0, 0, 250), new Color(250, 250, 0), new Color(250, 0, 250), new Color(0, 250, 250), new Color(250, 250, 250), new Color(180, 0, 0), new Color(0, 180, 0), new Color(0, 0, 180), new Color(180, 180, 0), new Color(180, 0, 180), new Color(0, 180, 180), new Color(180, 180, 180), new Color(250, 180, 0), new Color(250, 0, 180), new Color(250, 180, 180), new Color(180, 250, 0), new Color(0, 250, 180), new Color(180, 250, 180), new Color(180, 0, 250), new Color(0, 180, 250), new Color(180, 180, 250), new Color(180, 250, 250), new Color(250, 180, 250), new Color(250, 250, 180) };
/*     */ 
/*  45 */   public static final Color[] VALUE_COLORS = { new Color(0, 0, 0), new Color(250, 0, 0), new Color(0, 250, 0), new Color(0, 0, 250), new Color(250, 250, 0), new Color(250, 0, 250), new Color(0, 250, 250), new Color(250, 250, 250), new Color(180, 0, 0), new Color(0, 180, 0), new Color(0, 0, 180), new Color(180, 180, 0), new Color(180, 0, 180), new Color(0, 180, 180), new Color(180, 180, 180), new Color(250, 180, 0), new Color(250, 0, 180), new Color(250, 180, 180), new Color(180, 250, 0), new Color(0, 250, 180), new Color(180, 250, 180), new Color(180, 0, 250), new Color(0, 180, 250), new Color(180, 180, 250), new Color(180, 250, 250), new Color(250, 180, 250), new Color(250, 250, 180) };


/*     */ 
/*     */   public Color[] getPalette()
/*     */   {
/*  86 */     return (Color[])(Color[])COLORS.clone();
/*     */   }
/*     */ 
/*     */   public Color getColor(int index) {
/*  90 */     return COLORS[index];
/*     */   }
/*     */ 
/*     */   public String getDataType() {
/*  94 */     return "uint16_t";
/*     */   }
/*     */ 
/*     */   public int getDefaultPrimaryColor() {
/*  98 */     return Preferences.getInstance().get("dataformat.rgbcolor.defaultprimary", 7);
/*     */   }
/*     */ 
/*     */   public int getDefaultSecondaryColor() {
/* 102 */     return Preferences.getInstance().get("dataformat.rgbcolor.defaultsecondary", 0);
/*     */   }
/*     */ 
/*     */   public int getBytesPerFrame(Animation animation) {
/* 106 */     return animation.getPixelsTotal();
/*     */   }
/*     */
public int[] getBytes(Animation.Frame frame) {
     int pixelCount = frame.getPixelsTotal();
     int[] bytes = new int[pixelCount];
 
     for (int i = 0; i < pixelCount; i++) {
       int rgb = VALUE_COLORS[frame.getPixel(i)].getRGB();
       int R = rgb >> 16 & 0xFF;
       int G = rgb >> 8 & 0xFF;
       int B = rgb >> 0 & 0xFF;
 
       bytes[i] = (R >> 3 & 0x1F | (G >> 3 & 0x1F) << 5 | (B >> 3 & 0x1F) << 10);
     }
      return bytes;
   }
///////////////////////////////////
public int[] getOutput(Animation.Frame frame) {
    int[][][]output3 = frame.array2;
  // int[] bytes =new int[((Arrays.deepHashCode(frame.array2)))];
    int pixelCount = frame.getPixelsTotal();
      int[]bytes = new int[pixelCount];
/////////////////////////////////////////////////////////////////
      //   for ( int i = (int) 0.0D; (double)i < frame.getPixelsHigh(); i += 1.0D)  {
      //     for (int j = (int) 0.0D; (double)j <frame.getPixelsWide(); j += 1.0D)  {
      for (int i = 0; i < frame.getPixelsHigh(); i++) { //changed +=1 tp ++
          for (int j = 0; j < frame.getPixelsWide(); j++){

      // g2.setColor(
   /*Color*/ bytes [i]= /*new Color*/((output3[(int) j][(int) i][frame.currentFrame] >> 5 & 0x7) |
           (output3[(int) j][(int) i][frame.currentFrame] >> 2 & 0x7)|
           (output3[(int) j][(int) i][frame.currentFrame] << 1 & 0x6)); /*)*/
      //////////////////////////////////////////////////////////////////////////
             }}
  // System.out.println(Arrays.toString(bytes));

  //  System.out.println(bytes);
     return bytes;

    }

 }
