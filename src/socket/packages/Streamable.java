package socket.packages;

public interface Streamable {
    public String pack(Packet.Type type);
    public void unapck(String buffer);
}
