package audio;

import java.io.File;

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
    @SuppressWarnings("unused")
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
        util.Console.message("Loading new AudioClip : " + fileName, this);
        this.clip = null;

        try {
            File file = new File(fileName);
            this.setName(file.getName());

            this.clip = AudioSystem.getClip();
            this.clip.open(AudioSystem.getAudioInputStream(file));
        } catch (Exception ex) {
            util.Console.error("Failed to load new AudioClip : " + fileName, this);
            ex.printStackTrace();
        }
        util.Console.message("AudioClip loaded : " + this.name,this);
    }

    /**
     * Plays the audio clip.
     * If the audio clip is already playing, it will be stopped and restarted.
     */
    public void play() {
        if (this.clip == null) {
            util.Console.error("Failed to play AudioClip", this);
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
            util.Console.warning("Trying to stop already stopped AudioClip", this);
            return;
        }

        this.clip.stop();
    }

    /**
     * Restarts the audio clip by setting its frame position to 0.
     */
    public void restart() {
        if (this.clip == null) {
            util.Console.error("Trying to restart non existing clip", this);
            throw new NullPointerException();
        }

        this.clip.setFramePosition(0);
    }
}