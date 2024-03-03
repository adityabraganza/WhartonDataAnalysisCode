package NativeFunctions;

import java.util.ArrayList;
import java.util.List;

public class optimization {
    public static List<int[]> getWeight(int[] arr, int index, int max, List<int[]> combinations) {
        if (index >= arr.length) {
            combinations.add(arr.clone());
        } else {
            for (int i = 0; i <= max; i++) {
                arr[index] = i;
                getWeight(arr, index + 1, max, combinations);
            }
        }
        return combinations;
    }

    public static List<int[]> getWeight(int[] arr, int max) {
        return getWeight(arr, 0, max, new ArrayList<>());
    }
}
