package audio;

import game.Board;
import game.GameManager;
import game.Piece;
import gui.GuiManager;
import util.Array;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public abstract class AudioManager {
    public static final String DATA_PATH = "src\\audio\\sfx";

    private static boolean initialized = false;

    @SuppressWarnings("unused")
    public static boolean isInitialized() {
        return initialized;
    }

    public static void initialize() {
        if (initialized)
            return;

        try {
            load();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        AudioClip buttonClickSfx = find("buttonClick.wav");
        assert buttonClickSfx != null;
        GuiManager.onButtonClick.addAction(() -> play(buttonClickSfx));

        AudioClip startRoundSfx = find("startRound.wav");
        assert startRoundSfx != null;
        GameManager.onGameStarted.addAction(() -> play(startRoundSfx));

        AudioClip pieceMoveSfx = find("pieceMove.wav");
        assert pieceMoveSfx != null;
        Board.instance.onMove.addAction(() -> play(pieceMoveSfx));

        AudioClip pieceCaptureSfx = find("pieceCapture.wav");
        assert pieceCaptureSfx != null;
        Board.instance.onCapture.addAction(() -> play(pieceCaptureSfx));

        AudioClip checkSfx = find("check.wav");
        assert checkSfx != null;
        Board.instance.onCheck.add((game.Piece.Color ignore) -> play(checkSfx));

        AudioClip winSfx = find("gameWin.wav"), lossSfx = find("gameLoss.wav");
        assert winSfx != null && lossSfx != null;
        Board.instance.onCheck.add((game.Piece.Color color) -> {
            if (color == Piece.Color.White) {
                if (GameManager.localUser.isWhite()) {
                    play(lossSfx);
                } else {
                    play(winSfx);
                }
            } else {
                if (!GameManager.localUser.isWhite()) {
                    play(lossSfx);
                } else {
                    play(winSfx);
                }
            }
        });

        initialized = true;
    }

    @SuppressWarnings("unused")
    public static void free() {
        loadedClips.clear();
        initialized = false;
    }

    private static final Array<AudioClip> loadedClips = new Array<>();

    private static void load() throws FileNotFoundException {

        File file = new File(DATA_PATH);

        if (file.exists()) {
            String[] list = file.list();

            assert list != null;

            for (String item : list) {
                loadedClips.add(
                        new AudioClip(file.getPath() + "\\" + item));
            }
        } else
            throw new FileNotFoundException();
    }

    public static AudioClip get(final int index) {
        return loadedClips.get(index);
    }

    public static AudioClip find(final int id) {
        for (int i = 0; i < loadedClips.size(); i++)
            if (loadedClips.get(i).getID() == id)
                return loadedClips.get(i);
        return null;
    }

    public static AudioClip find(final String name) {
        for (int i = 0; i < loadedClips.size(); i++)
            if (loadedClips.get(i).isName(name))
                return loadedClips.get(i);
        return null;
    }

    @SuppressWarnings("unused")
    public static void playIndex(final int index) {
        get(index).play();
    }

    public static void play(AudioClip clip) {
        clip.play();
    }

    @SuppressWarnings("unused")
    public static void play(final int id) {
        Objects.requireNonNull(find(id)).play();
    }

    public static void play(final String name) {
        Objects.requireNonNull(find(name)).play();
    }
}