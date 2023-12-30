package com.tarek.player;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;

public class PlayerHelper {
  private static final AudioFormat.Encoding ENCODING = AudioFormat.Encoding.PCM_SIGNED;
  private static final float RATE = 44100;
  private static final int CHANNEL_COUNT = 2;
  private static final int SAMPLE_SIZE = 16;
  private static final int FRAME_SIZE = CHANNEL_COUNT * SAMPLE_SIZE / 8;

  public static DataLine.Info build_info(Class<? extends DataLine> clazz) {
    AudioFormat format =
        new AudioFormat(ENCODING, RATE, SAMPLE_SIZE, CHANNEL_COUNT, FRAME_SIZE, RATE, false);

    return new DataLine.Info(clazz, format);
  }
}
