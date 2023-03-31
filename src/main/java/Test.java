import BusinessLayer.DeliveryService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Test {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> twoEvenSquares =
                numbers.stream()
                        .filter(n -> {
                            System.out.println("filtering " + n);
                            return n % 2 == 0; // filtreaza pare
                        })
                     /*   .map(n -> {
                            System.out.println("mapping " + n);
                            return n * n; // le mapeaza la patrat
                        })*/
                     //   .skip(1)
                       // .limit(5)  //??
                        .collect(toList());
        System.out.println(twoEvenSquares);
    }
}
