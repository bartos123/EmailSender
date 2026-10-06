package utb.fai;

import java.net.*;
import java.io.*;

public class EmailSender {

    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private byte[] buffer = new byte[1024];



    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket (host, port);
        in = socket.getInputStream();
        out = socket.getOutputStream();
        in.read(buffer);
    }

    /*
     * Sends email from an email address to an email address with some subject and
     * text.
     * If the Socket throws an exception during sending, the exception is not
     * handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException {
        sendRaw("HELO localhost\r\n");
        sendRaw("MAIL FROM:" + formatEmail(from) + "\r\n");
        sendRaw("RCPT TO:" + formatEmail(to)+ "\r\n");
        sendRaw("DATA\r\n");
        String message = "From: " + from + "\r\n"
                + "To: " + to + "\r\n"
                + "Subject: " + subject + "\r\n\r\n"
                + text + "\r\n.\r\n";
        sendRaw(message);
    }


    /*
     * Sends QUIT and closes the socket
     */
    public void close() {
        try {
            sendRaw("QUIT\r\n");
            socket.close();
        } catch (Exception e) {
        }
    }

    private void sendRaw(String command) throws IOException {
        out.write(command.getBytes());
        out.flush();
        in.read(buffer);
    }
    private String formatEmail(String email) {
        if (email != null && email.startsWith("<") && email.endsWith(">")) {
            return email;
        }
        return "<" + email + ">";
    }
}
