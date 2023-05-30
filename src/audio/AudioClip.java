package audio;

import util.Console;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioClip {
    /**
     * Represents the name of the audio clip.
     */
    private String name;

    /**
     * Represents the Clip object that holds the audio data.
     */
    private Clip clip;

    /**
     * Retrieves the Clip object of the audio clip.
     *
     * @return the Clip object of the audio clip
     */
    public final Clip getClip() {
        return this.clip;
    }

    /**
     * Retrieves the name of the audio clip.
     *
     * @return the name of the audio clip
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets the name of the audio clip.
     *
     * @param name the name of the audio clip
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Checks if the audio clip is currently running.
     *
     * @return true if the audio clip is running, false otherwise
     */
    public final boolean isRunning() {
        return this.clip != null && this.clip.isRunning();
    }

    /**
     * Constructs an AudioClip object with the specified file name.
     *
     * @param fileName the name of the audio file
     */
    public AudioClip(String fileName) {
        load(fileName);
    }

    /**
     * Loads the audio file and initializes the Clip object.
     *
     * @param fileName the name of the audio file
     */
    private void load(String fileName) {
        util.Console.message("Loading new AudioClip : " + fileName, Console.PrintType.Audio);
        this.clip = null;

        try {
            File file = new File(fileName);
            this.name = file.getName();

            this.clip = AudioSystem.getClip();
            this.clip.open(AudioSystem.getAudioInputStream(file));
        } catch (Exception ex) {
            util.Console.error("Failed to load new AudioClip : " + fileName, Console.PrintType.Audio);
            ex.printStackTrace();
        }
        util.Console.message("AudioClip loaded : " + this.name, Console.PrintType.Audio);
    }

    /**
     * Plays the audio clip.
     * If the audio clip is already playing, it will be stopped and restarted.
     */
    public void play() {
        if (this.clip == null) {
            util.Console.error("Failed to play AudioClip", Console.PrintType.Audio);
            throw new NullPointerException();
        }

        stop();

        restart();

        this.clip.start();
    }

    /**
     * Stops the audio clip if it is currently running.
     */
    public void stop() {
        if (!isRunning()) {
            util.Console.warning("Trying to stop already stopped AudioClip", Console.PrintType.Audio);
            return;
        }

        this.clip.stop();
    }

    /**
     * Restarts the audio clip by setting its frame position to 0.
     */
    public void restart() {
        if (this.clip == null) {
            util.Console.error("Trying to restart non existing clip", Console.PrintType.Audio);
            throw new NullPointerException();
        }

        this.clip.setFramePosition(0);
    }
}