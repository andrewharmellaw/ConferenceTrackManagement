package sd.co.uk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// TODO: the contents of this class should be very small - pull all the logic out into classes with meaningful names
// TODO: find a better name for this then "Main" - take inspiration from the conference-planning domain
public class Main {

    // TODO: add a ConferenceCommittee class which will absorb the organiseTalk() method (make it static) and output a Conference
    static ArrayList<Talk> talkList = new ArrayList<Talk>();

    public static void main(String[] args) throws Exception {

        // if a file is given at run time read it
        if (args.length != 0) {
            String file_name = args[0].trim();
            File file = new File(file_name);

            if (file.exists()) {
                try {
                    String line = "";
                    BufferedReader b_reader = new BufferedReader(new FileReader(file));
                    while ((line = b_reader.readLine()) != null)
                    {
                        line = line.trim();
                        organiseTalk(line);
                    }

                } catch (IOException e) {
                    throw new Exception("Could not read file - " + file.getPath());

                }
                // call the below function
            } else {
                throw new Exception("File cant be found - " + file.getPath());
            }
        }
        // else read from terminal
        else {

            Scanner scanner = new Scanner(System.in);

            boolean hasNext = true;
            while (hasNext) {

                String line = scanner.nextLine();
                line = line.trim();
                if (line.isEmpty()) {
                    hasNext = false;
                    break;
                } else {
                    organiseTalk(line);
                }

            }
            scanner.close();
        }

        Conference conference = new Conference();

        ArrayList<Track> trackList = new ArrayList<Track>();
        try {
            trackList = conference.scheduleTalks(talkList);
        } catch (Exception e) {
            // TODO Auto-generated catch block - remove this comment
            e.printStackTrace();
        }

        if (null != trackList) {
            for (Track track : trackList) {
                System.out.println(track.toString());
            }
        }


    }

    // TODO: You could put this logic into the Talk Class
    public static void organiseTalk(String line) throws Exception {
        Talk talk;

        if (line.contains("lightning")) {
            talk = new Talk(line, 5);
            talkList.add(talk);

        } else if (line.contains("min")) {

            String title = line.substring(0, line.length() - 5);
            title = title.trim();
            int time = Integer.parseInt(line.substring(title.length(), line.length() - 3).trim());
            talk = new Talk(title, time);
            talkList.add(talk);

        } else {
            throw new Exception("Cant organise talk - " + line);
        }
    }


}
