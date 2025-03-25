package dk.dtu.compute.course02324.assignment3.lists.uses;


import dk.dtu.compute.course02324.assignment3.lists.implementations.GenericComparator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A GUI element that is allows the user to interact and
 * change a list of persons.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class PersonsGUI extends GridPane {

    /**
     * The list of persons to be maintained in this GUI.
     */
    final private ArrayList<Person> persons;

    private GridPane personsPane;

    private Integer weightCount = 1;
    private Integer age = 0;

    Label label_avg_w = new Label("Average Weight: \n" + 0 + " kg");

    Label label_max_age = new Label("Maximum Age: \n" + 0 + " years old");

    Label label_min_age = new Label("Maximum Age: \n" + 0 + " years old");

    Label label_most_occurring = new Label("Most Occurring Name: \n" + "NA");

    private TextArea textAreaExceptions;

    //REDUNDANT CODE AFTER IMPLEMENTING STREAMS
//    private double avg_weight() {
//        //replaced by stream
//        if (persons.isEmpty()) {
//            return 0;
//        }
//        double sum = 0;
//        for (int i = 0; i < persons.size(); i++) {
//            sum += persons.get(i).weight;
//        }
//        return sum / persons.size();
//    }
//
//    Label label_most_occurring = new Label("Most Occurring Name: \n" + "NA");
//
//    private HashMap<String, Integer> n_counter = new HashMap<>();
//
//    private void add_to_map(Person P) {
//        //replaced by stream
//        if (n_counter.containsKey(P.name)) {
//            n_counter.replace(P.name, n_counter.get(P.name) + 1);
//        } else {
//            n_counter.put(P.name, 1);
//        }
//    }
//
//    private void delete_from_map(Person P) {
//        //replaced by stream
//        n_counter.replace(P.name, n_counter.get(P.name) - 1);
//    }
//
//    int frequency_of_most_occ = 0;
//    String max_name = "NA";
//
//    private void persons_mode() {
//        //replaced by stream
//        frequency_of_most_occ = 0;
//        if (n_counter.isEmpty()) {
//            max_name = "NA";
//        }
//        for (String key : n_counter.keySet()) {
//            if (n_counter.get(key) > frequency_of_most_occ) {
//                frequency_of_most_occ = n_counter.get(key);
//                max_name = key;
//            }
//        }
//    }

    /**
     * Constructor which sets up the GUI attached a list of persons.
     *
     * @param persons the list of persons which is to be maintained in
     *                this GUI component; it must not be <code>null</code>
     */
    public PersonsGUI(@NotNull ArrayList<Person> persons) {
        this.persons = persons;

        this.setVgap(5.0);
        this.setHgap(5.0);

        // text filed for user entering a name
        TextField name_field = new TextField();
        name_field.setPrefColumnCount(5);
        name_field.setText("name");

        TextField w_field = new TextField();
        w_field.setPrefColumnCount(5);
        w_field.setText(Integer.toString(weightCount));
        // the following is a simple way to make sure that the user can only
        // enter Integer values to the text field
        w_field.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                try {
                    Integer.parseInt(newValue);
                    w_field.setText(newValue);
                } catch (NumberFormatException e) {
                    if (newValue.isEmpty()) {
                        w_field.setText("0");
                    } else {
                        w_field.setText(Integer.toString(weightCount));
                    }
                }
            }

        });

        //trying stuff remove this when it works start
        TextField age_field = new TextField();
        age_field.setPrefColumnCount(5);
        age_field.setText(Integer.toString(age));
        // the following is a simple way to make sure that the user can only
        // enter Integer values to the text field
        age_field.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                try {
                    Integer.parseInt(newValue);
                    age_field.setText(newValue);
                } catch (NumberFormatException e) {
                    if (newValue.isEmpty()) {
                        age_field.setText("0");
                    } else {
                        age_field.setText(Integer.toString(age));
                    }
                }
            }

        });
        //trying stuff remove this when it works end

        // button for adding a new person to the list (based on
        // the name in the text field (the weight is just incrementing)
        Button addButton = new Button("Add at the end of list");
        addButton.setOnAction(
                e -> {
                    Person person = new Person(name_field.getText(), Integer.parseInt(w_field.getText()), Integer.parseInt(age_field.getText()));
                    persons.add(person);
                    weightCount++;
                    w_field.setText(Integer.toString(weightCount));
                    // makes sure that the GUI is updated accordingly
                    update();
                });

        TextField index = new TextField();
        index.setPrefColumnCount(2);
        index.setText("0");
        w_field.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                try {
                    Integer.parseInt(newValue);
                    w_field.setText(newValue);
                } catch (NumberFormatException e) {
                    if (newValue.isEmpty()) {
                        w_field.setText("0");
                    } else {
                        w_field.setText(oldValue);
                    }
                }
            }

        });

        Button add_atIndexButton = new Button("Add at Index: ");
        add_atIndexButton.setOnAction(
                e -> {
                    Person person = new Person(name_field.getText(), Integer.parseInt(w_field.getText()), Integer.parseInt(age_field.getText()));
                    try {
                        persons.add(Integer.parseInt(index.getText()), person);
                    } catch (IndexOutOfBoundsException err) {
                        textAreaExceptions.appendText(err.getMessage() + "\n");
                    }
                    weightCount++;
                    w_field.setText(Integer.toString(weightCount));
                    index.setText("0");
                    // makes sure that the GUI is updated accordingly
                    update();
                });

        Comparator<Person> comparator = new GenericComparator<>();

        // button for sorting the list (according to the order of Persons,
        // which implement the interface Comparable, which is converted
        // to a Comparator by the GenericComparator above)
        Button sortButton = new Button("Sort");
        sortButton.setOnAction(
                e -> {
                    try {
                        persons.sort(comparator);

                    } catch (UnsupportedOperationException err) {
                        textAreaExceptions.appendText(err.getMessage() + "\n");
                    }
                    // makes sure that the GUI is updated accordingly
                    update();
                });

        // button for clearing the list
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(
                e -> {
                    persons.clear();
                    weightCount = 0;
                    // makes sure that the GUI is updated accordingly
                    update();
                });

        // -------------- GUIng --------------------

        this.setPadding(new Insets(5, 10, 5, 10)); // Top, Right, Bottom, Left
        Label l_name = new Label("Name: ");
        Label l_weight = new Label("Weight: ");
        Label l_age = new Label("Age: ");

        VBox age = new VBox(l_age, age_field);
        age.setSpacing(5);

        VBox name = new VBox(l_name, name_field);
        name.setSpacing(5);

        VBox weight = new VBox(l_weight, w_field);
        weight.setSpacing(5);

        VBox w_age = new VBox(weight, age);

        HBox name_weight = new HBox(name, w_age);
        name_weight.setSpacing(10);
        HBox add_at_index = new HBox(add_atIndexButton, index);
        add_at_index.setSpacing(15);
        // combines the above elements into vertically arranged boxes
        // which are then added to the left column of the grid pane
        VBox buttonBox = new VBox(name_weight, addButton, add_at_index, sortButton, clearButton);
        buttonBox.setSpacing(10);

        VBox valueBox = new VBox(label_avg_w, label_most_occurring, label_max_age, label_min_age);
        valueBox.setSpacing(10);
        VBox actionBox = new VBox(buttonBox, valueBox);
        actionBox.setSpacing(15);
        this.add(actionBox, 0, 0);

        // create the elements of the right column of the GUI ------------------
        // (scrollable person list) ...
        Label labelPersonsList = new Label("Persons:");

        personsPane = new GridPane();
        personsPane.setPadding(new Insets(5));
        personsPane.setHgap(5);
        personsPane.setVgap(5);

        ScrollPane scrollPane = new ScrollPane(personsPane);
//        scrollPane.setMinWidth(300);
//        scrollPane.setMaxWidth(300);
        scrollPane.setMinHeight(300);
        scrollPane.setMaxHeight(300);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Label labelExceptions = new Label("Exceptions:");
        textAreaExceptions = new TextArea();
        textAreaExceptions.setWrapText(true);
        textAreaExceptions.setText("");
        textAreaExceptions.setEditable(false);
        textAreaExceptions.setScrollTop(Double.MAX_VALUE);

        ScrollPane scrollPane_e = new ScrollPane(textAreaExceptions);
        scrollPane_e.setMinHeight(300);
        scrollPane_e.setMaxHeight(300);
        scrollPane_e.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane_e.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // ... and adds these elements to the right-hand columns of
        // the grid pane
        VBox personsList = new VBox(labelPersonsList, scrollPane, labelExceptions, scrollPane_e);
        personsList.setSpacing(5.0);
        this.add(personsList, 1, 0);

        // updates the values of the different components with the values from
        // the stack
        update();
    }

    /**
     * Updates the values of the GUI elements with the current values
     * from the list.
     */
    private void update() {
        double avg = persons.stream()
                .mapToDouble((Person -> Person.weight))
                .average()
                .orElse(0);
        label_avg_w.setText("Average Weight: \n" + avg + " kg");

        Map.Entry<String, Long> f_most_occ = persons.stream().collect(
                Collectors.groupingBy(Person -> Person.name,Collectors.counting()))
                .entrySet().stream()
                .max((x,y) -> x.getValue().compareTo(y.getValue()))
                .orElse(Map.entry("NA",0L));
        label_most_occurring.setText("Most Occurring Name: \n" + f_most_occ.getKey() + " x " + f_most_occ.getValue());
        int max = persons.stream()
                .map(Person::getAge)
                .max(Integer::compare)
                .orElse(0);
        label_max_age.setText("Maximum Age: \n" + max + " years old");
        int min = persons.stream()
                .map(Person::getAge)
                .min(Integer::compare)
                .orElse(0);
        label_min_age.setText("Minimum Age: \n" + min + " years old");
        personsPane.getChildren().clear();
        // adds all persons to the list in the personsPane (with
        // a delete button in front of it)
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            Label personLabel = new Label(i + ": " + person.toString());
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(
                    e -> {
                        persons.remove(person);
                        update();
                    }
            );
            HBox entry = new HBox(deleteButton, personLabel);
            entry.setSpacing(5.0);
            entry.setAlignment(Pos.BASELINE_LEFT);
            personsPane.add(entry, 0, i);
        }
    }
}