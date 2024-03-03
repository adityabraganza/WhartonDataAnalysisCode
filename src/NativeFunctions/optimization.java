package NativeFunctions;

import java.util.ArrayList;
import java.util.List;

public class optimization{
    public static List<int[]> getWeight(int[] arr, int index, int max) {
        List<int[]> combinations = new ArrayList<>();

        if (index >= arr.length) {
            combinations.add(arr.clone());
            return combinations;
        }

        for (int i = 0; i <= max; i++) {
            arr[index] = i;
            combinations.addAll(getWeight(arr, index + 1, max));
        }

        return combinations;
    }
}
