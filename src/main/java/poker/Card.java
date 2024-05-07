package poker;

public record Card(String suit, int value) {

    public static Card parse(String s) {
        var suit = s.substring(0, 1);
        var rank = s.substring(1).toLowerCase()
                .replace("k", "13")
                .replace("q", "12")
                .replace("j", "11")
                .replace("a", "1");

        var value = Integer.parseInt(rank);
        return new Card(suit, value);
    }

    public String rank() {
        return switch (value) {
            case 1 -> "A";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> String.valueOf(value);
        };
    }

    @Override
    public String toString() {
        return suit + rank();
    }
}
