package PoiSonic;

public abstract interface AnimationListener
{
  public abstract void frameAdded(Animation.Frame paramFrame, int paramInt1, int paramInt2);

  public abstract void frameSelected(Animation.Frame paramFrame);

  public abstract void frameRemoved(Animation.Frame paramFrame, int paramInt);

  public abstract void frameDataChanged(Animation.Frame paramFrame);

  public abstract void frameMoved(Animation.Frame paramFrame, int paramInt);
}

