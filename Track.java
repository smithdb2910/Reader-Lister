package Task1;

public class Track {
    private final String title;
    private final int minutes;
    private final int seconds;

    public Track(String title, int minutes, int seconds) {
        this.title = title;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public String getTitle() {
        return title;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    @Override
    public String toString() {
        return String.format("%-50s %s %-4s %s %-4s %s", title, '|', minutes, '|', seconds, '|');
    }
}
