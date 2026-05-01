import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class QuizServerMain {
    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 5555;
        QuizServerState state = new QuizServerState(new QuestionBank());

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("QuizServer listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket, state);
                handler.start();
            }
        }
    }
}
