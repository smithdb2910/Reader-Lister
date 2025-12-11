package Task1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlbumApplication {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Album> albums = new ArrayList<>();

        try {
            // read in data to a string to be split into separate albums
            String dataFile = "albums.txt";
            String data = new String(Files.readAllBytes(Paths.get(dataFile)));

            // get albums from text file
            String[] albumData = data.split("(\\r\\n)?-{3,}(\\r\\n)?");

            for (String album : albumData) {
                // get each line in album
                String[] albumLine = album.split("\\n");

                // get album information
                String[] albumInfo = albumLine[0].split(":");

                String title = null;
                String artist = null;
                int year = -1;
                String sales = null;

                for (int i = 0; i < albumInfo.length; i++) {
                    switch (i) {
                        case 1:
                            //System.out.println("Title: " + albumInfo[i]);
                            title = albumInfo[i];
                            break;
                        case 2:
                            //System.out.println("Artist: " + albumInfo[i]);
                            artist = albumInfo[i];
                            break;
                        case 3:
                            //System.out.println("Year: " + albumInfo[i]);
                            year = Integer.parseInt(albumInfo[i]);
                            break;
                        case 4:
                            //System.out.println("Sales: " + albumInfo[i]);
                            sales = albumInfo[i].replace("\r", "");
                            break;
                    }
                }

                // check values have been stored correctly
                if (title != null && artist != null && year != -1 && sales != null) {
                    Album a = new Album(title, artist, year, sales);

                    // regex to match track title, mins, secs
                    Pattern pTitle = Pattern.compile("^[\\w| ]+");
                    Pattern time = Pattern.compile("\\d{1,2}");

                    // add tracks to album
                    for (int i = 1; i < albumLine.length; i++) {
                        //System.out.println(temp[i]);

                        // track title
                        Matcher mTitle = pTitle.matcher(albumLine[i]);
                        String trackTitle = "";
                        if (mTitle.find())
                            trackTitle = mTitle.group();

                        // track mins and secs
                        Matcher mTime = time.matcher(albumLine[i]);
                        ArrayList<String> allMatches = new ArrayList<>();
                        while (mTime.find()) {
                            allMatches.add(mTime.group());
                        }

                        String trackMins = allMatches.get(0);
                        String trackSecs = allMatches.get(1);

                        // add track to album
                        Track t = new Track(trackTitle, Integer.parseInt(trackMins), Integer.parseInt(trackSecs));
                        a.addTrack(t);
                    }

                    // add album to albums
                    albums.add(a);
                    //System.out.println(a);
                }
                else {
                    throw new Exception("album info cannot be read in correctly. Please check formatting of file.");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // sort albums by sale rank
        albums.sort(new Album.SalesComparator());

        // main menu
        String choice;
        while (true) {
            System.out.println("");
            System.out.println("List albums.........1");
            System.out.println("Select album........2");
            System.out.println("Search titles.......3");
            System.out.println("Exit................0");
            System.out.println("");
            System.out.print("Enter choice:");
            choice = input.next();
            switch (choice) {
                case "1": {
                    System.out.println("\n");
                    System.out.println("--------------------------------------------------------------------------------------------------------------");
                    System.out.println("| Rank | Title                                              | Artist                         | Year | Sales  |");
                    System.out.println("--------------------------------------------------------------------------------------------------------------");
                    int id = 0;
                    for (Album album : albums) {
                        if (id < 9) {
                            System.out.println("|   0" + (id + 1) + " | " + album.toString());
                        }
                        else {
                            System.out.println("|   " + (id + 1) + " | " + album.toString());
                        }
                        id++;
                    }
                    System.out.println("--------------------------------------------------------------------------------------------------------------");
                    System.out.print("\n\n Enter any key to Go Back...");
                    input.next();
                    clrscr();
                    break;
                }
                case "2": {
                    System.out.println("\n");
                    input.nextLine();
                    System.out.print(String.format("Enter a album ID from the list [1 - %s] : ", albums.size()));
                    int albumId = input.nextInt();
                    System.out.println("");
                    System.out.println("-------------------------------------------------------------------------");
                    System.out.println("Album title:             " + albums.get(albumId - 1).getTitle());
                    System.out.println("Artist:                  " + albums.get(albumId - 1).getArtist());
                    System.out.println("Year of release:         " + albums.get(albumId - 1).getYear());
                    System.out.println("Sales to date:           " + albums.get(albumId - 1).getSales());
                    System.out.println("-------------------------------------------------------------------------");
                    System.out.println();
                    System.out.println("Track list:");
                    System.out.println("--------------------------------------------------------------------------");
                    System.out.println("| No. | Title                                              | Mins | Secs |");
                    System.out.println("--------------------------------------------------------------------------");
                    int id = 0;
                    for (Track t : albums.get(albumId - 1).getTracks()) {
                        if (id < 9) {
                            System.out.println("| 0" + (id + 1) + "  | " + t.toString());
                        }
                        else {
                            System.out.println("| " + (id + 1) + "  | " + t.toString());
                        }
                        id++;
                    }
                    System.out.println("--------------------------------------------------------------------------");
                    System.out.print("\n\n Enter any Key to Go Back...");
                    input.next();
                    input.nextLine();
                    clrscr();
                    break;
                }
                case "3": {
                    boolean isFound = false;
                    System.out.println("\n");
                    input.nextLine();
                    System.out.print("To search, Enter the text : ");
                    String searchTitle = input.nextLine();
                    for (Album album : albums) {
                        boolean albumContains = false;
                        int trackId = 0;
                        for (Track t : album.getTracks()) {
                            String[] searchList = t.getTitle().split(" ");
                            for (String s : searchList) {
                                if (s.toLowerCase().contains(searchTitle.toLowerCase())) {
                                    isFound = true;
                                    // if album has not already had a song which matches
                                    if (!albumContains) {
                                        // print album heading
                                        System.out.println();
                                        System.out.println("-----");
                                        System.out.println(String.format("Artist (%s) Album (%s)", album.getArtist(), album.getTitle()));
                                        System.out.println("Matching song title(s):");
                                        System.out.println("-----");

                                        // set to true to prevent printing album information more than once
                                        albumContains = true;
                                    }

                                    String title = t.getTitle();

                                    // get first and last occurrence of word matched from search
                                    int firstIndex = title.toLowerCase().indexOf(searchTitle.toLowerCase());
                                    int lastIndex = firstIndex + searchTitle.length();

                                    // update title to capitalise matched part
                                    String titleCapital = title.substring(0, firstIndex) + title.substring(firstIndex, lastIndex).toUpperCase() + title.substring(lastIndex);

                                    System.out.println(String.format("Track %2s. %s", trackId + 1, titleCapital));
                                }
                            }
                            trackId++;
                        }
                    }
                    if (!isFound) {
                        System.out.println("No Song Found...");
                    }
                    System.out.println("-------------------------------------------------------------------------------");
                    System.out.print("\n\nEnter any key to Go Back...");
                    input.next();
                    clrscr();
                    break;
                }
                case "0":
                    System.exit(0);
                default:
                    System.out.println(" Invalid Choice....\n\n");
                    System.out.println("\n\n Enter any Key to Go Back...");
                    input.nextLine();
                    break;
            }
        }
    }

    // when chosen to exit the output will enter f number of next lines, in this case, 800;
    public static void clrscr() {
        int f = 800;
        for (int clear = 0; clear < f; clear++) {
            System.out.println("\b");
        }
    }
}
