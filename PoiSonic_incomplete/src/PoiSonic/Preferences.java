package PoiSonic;


//jjj

import java.util.*;
import java.io.*;

public class Preferences {
  public static final String ANIMATION_DATAFORMAT = "animation.dataformat";
  public static final String ANIMATION_HEIGHT = "animation.height";
  public static final String ANIMATION_WIDTH = "animation.width";

  public static final String FRAME_DEFAULTDELAY = "frame.defaultdelay";
  public static final String FRAME_DEFAULTRTIME = "frame.defaultrtime";

  public static final String DATAFORMAT_ONECOLOR_DEFAULTPRIMARY = "dataformat.onecolor.defaultprimary";
  public static final String DATAFORMAT_ONECOLOR_DEFAULTSECONDARY = "dataformat.onecolor.defaultsecondary";
  public static final String DATAFORMAT_RGBCOLOR_DEFAULTPRIMARY = "dataformat.rgbcolor.defaultprimary";
  public static final String DATAFORMAT_RGBCOLOR_DEFAULTSECONDARY = "dataformat.rgbcolor.defaultsecondary";

  public static final String TCP_AUTOSTART = "tcp.autostart";
  public static final String TCP_PORT = "tcp.port";
  public static final String SERIAL_BAUD = "serial_baud";

  public static final String DIRECTORIES_FILE_OPEN = "directories.file.open";
  public static final String DIRECTORIES_FILE_SAVE = "directories.file.save";
  public static final String DIRECTORIES_FILE_EXPORT = "directories.file.export";

  public static final String WINDOW_TOP = "window.top";
  public static final String WINDOW_LEFT = "window.left";
  public static final String WINDOW_WIDTH = "window.width";
  public static final String WINDOW_HEIGHT = "window.height";


  private String filename;
  private Hashtable<String, String> preferences;

  public Preferences() {
    preferences = new Hashtable<String, String>();
  }

  public Preferences(String filename) throws FileNotFoundException, IOException {
    this();
    this.filename = filename;
    if(filename != null) {
      load(filename);
    }
  }

  private void load(String filename) throws FileNotFoundException, IOException {
    if(filename == null) throw new IllegalArgumentException("filename must be set for loading.");

    FileReader fr = new FileReader(filename);
    BufferedReader in = new BufferedReader(fr);

    String line, key, value;
    int indexEquals;
    while((line = in.readLine()) != null) {
      indexEquals = line.indexOf('=');
      key = line.substring(0, indexEquals);
      value = line.substring(indexEquals + 1);

      preferences.put(key, value);
    }

    in.close();
  }

  public void save(String filename) throws IOException {
    if(filename == null) throw new IllegalArgumentException("filename must be set for saving.");

    FileWriter fw = new FileWriter(filename);
    BufferedWriter out = new BufferedWriter(fw);

    ArrayList<String> keys = new ArrayList<String>(preferences.keySet());
    Collections.sort(keys);

    for(String key : keys ) {
      out.write(key);
      out.write('=');
      out.write(preferences.get(key));
      out.write("\r\n");
    }

    out.close();
  }

  public void save() throws IOException {
    save(filename);
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public void set(String key, String value) {
    preferences.put(key.toLowerCase(), value);
  }

  public void set(String key, int value) {
    set(key, Integer.toString(value));
  }

  public void set(String key, boolean value) {
    set(key, value?"1":"0");
  }

  public String get(String key, String defaultValue) {
    key = key.toLowerCase();

    if(preferences.containsKey(key)) {
      String value = preferences.get(key);
      return value;
    } else {
      return defaultValue;
    }
  }

  public int get(String key, int defaultValue) {
    String stringValue = get(key, Integer.toString(defaultValue));
    try {
      return Integer.parseInt(stringValue);
    } catch(NumberFormatException e) {
      return defaultValue;
    }
  }

  public boolean get(String key, boolean defaultValue) {
    int intval = get(key, defaultValue?1:0);
    return intval == 1;
  }

  private static Preferences instance;
  public static Preferences getInstance() {
    if(instance == null) instance = new Preferences();
    return instance;
  }

  public static Preferences instanceFromFile(String filename) throws IOException {
    if(instance != null) throw new IllegalStateException("Cannot create singleton instance from file, if there's already an instance.");

    try {
      instance = new Preferences(filename);
    } catch(FileNotFoundException e) {
      instance = new Preferences();
      instance.filename = filename;
    }

    return instance;
  }
}