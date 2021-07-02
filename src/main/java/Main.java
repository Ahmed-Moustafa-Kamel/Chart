import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Passenger> passengersDataset = new ReadJsonFile().readPassengersJsonFile();

        GraphPassengers.graphPassengerAges(passengersDataset);
        GraphPassengers.graphPassengerClass(passengersDataset);
        GraphPassengers.graphPassengerSurvived(passengersDataset);
        GraphPassengers.graphPassengerNotSurvivedSex(passengersDataset);
        ///



    }
}
