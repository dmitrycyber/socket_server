import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int port = 4004;
    private static List<ServerThread> serverList = new ArrayList<>();

    private static Socket clientSocket;
    private static ServerSocket server;
    private static InputStream inputStream;
    private static OutputStream outputStream;

    private static BufferedReader in;

    private static BufferedWriter out;
    private static ObjectOutputStream objOut;

    private static final String bookMess = "book";

    private static final String stopMess = "stop";


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server is running...");
        System.out.println("Listening port: " + port);

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                try {
                    ServerThread serverThread = new ServerThread(socket);
                    serverList.add(serverThread);
                } catch (IOException e) {
                    socket.close();
                }
            }
        }
        finally {
            server.close();
        }
    }


//        public static void main(String[] args) {
//        try {
//            try {
//                server = new ServerSocket(port);
//                System.out.println("Server is running...");
//                System.out.println("Listening port: " + port);
//
//                clientSocket = server.accept();
//
//                try {
//                    System.out.println("Client connected!");
//                    inputStream = clientSocket.getInputStream();
//                    outputStream = clientSocket.getOutputStream();
//                    in = new BufferedReader(new InputStreamReader(inputStream));
//                    out = new BufferedWriter(new OutputStreamWriter(outputStream));
//                    objOut = new ObjectOutputStream(outputStream);
//                    while (true) {
//                        String word = in.readLine();
//                        if (word.equals(stopMess)) {
//                            break;
//                        }
//                        if (word.equals(bookMess)) {
//                            Book book = new Book("qwe", 15);
//                            System.out.println("Sending object: " + book);
//                            objOut.writeObject(book);
//                            objOut.flush();
//                        }
//                        else {
//                            System.out.println("Sending string: " + word);
//                            out.write("Вы написали " + word + "\n");
//                            out.flush();
//                        }
//                        System.out.println("Successful!");
//
//
//                    }
//
//                } finally {
//                    clientSocket.close();
//                    inputStream.close();
//                    outputStream.close();
//                    objOut.close();
//                }
//            } finally {
//                System.out.println("Server shutdown!");
//                server.close();
//            }
//        } catch (IOException e) {
//            System.err.println(e);
//        }
//    }


    public static List<ServerThread> getServerList() {
        return serverList;
    }
}
