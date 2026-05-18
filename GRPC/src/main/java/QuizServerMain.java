import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class QuizServerMain {
    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 5555;
        int roundSeconds = args.length > 1 ? Integer.parseInt(args[1]) : 30;
        QuizServerState state = new QuizServerState(new QuestionBank());
        GrpcQuizService service = new GrpcQuizService(state, roundSeconds);

        Server server = ServerBuilder.forPort(port)
                .addService(service)
                .build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            try {
                server.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        }, "grpc-shutdown-hook"));

        try {
            server.start();
            System.out.println("QuizServer gRPC listening on port " + port);
            server.awaitTermination();
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
