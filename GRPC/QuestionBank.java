import java.util.List;

public class QuestionBank {

    private final List<Question> questions = List.of(
            new Question(1, "Which protocol is connection-oriented?", "UDP", "TCP", "ICMP", "ARP", "B",
                    "TCP establishes a reliable connection."),
            new Question(2, "What does DNS resolve?", "File paths", "Domain names to IPs", "Ports to processes", "MAC to IP", "B",
                    "DNS maps domain names to IP addresses."),
            new Question(3, "Which is best suited for low-latency streaming?", "TCP with retransmit", "UDP", "SMTP", "HTTP polling", "B",
                    "UDP avoids retransmission overhead and is common in real-time traffic."),
            new Question(4, "What is a race condition?", "A scheduled backup", "Two threads competing unsafely for shared state", "A dead host", "A network timeout", "B",
                    "Race conditions happen when unsynchronized concurrent access causes inconsistent results."),
            new Question(5, "Which data structure keeps sorted keys?", "HashMap", "ArrayList", "TreeMap", "HashSet", "C",
                    "TreeMap stores entries sorted by key.")
    );

    public List<Question> allQuestions() {
        return questions;
    }
}
