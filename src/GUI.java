import com.mhorak.dsa.sort.SelectionSort;
import com.mhorak.dsa.sort.ShellSort;
import com.mhorak.dsa.sort.Sort;
import com.mhorak.dsa.tools.Tools;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static com.mhorak.dsa.tools.Tools.decimalFormat;
import static com.mhorak.dsa.tools.Tools.filePath;

/**
 * Just the GUI. Makes all the work.
 */
public class GUI {

    /**
     * An array of integers used for storing and manipulating integer values.
     */
    private Integer[] arrayOfNumbersInt;
    /**
     * An array of doubles used for storing and manipulating double-precision floating-point values.
     */
    private Double[] arrayOfNumbersDouble;
    /**
     * An instance of the sorting algorithm used for sorting the array.
     */
    private Sort sortingAlgorithm;

    /**
     * Default constructor
     */
    public GUI() {
        // Create the main frame
        JFrame frame = initializeFrame();

        // Create a main panel to hold all components using GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing

        // Create a label at the top
        JLabel label = initializeLabel("Enter a number:", gbc, 0, mainPanel);

        // Create a checkbox for showing a process (disabled by default)
        JCheckBox showProcessCheckBox = initializeShowProcessCheckbox(gbc, mainPanel);

        // Create a text field for user input
        JTextField inputField = initializeNumberField(gbc, mainPanel, showProcessCheckBox);

        // Create a panel for task selection (e.g., "Standard" or "Individual")
        TaskPanel taskPanel = getTaskPanel(gbc, mainPanel);

        // Create a JTextArea for displaying the matrix
        JTextArea matrixArea = initializeMatrixArea(gbc, mainPanel);

        // Create radio buttons for sorting method selection (e.g., "Selection" or "Shell")
        SortingMethods sortingMethods = getSortingMethods(gbc, mainPanel);

        // Initialize the "Generate array" button
        initializeGenerateButton(gbc, mainPanel, taskPanel, inputField, matrixArea);

        // Initialize the timer label for displaying sorting time
        JLabel timer = initializeLabel("Sorting time: ", gbc, 4, mainPanel);

        // Initialize the "Sort" button for sorting the array
        initializeSortButton(gbc, mainPanel, taskPanel, sortingMethods, timer, matrixArea, showProcessCheckBox);

        // Initialize the "Sorted?" button for checking if the array is sorted
        initializeSortedButton(gbc, mainPanel, taskPanel);

        // Add the main panel to the frame
        frame.add(mainPanel);

        // Make the frame visible
        frame.setVisible(true);
    }


    /**
     * Initializes and configures the "Sorted?" button with its action listener.
     * This button allows the user to check if the array is sorted.
     *
     * @param gbc       The GridBagConstraints for specifying the button's layout.
     * @param mainPanel The main panel where the button will be added.
     * @param taskPanel The TaskPanel containing task selection radio buttons.
     */
    private void initializeSortedButton(GridBagConstraints gbc, JPanel mainPanel, TaskPanel taskPanel) {
        JButton sortedButton = new JButton("Sorted?");
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        mainPanel.add(sortedButton, gbc);

        sortedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean isSorted;
                    // Check if the array is sorted
                    if (taskPanel.standardRadioButton().isSelected()) {
                        isSorted = new Tools().isArraySorted(arrayOfNumbersInt);
                    } else {
                        isSorted = new Tools().isArraySorted(arrayOfNumbersDouble);
                    }

                    // Display a message popup based on whether the array is sorted or not
                    if (isSorted) {
                        JOptionPane.showMessageDialog(null, "The array is sorted.", "Array Status", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "The array is not sorted.", "Array Status", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception exception) {
                    // Handle any exceptions that may occur during array sorting check.
                    // In a production application, you should provide meaningful error handling.
                }
            }
        });
    }

    /**
     * Initializes and configures the "Generate array" button with its action listener.
     * This button generates an array of numbers based on user input and selected options.
     *
     * @param gbc        The GridBagConstraints for specifying the button's layout.
     * @param mainPanel  The main panel where the button will be added.
     * @param taskPanel  The TaskPanel containing task selection and number type radio buttons.
     * @param inputField The text field for user input.
     * @param matrixArea The text area for displaying the generated array.
     */
    private void initializeGenerateButton(GridBagConstraints gbc, JPanel mainPanel, TaskPanel taskPanel, JTextField inputField, JTextArea matrixArea) {
        JButton generateButton = new JButton("Generate array");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        mainPanel.add(generateButton, gbc);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (taskPanel.standardRadioButton().isSelected()) {
                        // Generate and display an array of Integers
                        arrayOfNumbersInt = generateAndDisplayArray(inputField, matrixArea, Integer.class, 0, taskPanel.hugeNumbersRadioButton().isSelected());
                    } else if (taskPanel.individualRadioButton().isSelected()) {
                        // Generate and display an array of Doubles
                        arrayOfNumbersDouble = generateAndDisplayArray(inputField, matrixArea, Double.class, 0.0, taskPanel.hugeNumbersRadioButton().isSelected());
                    }
                } catch (Exception exception) {
                    // Handle any exceptions that may occur during array generation.
                    // In a production application, you should provide meaningful error handling.
                }
            }
        });
    }

    /**
     * Generates an array based on user input and options, and displays it in the given text area.
     *
     * @param inputField     The text field for user input.
     * @param matrixArea     The text area for displaying the generated array.
     * @param elementType    The class representing the type of elements in the array (e.g., Integer.class, Double.class).
     * @param defaultValue   The default value for elements in the array.
     * @param useHugeNumbers Indicates whether to use huge numbers in the generated array.
     * @return The generated array.
     */
    private <T extends Number> T[] generateAndDisplayArray(
            JTextField inputField,
            JTextArea matrixArea,
            Class<T> elementType,
            T defaultValue,
            boolean useHugeNumbers
    ) {
        try {
            int input = Integer.parseInt(inputField.getText());
            T[] targetArray = (T[]) Array.newInstance(elementType, input);
            Arrays.fill(targetArray, defaultValue);

            if (useHugeNumbers) {
                Tools.initializeArray(targetArray, true); // Use huge numbers
            } else {
                Tools.initializeArray(targetArray, false); // Use low numbers
            }

            if (targetArray.length <= 15) {
                matrixArea.setText("");
                for (int i = 0; i < targetArray.length; i++) {
                    if (targetArray[i] instanceof Double) {
                        matrixArea.append(decimalFormat.format(targetArray[i]) + " ");
                    } else {
                        matrixArea.append(targetArray[i] + " ");
                    }
                }
            } else {
                matrixArea.setText("Array was generated");
            }

            /**
             * Applying an individual variant function
             */
            if (targetArray[0] instanceof Double) {
                Tools.mutateArray((Double[]) targetArray);

                //Printing new array so the function is visible
                if (targetArray.length <= 15) {
                    matrixArea.append("\n");
                    for (int i = 0; i < targetArray.length; i++) {
                        if (targetArray[i] instanceof Double) {
                            matrixArea.append(decimalFormat.format(targetArray[i]) + " ");
                        } else {
                            matrixArea.append(targetArray[i] + " ");
                        }
                    }
                }
            }

            writeArrayToFile(targetArray, filePath, true);

            return targetArray;
        } catch (Exception exception) {
            // Handle any exceptions that may occur during array generation.
            // In a production application, you should provide meaningful error handling.
            return null;
        }
    }


    /**
     * Initializes and configures the "Sort" button with its action listener.
     * This button triggers the sorting of an array based on user input and selected options.
     *
     * @param gbc                 The GridBagConstraints for specifying the button's layout.
     * @param mainPanel           The main panel where the button will be added.
     * @param taskPanel           The TaskPanel containing task selection and number type radio buttons.
     * @param sortingMethods      An instance of SortingMethods that provides sorting functionality.
     * @param timer               The label to display sorting time.
     * @param matrixArea          The text area for displaying the sorted array.
     * @param showProcessCheckBox The checkbox to control whether to show the sorting process.
     */
    private void initializeSortButton(GridBagConstraints gbc, JPanel mainPanel, TaskPanel taskPanel, SortingMethods sortingMethods, JLabel timer, JTextArea matrixArea, JCheckBox showProcessCheckBox) {
        JButton sortButton = new JButton("Sort");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        mainPanel.add(sortButton, gbc);

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (taskPanel.standardRadioButton().isSelected()) {
                    // Sort the array of Integers
                    sortArray(arrayOfNumbersInt, sortingMethods, timer, matrixArea, showProcessCheckBox);
                    writeArrayToFile(arrayOfNumbersInt, filePath, false);
                } else if (taskPanel.individualRadioButton().isSelected()) {
                    // Sort the array of Doubles
                    sortArray(arrayOfNumbersDouble, sortingMethods, timer, matrixArea, showProcessCheckBox);
                    writeArrayToFile(arrayOfNumbersInt, filePath, false);
                }


            }
        });
    }

    /**
     * Sorts an array of comparable elements using the selected sorting method.
     * Displays the sorting process and timer based on user preferences.
     *
     * @param arrayOfNumbers      The array of comparable elements to be sorted.
     * @param sortingMethods      An instance of SortingMethods that provides sorting method selection.
     * @param timer               The label to display sorting time.
     * @param matrixArea          The text area for displaying the sorting process.
     * @param showProcessCheckBox The checkbox to control whether to show the sorting process.
     */
    private void sortArray(Comparable[] arrayOfNumbers, SortingMethods sortingMethods, JLabel timer, JTextArea matrixArea, JCheckBox showProcessCheckBox) {
        if (sortingMethods.selectionSort().isSelected()) {
            // Initialize and perform selection sort
            sortingAlgorithm = new SelectionSort(arrayOfNumbers);
        } else if (sortingMethods.shellSort().isSelected()) {
            sortingAlgorithm = new ShellSort(arrayOfNumbers);
        }

        if (showProcessCheckBox.isSelected()) {
            // Display the sorting process step by step
            var arraySteps = performStepSorting();
            fillMatrix(matrixArea, arraySteps);
        } else {
            // Perform the sorting without displaying the process
            performSorting();
        }

        // Display the sorting timer
        showTimer(timer);
    }

    /**
     * Fills the provided text area with the contents of an ArrayList containing arrays of comparable elements.
     * Each array in the list represents a step in the sorting process.
     *
     * @param matrixArea The text area where the sorting process will be displayed.
     * @param arraySteps An ArrayList containing arrays representing steps of the sorting process.
     */
    private void fillMatrix(JTextArea matrixArea, ArrayList<Comparable[]> arraySteps) {
        matrixArea.setText(""); // Clear the text area
        for (int i = 0; i < arraySteps.size(); i++) {
            for (int j = 0; j < arraySteps.get(i).length; j++) {
                matrixArea.append(arraySteps.get(i)[j] + " "); // Append each element to the text area
            }
            matrixArea.append("\n"); // Add a new line after each step
        }
    }


    /**
     * Creates and configures a set of sorting method radio buttons and returns a SortingMethods object
     * that encapsulates the selected radio buttons.
     *
     * @param gbc       The GridBagConstraints for specifying the layout of components.
     * @param mainPanel The main panel where the radio buttons will be added.
     * @return A SortingMethods object representing the selected sorting method.
     */
    private static SortingMethods getSortingMethods(GridBagConstraints gbc, JPanel mainPanel) {
        // Create a ButtonGroup to manage the radio buttons
        ButtonGroup radioButtonGroup = new ButtonGroup();

        // Create the first radio button
        JRadioButton selectionSort = new JRadioButton("Selection");

        selectionSort.setSelected(true);
        radioButtonGroup.add(selectionSort); // Add to the group

        // Create the second radio button
        JRadioButton shellSort = new JRadioButton("Shell");
        radioButtonGroup.add(shellSort); // Add to the group

        // Create a panel for the radio buttons
        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.setLayout(new FlowLayout());
        radioButtonPanel.add(selectionSort);
        radioButtonPanel.add(shellSort);

        // Add the radio button panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        mainPanel.add(radioButtonPanel, gbc);

        // Create and return a SortingMethods object encapsulating the radio buttons
        SortingMethods sortingMethods = new SortingMethods(selectionSort, shellSort);
        return sortingMethods;
    }

    /**
     * A record that represents a pair of sorting method radio buttons.
     *
     * @param selectionSort The radio button for the Selection Sort method.
     * @param shellSort     The radio button for the Shell Sort method.
     */
    private record SortingMethods(JRadioButton selectionSort, JRadioButton shellSort) {
    }

    /**
     * Initializes a JTextArea component to display a matrix and adds it to the main panel.
     *
     * @param gbc       The GridBagConstraints for specifying the text area's layout.
     * @param mainPanel The main panel where the text area will be added.
     * @return The initialized JTextArea component for displaying the matrix.
     */
    private static JTextArea initializeMatrixArea(GridBagConstraints gbc, JPanel mainPanel) {
        // Create a JTextArea with 10 rows and 30 columns
        JTextArea matrixArea = new JTextArea(10, 30);

        // Make the text area read-only
        matrixArea.setEditable(false);

        // Create a scroll pane for the text area
        JScrollPane matrixScrollPane = new JScrollPane(matrixArea);

        // Set the position and size of the scroll pane in the main panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        mainPanel.add(matrixScrollPane, gbc);

        return matrixArea;
    }

    /**
     * Initializes a TaskPanel containing radio buttons for selecting task types and number types.
     * Adds the TaskPanel to the main panel.
     *
     * @param gbc       The GridBagConstraints for specifying the layout of the radio buttons.
     * @param mainPanel The main panel where the TaskPanel will be added.
     * @return The initialized TaskPanel with radio buttons for task and number type selection.
     */
    private static TaskPanel getTaskPanel(GridBagConstraints gbc, JPanel mainPanel) {
        // Create a panel for task-related radio buttons
        JPanel radioButtonPanelTask = new JPanel();
        radioButtonPanelTask.setLayout(new GridLayout(2, 1)); // Two rows, one column

        // Create a ButtonGroup for the existing radio buttons ("Standard" and "Individual")
        ButtonGroup radioButtonGroupTask = new ButtonGroup();

        // Create the "Standard" radio button
        JRadioButton standardRadioButton = new JRadioButton("Standard");
        standardRadioButton.setSelected(true);
        radioButtonGroupTask.add(standardRadioButton);
        radioButtonPanelTask.add(standardRadioButton);

        // Create the "Individual" radio button
        JRadioButton individualRadioButton = new JRadioButton("Individual");
        radioButtonGroupTask.add(individualRadioButton);
        radioButtonPanelTask.add(individualRadioButton);

        // Add the radio button panel for task selection to the top-right corner of the main panel
        gbc.gridx = 3;  // Adjust the column index to place it on the top-right
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 2; // Adjust the grid height to accommodate the existing radio buttons
        mainPanel.add(radioButtonPanelTask, gbc);

        // Create a panel for number type-related radio buttons
        JPanel radioButtonPanelNumbers = new JPanel();
        radioButtonPanelNumbers.setLayout(new GridLayout(2, 1)); // Two rows, one column

        // Create a separate ButtonGroup for the new radio buttons ("Huge numbers" and "Low numbers")
        ButtonGroup radioButtonGroupNumbers = new ButtonGroup();

        JRadioButton lowNumbersRadioButton = new JRadioButton("Low numbers");
        lowNumbersRadioButton.setSelected(true);
        radioButtonGroupNumbers.add(lowNumbersRadioButton);
        radioButtonPanelNumbers.add(lowNumbersRadioButton);

        JRadioButton hugeNumbersRadioButton = new JRadioButton("Huge numbers");
        radioButtonGroupNumbers.add(hugeNumbersRadioButton);
        radioButtonPanelNumbers.add(hugeNumbersRadioButton);

        // Add the radio button panel for number type selection to the top-right corner of the main panel
        gbc.gridx = 3;  // Adjust the column index to place it on the top-right
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1; // Adjust the grid height to accommodate the existing radio buttons
        mainPanel.add(radioButtonPanelNumbers, gbc);

        // Create and return a TaskPanel record with the initialized radio buttons
        TaskPanel taskPanel = new TaskPanel(standardRadioButton, individualRadioButton, hugeNumbersRadioButton, lowNumbersRadioButton);
        return taskPanel;
    }

    /**
     * A record representing a panel containing radio buttons for task and number type selection.
     */
    private record TaskPanel(JRadioButton standardRadioButton, JRadioButton individualRadioButton,
                             JRadioButton hugeNumbersRadioButton, JRadioButton smallNumbersRadioButton) {
    }

    /**
     * Initializes a JTextField for entering the number of elements in the array and adds it to the main panel.
     * Also, creates a DocumentListener to monitor changes in the inputField and enable/disable the showProcessCheckBox accordingly.
     *
     * @param gbc                 The GridBagConstraints for specifying the layout of the input field.
     * @param mainPanel           The main panel where the input field will be added.
     * @param showProcessCheckBox The checkbox to be enabled or disabled based on the inputField's value.
     * @return The initialized JTextField for entering the number of elements in the array.
     */
    private static JTextField initializeNumberField(GridBagConstraints gbc, JPanel mainPanel, JCheckBox showProcessCheckBox) {
        JTextField inputField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainPanel.add(inputField, gbc);

        // Create a DocumentListener to monitor changes in the inputField
        inputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateCheckBox();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateCheckBox();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateCheckBox();
            }

            /**
             * Helper method to enable or disable the checkbox based on inputField's value.
             * If the input is not a valid number, the checkbox is disabled.
             */
            private void updateCheckBox() {
                try {
                    int value = Integer.parseInt(inputField.getText());
                    showProcessCheckBox.setEnabled(value > 0 && value <= 15);
                    if (!showProcessCheckBox.isEnabled()) {
                        showProcessCheckBox.setSelected(false);
                    }
                } catch (NumberFormatException ex) {
                    // Handle the case where the input is not a valid number
                    showProcessCheckBox.setEnabled(false);
                }
            }
        });

        return inputField;
    }

    /**
     * Initializes a JCheckBox for enabling or disabling the display of the sorting process and adds it to the main panel.
     *
     * @param gbc       The GridBagConstraints for specifying the layout of the checkbox.
     * @param mainPanel The main panel where the checkbox will be added.
     * @return The initialized JCheckBox for showing or hiding the sorting process.
     */
    private static JCheckBox initializeShowProcessCheckbox(GridBagConstraints gbc, JPanel mainPanel) {
        JCheckBox showProcessCheckBox = new JCheckBox("Show process");
        showProcessCheckBox.setEnabled(false);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainPanel.add(showProcessCheckBox, gbc);
        return showProcessCheckBox;
    }

    /**
     * Initializes a JLabel with the given text and adds it to the main panel.
     *
     * @param text      The text to display on the label.
     * @param gbc       The GridBagConstraints for specifying the layout of the label.
     * @param gridy     The y-coordinate of the grid where the label will be placed.
     * @param mainPanel The main panel where the label will be added.
     * @return The initialized JLabel with the specified text.
     */
    private static JLabel initializeLabel(String text, GridBagConstraints gbc, int gridy, JPanel mainPanel) {
        JLabel label = new JLabel(text);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 2;
        mainPanel.add(label, gbc);
        return label;
    }

    /**
     * Initializes the main application JFrame with default settings.
     *
     * @return The initialized JFrame.
     */
    private static JFrame initializeFrame() {
        JFrame frame = new JFrame("Sorting algorithms");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        Dimension minimumSize = new Dimension(600, 400);
        frame.setMinimumSize(minimumSize);
        return frame;
    }

    /**
     * Updates the provided timer JLabel with the sorting time information.
     *
     * @param timer The JLabel to display the sorting time.
     */
    private void showTimer(JLabel timer) {
        timer.setText("Sorting time: " + sortingAlgorithm.getTimeOfProcessing().getSeconds() + "s " +
                sortingAlgorithm.getTimeOfProcessing().getNano() / 1000000 + "ms " +
                sortingAlgorithm.getTimeOfProcessing().getNano() / 1000 % 1000 + "mks");
    }


    /**
     * Performs sorting using the selected sorting algorithm.
     */
    private void performSorting() {
        if (sortingAlgorithm != null) {
            sortingAlgorithm.sort();
        }
    }

    /**
     * Performs sorting with steps using the selected sorting algorithm.
     *
     * @return An ArrayList containing steps of the sorting process.
     */
    private ArrayList performStepSorting() {
        return sortingAlgorithm.sortWithSteps();
    }

    /**
     * Writes an array of numbers to a text file, optionally cleaning the file before writing.
     *
     * @param array      The array of numbers to be written to the file.
     * @param outputPath The path to the output file where the array will be written.
     * @param cleanFile  If set to true, the file will be cleaned before writing; if false, the array will be appended to the file.
     * @param <T>        The type of elements in the array (e.g., Integer, Double).
     */
    private <T extends Number> void writeArrayToFile(T[] array, String outputPath, boolean cleanFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath, !cleanFile))) {
            if (cleanFile) {
                // Clean the file by truncating it if necessary
                writer.write(""); // This effectively truncates the file
            }
            else {
                writer.newLine();
                writer.newLine();
            }

            for (T element : array) {
                writer.write(element.toString());
                writer.write(" "); // Separate elements with a space
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
    }
}

