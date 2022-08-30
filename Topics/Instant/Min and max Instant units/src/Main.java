import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Instant> instantList = createInstantList(scanner);

        long result = getMaxMinusMin(instantList); 

        System.out.println(result);
    }  

    public static List<Instant> createInstantList(Scanner scanner) {
        List<Instant> instantList = new ArrayList<>();
        instantList.add(Instant.parse(scanner.nextLine()));
        instantList.add(Instant.parse(scanner.nextLine()));
        instantList.add(Instant.parse(scanner.nextLine()));
        instantList.add(Instant.parse(scanner.nextLine()));

        return instantList;
    }

    private static long getMaxMinusMin(List<Instant> instantList) {
        var max = instantList.stream().max(Instant::compareTo).orElseThrow();
        var min = instantList.stream().min(Instant::compareTo).orElseThrow();

        return max.minusSeconds(min.getEpochSecond()).getEpochSecond();
    }
}