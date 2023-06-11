package audio;

import utility.Asset;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * The AudioClip class represents an audio clip asset that extends the base Asset class.
 * It provides methods for playing, stopping, and restarting the audio clip.
 */
public class AudioClip extends Asset {
    private final Clip clip;

    /**
     * Returns the Clip object associated with the audio clip.
     *
     * @return The Clip object.
     */
    @SuppressWarnings("unused")
    public final Clip getClip() {
        return this.clip;
    }

    private final int numChannels;

    /**
     * Returns the number of channels in the audio clip.
     *
     * @return The number of channels.
     */
    @SuppressWarnings("unused")
    public final int getNumChannels() {
        return this.numChannels;
    }

    private final int sampleSize;

    /**
     * Returns the sample size in bits of the audio clip.
     *
     * @return The sample size in bits.
     */
    @SuppressWarnings("unused")
    public final int getSampleSize() {
        return this.sampleSize;
    }

    private final boolean bigEndian;

    /**
     * Checks if the audio clip uses big-endian byte order.
     *
     * @return true if the audio clip is in big-endian byte order, false otherwise.
     */
    @SuppressWarnings("unused")
    public final boolean isBigEndian() {
        return this.bigEndian;
    }

    private final float sampleRate;

    /**
     * Returns the sample rate of the audio clip.
     *
     * @return The sample rate.
     */
    @SuppressWarnings("unused")
    public final float getSampleRate() {
        return this.sampleRate;
    }

    /**
     * Constructs a new AudioClip object with the audio file at the specified path.
     *
     * @param path The path of the audio file.
     */
    public AudioClip(final String path) {
        super(new File(path).getName(), path);

        File audioFile = new File(path);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            this.clip = AudioSystem.getClip();
            this.clip.open(audioStream);

            AudioFormat audioFormat = audioStream.getFormat();
            this.numChannels = audioFormat.getChannels();
            this.sampleSize = audioFormat.getSampleSizeInBits();
            this.bigEndian = audioFormat.isBigEndian();
            this.sampleRate = audioFormat.getSampleRate();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Checks if the audio clip is currently running (playing).
     *
     * @return true if the audio clip is running, false otherwise.
     */
    public final boolean isRunning() {
        return this.clip.isRunning();
    }

    /**
     * Plays the audio clip from the beginning.
     * If the audio clip is already running, it is stopped and restarted.
     */
    public void play() {
        if (isRunning())
            stop();

        restart();

        this.clip.start();
    }

    /**
     * Stops the audio clip playback.
     */
    public void stop() {
        this.clip.stop();
    }

    /**
     * Restarts the audio clip playback from the beginning.
     */
    public void restart() {
        this.clip.setFramePosition(0);
    }

    /**
     * Returns a string representation of the audio clip.
     *
     * @return A string representation of the audio clip.
     */
    @Override
    public String toString() {
        return super.toString() + " [" + this.numChannels + "] [" + this.sampleSize + "] [" + this.bigEndian + "] [" + this.sampleRate + "] ;";
    }
}