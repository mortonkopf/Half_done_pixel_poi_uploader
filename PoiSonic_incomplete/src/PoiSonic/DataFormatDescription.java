package PoiSonic;

/*    */ public class DataFormatDescription
/*    */ {
/*    */   private int code;
/*    */   private String description;
/*    */ 
/*    */   public DataFormatDescription(int code, String description)
/*    */   {
/*  6 */     this.code = code;
/*  7 */     this.description = description;
/*    */   }
/*    */ 
/*    */   public int getCode() {
/* 11 */     return this.code;
/*    */   }
/*    */ 
/*    */   public String getDescription() {
/* 15 */     return this.description;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 19 */     return this.description;
/*    */   }
/*    */ }
