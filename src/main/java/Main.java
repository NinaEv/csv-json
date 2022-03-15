import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        File fileName = new File("src\\main\\resources\\data.csv");
        File result = new File("src\\main\\resources\\data.json");
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        List<Employee> list = parseSCV(fileName, columnMapping);
        listToJson(result, list);
    }

    public static List<Employee> parseSCV(File file, String[] map) {
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(map);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            return csv.parse();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void listToJson(File file, List<Employee> list) {
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(gson.toJson(list, listType));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
