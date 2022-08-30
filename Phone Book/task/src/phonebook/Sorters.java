package phonebook;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Sorters {

    public static List<PhoneNumber> bubbleSort(List<PhoneNumber> array, long startTime, Instant linearTimeTaken) {
        List<PhoneNumber> sortedArray = new ArrayList<>(array);

        // Keep track of the maximum index to check
        int maxIndex = sortedArray.size() - 1;
        boolean repeat = true;
        // Sorting will repeat until the whole array is sorted.
        while (repeat) {
            // Repeat will be kept false once the array is sorted
            repeat = false;
            // Loop through the whole array until the max index is reached
            for (int i = 0; i < maxIndex; i++) {
                // If the current element is bigger than the next element, swap them
                if (sortedArray.get(i).getName().compareTo(sortedArray.get(i + 1).getName()) > 0) {
                    // Array is not fully sorted yet, so set repeat back to true
                    repeat = true;
                    // If the current index + 1 is equal to the max index, decrease the max index by 1
                    if (i + 1 == maxIndex) {
                        maxIndex--;
                    }

                    // Swap the elements using a temp variable
                    PhoneNumber temp = sortedArray.get(i);
                    sortedArray.set(i, sortedArray.get(i + 1));
                    sortedArray.set(i + 1, temp);
                }
            }

            if (Instant.ofEpochMilli(System.currentTimeMillis() - startTime)
                    .isAfter(Instant.ofEpochMilli(linearTimeTaken.toEpochMilli() * 10))) {
                return new ArrayList<>();
            }
        }

        return sortedArray;
    }

    public static List<PhoneNumber> quickSort(List<PhoneNumber> array) {
        if (array.size() == 0) {
            return new ArrayList<>();
        }
        if (array.size() == 1) {
            return new ArrayList<>(array);
        }

        List<PhoneNumber> leftSubArray = new ArrayList<>();
        List<PhoneNumber> rightSubArray = new ArrayList<>();
        int pivot = getPivot(array);

        for (PhoneNumber pn : array) {
            if (!pn.equals(array.get(pivot))) {
                if (array.get(pivot).getName().compareTo(pn.getName()) > 0) {
                    leftSubArray.add(pn);
                } else {
                    rightSubArray.add(pn);
                }
            }
        }

        ArrayList<PhoneNumber> sortedArray = new ArrayList<>(quickSort(leftSubArray));
        sortedArray.add(array.get(pivot));
        sortedArray.addAll(quickSort(rightSubArray));

        return sortedArray;
    }

    private static int getPivot(List<PhoneNumber> array) {
        String first = array.get(0).getName();
        String middle = array.get((array.size() - 1) / 2).getName();
        String last = array.get(array.size() - 1).getName();

        if (first.compareTo(middle) >= 0 && first.compareTo(last) >= 0) {
            return (middle.compareTo(last) >= 0) ? (array.size() - 1) / 2 : array.size() - 1;
        } else if (middle.compareTo(first) >= 0 && middle.compareTo(last) >= 0) {
            return (first.compareTo(last) >= 0) ? 0 : array.size() - 1;
        } else if (last.compareTo(first) >= 0 && last.compareTo(middle) >= 0) {
            return (first.compareTo(middle) >= 0) ? 0 / 2 : (array.size() - 1) / 2;
        }
        return array.size() - 1;
    }
}
