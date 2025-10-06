package utb.fai;

import java.net.*;
import java.io.*;

public class EmailSender {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception
     * during opening,
     * the exception is not handled in the constructor.
     */
    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        // Read server greeting
        System.out.println("SERVER: " + reader.readLine());

    }


    public void send(String from, String to, String subject, String text) throws IOException {
        sendCommand("HELO localhost");
        sendCommand("MAIL FROM:<" + from + ">");
        sendCommand("RCPT TO:<" + to + ">");
        sendCommand("DATA");

        // Write email headers and body
        writer.write("From: " + from + "\r\n");
        writer.write("To: " + to + "\r\n");
        writer.write("Subject: " + subject + "\r\n");
        writer.write("\r\n");
        writer.write(text + "\r\n");
        writer.write(".\r\n");
        writer.flush();

        System.out.println("SERVER: " + reader.readLine());

    }
    private void sendCommand(String cmd) throws IOException {
        writer.write(cmd + "\r\n");
        writer.flush();
        String response = reader.readLine();
        System.out.println("CLIENT: " + cmd);
        System.out.println("SERVER: " + response);
    }

    /*
     * Sends QUIT and closes the socket
     */
    public void close() {
        try {
            sendCommand("QUIT");
            socket.close();
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }

    }
}
