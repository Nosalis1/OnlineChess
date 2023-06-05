package socket.packages;

public interface Streamable {
    String pack();

    void unapck(String buffer);
}