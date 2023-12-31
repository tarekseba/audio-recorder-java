package com.tarek.GUI.player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class SoundPlayerUI extends JPanel {
  JButton start_btn = new JButton("Start recording");
  JButton stop_btn = new JButton("Stop recording");
  JButton pause_btn = new JButton("Pause");
  JCheckBox echo = new JCheckBox("Echo", true);

  public SoundPlayerUI() {
    super();
    init_gui();
    setPreferredSize(new Dimension(500, 200));
    setSize(500, 200);
  }

  private void init_gui() {
    JPanel panel = build_button_panel();
    add(panel, BorderLayout.SOUTH);
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

  private void init_buttons() {
    pause_btn.setEnabled(false);
    stop_btn.setEnabled(false);
  }

  public void on_start(ActionListener al) {
    start_btn.addActionListener(al);
  }

  public void on_stop(ActionListener al) {
    stop_btn.addActionListener(al);
  }

  public void on_pause(ActionListener al) {
    pause_btn.addActionListener(al);
  }

  public void on_echo(ItemListener il) {
    echo.addItemListener(il);
  }

  public JButton start_btn() {
    return start_btn;
  }

  public JButton stop_btn() {
    return stop_btn;
  }

  public JButton pause_btn() {
    return pause_btn;
  }

  public JCheckBox get_echo() {
    return echo;
  }
}
