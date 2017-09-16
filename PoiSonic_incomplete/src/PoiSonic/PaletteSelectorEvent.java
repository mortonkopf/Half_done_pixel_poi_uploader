package PoiSonic;

/*    */ import java.awt.Color;
/*    */ import java.util.EventObject;
/*    */ 
/*    */ public class PaletteSelectorEvent extends EventObject
/*    */ {
/*    */   public static final int PRIMARY_COLOR = 1;
/*    */   public static final int SECONDARY_COLOR = 2;
/*    */   private int changedColor;
/*    */   private int colorIndex;
/*    */   private Color color;
/*    */ 
/*    */   public PaletteSelectorEvent(PaletteSelector source)
/*    */   {
/* 13 */     super(source);
/*    */   }
/*    */ 
/*    */   public PaletteSelectorEvent(PaletteSelector source, int changedColor, int colorIndex, Color color) {
/* 17 */     this(source);
/* 18 */     this.changedColor = changedColor;
/* 19 */     this.colorIndex = colorIndex;
/* 20 */     this.color = color;
/*    */   }
/*    */ 
/*    */   public int getChangedColor() {
/* 24 */     return this.changedColor;
/*    */   }
/*    */ 
/*    */   public int getColorIndex() {
/* 28 */     return this.colorIndex;
/*    */   }
/*    */ 
/*    */   public Color getColor() {
/* 32 */     return this.color;
/*    */   }
/*    */ }

