package PoiSonic;

/*    */ import java.awt.Color;
/*    */ 
/*    */ public abstract class DataFormat
/*    */ {
/*    */   public static final int ONE_COLOR = 1;
/*    */   public static final int RGB_COLOR = 2;
/*    */ 
/*    */   public abstract Color[] getPalette();
/*    */ 
/*    */   public abstract Color getColor(int paramInt);
/*    */ 
/*    */   public abstract String getDataType();
/*    */ 
/*    */   public abstract int getDefaultPrimaryColor();
/*    */ 
/*    */   public abstract int getDefaultSecondaryColor();
/*    */ 
/*    */   public abstract int getBytesPerFrame(Animation paramAnimation);
/*    */ 
/*    */   public abstract int[] getBytes(Animation.Frame paramFrame);
/////////////////////////////////////
            public abstract int[] getOutput(Animation.Frame paramFrame);

///////////////////////////////////
/*    */ 
/*    */   public int getFormatCode()
/*    */   {
/* 16 */     return getFormatCode(this);
/*    */   }
/*    */ 
/*    */   public static DataFormat newFormatByCode(int code) {
/* 20 */     switch (code) { case 1:
/* 21 */       return new OneColorFormat();
/*    */     case 2:
/* 22 */       return new RgbColorFormat(); }
/* 23 */     throw new IllegalArgumentException("Unknown data format code.");
/*    */   }
/*    */ 
/*    */   public static int getFormatCode(DataFormat format)
/*    */   {
/* 28 */     if ((format instanceof OneColorFormat))
/* 29 */       return 1;
/* 30 */     if ((format instanceof RgbColorFormat)) {
/* 31 */       return 2;
/*    */     }
/* 33 */     throw new IllegalArgumentException("Unknown data format.");
/*    */   }
/*    */ 
/*    */   public static DataFormatDescription[] getDataFormats() {
/* 37 */     return new DataFormatDescription[] { new DataFormatDescription(1, "Single color, 2 bits/pixel"), new DataFormatDescription(2, "Multi color, 2 bytes/pixel") };
/*    */   }
/*    */ }
