package dk.dtu.compute.course02324.assignment3.lists.uses;

import javax.validation.constraints.NotNull;
import java.util.Iterator;

public class Person implements Comparable<Person> {

    final public String name;

    public double weight;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;

    Person(@NotNull String name, @NotNull double weight, @NotNull int age) {
        if (name == null || weight < 0) {
            throw new IllegalArgumentException("A persons must be initialized with a" +
                    "(non null) name and an age greater than 0");
        }
        this.name = name;
        this.weight = weight;
        this.age = age;
    }

    @Override
    public int compareTo(@NotNull Person o) {
        if (o == null) {
            throw new IllegalArgumentException("Argument of compareTo() must not be null");
        }
        return name.compareTo(o.name);
        // throw new UnsupportedOperationException("This operation is not yet implemented");
    }

    /**
     * Computes a simple string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        // This could be automatically generated, but this automatically
        // generated representation is a bit too verbose. Therefore, we
        // chose a simpler representation here.
        return name + ", " + weight + " kg, " + age + " years old";
    }

    /*
     * The following two methods must be implemented in order to respect the contract of objects
     * with respect to equals(), hashCode() and compareTo() methods. The details and reasons
     * as to why will be discussed much later.
     *
     * The implementations can be done fully automatically my IntelliJ (using the two attributes
     * of person).
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(@NotNull Object o) {
        if (!(o instanceof Person)) {return false;}
        Person p = (Person)o;
        return name.equals(p.name) ;//& weight == p.weight ;

        //      this must be implemented in accordance with the compareTo() method!
        //      See lectures for course 02324!
        //      Also add JavaDocs for @param and @return !
    }
}