import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static ObjectOutputStream objOut;


    public ServerThread (Socket socket) throws IOException {
        this.socket = socket;
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        in = new BufferedReader(new InputStreamReader(inputStream));
        out = new BufferedWriter(new OutputStreamWriter(outputStream));
        objOut = new ObjectOutputStream(outputStream);
        start();
    }


    @Override
    public void run() {
        try {
            while (true){
                String word = in.readLine();
                if (word.equals("stop")){
                    killSocket();
                    System.out.println("Socket closed successfully");
                    break;
                }
                if (word.equals("book")){
                    Book book = new Book("123", 123);
                    System.out.println("Sending object: " + book);
                    sendObject(book);
                }
                for(ServerThread st : Server.getServerList()){
                    System.out.println("Sending string: " + word);
                    st.sendMessage("You write: " + word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void killSocket() {
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
            objOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Server.getServerList().remove(this);
    }

    public void sendMessage(String message){
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObject(Object object){
        try {
            objOut.writeObject(object);
            objOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
