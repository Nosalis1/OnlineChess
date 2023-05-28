package socket.packages;

public interface Streamable {
    public String pack();
    public void unapck(String buffer);
}
