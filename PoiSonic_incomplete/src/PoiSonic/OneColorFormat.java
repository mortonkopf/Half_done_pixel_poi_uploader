package PoiSonic;

/*    */ import java.awt.Color;
/*    */ 
/*    */ public class OneColorFormat extends DataFormat
/*    */ {
/*    */   public static final int PWM_LEVELS = 4;
/*    */   public static final int PWM_MIN_RED = 128;
/*    */   public static final int PWM_MAX_RED = 250;
/* 11 */   public static final Color[] COLORS = new Color[4];
/*    */ 
/*    */   public Color[] getPalette()
/*    */   {
/* 21 */     return (Color[])(Color[])COLORS.clone();
/*    */   }
/*    */ 
/*    */   public Color getColor(int index) {
/* 25 */     return COLORS[index];
/*    */   }
/*    */ 
/*    */   public String getDataType() {
/* 29 */     return "uint8_t";
/*    */   }
/*    */ 
/*    */   public int getDefaultPrimaryColor() {
/* 33 */     return Preferences.getInstance().get("dataformat.onecolor.defaultprimary", 3);
/*    */   }
/*    */ 
/*    */   public int getDefaultSecondaryColor() {
/* 37 */     return Preferences.getInstance().get("dataformat.onecolor.defaultsecondary", 0);
/*    */   }
/*    */ 
/*    */   public int getBytesPerFrame(Animation animation) {
/* 41 */     return animation.getPixelsTotal() / 4;
/*    */   }
/*    */ 
/*    */   public int[] getBytes(Animation.Frame frame) {
/* 45 */     int pixelCount = frame.getPixelsTotal();
/* 46 */     int[] bytes = new int[pixelCount / 4];
/* 47 */     int n = 0;
/*    */ 
/* 49 */     for (int i = 0; i < pixelCount; i += 4) {
/* 50 */       bytes[n] = 0;
/* 51 */       for (int j = i; (j < i + 4) && (j < pixelCount); j++) {
/* 52 */         bytes[n] |= frame.getPixel(j) << 2 * (j - i);
/*    */       }
/* 54 */       n++;
/*    */     }
/*    */ 
/* 57 */     return bytes;
/*    */   }
public int[] getOutput(Animation.Frame frame){
    int pixelCount = frame.getPixelsTotal();
/* 46 */     int[] bytes = new int[pixelCount / 4];
/* 47 */     int n = 0;
/*    */
/* 49 */     for (int i = 0; i < pixelCount; i += 4) {
/* 50 */       bytes[n] = 0;
/* 51 */       for (int j = i; (j < i + 4) && (j < pixelCount); j++) {
/* 52 */         bytes[n] |= frame.getPixel(j) << 2 * (j - i);
/*    */       }
/* 54 */       n++;
/*    */     }
/*    */
/* 57 */     return bytes;
    
}
/*    */ 
/*    */   static
/*    */   {
/* 13 */     COLORS[0] = new Color(128, 128, 128);
/* 14 */     for (int i = 1; i < 4; i++) {
/* 15 */       int r = i * 122 / 3 + 128;
/* 16 */       COLORS[i] = new Color(r, 0, 0);
/*    */     }
/*    */   }
/*    */ }

