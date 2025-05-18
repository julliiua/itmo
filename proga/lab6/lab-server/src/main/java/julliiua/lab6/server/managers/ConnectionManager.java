package julliiua.lab6.server.managers;

import julliiua.lab6.common.utility.Request;
import julliiua.lab6.common.utility.Response;
import julliiua.lab6.server.Server;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ConnectionManager {
    private final int PORT;
    private ServerSocketChannel serverChannel;

    public ConnectionManager(int port) {
        this.PORT = port;
    }


    public void startServer() throws IOException {
        serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(PORT));
        serverChannel.configureBlocking(false);
    }

    public void close() throws IOException {
        if (serverChannel != null) {
            serverChannel.close();
        }
    }

    public ServerSocketChannel getServerSocketChannel() {
        return serverChannel;
    }


    public void send(Response response, SocketChannel clientChannel) throws IOException, ClassNotFoundException {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();

             ObjectOutputStream clientDataOut = new ObjectOutputStream(bytes)) {
            clientDataOut.writeObject(response);
            byte[] byteResponse = bytes.toByteArray();

            ByteBuffer dataLength = ByteBuffer.allocate(8).putInt(byteResponse.length);
            dataLength.flip();
            clientChannel.write(dataLength);
            Server.logger.info("Packet with message length sent: " + byteResponse.length);

            while (byteResponse.length > 256) {
                ByteBuffer packet = ByteBuffer.wrap(Arrays.copyOfRange(byteResponse, 0, 256));
                clientChannel.write(packet);
                byteResponse = Arrays.copyOfRange(byteResponse, 256, byteResponse.length);
                Server.logger.info("Packet of bytes sent with length: " + packet.position());
            }
            ByteBuffer packet = ByteBuffer.wrap(byteResponse);
            clientChannel.write(packet);
            Thread.sleep(300);
            Server.logger.info("Last packet of bytes sent with length: " + packet.position());
            ByteBuffer stopPacket = ByteBuffer.wrap(new byte[]{69, 69});
            clientChannel.write(stopPacket);
            Server.logger.info("Stop packet sent\n");
        } catch (InterruptedException e) {
            Server.logger.severe("Error while sending data: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public Request receive(SocketChannel clientChannel, SelectionKey key) throws IOException, ClassNotFoundException, NullRequestException {
        ByteBuffer clientData = ByteBuffer.allocate(2048);
        int bytesRead = clientChannel.read(clientData);
        Server.logger.info(bytesRead + " bytes received from client");
        if (bytesRead == -1) {
            key.cancel();
            Server.logger.warning("Client closed the connection");
            throw new NullRequestException("Client closed the connection");
        }
        clientData.flip(); // Переключаем ByteBuffer в режим чтения

        try (ObjectInputStream clientDataIn = new ObjectInputStream(new ByteArrayInputStream(clientData.array(), 0, bytesRead))) {
            Server.logger.info("Request received from client");
            return (Request) clientDataIn.readObject();
        } catch (StreamCorruptedException e) {
            key.cancel();
            Server.logger.severe("Request was not received from client: " + e.getMessage());
            throw new NullRequestException("Request was not received from client");
        }
    }

    public static class NullRequestException extends Exception {
        public NullRequestException(String message) {
            super(message);
        }
    }

}