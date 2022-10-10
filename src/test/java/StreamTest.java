import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    String[] array;

    @BeforeClass
    public void initialData() {
        array  = new String[]{"answer","Apple", "Doctor", "Arrow", "Ask", "Read"};
    }

    @BeforeMethod
    public void beforeStartTests(Method method) {
        System.out.println("Start " + method.getName() + " -------------------" );
    }

    @Test
    public void usingFor() {
        List<String> names = Arrays.asList(array);

        //for counting start with A
        int count = 0;
        for (String name : names) {
            if(name.toUpperCase(Locale.ROOT).startsWith("A")) count++;
        }
        System.out.println(count);
    }

    @Test
    public void usingStreamFilter() {
        List<String> names = Arrays.asList(array);

        System.out.println(
                names.stream().filter(x -> x.toUpperCase(Locale.ROOT).startsWith("A")).count()
        );

        names.stream().filter(x -> x.length() > 4)
                    .forEach(System.out::println);

        names.stream().filter(x -> x.length() > 4)
                .limit(1)
                .forEach(System.out::println);

    }

    @Test
    public void usingStreamMap() {
        List<String> names = Arrays.asList(array);

        names.stream().filter(x->x.endsWith("r"))
                    .map(x -> x.toUpperCase(Locale.ROOT))
                    .forEach(System.out::println);

        System.out.println("---------");

        names.stream().map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted()
                .forEach(System.out::println);

    }

    @Test
    public void usingStreamConcat() {
        List<String> names1 = Arrays.asList(array);
        List<String> names2 = Arrays.asList("Zoo","Pear", "Coffee");

        Stream.concat(names1.stream(), names2.stream())
                .map(s -> s.toUpperCase(Locale.ROOT))
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void usingStreamCollect_1() {
        List<String> names = Arrays.asList(array);

        List<String> result = names.stream().map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .collect(Collectors.toList());

        result.forEach(System.out::println);

    }

    @Test
    public void usingStreamCollect_2() {
        List<Integer> values = Arrays.asList(1, 2, 2, 7, 5, 7, 8, 9, 4);

        values.stream().distinct().sorted().forEach(System.out::println);

    }
}
