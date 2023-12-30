package com.tarek.GUI;

import com.tarek.player.IRecorder;
import com.tarek.player.Recorder;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SoundPlayer extends JFrame {
  IRecorder recorder;

  JButton start_btn = new JButton("Start recording");
  JButton stop_btn = new JButton("Stop recording");
  JButton pause_btn = new JButton("Pause");
  JCheckBox echo = new JCheckBox("Echo", true);

  public SoundPlayer() {
    super();
    try {
      this.recorder = new Recorder();
    } catch (Exception e) {
      System.out.println("Failed to create " + Recorder.class.getName() + "\n Exiting...");
      System.exit(-1);
    }
    init_gui();
    setSize(500, 200);
    setResizable(false);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  private void init_gui() {
    JPanel panel = build_button_panel();
    getContentPane().add(panel, BorderLayout.SOUTH);
  }

  private void init_buttons() {
    pause_btn.setEnabled(false);
    stop_btn.setEnabled(false);
    init_button_actions();
  }

  private void init_button_actions() {
    start_btn.addActionListener(
        (ActionEvent e) -> {
          try {
            recorder.start();
            start_btn.setEnabled(false);
            stop_btn.setEnabled(true);
            pause_btn.setEnabled(true);
            System.out.println("Recording...");
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        });

    stop_btn.addActionListener(
        (ActionEvent e) -> {
          recorder.stop();
          start_btn.setEnabled(true);
          stop_btn.setEnabled(false);
          pause_btn.setEnabled(false);
        });

    pause_btn.addActionListener(
        (ActionEvent e) -> {
          recorder.pause();
          pause_btn.setEnabled(false);
          start_btn.setEnabled(true);
        });

    echo.addItemListener(
        (ItemEvent e) -> {
          recorder.set_echo(e.getStateChange() == 1);
        });
  }

  private JPanel build_button_panel() {
    JPanel panel = new JPanel();

    init_buttons();

    panel.add(start_btn);
    panel.add(pause_btn);
    panel.add(stop_btn);
    panel.add(echo);

    return panel;
  }
}
