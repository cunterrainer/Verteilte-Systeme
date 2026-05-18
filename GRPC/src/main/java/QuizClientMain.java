import quiz.grpc.GameEvent;
import quiz.grpc.HelloResponse;
import quiz.grpc.ListRoomsResponse;
import quiz.grpc.ReadyResponse;
import quiz.grpc.RoomActionResponse;
import quiz.grpc.RoomInfo;
import quiz.grpc.SubmitAnswerResponse;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class QuizClientMain {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5555;
        String username = args.length > 2 ? args[2] : "player-" + System.currentTimeMillis();

        try (QuizClient client = new QuizClient(host, port)) {
            HelloResponse helloResponse = client.hello(username);
            if (!helloResponse.getSuccess()) {
                System.out.println("HELLO failed: " + helloResponse.getError());
                return;
            }

            client.subscribe(new QuizClient.EventListener() {
                @Override
                public void onRoomEvent(quiz.grpc.RoomEvent event) {
                    System.out.printf("[ROOM_EVENT] type=%s room=%s players=%d ready=%d msg=%s%n",
                            event.getType(), event.getRoomId(), event.getPlayerCount(), event.getReadyCount(),
                            event.getMessage());
                }

                @Override
                public void onGameEvent(GameEvent event) {
                    if (event.getType() == GameEvent.Type.ROUND_START && event.hasQuestion()) {
                        System.out.printf("[GAME_EVENT] ROUND_START room=%s round=%d time=%ds%n",
                                event.getRoomId(), event.getRoundNumber(), event.getRoundSeconds());
                        System.out.println("Question: " + event.getQuestion().getText());
                        System.out.println("A=" + event.getQuestion().getOptionA());
                        System.out.println("B=" + event.getQuestion().getOptionB());
                        System.out.println("C=" + event.getQuestion().getOptionC());
                        System.out.println("D=" + event.getQuestion().getOptionD());
                        return;
                    }
                    System.out.printf("[GAME_EVENT] type=%s room=%s msg=%s scoreboard=%s%n",
                            event.getType(), event.getRoomId(), event.getMessage(), event.getScoreboard());
                }

                @Override
                public void onStreamError(Throwable throwable) {
                    System.out.println("[STREAM_ERROR] " + throwable.getMessage());
                }
            });

            printHelp();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("> ");
                if (!scanner.hasNextLine()) {
                    break;
                }
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s+", 2);
                String command = parts[0].toUpperCase(Locale.ROOT);

                switch (command) {
                    case "HELP":
                        printHelp();
                        break;
                    case "LIST":
                    case "LIST_ROOMS": {
                        ListRoomsResponse response = client.listRooms();
                        if (!response.getError().isEmpty()) {
                            System.out.println("ERR: " + response.getError());
                            break;
                        }
                        List<RoomInfo> rooms = response.getRoomsList();
                        if (rooms.isEmpty()) {
                            System.out.println("No rooms.");
                            break;
                        }
                        for (RoomInfo room : rooms) {
                            System.out.printf("%s:%s:%d%n", room.getRoomId(), room.getRoomName(),
                                    room.getPlayerCount());
                        }
                        break;
                    }
                    case "CREATE":
                    case "CREATE_ROOM": {
                        if (parts.length < 2 || parts[1].isBlank()) {
                            System.out.println("Usage: CREATE <room_name>");
                            break;
                        }
                        RoomActionResponse response = client.createRoom(parts[1]);
                        if (!response.getSuccess()) {
                            System.out.println("ERR: " + response.getError());
                            break;
                        }
                        System.out.println("ROOM_CREATED: " + response.getRoomId());
                        break;
                    }
                    case "JOIN":
                    case "JOIN_ROOM": {
                        if (parts.length < 2 || parts[1].isBlank()) {
                            System.out.println("Usage: JOIN <room_id>");
                            break;
                        }
                        RoomActionResponse response = client.joinRoom(parts[1]);
                        if (!response.getSuccess()) {
                            System.out.println("ERR: " + response.getError());
                            break;
                        }
                        System.out.println("ROOM_JOINED: " + response.getRoomId());
                        break;
                    }
                    case "READY": {
                        ReadyResponse response = client.ready();
                        if (!response.getSuccess()) {
                            System.out.println("ERR: " + response.getError());
                            break;
                        }
                        System.out.println(response.getGameStarted() ? "READY: GAME_STARTING" : "READY: WAITING");
                        break;
                    }
                    case "ANSWER": {
                        if (parts.length < 2 || parts[1].isBlank()) {
                            System.out.println("Usage: ANSWER <A|B|C|D>");
                            break;
                        }
                        SubmitAnswerResponse response = client.submitAnswer(parts[1]);
                        if (response.getStatus() == SubmitAnswerResponse.Status.ACCEPTED) {
                            System.out.println("ANSWER_ACCEPTED");
                        } else {
                            System.out.println("ERR: " + response.getError());
                        }
                        break;
                    }
                    case "LEAVE": {
                        RoomActionResponse response = client.leaveRoom();
                        if (!response.getSuccess()) {
                            System.out.println("ERR: " + response.getError());
                            break;
                        }
                        System.out.println("LEFT_ROOM");
                        break;
                    }
                    case "QUIT": {
                        if (!client.quit().getSuccess()) {
                            System.out.println("ERR: quit failed");
                        }
                        return;
                    }
                    default:
                        System.out.println("Unknown command. Type HELP.");
                }
            }
        }
    }

    private static void printHelp() {
        System.out.println("Commands:");
        System.out.println("  HELP");
        System.out.println("  LIST or LIST_ROOMS");
        System.out.println("  CREATE <room_name>");
        System.out.println("  JOIN <room_id>");
        System.out.println("  READY");
        System.out.println("  ANSWER <A|B|C|D>");
        System.out.println("  LEAVE");
        System.out.println("  QUIT");
    }
}
