package socket.packages;

public interface Streamable {
    String pack(Packet.Type type);
    void unapck(String buffer);
}
