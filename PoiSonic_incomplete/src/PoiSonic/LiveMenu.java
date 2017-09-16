package PoiSonic;

/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Hashtable;
/*     */ import javax.swing.JCheckBoxMenuItem;
/*     */ import javax.swing.JMenu;
/*     */ 
/*     */ public class LiveMenu extends JMenu
/*     */   implements ActionListener, AnimationListener
/*     */ {
/*     */   private Hashtable<Integer, JCheckBoxMenuItem> serialSpeedMenuItems;
/*     */   private JCheckBoxMenuItem liveTcpServerItem;
/*  10 */   private LiveTcpServer liveTcpServer = null;
/*     */ 
/*  12 */   private int currentSerialSpeed = 9600;
/*  13 */   private int tcpPort = 4321;
/*     */   private Animation animation;
/*     */ 
/*     */   public LiveMenu()
/*     */   {
/*  18 */     setText("Connect Port");
/*  19 */     setMnemonic(76);
/*     */ 
/*  21 */     JMenu liveSerialPort = new JMenu("Serial Port");
/*  22 */     liveSerialPort.setMnemonic(80);
/*     */ 
/*  25 */     JCheckBoxMenuItem serialPortNone = new JCheckBoxMenuItem("None", true);
/*  26 */     liveSerialPort.add(serialPortNone);
/*     */ 
/*  29 */     JMenu liveSerialSpeed = new JMenu("Serial Speed");
/*  30 */     liveSerialSpeed.setMnemonic(83);
                /////line added
              add(liveSerialSpeed);
//////
/*     */ 
/*  33 */     this.serialSpeedMenuItems = new Hashtable();
/*     */ 
/*  35 */     this.currentSerialSpeed = Preferences.getInstance().get("serial.baud", this.currentSerialSpeed);
/*  36 */     for (int speed : new int[] { 300, 1200, 2400, 4800, 9600, 14400, 19200, 28800, 38400, 57600, 115200 }) {
/*  37 */       JCheckBoxMenuItem speedOption = new JCheckBoxMenuItem(Integer.toString(speed));
/*  38 */       speedOption.setState(speed == this.currentSerialSpeed);
/*  39 */       speedOption.addActionListener(this);
/*  40 */       liveSerialSpeed.add(speedOption);
/*     */ 
/*  42 */       this.serialSpeedMenuItems.put(Integer.valueOf(speed), speedOption);
/*     */     }
/*     */ 
/*  45 */     this.tcpPort = Preferences.getInstance().get("tcp.port", this.tcpPort);
/*  46 */     this.liveTcpServerItem = new JCheckBoxMenuItem("TCP Port " + this.tcpPort);
/*  47 */     this.liveTcpServerItem.setMnemonic(84);
/*  48 */     this.liveTcpServerItem.addActionListener(this);
/*  49 */     add(this.liveTcpServerItem);
/*     */   }
/*     */ 
/*     */   private void reconnectSerial()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/*  60 */     if (this.serialSpeedMenuItems.containsValue(e.getSource())) {
/*  61 */       int clickedSpeed = Integer.parseInt(((JCheckBoxMenuItem)e.getSource()).getActionCommand());
/*     */ 
/*  63 */       if (clickedSpeed != this.currentSerialSpeed) {
/*  64 */         ((JCheckBoxMenuItem)this.serialSpeedMenuItems.get(Integer.valueOf(this.currentSerialSpeed))).setState(false);
/*  65 */         ((JCheckBoxMenuItem)this.serialSpeedMenuItems.get(Integer.valueOf(clickedSpeed))).setState(true);
/*  66 */         this.currentSerialSpeed = clickedSpeed;
/*     */ 
/*  68 */         reconnectSerial();
/*     */       }
/*  70 */     } else if (e.getSource() == this.liveTcpServerItem) {
/*  71 */       if (this.liveTcpServerItem.getState())
/*  72 */         startTcpServer();
/*     */       else
/*  74 */         stopTcpServer();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void startTcpServer()
/*     */   {
/*  80 */     if (this.liveTcpServer == null) {
/*  81 */       this.liveTcpServer = new LiveTcpServer(this.tcpPort);
/*  82 */       this.liveTcpServer.start();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void stopTcpServer() {
/*  87 */     if (this.liveTcpServer != null) {
/*  88 */       this.liveTcpServer.stop();
/*  89 */       this.liveTcpServer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAnimation(Animation animation) {
/*  94 */     if (this.animation != null) this.animation.removeAnimationListener(this);
/*  95 */     this.animation = animation;
/*  96 */     this.animation.addAnimationListener(this);
/*     */   }
/*     */ 
/*     */   public void frameSelected(Animation.Frame frame) {
/* 100 */     if (this.liveTcpServer != null)
/* 101 */       this.liveTcpServer.sendFrame(frame);
/*     */   }
/*     */ 
/*     */   public void frameDataChanged(Animation.Frame frame)
/*     */   {
/* 106 */     if (this.liveTcpServer != null)
/* 107 */       this.liveTcpServer.sendFrame(frame);
/*     */   }
/*     */ 
/*     */   public void frameAdded(Animation.Frame newFrame, int newFrameNr, int totalFrames)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void frameRemoved(Animation.Frame removedFrame, int totalFrames)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void frameMoved(Animation.Frame frame, int newFrameNr)
/*     */   {
/*     */   }
/*     */ }
