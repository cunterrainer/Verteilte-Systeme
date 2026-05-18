import io.grpc.Status;
import io.grpc.stub.ServerCallStreamObserver;
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
import quiz.grpc.QuestionPayload;
import quiz.grpc.ReadyRequest;
import quiz.grpc.ReadyResponse;
import quiz.grpc.RoomActionResponse;
import quiz.grpc.RoomEvent;
import quiz.grpc.RoomInfo;
import quiz.grpc.SubmitAnswerRequest;
import quiz.grpc.SubmitAnswerResponse;
import quiz.grpc.SubscribeRequest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class GrpcQuizService extends QuizServiceGrpc.QuizServiceImplBase implements GameEventPublisher {
    private final QuizServerState state;
    private final int roundSeconds;

    private final Map<String, CopyOnWriteArrayList<ServerCallStreamObserver<RoomEvent>>> roomSubscribers = new ConcurrentHashMap<>();
    private final Map<String, CopyOnWriteArrayList<ServerCallStreamObserver<GameEvent>>> gameSubscribers = new ConcurrentHashMap<>();

    public GrpcQuizService(QuizServerState state, int roundSeconds) {
        this.state = state;
        this.roundSeconds = roundSeconds;
    }

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String username = safeTrim(request.getUsername());
        if (username.isEmpty()) {
            responseObserver.onNext(HelloResponse.newBuilder().setSuccess(false).setError("MISSING_USERNAME").build());
            responseObserver.onCompleted();
            return;
        }
        boolean ok = state.registerUser(username);
        if (!ok) {
            responseObserver.onNext(HelloResponse.newBuilder().setSuccess(false).setError("USERNAME_IN_USE").build());
            responseObserver.onCompleted();
            return;
        }
        responseObserver.onNext(HelloResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void quit(QuitRequest request, StreamObserver<QuitResponse> responseObserver) {
        String username = safeTrim(request.getUsername());
        if (!state.isRegistered(username)) {
            responseObserver.onNext(QuitResponse.newBuilder().setSuccess(false).setError("HELLO_REQUIRED").build());
            responseObserver.onCompleted();
            return;
        }

        QuizRoom room = state.leaveCurrentRoom(username);
        if (room != null) {
            publishRoomUpdate(room);
        }

        state.unregisterUser(username);
        roomSubscribers.remove(username);
        gameSubscribers.remove(username);

        responseObserver.onNext(QuitResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void listRooms(ListRoomsRequest request, StreamObserver<ListRoomsResponse> responseObserver) {
        String username = safeTrim(request.getUsername());
        if (!state.isRegistered(username)) {
            responseObserver.onNext(ListRoomsResponse.newBuilder().setError("HELLO_REQUIRED").build());
            responseObserver.onCompleted();
            return;
        }

        ListRoomsResponse.Builder builder = ListRoomsResponse.newBuilder();
        for (QuizServerState.RoomSnapshot snapshot : state.listRooms()) {
            builder.addRooms(RoomInfo.newBuilder()
                    .setRoomId(snapshot.getRoomId())
                    .setRoomName(snapshot.getRoomName())
                    .setPlayerCount(snapshot.getPlayerCount())
                    .build());
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void createRoom(CreateRoomRequest request, StreamObserver<RoomActionResponse> responseObserver) {
        String username = safeTrim(request.getUsername());
        if (!state.isRegistered(username)) {
            responseObserver
                    .onNext(RoomActionResponse.newBuilder().setSuccess(false).setError("HELLO_REQUIRED").build());
            responseObserver.onCompleted();
            return;
        }

        PlayerSession session = state.getSession(username);
        if (session != null && session.getRoomId() != null) {
            responseObserver
                    .onNext(RoomActionResponse.newBuilder().setSuccess(false).setError("ALREADY_IN_ROOM").build());
            responseObserver.onCompleted();
            return;
        }

        String roomName = safeTrim(request.getRoomName());
        if (roomName.isEmpty()) {
            responseObserver
                    .onNext(RoomActionResponse.newBuilder().setSuccess(false).setError("MISSING_ROOM_NAME").build());
            responseObserver.onCompleted();
            return;
        }

        QuizRoom room = state.createRoom(roomName, username);
        if (room == null) {
            responseObserver
                    .onNext(RoomActionResponse.newBuilder().setSuccess(false).setError("ROOM_CREATE_FAILED").build());
            responseObserver.onCompleted();
            return;
        }

        publishRoomJoined(room);
        publishRoomUpdate(room);

        responseObserver.onNext(RoomActionResponse.newBuilder()
                .setSuccess(true)
                .setRoomId(room.getRoomId())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void joinRoom(JoinRoomRequest request, StreamObserver<RoomActionResponse> responseObserver) {
        String username = safeTrim(request.getUsername());
        if (!state.isRegistered(username)) {
            responseObserver
                    .onNext(RoomActionResponse.newBuilder().setSuccess(false).setError("HELLO_REQUIRED").build());
            responseObserver.onCompleted();
            return;
        }

        PlayerSession session = state.getSession(username);
        if (session != null && session.getRoomId() != null) {
            responseObserver
                    .onNext(RoomActionResponse.newBuilder().setSuccess(false).setError("ALREADY_IN_ROOM").build());
            responseObserver.onCompleted();
            return;
        }

        String roomId = safeTrim(request.getRoomId());
        if (roomId.isEmpty()) {
            responseObserver
                    .onNext(RoomActionResponse.newBuilder().setSuccess(false).setError("MISSING_ROOM_ID").build());
            responseObserver.onCompleted();
            return;
        }

        QuizRoom room = state.joinRoom(roomId, username);
        if (room == null) {
            responseObserver
                    .onNext(RoomActionResponse.newBuilder().setSuccess(false).setError("ROOM_JOIN_FAILED").build());
            responseObserver.onCompleted();
            return;
        }

        publishRoomJoined(room);
        publishRoomUpdate(room);

        responseObserver.onNext(RoomActionResponse.newBuilder()
                .setSuccess(true)
                .setRoomId(room.getRoomId())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void leaveRoom(LeaveRoomRequest request, StreamObserver<RoomActionResponse> responseObserver) {
        String username = safeTrim(request.getUsername());
        if (!state.isRegistered(username)) {
            responseObserver
                    .onNext(RoomActionResponse.newBuilder().setSuccess(false).setError("HELLO_REQUIRED").build());
            responseObserver.onCompleted();
            return;
        }

        QuizRoom room = state.leaveCurrentRoom(username);
        if (room == null) {
            responseObserver.onNext(RoomActionResponse.newBuilder().setSuccess(false).setError("NOT_IN_ROOM").build());
            responseObserver.onCompleted();
            return;
        }

        publishRoomUpdate(room);

        responseObserver.onNext(RoomActionResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void ready(ReadyRequest request, StreamObserver<ReadyResponse> responseObserver) {
        String username = safeTrim(request.getUsername());
        if (!state.isRegistered(username)) {
            responseObserver.onNext(ReadyResponse.newBuilder().setSuccess(false).setError("HELLO_REQUIRED").build());
            responseObserver.onCompleted();
            return;
        }

        PlayerSession session = state.getSession(username);
        if (session == null || session.getRoomId() == null) {
            responseObserver.onNext(ReadyResponse.newBuilder().setSuccess(false).setError("NOT_IN_ROOM").build());
            responseObserver.onCompleted();
            return;
        }

        QuizRoom room = state.getRoom(session.getRoomId());
        if (room == null || !room.hasPlayer(username)) {
            responseObserver.onNext(ReadyResponse.newBuilder().setSuccess(false).setError("ROOM_NOT_FOUND").build());
            responseObserver.onCompleted();
            return;
        }

        if (!room.markReady(username)) {
            responseObserver.onNext(ReadyResponse.newBuilder().setSuccess(false).setError("INVALID_STATE").build());
            responseObserver.onCompleted();
            return;
        }
        session.setReady(true);

        publishRoomUpdate(room);

        boolean gameStarted = false;
        if (room.startGame()) {
            gameStarted = true;
            publishGameStarting(room.getRoomId());
            Thread loopThread = new Thread(new RoomGameLoop(room, state, roundSeconds, this),
                    "room-loop-" + room.getRoomId());
            loopThread.start();
        }

        responseObserver.onNext(ReadyResponse.newBuilder().setSuccess(true).setGameStarted(gameStarted).build());
        responseObserver.onCompleted();
    }

    @Override
    public void submitAnswer(SubmitAnswerRequest request, StreamObserver<SubmitAnswerResponse> responseObserver) {
        String username = safeTrim(request.getUsername());
        if (!state.isRegistered(username)) {
            responseObserver.onNext(SubmitAnswerResponse.newBuilder()
                    .setStatus(SubmitAnswerResponse.Status.NOT_IN_ROOM)
                    .setError("HELLO_REQUIRED")
                    .build());
            responseObserver.onCompleted();
            return;
        }

        PlayerSession session = state.getSession(username);
        if (session == null || session.getRoomId() == null) {
            responseObserver.onNext(SubmitAnswerResponse.newBuilder()
                    .setStatus(SubmitAnswerResponse.Status.NOT_IN_ROOM)
                    .setError("NOT_IN_ROOM")
                    .build());
            responseObserver.onCompleted();
            return;
        }

        QuizRoom room = state.getRoom(session.getRoomId());
        if (room == null) {
            responseObserver.onNext(SubmitAnswerResponse.newBuilder()
                    .setStatus(SubmitAnswerResponse.Status.NOT_IN_ROOM)
                    .setError("ROOM_NOT_FOUND")
                    .build());
            responseObserver.onCompleted();
            return;
        }

        QuizRoom.SubmitStatus status = room.submitAnswer(username, request.getAnswer());
        responseObserver.onNext(SubmitAnswerResponse.newBuilder()
                .setStatus(mapSubmitStatus(status))
                .setError(status == QuizRoom.SubmitStatus.ACCEPTED ? "" : status.name())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void streamRoomEvents(SubscribeRequest request, StreamObserver<RoomEvent> responseObserver) {
        String username = safeTrim(request.getUsername());
        if (!state.isRegistered(username)) {
            responseObserver.onError(Status.PERMISSION_DENIED.withDescription("HELLO_REQUIRED").asRuntimeException());
            return;
        }

        ServerCallStreamObserver<RoomEvent> observer = (ServerCallStreamObserver<RoomEvent>) responseObserver;
        registerSubscription(username, observer, roomSubscribers);
    }

    @Override
    public void streamGameEvents(SubscribeRequest request, StreamObserver<GameEvent> responseObserver) {
        String username = safeTrim(request.getUsername());
        if (!state.isRegistered(username)) {
            responseObserver.onError(Status.PERMISSION_DENIED.withDescription("HELLO_REQUIRED").asRuntimeException());
            return;
        }

        ServerCallStreamObserver<GameEvent> observer = (ServerCallStreamObserver<GameEvent>) responseObserver;
        registerSubscription(username, observer, gameSubscribers);
    }

    @Override
    public void publishGameStarting(String roomId) {
        GameEvent event = GameEvent.newBuilder()
                .setType(GameEvent.Type.GAME_STARTING)
                .setRoomId(roomId)
                .setMessage("GAME_STARTING")
                .build();
        sendGameEventToRoom(roomId, event);
    }

    @Override
    public void publishRoundStart(QuizRoom room, Question question, int roundNumber, int roundSeconds) {
        QuestionPayload payload = QuestionPayload.newBuilder()
                .setId(question.getId())
                .setText(question.getText())
                .setOptionA(question.getOptionA())
                .setOptionB(question.getOptionB())
                .setOptionC(question.getOptionC())
                .setOptionD(question.getOptionD())
                .build();

        GameEvent event = GameEvent.newBuilder()
                .setType(GameEvent.Type.ROUND_START)
                .setRoomId(room.getRoomId())
                .setRoundNumber(roundNumber)
                .setRoundSeconds(roundSeconds)
                .setQuestion(payload)
                .build();
        sendGameEvent(room.playerUsernamesSnapshot(), event);
    }

    @Override
    public void publishRoundResult(String roomId, QuizRoom.RoundOutcome outcome) {
        GameEvent event = GameEvent.newBuilder()
                .setType(GameEvent.Type.ROUND_RESULT)
                .setRoomId(roomId)
                .setCorrectOption(outcome.getCorrectOption())
                .setFirstCorrectUser(outcome.getFirstCorrectUser())
                .setExplanation(outcome.getExplanation())
                .build();
        sendGameEventToRoom(roomId, event);
    }

    @Override
    public void publishScoreboard(String roomId, String scoreboard) {
        GameEvent event = GameEvent.newBuilder()
                .setType(GameEvent.Type.SCOREBOARD)
                .setRoomId(roomId)
                .setScoreboard(scoreboard)
                .build();
        sendGameEventToRoom(roomId, event);
    }

    @Override
    public void publishGameOver(String roomId, String message, String scoreboard) {
        GameEvent event = GameEvent.newBuilder()
                .setType(GameEvent.Type.GAME_OVER)
                .setRoomId(roomId)
                .setMessage(message)
                .setScoreboard(scoreboard)
                .build();
        sendGameEventToRoom(roomId, event);
    }

    @Override
    public void publishRoomClosed(String roomId) {
        GameEvent event = GameEvent.newBuilder()
                .setType(GameEvent.Type.ROOM_CLOSED)
                .setRoomId(roomId)
                .setMessage("ROOM_CLOSED")
                .build();
        sendGameEventToRoom(roomId, event);
    }

    @Override
    public void publishError(String roomId, String message) {
        GameEvent event = GameEvent.newBuilder()
                .setType(GameEvent.Type.ERROR)
                .setRoomId(roomId)
                .setMessage(message)
                .build();
        sendGameEventToRoom(roomId, event);
    }

    private void publishRoomJoined(QuizRoom room) {
        RoomEvent event = RoomEvent.newBuilder()
                .setType(RoomEvent.Type.ROOM_JOINED)
                .setRoomId(room.getRoomId())
                .setPlayerCount(room.getPlayerCount())
                .setReadyCount(room.getReadyCount())
                .setMessage("ROOM_JOINED")
                .build();
        sendRoomEvent(room.playerUsernamesSnapshot(), event);
    }

    private void publishRoomUpdate(QuizRoom room) {
        RoomEvent event = RoomEvent.newBuilder()
                .setType(RoomEvent.Type.ROOM_UPDATED)
                .setRoomId(room.getRoomId())
                .setPlayerCount(room.getPlayerCount())
                .setReadyCount(room.getReadyCount())
                .setMessage("ROOM_UPDATED")
                .build();
        sendRoomEvent(room.playerUsernamesSnapshot(), event);
    }

    private void sendGameEventToRoom(String roomId, GameEvent event) {
        QuizRoom room = state.getRoom(roomId);
        if (room == null) {
            return;
        }
        sendGameEvent(room.playerUsernamesSnapshot(), event);
    }

    private void sendRoomEvent(List<String> users, RoomEvent event) {
        for (String username : users) {
            CopyOnWriteArrayList<ServerCallStreamObserver<RoomEvent>> observers = roomSubscribers.get(username);
            if (observers == null) {
                continue;
            }
            for (ServerCallStreamObserver<RoomEvent> observer : observers) {
                if (observer.isCancelled()) {
                    observers.remove(observer);
                    continue;
                }
                try {
                    observer.onNext(event);
                } catch (Exception exception) {
                    observers.remove(observer);
                }
            }
        }
    }

    private void sendGameEvent(List<String> users, GameEvent event) {
        for (String username : users) {
            CopyOnWriteArrayList<ServerCallStreamObserver<GameEvent>> observers = gameSubscribers.get(username);
            if (observers == null) {
                continue;
            }
            for (ServerCallStreamObserver<GameEvent> observer : observers) {
                if (observer.isCancelled()) {
                    observers.remove(observer);
                    continue;
                }
                try {
                    observer.onNext(event);
                } catch (Exception exception) {
                    observers.remove(observer);
                }
            }
        }
    }

    private static String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }

    private static SubmitAnswerResponse.Status mapSubmitStatus(QuizRoom.SubmitStatus status) {
        switch (status) {
            case ACCEPTED:
                return SubmitAnswerResponse.Status.ACCEPTED;
            case INVALID_STATE:
                return SubmitAnswerResponse.Status.INVALID_STATE;
            case INVALID_OPTION:
                return SubmitAnswerResponse.Status.INVALID_OPTION;
            case ALREADY_ANSWERED:
                return SubmitAnswerResponse.Status.ALREADY_ANSWERED;
            case NOT_IN_ROOM:
                return SubmitAnswerResponse.Status.NOT_IN_ROOM;
            default:
                return SubmitAnswerResponse.Status.STATUS_UNSPECIFIED;
        }
    }

    private static <T> void registerSubscription(
            String username,
            ServerCallStreamObserver<T> observer,
            Map<String, CopyOnWriteArrayList<ServerCallStreamObserver<T>>> target) {
        target.computeIfAbsent(username, unused -> new CopyOnWriteArrayList<>()).add(observer);
        observer.setOnCancelHandler(() -> {
            CopyOnWriteArrayList<ServerCallStreamObserver<T>> observers = target.get(username);
            if (observers == null) {
                return;
            }
            observers.remove(observer);
            if (observers.isEmpty()) {
                target.remove(username);
            }
        });
    }
}
