/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portfolio.sda.comunication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author Gilvanei
 */
public class MulticastReceiver {
    private static final int PORT = 8888;
    private final MulticastSocket socket;

    public MulticastReceiver(InetAddress address) throws IOException {
        this.socket = new MulticastSocket(PORT);
        this.socket.joinGroup(address);
        //this.player = player;
    }

    public void run() {
        byte[] inBuffer = new byte[256];
        DatagramPacket packet = new DatagramPacket(inBuffer, inBuffer.length);
        String message;

        while (true) {
            try {
                this.socket.receive(packet);
                message = new String(inBuffer, 0, packet.getLength());
                System.out.println(message);
                MulticastCommunication mc = new MulticastCommunication();
                mc.checarProtocolo(message);

            } catch (IOException ex) {
                
            }
        }

    }
}
