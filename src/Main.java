import jodd.json.JsonSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        HashMap<String, ArrayList<Person>> countryAndNameHash = new HashMap<>();


        System.out.println();
        System.out.println("Welcome to the country and name directory!");
        System.out.println("------------------------------------------");
        System.out.println();

        File f = new File("people.csv");
        Scanner scanner = null;
        try {
            scanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("File has not been found!");
        }
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] columns = line.split(",");
            String key = columns[4];

            if (!key.equals("country")) {
                if (!countryAndNameHash.containsKey(key)) {
                    Person person = new Person(columns[1], columns[2], columns[4]);
                    ArrayList<Person> nameArrayList = new ArrayList<>();
                    nameArrayList.add(person); // problem here, adding new value into each hashmap key
                    countryAndNameHash.put(key, nameArrayList);
                }
                else if (countryAndNameHash.containsKey(key)) {
                    ArrayList<Person> nameArrayList = new ArrayList<>();
                    Person person = new Person(columns[1], columns[2], columns[4]);
                    nameArrayList.add(person);
                    countryAndNameHash.get(key).add(0, person);
                }

            }
        }

        scanner.close();

        for (ArrayList<Person> people : countryAndNameHash.values()) {
            Collections.sort(people);
        }

        System.out.printf(countryAndNameHash.toString().replace(",", "").replace("]", "").replace("[", "").replace("{", " ").replace("}", ""));

        File f1 = new File("people.json");

        JsonSerializer serializer = new JsonSerializer();
        String jsonPeople = serializer.deep(true).serialize(countryAndNameHash);
        FileWriter fw = new FileWriter(f1);
        fw.write(jsonPeople);
        fw.close();
    }
}