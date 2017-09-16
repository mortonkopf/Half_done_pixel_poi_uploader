package PoiSonic;

/*    */ import java.io.File;
/*    */ import javax.swing.JFileChooser;
/*    */ import javax.swing.JOptionPane;
/*    */ import javax.swing.filechooser.FileFilter;
/*    */ import javax.swing.filechooser.FileNameExtensionFilter;
/*    */ 
/*    */ public class SaveFileChooser extends JFileChooser
/*    */ {
/*    */   public SaveFileChooser()
/*    */   {
/*    */   }
/*    */ 
/*    */   public SaveFileChooser(String dir)
/*    */   {
/* 12 */     super(dir);
/*    */   }
/*    */ 
/*    */   public File getSelectedFile()
/*    */   {
/* 17 */     File file = super.getSelectedFile();
/* 18 */     if (file != null) {
/* 19 */       String filename = file.getAbsolutePath();
/*    */ 
/* 21 */       FileFilter filter = getFileFilter();
/* 22 */       if (filter != null) {
/* 23 */         String extension = getExtensionForFilter(filter);
/* 24 */         if (!filename.endsWith(extension)) {
/* 25 */           file = new File(filename + extension);
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 30 */     return file;
/*    */   }
/*    */ 
/*    */   private String getExtensionForFilter(FileFilter filter) {
/* 34 */     if ((filter instanceof FileNameExtensionFilter)) {
/* 35 */       FileNameExtensionFilter extensionFilter = (FileNameExtensionFilter)filter;
/* 36 */       return "." + extensionFilter.getExtensions()[0];
/*    */     }
/* 38 */     return "";
/*    */   }
/*    */ 
/*    */   public void approveSelection()
/*    */   {
/* 44 */     File file = getSelectedFile();
/* 45 */     if (file.exists()) {
/* 46 */       int answer = JOptionPane.showConfirmDialog(this, file + " exists. Overwrite?", "Overwrite?", 2, 3);
/*    */ 
/* 50 */       if (answer != 0) {
/* 51 */         return;
/*    */       }
/*    */     }
/* 54 */     super.approveSelection();
/*    */   }
/*    */ }

