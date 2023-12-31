package com.tarek.GUI.player;

import com.tarek.GUI.annotations.Page;
import com.tarek.player.IRecorder;
import com.tarek.player.Recorder;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import javax.swing.JPanel;

@Page(title = "Sound player")
public class SoundPlayer extends JPanel {
  IRecorder recorder;
  SoundPlayerUI player;

  public SoundPlayer() {
    super();
    try {
      this.recorder = new Recorder();
    } catch (Exception e) {
      System.out.println("Failed to create " + Recorder.class.getName() + "\n Exiting...");
      System.exit(-1);
    }
    player = new SoundPlayerUI();
    addListeners();
    add(player);
  }

  private void addListeners() {
    player.on_start(
        (ActionEvent e) -> {
          try {
            recorder.start();
            player.start_btn().setEnabled(false);
            player.stop_btn().setEnabled(true);
            player.pause_btn().setEnabled(true);
            System.out.println("Mic open...");
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        });

    player.on_stop(
        (ActionEvent e) -> {
          recorder.stop();
          player.start_btn().setEnabled(true);
          player.stop_btn().setEnabled(false);
          player.pause_btn().setEnabled(false);
        });

    player.on_pause(
        (ActionEvent e) -> {
          recorder.pause();
          player.pause_btn().setEnabled(false);
          player.start_btn().setEnabled(true);
        });

    player.on_echo(
        (ItemEvent e) -> {
          recorder.set_echo(e.getStateChange() == 1);
        });
  }
}
