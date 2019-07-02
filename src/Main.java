import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

class Coordinate {
    int x;
    int y;
    long dist;
    String id;
    Coordinate(int x, int y, String id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }
}


class Comp implements Comparator<Coordinate> {

    public int compare(Coordinate a, Coordinate b) {
        if (a.dist < b.dist) {
            return -1;
        }
        else if (a.dist > b.dist) {
            return 1;
        }
        return 0;
    }
}
public class Main {

    public static boolean checkInput(String str, int[] origin, Set<String> set) {
        if (str == null || str.length() == 0) {
            return false;
        }
        String[] tmp = str.split(" ");
        if (tmp.length != 2) {
            return false;
        }
        String key = tmp[0] + "," + tmp[1];
        if (!set.contains(key)) {
            return false;
        }
        origin[0] = Integer.parseInt(tmp[0]);
        origin[1] = Integer.parseInt(tmp[1]);
        return true;
    }
    public static void main(String[] arg) {
        JSONParser parser = new JSONParser();

        JSONArray a = null;
        try {
            a = (JSONArray) parser.parse(new FileReader("coordinates.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int n = a.size();
        Coordinate[] coordinates = new Coordinate[n];



        Set<String> set = new HashSet<>();
        for (int i = 0; i < n; i++)
        {
            JSONObject person = (JSONObject)a.get(i);

            String id = (String) person.get("id");
            //System.out.println(id);

            String key = (String) person.get("value");
            set.add(key);
            //System.out.println(city);
            String[] coor = key.split(",");
            coordinates[i] = new Coordinate(Integer.parseInt(coor[0]), Integer.parseInt(coor[1]), id);
        }
        //for (int origin = 0; origin < n; origin++) {
        //    System.out.println("\n\nif origin index is " + origin + ":");
        System.out.println("Type the coordinate please like '1 1'");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int[] origin = new int[2];
        if (!checkInput(input, origin, set)) {
            System.out.println("Invalid coordinate");
            return ;
        }
        int originx = origin[0];
        int originy = origin[1];


        for (Coordinate C : coordinates) {
            C.dist = (long) (C.x - origin[0]) * (long) (C.x - origin[0]);
            C.dist += (long) (C.y - origin[1]) * (long) (C.y - origin[1]);
        }
        Arrays.sort(coordinates, new Comp());
        for (int i = 0; i < n; i++) {
            System.out.println(i + ", id: " + coordinates[i].id + ", x: " + coordinates[i].x + ", y: " + coordinates[i].y + ", dist: " + coordinates[i].dist);
        }
    }
}
