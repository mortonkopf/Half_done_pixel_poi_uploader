package PoiSonic;

/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.net.ServerSocket;
/*    */ import java.net.Socket;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class LiveTcpServer
/*    */   implements Runnable
/*    */ {
/*    */   private int port;
/*    */   private ServerSocket server;
/*  8 */   private boolean running = false;
/*  9 */   private boolean interrupted = false;
/*    */   private ArrayList<Socket> clients;
/*    */ 
/*    */   public LiveTcpServer(int port)
/*    */   {
/* 14 */     this.port = port;
/* 15 */     this.clients = new ArrayList();
/*    */   }
/*    */ 
/*    */   public void start() {
/* 19 */     System.out.println("LiveTcpServer starting on port " + this.port);
/* 20 */     if (!this.running) {
/* 21 */       Thread thread = new Thread(this);
/* 22 */       thread.start();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void stop() {
/* 27 */     System.out.println("LiveTcpServer stopping on port " + this.port);
/* 28 */     if (this.running) {
/* 29 */       this.interrupted = true;
/*    */       try
/*    */       {
/* 32 */         this.server.close();
/*    */       } catch (IOException e) {
/* 34 */         e.printStackTrace();
/*    */       }
/*    */ 
/* 37 */       this.clients.clear();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void sendFrame(Animation.Frame frame) {
/* 42 */     byte[] buffer = new byte[frame.getPixelsTotal()];
/* 43 */     for (int i = 0; i < buffer.length; i++) {
/* 44 */       buffer[i] = (byte)frame.getPixel(i);
/*    */     }
/*    */ 
/* 47 */     for (Socket client : this.clients)
/*    */       try {
/* 49 */         client.getOutputStream().write(buffer);
/*    */       } catch (IOException e) {
/* 51 */         e.printStackTrace();
/* 52 */         this.clients.remove(client);
/*    */       }
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/* 58 */     this.running = true;
/*    */     try
/*    */     {
/* 61 */       this.server = new ServerSocket(this.port, 1);
/*    */ 
/* 63 */       while (!this.interrupted) {
/* 64 */         Socket client = this.server.accept();
/* 65 */         System.out.println(client);
/* 66 */         this.clients.add(client);
/*    */       }
/*    */     } catch (IOException e) {
/* 69 */       e.printStackTrace();
/*    */     }
/*    */ 
/* 72 */     this.running = false;
/*    */   }
/*    */ }
