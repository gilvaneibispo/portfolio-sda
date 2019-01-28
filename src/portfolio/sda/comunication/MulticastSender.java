/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portfolio.sda.comunication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author Gilvanei
 */
public class MulticastSender {

    private static final int PORT = 8888;
    private final InetAddress address;

    public MulticastSender(InetAddress address) {
        
        this.address = address;
    }

    public void sendPacket(String data) throws SocketException, IOException {
        
        DatagramSocket socket = new DatagramSocket();
        byte[] buffer = data.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, this.address, PORT);

        //Enviando...
        socket.send(packet);
    }
}
