package audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioClip {
    // Private fields for the name and Clip object
    private String name;
    private Clip clip;

    // Getter for the Clip object
    public final Clip getClip() {
        return this.clip;
    }

    // Getter for the name
    public final String getName() {
        return name;
    }

    // Setter for the name
    public void setName(final String name) {
        this.name = name;
    }

    // Constructor for the AudioClip class
    public AudioClip(String fileName) {
        // Call the private load method to load the audio clip from the file
        load(fileName);
    }

    // Private method to load the audio clip from the file
    private void load(String fileName) {
        // Initialize the clip to null
        this.clip = null;

        try {
            // Create a File object using the provided file name
            File file = new File(fileName);

            // Set the name of the audio clip to the file name
            this.name = file.getName();

            // Get a Clip object from the AudioSystem
            this.clip = AudioSystem.getClip();

            // Open the audio clip using the AudioSystem's AudioInputStream
            this.clip.open(AudioSystem.getAudioInputStream(file));
        } catch (IOException ex) {
            // Print the stack trace if an IOException occurs during audio loading
            ex.printStackTrace();
        } catch (Exception ex) {
            // Print the stack trace if any other exception occurs during audio loading
            ex.printStackTrace();
        }
    }

    // Play the audio clip
    public void play() {
        // Check if the clip is null, throw an exception if it is
        if (this.clip == null)
            throw new NullPointerException();

        // Stop the clip if it is already running
        stop();

        // Set the frame position of the clip to the beginning
        restart();

        // Start playing the clip
        this.clip.start();
    }

    // Stop the audio clip
    public void stop() {
        // Return if the clip is not running
        if (!isRunning())
            return;

        // Stop the clip
        this.clip.stop();
    }

    // Restart the audio clip by setting the frame position to the beginning
    public void restart() {
        // Return if the clip is null
        if (this.clip == null)
            return;

        // Set the frame position of the clip to the beginning
        this.clip.setFramePosition(0);
    }

    // Check if the audio clip is currently running
    public final boolean isRunning() {
        return this.clip != null && this.clip.isRunning();
    }
}
