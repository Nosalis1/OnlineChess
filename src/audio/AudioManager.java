package audio;

import game.Board;
import game.Piece;
import util.Array;
import util.Console;

public class AudioManager {
    /**
     * Represents the singleton instance of the AudioManager.
     */
    public static AudioManager instance;

    /**
     * Initializes the AudioManager by loading audio clips and creating the instance.
     */
    public static void initialize() {
        if (instance == null)
            instance = new AudioManager();

        final String[] FILE_PATHS = {
                "src/audio/sfx/capture.wav",
                "src/audio/sfx/end.wav",
                "src/audio/sfx/move.wav",
                "src/audio/sfx/start.wav"
        };

        for (String path : FILE_PATHS) {
            clips.add(new AudioClip(path));
        }
    }

    /**
     * Holds an array of audio clips.
     */
    public static final Array<AudioClip> clips = new Array<>();

    /**
     * Finds and retrieves an audio clip from the clips array based on the specified index.
     *
     * @param index the index of the audio clip
     * @return the audio clip at the specified index, or null if not found
     */
    public static AudioClip findClip(final int index) {
        return clips.get(index);
    }

    /**
     * Finds and retrieves an audio clip from the clips array based on the specified name.
     *
     * @param name the name of the audio clip
     * @return the audio clip with the specified name, or null if not found
     */
    public static AudioClip findClip(final String name) {
        for (int i = 0; i < clips.size(); i++) {
            if (clips.get(i).getName().equals(name)) {
                return clips.get(i);
            }
        }
        return null;
    }

    /**
     * Plays the audio clip at the specified index.
     *
     * @param index the index of the audio clip to play
     */
    public static void playClip(final int index) {
        AudioClip clip = findClip(index);
        if (clip == null) {
            util.Console.error("Failed to play AudioClip : " + index, Console.PrintType.Audio);
            throw new NullPointerException();
        }
        clip.play();
    }

    /**
     * Plays the audio clip with the specified name.
     *
     * @param name the name of the audio clip to play
     */
    public static void playClip(final String name) {
        AudioClip clip = findClip(name);
        if (clip == null) {
            util.Console.error("Failed to play AudioClip : " + name, Console.PrintType.Audio);
            throw new NullPointerException();
        }

        clip.play();
    }

    /**
     * Constructs an AudioManager object and subscribes to events on the Board class.
     */
    public AudioManager() {
        Board.instance.onPieceEaten.add(this::onPieceEaten);
        Board.instance.onPieceMoved.add(this::onPieceMoved);
    }

    /**
     * Plays the capture sound effect when a piece is eaten.
     *
     * @param piece the piece that was eaten
     */
    private void onPieceEaten(Piece piece) {
        playClip(0);
    }

    /**
     * Plays the move sound effect when a piece is moved.
     *
     * @param piece the piece that was moved
     */
    private void onPieceMoved(Piece piece) {
        playClip(2);
    }

}
