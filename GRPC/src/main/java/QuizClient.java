import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import quiz.grpc.CreateRoomRequest;
import quiz.grpc.GameEvent;
import quiz.grpc.HelloRequest;
import quiz.grpc.HelloResponse;
import quiz.grpc.JoinRoomRequest;
import quiz.grpc.LeaveRoomRequest;
import quiz.grpc.ListRoomsRequest;
import quiz.grpc.ListRoomsResponse;
import quiz.grpc.QuitRequest;
import quiz.grpc.QuitResponse;
import quiz.grpc.QuizServiceGrpc;
import quiz.grpc.ReadyRequest;
import quiz.grpc.ReadyResponse;
import quiz.grpc.RoomActionResponse;
import quiz.grpc.RoomEvent;
import quiz.grpc.SubmitAnswerRequest;
import quiz.grpc.SubmitAnswerResponse;
import quiz.grpc.SubscribeRequest;

import java.util.concurrent.TimeUnit;

public class QuizClient implements AutoCloseable {
    public interface EventListener {
        void onRoomEvent(RoomEvent event);

        void onGameEvent(GameEvent event);

        default void onStreamError(Throwable throwable) {
        }

        default void onStreamCompleted() {
        }
    }

    private final ManagedChannel channel;
    private final QuizServiceGrpc.QuizServiceBlockingStub blockingStub;
    private final QuizServiceGrpc.QuizServiceStub asyncStub;
    private volatile String username;

    public QuizClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.blockingStub = QuizServiceGrpc.newBlockingStub(channel);
        this.asyncStub = QuizServiceGrpc.newStub(channel);
    }

    public HelloResponse hello(String username) {
        HelloResponse response = blockingStub.hello(HelloRequest.newBuilder().setUsername(username).build());
        if (response.getSuccess()) {
            this.username = username;
        }
        return response;
    }

    public ListRoomsResponse listRooms() {
        return blockingStub.listRooms(ListRoomsRequest.newBuilder().setUsername(requireUsername()).build());
    }

    public RoomActionResponse createRoom(String roomName) {
        return blockingStub.createRoom(CreateRoomRequest.newBuilder()
                .setUsername(requireUsername())
                .setRoomName(roomName)
                .build());
    }

    public RoomActionResponse joinRoom(String roomId) {
        return blockingStub.joinRoom(JoinRoomRequest.newBuilder()
                .setUsername(requireUsername())
                .setRoomId(roomId)
                .build());
    }

    public RoomActionResponse leaveRoom() {
        return blockingStub.leaveRoom(LeaveRoomRequest.newBuilder()
                .setUsername(requireUsername())
                .build());
    }

    public ReadyResponse ready() {
        return blockingStub.ready(ReadyRequest.newBuilder().setUsername(requireUsername()).build());
    }

    public SubmitAnswerResponse submitAnswer(String option) {
        return blockingStub.submitAnswer(SubmitAnswerRequest.newBuilder()
                .setUsername(requireUsername())
                .setAnswer(option)
                .build());
    }

    public QuitResponse quit() {
        String currentUsername = requireUsername();
        QuitResponse response = blockingStub.quit(QuitRequest.newBuilder().setUsername(currentUsername).build());
        if (response.getSuccess()) {
            this.username = null;
        }
        return response;
    }

    public void subscribe(EventListener listener) {
        String currentUsername = requireUsername();

        SubscribeRequest request = SubscribeRequest.newBuilder().setUsername(currentUsername).build();
        asyncStub.streamRoomEvents(request, new StreamObserver<>() {
            @Override
            public void onNext(RoomEvent value) {
                listener.onRoomEvent(value);
            }

            @Override
            public void onError(Throwable throwable) {
                listener.onStreamError(throwable);
            }

            @Override
            public void onCompleted() {
                listener.onStreamCompleted();
            }
        });

        asyncStub.streamGameEvents(request, new StreamObserver<>() {
            @Override
            public void onNext(GameEvent value) {
                listener.onGameEvent(value);
            }

            @Override
            public void onError(Throwable throwable) {
                listener.onStreamError(throwable);
            }

            @Override
            public void onCompleted() {
                listener.onStreamCompleted();
            }
        });
    }

    private String requireUsername() {
        if (username == null || username.isBlank()) {
            throw new IllegalStateException("Call hello(username) before this operation.");
        }
        return username;
    }

    @Override
    public void close() {
        channel.shutdown();
        try {
            if (!channel.awaitTermination(3, TimeUnit.SECONDS)) {
                channel.shutdownNow();
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            channel.shutdownNow();
        }
    }
}
