import audio.AudioClip;
import util.Array;

public class AudioManager {
    public static void initialize() {
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

    public static final util.Array<audio.AudioClip> clips = new Array<>();

    public static audio.AudioClip findClip(final int index) {
        return clips.get(index);
    }

    public static audio.AudioClip findClip(final String name) {
        for (int i = 0; i < clips.size(); i++)
            if (clips.get(i).getName().equals(name)) {
                return clips.get(i);
            }
        return null;
    }

    public static void playClip(final int index) {
        findClip(index).play();
    }

    public static void playClip(final String name) {
        audio.AudioClip clip = findClip(name);

        if (clip != null)
            clip.play();
    }
}
