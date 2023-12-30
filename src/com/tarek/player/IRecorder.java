package com.tarek.player;

public interface IRecorder {
  public void start() throws Exception;

  public void stop();

  public void pause();

  public boolean isOpen();

  public boolean isRecording();

  public boolean is_echo();

  public void set_echo(boolean echo);
}
