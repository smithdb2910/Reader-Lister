package Task1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Album {
    private String title;
    private String artist;
    private int year;
    private String sales;
    private final ArrayList<Track> tracks;

    private Album() {
        this.tracks = new ArrayList<>();
    }

    public Album(String title, String artist, int year, String sales) {
        this();
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.sales = sales;
    }

    public Album(String title, String artist, int year, String sales, List<Track> tracks) {
        this();
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.sales = sales;
        this.tracks.addAll(tracks);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public void addTrack(Track track) {
        this.tracks.add(track);
    }

    public Track getTrack(int index) {
        return this.tracks.get(index);
    }

    public Track getTrack(String track) {
        // find the track and return it if it exists
        for (Track t : this.tracks) {
            if (t.getTitle().contains(track))
                return t;
        }
        return null;
    }

    public ArrayList<Track> getTracks() {
        return this.tracks;
    }

    @Override
    public String toString() {
        return String.format("%-50s %s %-30s %s %-4s %s %-6s %s", title, '|', artist, '|', year, '|', sales, '|');
    }

    public static class SalesComparator implements Comparator<Album> {
        @Override
        public int compare(Album o1, Album o2) {
            if (o1.getSales().contains("M")) {
                if (o2.getSales().contains("M")) {
                    return o2.getSales().compareTo(o1.getSales());
                }
                else {
                    return -1;
                }
            }
            else {
                if (o2.getSales().contains("M")) {
                    return 1;
                }
                else {
                    return o2.getSales().compareTo(o1.getSales());
                }
            }
        }
    }
}
