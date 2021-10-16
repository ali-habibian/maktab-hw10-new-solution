package q2;

import java.util.Arrays;
import java.util.List;

public class UnboundedWildcard {
    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        printList(list1);

        List<Double> list2 = Arrays.asList(1.1, 2.2, 3.3);
        printList(list2);
    }

    private static void printList(List<?> list) {

        System.out.println(list);
    }
}
