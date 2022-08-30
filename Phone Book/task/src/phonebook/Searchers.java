package phonebook;

import java.util.List;

public class Searchers {

    public static Integer linearSearch(List<PhoneNumber> array, String value) {
        // Loop through all phone numbers in the array until the value is found
        for (PhoneNumber pn : array) {
            if (pn.getName().equals(value)) {
                return pn.getNumber();
            }
        }
        // Return null if the value wasn't found
        return null;
    }

    public static Integer jumpSearch(List<PhoneNumber> array, String value) {
        if (array.size() == 0) {
            return -1;
        }

        int currentPos = 0;
        int previousPos = 0;
        int finalPos = array.size() - 1;
        int stepSize = ((int) Math.floor(Math.sqrt(array.size())));
        stepSize = (stepSize == 0) ? 1 : stepSize;

        while (array.get(currentPos).getName().compareTo(value) < 0) {
            if (currentPos == finalPos) {
                return -1;
            }
            previousPos = currentPos;
            currentPos = Math.min(currentPos + stepSize, finalPos);
        }

        if (array.get(currentPos).getName().equals(value)) {
            return currentPos;
        }

        int result = jumpSearch(array.subList(previousPos, currentPos), value);
        return (result == -1) ? result : result + previousPos;
    }

    public static Integer binarySearch(List<PhoneNumber> array, String value) {
        // TODO: make this return null
        if (array.isEmpty()) {
            return 0;
        }

        int middleIndex = (array.size() - 1) / 2;
        // If the value at the current middle index is equal to the value we're searching for, return that index
        if (array.get(middleIndex).getName().equals(value)) {
            return middleIndex;
        }

        if (value.compareTo(array.get(middleIndex).getName()) > 0) {
            // Return the result of a recursive binary search on the right half of the array,
            // and add the middle index + 1 to the result
            return binarySearch(array.subList(middleIndex + 1, array.size()), value) + middleIndex + 1;
        } else {
            // Return the result of a recursive binary search on the left half of the array
            return binarySearch(array.subList(0, middleIndex), value);
        }
    }
}
