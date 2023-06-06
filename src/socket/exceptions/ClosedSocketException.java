package socket.exceptions;

import socket.Client;

public class ClosedSocketException extends Exception {
    public ClosedSocketException(Client client) {

    }
}
