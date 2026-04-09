package utb.fai;

public class App {

    public static void main(String[] args) {
        if(args.length != 6){
            System.out.println("Insert in following order:\n<host> <port> <from> <to> <subject> <text>");
            return;
        }

        
        try {
            EmailSender sender = new EmailSender(args[0], Integer.parseInt(args[1]));
            
            //EmailSender sender = new EmailSender("smtp.utb.cz", 25);
            //sender.send("you@utb.cz", "you@utb.cz", "Email from Java", "Funguje to?\nSnad...");
            
            sender.send(args[2], args[3], args[4], args[5]);
            sender.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
