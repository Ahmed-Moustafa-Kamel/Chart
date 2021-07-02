import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadJsonFile {

    public List<Passenger> readPassengersJsonFile() throws IOException
    {
        List<Passenger> passengerList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try (InputStream input = new FileInputStream("C:\\Users\\lenovo\\OneDrive\\Desktop\\ITI\\9. Java & UML programming\\Day5\\Data_to_use\\titanic.json"))
        {
            passengerList = objectMapper.readValue(input, new TypeReference<List<Passenger>>() {});
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return passengerList;
    }
}