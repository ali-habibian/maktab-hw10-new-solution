package q2;

import java.util.Arrays;
import java.util.List;

public class LowerBoundedWildcard {
    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(4, 5, 6, 7);
        printOnlyIntegerClassOrSuperClass(list1);

        List<Number> list2 = Arrays.asList(4, 5, 6, 7);
        printOnlyIntegerClassOrSuperClass(list2);
    }

    public static void printOnlyIntegerClassOrSuperClass(List<? super Integer> list) {
        System.out.println(list);
    }
}
