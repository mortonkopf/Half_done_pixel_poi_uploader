package PoiSonic;

import java.awt.Graphics;

public abstract interface FrameTool
{
  public abstract void startTool(FramePixelEditor paramFramePixelEditor);

  public abstract void stopTool(FramePixelEditor paramFramePixelEditor);

  public abstract void drawOverlay(Graphics paramGraphics);
}

