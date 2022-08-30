package phonebook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static final String BASE_URL = "C:\\Users\\Stefan Koelewijn\\Downloads\\";
    public static List<PhoneNumber> directory;
    public static List<String> find;

    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader(BASE_URL + "\\directory.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            directory = bufferedReader.lines()
                    .map(s -> {
                        String[] pn = s.split(" ", 2);
                        return new PhoneNumber(Integer.parseInt(pn[0]), pn[1]);
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileReader fileReader = new FileReader(BASE_URL + "\\find.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            find = bufferedReader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Instant linearTimeTaken = linearSearchLooper(directory, find);
        Instant bubbleJumpTimeTaken = bubbleJumpLooper(directory, find, linearTimeTaken);
        Instant quickBinaryTimeTaken = quickBinaryLooper(directory, find);
        Instant hashTableTimeTaken = hashTableLooper(directory, find);
    }

    private static Instant linearSearchLooper(List<PhoneNumber> array, List<String> find) {
        System.out.println("Start searching (linear search)...");
        long startTime = System.currentTimeMillis();
        List<Integer> numbersFound = new ArrayList<>();

        for (String f : find) {
            numbersFound.add(Searchers.linearSearch(array, f));
        }

        long endTime = System.currentTimeMillis();
        Instant timeTaken = Instant.ofEpochMilli(endTime - startTime);

        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n%n",
                numbersFound.size(),
                find.size(),
                timeTaken.atZone(ZoneId.systemDefault()).getMinute(),
                timeTaken.getEpochSecond(),
                timeTaken.get(ChronoField.MILLI_OF_SECOND)
        );
        return timeTaken;
    }

    private static Instant bubbleJumpLooper(List<PhoneNumber> array, List<String> find, Instant linearTimeTaken) {
        System.out.println("Start searching (bubble sort + jump search)...");
        long startTime = System.currentTimeMillis();
        boolean linearSearched = false;

        List<PhoneNumber> sortedArray = Sorters.bubbleSort(array, startTime, linearTimeTaken);
        Instant sortingTime = Instant.ofEpochMilli(System.currentTimeMillis() - startTime);

        List<Integer> numbersFound = new ArrayList<>();
        if (!sortedArray.isEmpty()) {
            for (String f : find) {
                Integer result = Searchers.jumpSearch(sortedArray, f);
                if (result != -1) {
                    numbersFound.add(result);
                }
            }
        } else {
            linearSearched = true;
            for (String f : find) {
                numbersFound.add(Searchers.linearSearch(array, f));
            }
        }

        Instant searchingTime = Instant.ofEpochMilli(System.currentTimeMillis() - sortingTime.toEpochMilli() - startTime);
        Instant timeTaken = Instant.ofEpochMilli(System.currentTimeMillis() - startTime);

        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n",
                numbersFound.size(),
                find.size(),
                timeTaken.atZone(ZoneId.systemDefault()).getMinute(),
                timeTaken.getEpochSecond(),
                timeTaken.get(ChronoField.MILLI_OF_SECOND)
        );
        System.out.printf("Sorting time: %d min. %d sec. %d ms.",
                sortingTime.atZone(ZoneId.systemDefault()).getMinute(),
                sortingTime.getEpochSecond(),
                sortingTime.get(ChronoField.MILLI_OF_SECOND)
        );
        if (linearSearched) {
            System.out.println(" - STOPPED, moved to linear search");
        } else {
            System.out.println();
        }
        System.out.printf("Searching time: %d min. %d sec. %d ms.%n%n",
                searchingTime.atZone(ZoneId.systemDefault()).getMinute(),
                searchingTime.getEpochSecond(),
                searchingTime.get(ChronoField.MILLI_OF_SECOND)
        );
        return timeTaken;
    }

    private static Instant quickBinaryLooper(List<PhoneNumber> array, List<String> find) {
        System.out.println("Start searching (quick sort + binary search)...");
        long startTime = System.currentTimeMillis();

        List<PhoneNumber> sortedArray = Sorters.quickSort(array);
        Instant sortingTime = Instant.ofEpochMilli(System.currentTimeMillis() - startTime);

        List<Integer> numbersFound = new ArrayList<>();
        for (String f : find) {
            numbersFound.add(Searchers.binarySearch(sortedArray, f));
        }

        Instant searchingTime = Instant.ofEpochMilli(System.currentTimeMillis() - sortingTime.toEpochMilli() - startTime);
        Instant timeTaken = Instant.ofEpochMilli(System.currentTimeMillis() - startTime);

        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n",
                numbersFound.size(),
                find.size(),
                timeTaken.atZone(ZoneId.systemDefault()).getMinute(),
                timeTaken.getEpochSecond(),
                timeTaken.get(ChronoField.MILLI_OF_SECOND)
        );
        System.out.printf("Sorting time: %d min. %d sec. %d ms.%n",
                sortingTime.atZone(ZoneId.systemDefault()).getMinute(),
                sortingTime.getEpochSecond(),
                sortingTime.get(ChronoField.MILLI_OF_SECOND)
        );
        System.out.printf("Searching time: %d min. %d sec. %d ms.%n",
                searchingTime.atZone(ZoneId.systemDefault()).getMinute(),
                searchingTime.getEpochSecond(),
                searchingTime.get(ChronoField.MILLI_OF_SECOND)
        );

        return timeTaken;
    }

    private static Instant hashTableLooper(List<PhoneNumber> array, List<String> find) {
        System.out.println("Start searching (hash table)...");
        long startTime = System.currentTimeMillis();

        HashMap<String, Integer> map = new HashMap<>(1_200_000, 0.90f);
        array.forEach(pn -> map.put(pn.getName(), pn.getNumber()));
        Instant creatingTime = Instant.ofEpochMilli(System.currentTimeMillis() - startTime);

        List<Integer> numbersFound = new ArrayList<>();
        for (String f : find) {
            numbersFound.add(map.get(f));
        }

        Instant searchingTime = Instant.ofEpochMilli(System.currentTimeMillis() - creatingTime.toEpochMilli() - startTime);
        Instant timeTaken = Instant.ofEpochMilli(System.currentTimeMillis() - startTime);

        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n",
                numbersFound.size(),
                find.size(),
                timeTaken.atZone(ZoneId.systemDefault()).getMinute(),
                timeTaken.getEpochSecond(),
                timeTaken.get(ChronoField.MILLI_OF_SECOND)
        );
        System.out.printf("Creating time: %d min. %d sec. %d ms.%n",
                creatingTime.atZone(ZoneId.systemDefault()).getMinute(),
                creatingTime.getEpochSecond(),
                creatingTime.get(ChronoField.MILLI_OF_SECOND)
        );
        System.out.printf("Searching time: %d min. %d sec. %d ms.%n",
                searchingTime.atZone(ZoneId.systemDefault()).getMinute(),
                searchingTime.getEpochSecond(),
                searchingTime.get(ChronoField.MILLI_OF_SECOND)
        );

        return timeTaken;
    }
}
