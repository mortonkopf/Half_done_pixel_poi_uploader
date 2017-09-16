package PoiSonic;

/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class PaletteSelector extends JPanel
/*     */ {
/*   7 */   private int primaryColor = 0;
/*   8 */   private int secondaryColor = 0;
/*     */   private DataFormat dataFormat;
/*     */   private ArrayList<PaletteSelectorListener> listeners;
/*     */ 
/*     */   public PaletteSelector()
/*     */   {
/*  14 */     super(null);
/*     */ 
/*  16 */     this.listeners = new ArrayList();
/*     */   }
/*     */ 
/*     */   public void setDataFormat(DataFormat dataFormat) {
/*  20 */     this.dataFormat = dataFormat;
/*  21 */     setPrimaryColor(dataFormat.getDefaultPrimaryColor());
/*  22 */     setSecondaryColor(dataFormat.getDefaultSecondaryColor());
/*  23 */     removeAll();
/*  24 */     updateColors();
/*     */   }
/*     */ 
/*     */   private void updateColors() {
/*  28 */     Color[] colors = this.dataFormat.getPalette();
/*  29 */     int perRow = 4;
/*  30 */     setPreferredSize(new Dimension(120, (colors.length + perRow - 1) / perRow * 25 + 25));
/*     */ 
/*  32 */     int y = 25;
/*  33 */     int x = 5;
/*  34 */     for (int i = 0; i < colors.length; i++) {
/*  35 */       PaletteSelector.PaletteColor pc = new PaletteSelector.PaletteColor(i, colors[i]);
/*  36 */       pc.setBounds(x, y, 20, 20);
/*  37 */       add(pc);
/*     */ 
/*  39 */       x += 25;
/*  40 */       if (x + 25 > 120) {
/*  41 */         x = 5;
/*  42 */         y += 25;
/*     */       }
/*     */     }
/*  45 */     validate();
/*  46 */     repaint();
/*     */   }
/*     */ 
/*     */   public void addPaletteSelectorListener(PaletteSelectorListener listener) {
/*  50 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */   public void removePaletteSelectorListener(PaletteSelectorListener listener) {
/*  54 */     this.listeners.remove(listener);
/*     */   }
/*     */ 
/*     */   protected void colorChanged(int changedColor, int color) {
/*  58 */     for (PaletteSelectorListener listener : this.listeners)
/*  59 */       listener.colorChanged(new PaletteSelectorEvent(this, changedColor, color, this.dataFormat.getColor(color)));
/*     */   }
/*     */ 
/*     */   protected void setPrimaryColor(int color)
/*     */   {
/*  64 */     this.primaryColor = color;
/*  65 */     colorChanged(1, color);
/*  66 */     repaint();
/*     */   }
/*     */ 
/*     */   public int getPrimaryColor() {
/*  70 */     return this.primaryColor;
/*     */   }
/*     */ 
/*     */   protected void setSecondaryColor(int color) {
/*  74 */     this.secondaryColor = color;
/*  75 */     colorChanged(2, color);
/*  76 */     repaint();
/*     */   }
/*     */ 
/*     */   public int getSecondaryColor() {
/*  80 */     return this.secondaryColor;
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g) {
/*  84 */     super.paint(g);
/*  85 */     Graphics2D g2 = (Graphics2D)g;
/*  86 */     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*     */ 
/*  88 */     g.setColor(this.dataFormat.getColor(this.secondaryColor));
/*  89 */     g.fillOval(15, 0, 20, 20);
/*  90 */     g.setColor(Color.BLACK);
/*  91 */     g.drawOval(15, 0, 20, 20);
/*     */ 
/*  93 */     g.setColor(this.dataFormat.getColor(this.primaryColor));
/*  94 */     g.fillOval(0, 0, 20, 20);
/*  95 */     g.setColor(Color.BLACK);
/*  96 */     g.drawOval(0, 0, 20, 20);
/*     */   }
/*     */   class PaletteColor extends JComponent implements MouseListener {
/*     */     private int colorNr;
/*     */     private Color color;
/*     */ 
/* 104 */     public PaletteColor(int colorNr, Color color) { this.colorNr = colorNr;
/* 105 */       this.color = color;
/* 106 */       addMouseListener(this); }
/*     */ 
/*     */     public void paint(Graphics g)
/*     */     {
/* 110 */       Graphics2D g2 = (Graphics2D)g;
/* 111 */       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*     */ 
/* 113 */       g.setColor(this.color);
/* 114 */       g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
/* 115 */       g.setColor(Color.BLACK);
/* 116 */       g.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e) {
/* 120 */       if (e.getButton() == 1)
/* 121 */         PaletteSelector.this.setPrimaryColor(this.colorNr);
/*     */       else
/* 123 */         PaletteSelector.this.setSecondaryColor(this.colorNr);
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseClicked(MouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

