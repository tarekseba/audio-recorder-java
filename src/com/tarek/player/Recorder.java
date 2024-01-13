package com.tarek.player;

import java.io.ByteArrayInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.SwingWorker;

public class Recorder implements IRecorder {
  private boolean recording = false;
  private ByteArrayInputStream input_buffer;
  private TargetDataLine target_line;
  private SourceDataLine source_line;
  private boolean echo = true;
  private SwingWorker worker;
  private int BUF_SIZE = 4096;

  public Recorder() throws Exception {
    input_buffer = new ByteArrayInputStream(new byte[BUF_SIZE]);
    target_line =
        (TargetDataLine) AudioSystem.getLine(PlayerHelper.build_info(TargetDataLine.class));
    source_line =
        (SourceDataLine) AudioSystem.getLine(PlayerHelper.build_info(SourceDataLine.class));
    source_line.open();
    source_line.start();
  }

  @Override
  public boolean isOpen() {
    return target_line.isOpen();
  }

  @Override
  public boolean isRecording() {
    return false;
  }

  @Override
  public void start() throws Exception {
    if (!target_line.isOpen()) {
      target_line.open();
    }
    target_line.flush();
    target_line.start();
    if (is_echo()) {
      if (!source_line.isOpen()) {
        source_line.open();
      }
      source_line.start();
    }
    worker =
        new SwingWorker<Void, Void>() {
          @Override
          protected Void doInBackground() throws Exception {
            int byte_count = 0;
            int bytes = 0;
            byte[] buf = new byte[BUF_SIZE];
            while (((bytes = target_line.read(buf, 0, BUF_SIZE))) != -1) {
              byte_count += bytes;
              if (is_echo()) {
                source_line.write(buf, 0, BUF_SIZE);
              }
            }
            return null;
          }
        };
    worker.execute();
  }

  @Override
  public void pause() {
    if (target_line.isOpen()) {
      target_line.stop();
      target_line.flush();
      source_line.stop();
      source_line.flush();
    }
  }

  @Override
  public void stop() {
    if (target_line.isOpen()) {
      target_line.stop();
      source_line.stop();
    }
    target_line.close();
    source_line.close();
  }

  @Override
  public boolean is_echo() {
    return echo;
  }

  @Override
  public void set_echo(boolean echo) {
    this.echo = echo;
    if (echo) {
      try {
        source_line.open();
        source_line.start();
      } catch (Exception e) {
        System.out.println("Failed to open source line");
        e.printStackTrace();
      }
    } else {
      source_line.close();
      source_line.stop();
    }
  }
}
