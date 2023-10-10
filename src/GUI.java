import com.mhorak.dsa.sort.*;
import com.mhorak.dsa.tools.Tools;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.mhorak.dsa.tools.Tools.decimalFormat;
import static com.mhorak.dsa.tools.Tools.filePath;

/**
 * Just the GUI. Makes all the work.
 */
public class GUI {

    /**
     * An array of numbers.
     */
    private Object[] arrayOfNumbers;

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
        initializeLabel("Enter a number:", gbc, 0, mainPanel);

        MayTheProcessBeActive mayTheProcessBeActive = new MayTheProcessBeActive(false, true);
        // Create a checkbox for showing a process (disabled by default)
        JCheckBox showProcessCheckBox = initializeShowProcessCheckbox(gbc, mainPanel);

        // Create a text field for user input
        JTextField inputField = initializeNumberField(gbc, mainPanel, showProcessCheckBox, mayTheProcessBeActive);

        // Create a panel for task selection (e.g., "Standard" or "Individual")
        TaskPanel taskPanel = getTaskPanel(gbc, mainPanel, showProcessCheckBox, mayTheProcessBeActive);

        // Create a JTextArea for displaying the matrix
        JTextArea matrixArea = initializeMatrixArea(gbc, mainPanel);

        // Create radio buttons for sorting method selection (e.g., "Selection" or "Shell")
        SortingMethods sortingMethods = getSortingMethods(gbc, mainPanel);

        // Initialize the "Generate array" button
        initializeGenerateButton(gbc, mainPanel, sortingMethods, taskPanel, inputField, matrixArea);

        // Initialize the timer label for displaying sorting time
        JLabel timer = initializeLabel("Sorting time: ", gbc, 4, mainPanel);

        // Initialize the "Sort" button for sorting the array
        initializeSortButton(gbc, mainPanel, taskPanel, sortingMethods, timer, matrixArea, showProcessCheckBox);

        // Initialize the "Sorted?" button for checking if the array is sorted
        initializeSortedButton(gbc, mainPanel, taskPanel, sortingMethods);

        // Add the main panel to the frame
        frame.add(mainPanel);

        // Make the frame visible
        frame.setVisible(true);
    }


    /**
     * Initializes and configures the "Sorted?" button with its action listener.
     * This button allows the user to check if the array is sorted and displays a message popup
     * based on the sorting status.
     *
     * @param gbc            The GridBagConstraints for specifying the button's layout.
     * @param mainPanel      The main panel where the button will be added.
     * @param taskPanel      The task panel containing user task options and settings.
     * @param sortingMethods An instance of SortingMethods that provides sorting method selection.
     */
    private void initializeSortedButton(GridBagConstraints gbc, JPanel mainPanel, TaskPanel taskPanel, SortingMethods sortingMethods) {
        JButton sortedButton = new JButton("Sorted?");
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        mainPanel.add(sortedButton, gbc);

        sortedButton.addActionListener(e -> {
            try {

                boolean isSorted;
                // Check if the array is sorted
                isSorted = Tools.isArraySorted(arrayOfNumbers, !(taskPanel.individualRadioButton.isSelected() && sortingMethods.mergeSort.isSelected()));

                // Display a message popup based on whether the array is sorted or not
                if (isSorted) {
                    JOptionPane.showMessageDialog(null, "The array is sorted.", "Array Status", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "The array is not sorted.", "Array Status", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception exception) {
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
    private void initializeGenerateButton(GridBagConstraints gbc, JPanel mainPanel, SortingMethods sortingMethods, TaskPanel taskPanel, JTextField inputField, JTextArea matrixArea) {
        JButton generateButton = new JButton("Generate array");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        mainPanel.add(generateButton, gbc);

        generateButton.addActionListener(e -> {
            try {
                if (taskPanel.standardRadioButton().isSelected()) {
                    // Generate and display an array of Integers
                    arrayOfNumbers = generateAndDisplayArray(inputField, matrixArea, 0, taskPanel.hugeNumbersRadioButton().isSelected());
                } else if (taskPanel.individualRadioButton().isSelected()) {

                    if (sortingMethods.selectionSort.isSelected()) {
                        // Generate and display an array of Doubles
                        arrayOfNumbers = generateAndDisplayArray(inputField, matrixArea, 1, taskPanel.hugeNumbersRadioButton().isSelected());
                    } else if (sortingMethods.shellSort.isSelected()) {
                        arrayOfNumbers = generateAndDisplayArray(inputField, matrixArea, 2, taskPanel.hugeNumbersRadioButton().isSelected());
                    } else if (sortingMethods.quickSort.isSelected()) {
                        arrayOfNumbers = generateAndDisplayArray(inputField, matrixArea, 3, taskPanel.hugeNumbersRadioButton().isSelected());
                    } else if (sortingMethods.mergeSort.isSelected()) {
                        arrayOfNumbers = generateAndDisplayArray(inputField, matrixArea, 4, taskPanel.hugeNumbersRadioButton().isSelected());
                    }
                }
            } catch (Exception exception) {
            }
        });
    }

    /**
     * Generates an array based on user input and options, and displays it in the given text area.
     *
     * @param inputField     The text field for user input.
     * @param matrixArea     The text area for displaying the generated array.
     * @param labNumber      The number of lab to decide a variant.
     * @param useHugeNumbers Indicates whether to use huge numbers in the generated array.
     * @return The generated array.
     */
    private Object[] generateAndDisplayArray(
            JTextField inputField,
            JTextArea matrixArea,
            int labNumber,
            boolean useHugeNumbers
    ) {
        try {
            int input = Integer.parseInt(inputField.getText());
            Object[] targetArray;
            switch (labNumber) {
                case 0 -> {
                    targetArray = new Integer[input];
                    Arrays.fill(targetArray, 0);
                }
                case 1, 3, 4 -> {
                    targetArray = new Double[input];
                    Arrays.fill(targetArray, 0.0);
                }
                case 2 -> {
                    targetArray = new Double[input][input];
                    for (Object row : targetArray) {
                        Arrays.fill((Double[]) row, 0.0);
                    }
                }
                default -> {
                    targetArray = new Integer[input];
                    Arrays.fill(targetArray, 0);
                }
            }

            Tools.initializeArray(targetArray, useHugeNumbers);

            if (targetArray.length <= 15) {
                matrixArea.setText("");

                if (targetArray[0] instanceof Double[]) {
                    assert targetArray instanceof Double[][];
                    Double[][] newArray = (Double[][]) targetArray;
                    for (Double[] element : newArray) {
                        for (Double elemento : element) {
                            matrixArea.append(decimalFormat.format(elemento) + " ");
                        }
                        matrixArea.append("\n");
                    }
                } else if (targetArray[0] instanceof Double) {
                    for (var element : targetArray) {
                        matrixArea.append(decimalFormat.format(element) + " ");
                    }
                } else {
                    for (var element : targetArray) {
                        matrixArea.append(element + " ");
                    }
                }

            } else {
                matrixArea.setText("Array was generated");
            }

            /*
             * Applying an individual variant function
             */
            if (labNumber == 1 || labNumber == 4) {
                Tools.mutateArray((Double[]) targetArray, labNumber);

                //Printing new array so the function is visible
                printMutatedArray(matrixArea, targetArray);
            }
            if (labNumber == 3) {
                targetArray = Tools.removeMode((Double[]) targetArray);
                //Printing new array so the function is visible
                printMutatedArray(matrixArea, targetArray);
            }

            writeArrayToFile(targetArray, filePath, true);

            return targetArray;
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Appends the elements of the target array to the provided JTextArea for displaying mutated arrays.
     * If the length of the target array is 15 or less, it formats and appends the elements to the JTextArea.
     *
     * @param matrixArea  The JTextArea where the mutated array will be displayed.
     * @param targetArray The array whose elements will be displayed in the JTextArea.
     */
    private void printMutatedArray(JTextArea matrixArea, Object[] targetArray) {
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

        sortButton.addActionListener(e -> {
            if (taskPanel.standardRadioButton().isSelected()) {
                // Sort the array of Integers
                sortArrayLab((Integer[]) arrayOfNumbers, sortingMethods, timer, matrixArea, showProcessCheckBox);
                writeArrayToFile(arrayOfNumbers, filePath, false);
            } else if (taskPanel.individualRadioButton().isSelected()) {
                // Sort the array of Doubles
                sortArrayIndividual(arrayOfNumbers, sortingMethods, timer, matrixArea);
                writeArrayToFile(arrayOfNumbers, filePath, false);
            }
        });
    }

    /**
     * Sorts an array of comparable elements using the selected sorting method.
     * Displays the sorting process and timer based on user preferences.
     *
     * @param arrayOfNumbers The array of comparable elements to be sorted.
     * @param sortingMethods An instance of SortingMethods that provides sorting method selection.
     * @param timer          The label to display sorting time.
     * @param matrixArea     The text area for displaying the sorting process.
     */
    private void sortArrayIndividual(Object arrayOfNumbers, SortingMethods sortingMethods, JLabel timer, JTextArea matrixArea) {
        // Determine the selected sorting method and initialize sortingAlgorithm
        if (sortingMethods.selectionSort().isSelected()) {
            sortingAlgorithm = new SelectionSort();
        } else if (sortingMethods.shellSort().isSelected()) {
            sortingAlgorithm = new ShellSort();
        } else if (sortingMethods.quickSort().isSelected()) {
            sortingAlgorithm = new QuickSort();
        } else if (sortingMethods.mergeSort().isSelected()) {
            sortingAlgorithm = new MergeSort();
        }

        // Sort the array based on the selected sorting method
        if (sortingMethods.selectionSort().isSelected()) {
            arrayOfNumbers = sortingAlgorithm.sortIndividual((Double[]) arrayOfNumbers);
        } else if (sortingMethods.shellSort().isSelected()) {
            arrayOfNumbers = sortingAlgorithm.sortIndividual((Double[][]) arrayOfNumbers);
        } else if (sortingMethods.quickSort().isSelected()) {
            arrayOfNumbers = sortingAlgorithm.sortIndividual((Double[]) arrayOfNumbers);
        } else if (sortingMethods.mergeSort().isSelected()) {
            arrayOfNumbers = sortingAlgorithm.sortIndividual((Double[]) arrayOfNumbers);
        }

        // Display the sorted array in the matrixArea
        showSortedArray((Object[]) arrayOfNumbers, matrixArea);

        // Display the sorting timer
        showTimer(timer);
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
    private void sortArrayLab(Integer[] arrayOfNumbers, SortingMethods sortingMethods, JLabel timer, JTextArea matrixArea, JCheckBox showProcessCheckBox) {
        if (sortingMethods.selectionSort().isSelected()) {
            // Initialize and perform selection sort
            sortingAlgorithm = new SelectionSort();
        } else if (sortingMethods.shellSort().isSelected()) {
            sortingAlgorithm = new ShellSort();
        } else if (sortingMethods.quickSort().isSelected()) {
            sortingAlgorithm = new QuickSort();
        } else if (sortingMethods.mergeSort().isSelected()) {
            sortingAlgorithm = new MergeSort();
        }

        if (showProcessCheckBox.isSelected()) {
            // Display the sorting process step by step
            ArrayList<Integer[]> arraySteps = sortingAlgorithm.sortLabWithSteps(arrayOfNumbers);
            fillMatrix(matrixArea, arraySteps);
        } else {
            // Perform the sorting without displaying the process
            arrayOfNumbers = sortingAlgorithm.sortLab(arrayOfNumbers);
            showSortedArray(arrayOfNumbers, matrixArea);
        }

        // Display the sorting timer
        showTimer(timer);
    }

    /**
     * Shows sorted array in matrixArea
     *
     * @param arrayOfNumbers Array of numbers to display.
     * @param matrixArea     The text area where the sorted array will be displayed.
     */
    private static void showSortedArray(Object[] arrayOfNumbers, JTextArea matrixArea) {
        if (arrayOfNumbers.length <= 15) {
            matrixArea.append("\n");
            if (arrayOfNumbers[0] instanceof Double[]) {
                Double[][] newArray = (Double[][]) arrayOfNumbers;
                for (var element : newArray) {
                    for (var elemento : element) {
                        matrixArea.append(decimalFormat.format(elemento) + " ");
                    }
                    matrixArea.append("\n");
                }
            } else if (arrayOfNumbers[0] instanceof Double) {
                for (var element : arrayOfNumbers) {
                    matrixArea.append(decimalFormat.format(element) + " ");
                }
            } else {
                for (var element : arrayOfNumbers) {
                    matrixArea.append(element + " ");
                }
            }
        } else {
            matrixArea.append("\nThe array is sorted");
        }
    }

    /**
     * Fills the provided text area with the contents of an ArrayList containing arrays of comparable elements.
     * Each array in the list represents a step in the sorting process.
     *
     * @param matrixArea The text area where the sorting process will be displayed.
     * @param arraySteps An ArrayList containing arrays representing steps of the sorting process.
     */
    private void fillMatrix(JTextArea matrixArea, ArrayList<Integer[]> arraySteps) {
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
        radioButtonGroup.add(selectionSort); // Add to the group
        selectionSort.setSelected(true);

        // Create the second radio button
        JRadioButton shellSort = new JRadioButton("Shell");
        radioButtonGroup.add(shellSort); // Add to the group

        // Create the third radio button
        JRadioButton quickSort = new JRadioButton("Quick");
        radioButtonGroup.add(quickSort); // Add to the group

        // Create the forth radio button
        JRadioButton mergeSort = new JRadioButton("Merge");
        radioButtonGroup.add(mergeSort); // Add to the group

        // Create a panel for the radio buttons
        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.setLayout(new FlowLayout());
        radioButtonPanel.add(selectionSort);
        radioButtonPanel.add(shellSort);
        radioButtonPanel.add(quickSort);
        radioButtonPanel.add(mergeSort);

        // Add the radio button panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        mainPanel.add(radioButtonPanel, gbc);

        // Create and return a SortingMethods object encapsulating the radio buttons
        SortingMethods sortingMethods = new SortingMethods(selectionSort, shellSort, quickSort, mergeSort);
        return sortingMethods;
    }

    /**
     * A record that represents a set of sorting method radio buttons.
     *
     * @param selectionSort The radio button for the Selection Sort method.
     * @param shellSort     The radio button for the Shell Sort method.
     * @param quickSort     The radio button for the Quick Sort method.
     * @param mergeSort     The radio button for the Merge Sort method.
     */
    private record SortingMethods(JRadioButton selectionSort, JRadioButton shellSort, JRadioButton quickSort,
                                  JRadioButton mergeSort) {
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
    private static TaskPanel getTaskPanel(GridBagConstraints gbc, JPanel mainPanel, JCheckBox showProcessCheckbox, MayTheProcessBeActive mayTheProcessBeActive) {
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

        standardRadioButton.addActionListener(e -> {
            if (standardRadioButton.isSelected()) {
                mayTheProcessBeActive.labVariant = true;
                showProcessCheckbox.setEnabled(mayTheProcessBeActive.canBeEnabled());
            }
        });


        // Create the "Individual" radio button
        JRadioButton individualRadioButton = new JRadioButton("Individual");
        radioButtonGroupTask.add(individualRadioButton);
        radioButtonPanelTask.add(individualRadioButton);

        individualRadioButton.addActionListener(e -> {
            if (individualRadioButton.isSelected()) {
                showProcessCheckbox.setSelected(false);
                mayTheProcessBeActive.labVariant = false;
                showProcessCheckbox.setEnabled(mayTheProcessBeActive.canBeEnabled());
            }
        });

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
     * @param gbc                   The GridBagConstraints for specifying the layout of the input field.
     * @param mainPanel             The main panel where the input field will be added.
     * @param showProcessCheckBox   The checkbox to be enabled or disabled based on the inputField's value.
     * @param mayTheProcessBeActive An instance of the {@link MayTheProcessBeActive} class that determines whether the process can be active.
     * @return The initialized JTextField for entering the number of elements in the array.
     */
    private static JTextField initializeNumberField(GridBagConstraints gbc, JPanel mainPanel, JCheckBox showProcessCheckBox, MayTheProcessBeActive mayTheProcessBeActive) {
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
                    mayTheProcessBeActive.numberOfElements = (value > 0 && value <= 15);
                    showProcessCheckBox.setEnabled(mayTheProcessBeActive.canBeEnabled());

                    if (!showProcessCheckBox.isEnabled()) {
                        showProcessCheckBox.setSelected(false);

                    }
                } catch (NumberFormatException ex) {
                    // Handle the case where the input is not a valid number
                    mayTheProcessBeActive.numberOfElements = false;
                    showProcessCheckBox.setEnabled(mayTheProcessBeActive.canBeEnabled());
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
     * Writes an array of numbers to a text file, optionally cleaning the file before writing.
     *
     * @param array      The array of numbers to be written to the file.
     * @param outputPath The path to the output file where the array will be written.
     * @param cleanFile  If set to true, the file will be cleaned before writing; if false, the array will be appended to the file.
     */
    private void writeArrayToFile(Object[] array, String outputPath, boolean cleanFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath, !cleanFile))) {
            if (cleanFile) {
                // Clean the file by truncating it if necessary
                writer.write(""); // This effectively truncates the file
            } else {
                writer.newLine();
                writer.newLine();
            }
            if (array[0] instanceof Double[]) {
                Double[][] newArray = (Double[][]) array;
                for (var element : newArray) {
                    for (var elemento : element) {
                        writer.write(elemento.toString());
                        writer.write(" "); // Separate elements with a space
                    }
                    writer.write("\n");
                }
            } else {
                for (var element : array) {
                    writer.write(element.toString());
                    writer.write(" "); // Separate elements with a space
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
    }

    /**
     * Helper class that tracks whether the process can be active based on certain conditions.
     */
    private static class MayTheProcessBeActive {
        /**
         * Indicates whether the number of elements is within a valid range.
         */
        public boolean numberOfElements;

        /**
         * Indicates the state of the lab variant.
         */
        public boolean labVariant;

        /**
         * Initializes a new instance of the MayTheProcessBeActive class with the provided values.
         *
         * @param numberOfElements Indicates whether the number of elements is within a valid range.
         * @param labVariant       Indicates the state of the lab variant.
         */
        MayTheProcessBeActive(boolean numberOfElements, boolean labVariant) {
            this.numberOfElements = numberOfElements;
            this.labVariant = labVariant;
        }

        /**
         * Checks whether the process can be enabled based on the current conditions.
         *
         * @return True if the process can be enabled, false otherwise.
         */
        public boolean canBeEnabled() {
            // The process can be enabled only if both numberOfElements and labVariant are true.
            return numberOfElements && labVariant;
        }
    }
}

