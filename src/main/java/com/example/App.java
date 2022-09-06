package com.example;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.example.model.Person;
import com.example.model.Product;

public class App {
        public static void main(String[] args) {

                Person person1 = new Person(1, "Arnold", LocalDate.of(1991, 1, 21));
                Person person2 = new Person(2, "Vásquez", LocalDate.of(1990, 2, 21));
                Person person3 = new Person(3, "Jaime", LocalDate.of(1980, 6, 23));
                Person person4 = new Person(4, "Duke", LocalDate.of(2019, 5, 15));
                Person person5 = new Person(5, "James", LocalDate.of(2010, 1, 4));

                Product product1 = new Product(1, "Ceviche", 15.0);
                Product product2 = new Product(2, "Porotos", 25.50);
                Product product3 = new Product(3, "Empanadas", 35.50);
                Product product4 = new Product(4, "Humitas", 15.0);

                List<Person> people = Arrays.asList(person1, person2, person3, person4, person5);
                List<Product> products = Arrays.asList(product1, product2, product3, product4);

                /*
                 * for(int i = 0; i<people.size(); i++){
                 * System.out.println(people.get(i));
                 * 
                 * }
                 */
                /*
                 * for(Person p : people){
                 * System.out.println(p);
                 * }
                 */
                /*
                 * people.forEach(x -> System.out.println(x));
                 * people.forEach(System.out::println);
                 */

                // 1-Filter (param: Predicate)
                List<Person> filteredList1 = people.stream()
                                .filter(p -> App.getAge(p.getBirthDate()) >= 18)
                                .collect(Collectors.toList());
                // App.printList(filteredList1);
                // 2-Map (param: Function)
                Function<String, String> coderFunction = name -> "Coder " + name;
                List<String> filteredList2 = people.stream()
                                // .filter(p -> App.getAge(p.getBirthDate()) >= 18)
                                // .map(p -> App.getAge(p.getBirthDate()))
                                // .map(p -> "Coder " + p.getName())
                                // .map(p-> p.getName())
                                .map(Person::getName)
                                .map(coderFunction)
                                .collect(Collectors.toList());
                // App.printList(filteredList2);

                // 3-Sorted (param: Comparator)
                Comparator<Person> byNameAsc = (Person o1, Person o2) -> o1.getName().compareTo(o2.getName());
                Comparator<Person> byNameDesc = (Person o1, Person o2) -> o2.getName().compareTo(o1.getName());
                Comparator<Person> byBirthDate = (Person o1, Person o2) -> o1.getBirthDate()
                                .compareTo(o2.getBirthDate());

                List<Person> filteredList3 = people.stream()
                                .sorted(byBirthDate)
                                .collect(Collectors.toList());
                // App.printList(filteredList3);

                // 4-Match (param: Predicate)
                Predicate<Person> startsWithPredicate = person -> person.getName().startsWith("J");
                // anyMatch : No evalua todo el stream, termina en la coincidencia
                boolean rpta1 = people.stream()
                                .anyMatch(startsWithPredicate);
                // allMatch : Evalua todo el stream bajo la condicion
                boolean rpta2 = people.stream()
                                .allMatch(startsWithPredicate);

                // noneMatch : Evalua todo el stream bajo la condicion
                boolean rpta3 = people.stream()
                                .noneMatch(startsWithPredicate);

                // 5-Limit/Skip
                int pageNumber = 1;
                int pageSize = 2;
                List<Person> filteredList4 = people.stream()
                                .skip(pageNumber * pageSize)
                                .limit(pageSize)
                                .collect(Collectors.toList());
                // App.printList(filteredList4);

                // 6-Collectors
                // GroupBy
                Map<String, List<Product>> collect1 = products.stream()
                                .filter(p -> p.getPrice() > 20)
                                .collect(Collectors.groupingBy(Product::getName));
                // System.out.println(collect1);
                // Counting
                Map<String, Long> collect2 = products.stream()
                                .collect(Collectors.groupingBy(
                                                Product::getName, Collectors.counting()));
                // System.out.println(collect2);
                // Agrupando por nombre producto y sumando
                Map<String, Double> collect3 = products.stream()
                                .collect(Collectors.groupingBy(
                                                Product::getName,
                                                Collectors.summingDouble(Product::getPrice)));
                // System.out.println(collect3);
                // Obteniendo suma y resumen
                DoubleSummaryStatistics statistics = products.stream()
                                .collect(Collectors.summarizingDouble(Product::getPrice));
                // System.out.println(statistics);

                // 7-reduce
                Optional<Double> sum = products.stream()
                                .map(Product::getPrice)
                                .reduce(Double::sum);
                // .reduce((a,b) -> a+b)
                System.out.println(sum.get());

        }

        public static int getAge(LocalDate birthDate) {
                return Period.between(birthDate, LocalDate.now()).getYears();
        }

        public static void printList(List<?> list) {
                list.forEach(System.out::println);
        }
}
