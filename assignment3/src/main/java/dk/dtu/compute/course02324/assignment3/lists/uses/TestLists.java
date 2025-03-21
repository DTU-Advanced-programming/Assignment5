package dk.dtu.compute.course02324.assignment3.lists.uses;


import dk.dtu.compute.course02324.assignment3.lists.implementations.BubbleSort;
import dk.dtu.compute.course02324.assignment3.lists.implementations.GenericComparator;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * To quickly play with the list implementations, this class implements some
 * uses in the static main method.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class TestLists {

    public static Comparator<Person> comparator = new GenericComparator<>();

    public static void main(String[] args) {
        ArrayList<Person> persons = new ArrayList<>();

        persons.add(new Person("Egon", 50, 40 ));
        persons.add(new Person("Ekkart", 74, 35));
        persons.add(new Person("Anton", 84, 55));
        persons.add(new Person("Carlos", 70, 65));
        persons.add(new Person("Ekkart", 70, 75));

        print(persons);
        System.out.println("--------------------");
        persons.sort(comparator);
        // BubbleSort.sort(comparator,persons);

        print(persons);
        System.out.println("--------------------");

        persons.add(2, new Person("Xavi", 85, 25));

        print(persons);
        System.out.println("--------------------");
        // persons.sort(comparator);
        BubbleSort.sort(comparator, persons);

        print(persons);
        System.out.println("--------------------");

        persons.add(new Person("Egon", 50, 25));
        persons.add(new Person("Ekkart", 74, 35));
        persons.add(new Person("Anton", 84, 45));
        persons.add(new Person("Carlos", 70,55));
        persons.add(new Person("Ekkart", 70, 65));

        print(persons);
        System.out.println("--------------------");

        // persons.add(0, new Person("Egon", 100 )); // should throw an exception
        persons.add(new Person("Egon", 100, 58 ));
        print(persons);
        System.out.println("--------------------");

        // persons.sort(comparator);  // should throw an exception
    }

    public static void print(ArrayList<?> list) {
        for (int i = 0; i < list.size(); i++ ) {
            System.out.println(i + ": " + list.get(i));
        }
    }

}
