// -----------------------------------------------------------------
// Assignment 3
// Written by: Noah Afriat (40276193) and Robert Mounsef (40279248)
// -----------------------------------------------------------------

/**
 * Noah Afriat (40276193) and Robert Mounsef(40279248)
 * COMP249
 * Assignment 3 
 * April 14, 2024
 */


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The VocabularyControlCenter class manages a doubly linked list of topics, each containing a singly linked list of words.
 * It provides functionality to insert topics before existing ones, display topics, and perform other operations on the vocabulary.
 */
public class VocabularyControlCenter {
    private VocabNode head;
    private VocabNode tail;
    private String loadedFileName;
    private int topicCount;
    private boolean fileLoaded;
    
    /**
     * Constructs a new VocabularyControlCenter with initial values.
     */
    public VocabularyControlCenter() {
        head = null;
        tail = null;
        loadedFileName = "";
        topicCount = 0;
        fileLoaded = false;
    }
    
    /**
     * The VocabNode class represents a node in the doubly linked list.
     * Each node contains a reference to a Vocab object and references to the previous and next nodes.
     */
    private class VocabNode {
        Vocab vocab;
        VocabNode prev;
        VocabNode next;

        /**
         * Constructs a new VocabNode with the given Vocab object.
         * @param vocab The Vocab object to be stored in the node.
         */
        public VocabNode(Vocab vocab) {
            this.vocab = vocab;
            this.prev = null;
            this.next = null;
        }
    }
    
    /**
     * The WordNode class represents a node in the singly linked list of words associated with a topic.
     * Each node contains a word string and a reference to the next node.
     */
    private static class WordNode {
        String word;
        WordNode next;

        /**
         * Constructs a new WordNode with the given word string.
         * @param word The word string to be stored in the node.
         */
        public WordNode(String word) {
            this.word = word;
            this.next = null;
        }
    }
    
    /**
     * The Vocab class represents a vocabulary topic.
     * It contains a topic string and a reference to the head of the singly linked list of words.
     */
    private class Vocab {
        String topic;
        WordNode head;

        /**
         * Constructs a new Vocab with the given topic string.
         * @param topic The topic string for the vocabulary.
         */
        public Vocab(String topic) {
            this.topic = topic;
            head = null;
        }

        /**
         * Adds a new word to the singly linked list of words within the vocabulary.
         * @param word The word to be added.
         */
        public void addWord(String word) {
            WordNode newNode = new WordNode(word);
            if (head == null) {
                head = newNode;
            } else {
                WordNode current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
        }

        /**
         * Displays the words within the vocabulary.
         */
        public void displayWords() {
            WordNode current = head;
            while (current != null) {
                System.out.println(current.word);
                current = current.next;
            }
        }
    }
    
    /**
     * Allows the user to insert a new topic before an existing topic in the vocabulary.
     * The user is prompted to choose a topic number before which the new topic will be inserted.
     * The user is then prompted to enter the name of the new topic and its associated words.
     * If the user chooses 0 as the topic number, the insertion operation is cancelled, and the method returns.
     * 
     * @see VocabularyControlCenter#insertTopicBefore(int, Vocab)
     */
    public void insertBefore() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            displayTopics();
            System.out.print("Enter your choice: ");
            
            if (scanner.hasNextInt()) {
                int topicNumber = scanner.nextInt();
                scanner.nextLine(); 
                
                if (topicNumber < 0 || topicNumber >= topicCount) {
                    System.out.println("Invalid topic number. Please choose a valid topic number.");
                    continue;
                } else if (topicNumber == 0) {
                    System.out.println("Exiting...");
                    return;
                }
                
                System.out.print("Enter a topic name: ");
                String newTopicName = scanner.nextLine();
                Vocab newVocab = new Vocab(newTopicName);
                System.out.println("Enter a word - to quit press enter");
                
                while (true) {
                    String newItem = scanner.nextLine().trim();
                    if (newItem.isEmpty()) {
                        break;
                    }
                    newVocab.addWord(newItem);
                }
                
                insertTopicBefore(topicNumber, newVocab);
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid topic number.");
                scanner.next(); 
            }
        }
    }
    
    /**
     * Inserts a new topic before the specified topic number in the vocabulary.
     * This method is called by {@link #insertBefore()}.
     * @param topicNumber The topic number before which the new topic will be inserted.
     * @param newVocab The new Vocab object to be inserted.
     */
    private void insertTopicBefore(int topicNumber, Vocab newVocab) {
        VocabNode current = head;
        int currentTopicNumber = 1;
        while (current != null) {
            if (currentTopicNumber == topicNumber) {
                VocabNode newNode = new VocabNode(newVocab);
                newNode.next = current;
                newNode.prev = current.prev;
                if (current.prev != null) {
                    current.prev.next = newNode;
                } else {
                    head = newNode;
                }
                current.prev = newNode;
                topicCount++;
                return;
            }
            current = current.next;
            currentTopicNumber++;
        }
    }

/**
 * Inserts a new topic after the specified topic number in the vocabulary.
 * This method prompts the user for input to create a new topic and adds it after the specified topic number.
 */
public void insertAfter() {
    Scanner scanner = new Scanner(System.in);
    
    while (true) {
        displayTopics();
        System.out.print("Enter your choice: ");
        
        if (scanner.hasNextInt()) {
            int topicNumber = scanner.nextInt();
            scanner.nextLine(); 
            
            if (topicNumber < 0 || topicNumber >= topicCount) {
                System.out.println("Invalid topic number. Please choose a valid topic number.");
                continue;
            } else if (topicNumber == 0) {
                System.out.println("Exiting...");
                return; 
            }
            
            // Valid topic number entered, proceed with input
            System.out.print("Enter a topic name: ");
            String newTopicName = scanner.nextLine();
            Vocab newVocab = new Vocab(newTopicName);
            System.out.println("Enter a word - to quit press enter");
            
            while (true) {
                String newItem = scanner.nextLine().trim();
                if (newItem.isEmpty()) {
                    break;
                }
                newVocab.addWord(newItem);
            }
            
            insertTopicAfter(topicNumber, newVocab);
            break; 
        } else {
            System.out.println("Invalid input. Please enter a valid topic number.");
            scanner.next(); 
        }
    }
}

/**
 * Inserts a new topic after the specified topic number in the vocabulary.
 * This method is called by {@link #insertAfter()}.
 * @param topicNumber The topic number after which the new topic will be inserted.
 * @param newVocab The new Vocab object to be inserted.
 */
private void insertTopicAfter(int topicNumber, Vocab newVocab) {
    VocabNode current = head;
    int currentTopicNumber = 1;
    while (current != null) {
        if (currentTopicNumber == topicNumber) {
            VocabNode newNode = new VocabNode(newVocab);
            newNode.prev = current;
            newNode.next = current.next;
            if (current.next != null) {
                current.next.prev = newNode;
            } else {
                tail = newNode; // Update tail if inserting at the end
            }
            current.next = newNode;
            topicCount++;
            return;
        }
        current = current.next;
        currentTopicNumber++;
    }
}

/**
 * Removes the topic with the specified topic number from the vocabulary.
 * @param topicNumber The topic number of the topic to be removed.
 */
public void removeTopic(int topicNumber) {
    if (topicNumber < 0 || topicNumber > topicCount) {
        System.out.println("Invalid topic number. Please choose a valid topic number.");
        return;
    } else if (topicNumber == 0) {
        System.out.println("Exiting...");
        return; 
    }
    
    VocabNode current = head;
    int currentTopicNumber = 1;
    while (current != null) {
        if (currentTopicNumber == topicNumber) {
            // Remove the current node
            if (current.prev != null) {
                current.prev.next = current.next;
            } else {
                head = current.next; // Update head if removing the first node
            }
            if (current.next != null) {
                current.next.prev = current.prev;
            } else {
                tail = current.prev; // Update tail if removing the last node
            }
            topicCount--;
            System.out.println("Topic removed successfully.");
            return;
        }
        current = current.next;
        currentTopicNumber++;
    }
    // If the topic number is not found
    System.out.println("Topic with number '" + topicNumber + "' not found.");
}

/**
 * Modifies the specified topic by adding, removing, or changing words.
 * This method displays a menu for modifying topics and handles user input accordingly.
 * @param topicName The name of the topic to be modified.
 */
public void modifyTopic(String topicName) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("-----------------------------");
    System.out.println("Modify Topics Menu");
    System.out.println("-----------------------------");
    System.out.println("a add a word");
    System.out.println("r remove a word");
    System.out.println("c change a word");
    System.out.println("0 Exit");
    System.out.println("-----------------------------");
    System.out.print("Enter Your Choice: ");
    String choice = scanner.nextLine().toLowerCase();
    switch (choice) {
        case "a":
            System.out.print("Enter a word\n");
            String wordToAdd = scanner.nextLine().trim();
            if (!wordToAdd.isEmpty()) {
                addWordToTopic(topicName, wordToAdd);
            } else {
                System.out.println("Invalid input. Please enter a word.");
            }
            break;
        case "r":
            System.out.print("Enter a word\n");
            String wordToRemove = scanner.nextLine().trim();
            if (!wordToRemove.isEmpty()) {
                removeWordFromTopic(topicName, wordToRemove);
            } else {
                System.out.println("Invalid input. Please enter a word.");
            }
            break;
        case "c":
            System.out.print("Enter a word\n");
            String wordToChange = scanner.nextLine().trim();
            if (!wordToChange.isEmpty()) {
                System.out.print("Enter the new word: ");
                String newWord = scanner.nextLine().trim();
                if (!newWord.isEmpty()) {
                    changeWordInTopic(topicName, wordToChange, newWord);
                } else {
                    System.out.println("Invalid input. Please enter a new word.");
                }
            } else {
                System.out.println("Invalid input. Please enter a word to change.");
            }
            break;
        case "0":
            break;
        default:
            System.out.println("Invalid choice.");
            break;
    }
}

/**
 * Adds a word to the specified topic in the vocabulary.
 *
 * @param topicName  The name of the topic to which the word will be added.
 * @param wordToAdd  The word to be added to the topic.
 */
private void addWordToTopic(String topicName, String wordToAdd) {
    // Trim the input string to remove leading and trailing whitespace
    wordToAdd = wordToAdd.trim();
    // Check if the trimmed word is empty
    if (wordToAdd.isEmpty()) {
        System.out.println("No word entered. Word not added to the topic '" + topicName + "'.");
        return;
    }
    // Find the VocabNode corresponding to the specified topic
    VocabNode current = head;
    while (current != null) {
        if (current.vocab.topic.equalsIgnoreCase(topicName)) {
            // Found the topic, create a new WordNode with the word to add
            WordNode newNode = new WordNode(wordToAdd);
            // Add the new word to the end of the topic's word list
            current.vocab.addWord(wordToAdd);
            System.out.println("Word '" + wordToAdd + "' added to the topic '" + topicName + "'.");
            return;
        }
        current = current.next;
    }
    // If the topic is not found
    System.out.println("Topic '" + topicName + "' not found.");
}

/**
 * Removes a word from the specified topic in the vocabulary.
 *
 * @param topicName     The name of the topic from which the word will be removed.
 * @param wordToRemove  The word to be removed from the topic.
 */
private void removeWordFromTopic(String topicName, String wordToRemove) {
    VocabNode current = head;
    while (current != null) {
        if (current.vocab.topic.equalsIgnoreCase(topicName)) {
            WordNode prev = null;
            WordNode currentWord = current.vocab.head;
            while (currentWord != null) {
                if (currentWord.word.equalsIgnoreCase(wordToRemove)) {
                    // Found the word, remove it from the list
                    if (prev != null) {
                        prev.next = currentWord.next;
                    } else {
                        current.vocab.head = currentWord.next;
                    }
                    
                    return;
                }
                prev = currentWord;
                currentWord = currentWord.next;
            }
            // If the word is not found in the topic
            System.out.println("Word '" + wordToRemove + "' not found in the topic '" + topicName + "'.");
            return;
        }
        current = current.next;
    }
    // If the topic is not found
    System.out.println("Topic '" + topicName + "' not found.");
}

/**
 * Changes a word in the specified topic in the vocabulary.
 *
 * @param topicName   The name of the topic in which the word will be changed.
 * @param oldWord     The word to be replaced.
 * @param newWord     The new word to replace the old word.
 */
private void changeWordInTopic(String topicName, String oldWord, String newWord) {
    VocabNode current = head;
    while (current != null) {
        if (current.vocab.topic.equalsIgnoreCase(topicName)) {
            WordNode currentWord = current.vocab.head;
            while (currentWord != null) {
                if (currentWord.word.equalsIgnoreCase(oldWord)) {
                    currentWord.word = newWord;
                    return;
                }
                currentWord = currentWord.next;
            }
            // If the word is not found in the topic
            System.out.println("Word '" + oldWord + "' not found in the topic '" + topicName + "'.");
            return;
        }
        current = current.next;
    }
    // If the topic is not found
    System.out.println("Topic '" + topicName + "' not found.");
}

/**
 * Retrieves the name of the topic based on its number in the vocabulary.
 *
 * @param choiceInt  The number corresponding to the topic.
 * @return The name of the topic if found, otherwise null.
 */
private String getTopicNameByNumber(int choiceInt) {
    VocabNode current = head;
    int currentTopicNumber = 1;
    while (current != null) {
        if (currentTopicNumber == choiceInt) {
            return current.vocab.topic;
        }
        current = current.next;
        currentTopicNumber++;
    }
    return null; // If the topic number is not found
}

/**
 * Searches for topics containing a specific word and displays the topics where the word is found.
 * This method prompts the user to input a word and displays the topics containing that word.
 */
public void searchTopicsForWord() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
        System.out.print("Enter the word to search for: ");
        String word = scanner.nextLine();
        if (word.isEmpty()) {
            System.out.println("Invalid input. Please enter a valid word.\n");
            continue; // Restart the loop to prompt the user again
        }
        boolean found = false;
        VocabNode current = head;
        int count = 0;
        while (current != null) {
            WordNode wordCurrent = current.vocab.head;
            while (wordCurrent != null) {
                if (wordCurrent.word.equalsIgnoreCase(word)) {
                    if (count == 0) {
                        System.out.println("The word '" + word + "' was found in the following topic(s):");
                        count++;
                    }
                    System.out.println("- " + current.vocab.topic);
                    found = true;
                    break;
                }
                wordCurrent = wordCurrent.next;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("The word '" + word + "' was not found in any topic.");
        }
        break; 
    }
}
/**
 * Loads vocabulary data from a file and constructs the vocabulary structure.
 *
 * @param fileName The name of the file to load the data from.
 * @return The name of the loaded file.
 * @throws IOException If an I/O error occurs while reading from the file.
 */
public String loadFromFile(String fileName) throws IOException {
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(fileName));
        fileLoaded = true;
    } catch (FileNotFoundException e) {
        System.out.println("File not found.");
        return "";
    }
    String line;
    Vocab currentVocab = null;
    
    while ((line = reader.readLine()) != null) {
        if (line.startsWith("#")) {
            String topic = line.substring(1).trim();
            currentVocab = new Vocab(topic);
            insertAtEnd(currentVocab);
        } else if (currentVocab != null) {
            String word = line.trim();
            if (!word.isEmpty())
                currentVocab.addWord(word);
        }
    }
    
    reader.close();
    System.out.println("Done loading");
    loadedFileName = fileName;
    return loadedFileName;
}

/**
 * Inserts a vocabulary node at the end of the vocabulary list.
 *
 * @param vocab The vocabulary to insert.
 */
private void insertAtEnd(Vocab vocab) {
    VocabNode newNode = new VocabNode(vocab);
    if (head == null) {
        head = newNode;
        tail = newNode;
    } else {
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
    }
}

/**
 * Displays words from the vocabulary that start with a specified letter.
 * This method prompts the user to input a letter and displays words starting with that letter.
 */
public void showWordsStartingWithLetter() {
    Scanner scanner = new Scanner(System.in);
    char letter;
    while (true) {
        System.out.print("Enter the letter to search for: ");
        String input = scanner.nextLine().trim();
        
        if (input.isEmpty() || input.length() > 1 || !Character.isLetter(input.charAt(0))) {
            System.out.println("Invalid input. Please enter a valid letter.\n");
            continue; 
        }
        
        letter = input.charAt(0);
        break; 
       
    }
    ArrayList<String> wordsStartingWithLetter = new ArrayList<>();
    VocabNode current = head;
    while (current != null) {
        WordNode wordCurrent = current.vocab.head;
        while (wordCurrent != null) {
            if (wordCurrent.word.length() > 0 && wordCurrent.word.charAt(0) == letter) {
                wordsStartingWithLetter.add(wordCurrent.word);
            }
            wordCurrent = wordCurrent.next;
        }
        current = current.next;
    }
    if (wordsStartingWithLetter.isEmpty()) {
        System.out.println("No words found starting with the letter " + letter);
    } else {
        sort(wordsStartingWithLetter);
        
        System.out.println("Words starting with the letter " + letter + ":");
        for (String word : wordsStartingWithLetter) {
            System.out.println(word);
        }
    }
}
/**
 * Sorts an ArrayList of strings in ascending order using the insertion sort algorithm.
 * 
 * @param arr The ArrayList of strings to be sorted.
 */
private void sort(ArrayList<String> arr) {
    for (int i = 1; i < arr.size(); i++) {
        String current = arr.get(i);
        int j = i - 1;
        while (j >= 0 && arr.get(j).compareTo(current) > 0) {
            arr.set(j + 1, arr.get(j));
            j--;
        }
        arr.set(j + 1, current);
    }
}

/**
 * Saves the vocabulary data to a file specified by the user.
 * The user is prompted to enter the name of the file in the format (fileName.txt).
 * If the file already exists, the user is asked whether to override it.
 * If the user chooses not to override the existing file, the save operation is cancelled.
 * Vocabulary data includes topics and associated words.
 * Each topic is preceded by a '#' symbol in the file.
 * Each word associated with a topic is written on a new line.
 * 
 * @throws IOException if an I/O error occurs while writing to the file
 */
public void saveToFile() throws IOException {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter the name of the file to save in the format (fileName.txt): ");
    String fileName = scanner.nextLine();

    File file = new File(fileName);

    if (file.exists()) {
        System.out.print("File already exists. Do you want to override it? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!confirm.equals("yes")) {
            System.out.println("Save operation cancelled.");
            return;
        }
    }

    BufferedWriter writer = null;

    try {
        writer = new BufferedWriter(new FileWriter(file));

        VocabNode current = head;
        while (current != null) {
            // Write the topic to the file
            writer.write("# " + current.vocab.topic);
            writer.newLine();

            // Write the words associated with the topic
            WordNode wordCurrent = current.vocab.head;
            while (wordCurrent != null) {
                writer.write(wordCurrent.word);
                writer.newLine();
                wordCurrent = wordCurrent.next;
            }

            current = current.next; // Move to the next VocabNode
        }

        System.out.println("Data saved to file successfully.");
    } finally {
        if (writer != null) {
            writer.close();
        }
    }
}
/**
 * Allows the user to browse topics and display the associated words.
 * The user is prompted to choose a topic, and the words associated with
 * the selected topic are displayed.
 */
public void browseTopic() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
        displayTopics();
        System.out.print("Enter your choice: ");
        if (scanner.hasNextInt()) {
            int topicChoice = scanner.nextInt();
            if (topicChoice < 0 || topicChoice >= topicCount) {
                System.out.println("Invalid topic choice, please try again.");
            } else if (topicChoice == 0) {
                System.out.println("Exiting browse topics...");
                return;
            } else {
                VocabNode current = head;
                int currentTopicCount = 1;
                while (current != null) {
                    if (currentTopicCount == topicChoice) {
                        System.out.println("Topic: " + current.vocab.topic);
                        displayWords(current.vocab);
                        return;
                    }
                    current = current.next;
                    currentTopicCount++;
                }
            }
        } else {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume invalid input to avoid infinite loop
        }
    }
}

/**
 * Displays the words associated with a specified vocabulary.
 *
 * @param vocab The vocabulary whose words are to be displayed.
 */
private void displayWords(Vocab vocab) {
    WordNode current = vocab.head;
    int wordCount = 1;
    while (current != null) {
        
        System.out.printf("%-3d %-22s", wordCount, current.word);
        if (wordCount % 4 == 0)
            System.out.println();
        
        current = current.next;
        wordCount++;
    }
    
}

/**
 * Displays the list of topics available for browsing.
 */
public void displayTopics() {
    System.out.println("-----------------------------");
    System.out.println("Pick a topic");
    System.out.println("-----------------------------");
    VocabNode current = head;
    topicCount = 1;
    while (current != null) {
        System.out.println(" " + topicCount + " " + current.vocab.topic);
        current = current.next;
        topicCount++;
    }
    System.out.println(" 0 Exit");
    System.out.println("-----------------------------");
}

/**
 * Displays the main menu of the Vocabulary Control Center,
 * listing available operations and their corresponding numbers.
 */
public void displayMenu() {
    System.out.println("-----------------------------");
    System.out.println("Vocabulary Control Center");
    System.out.println("-----------------------------");
    System.out.println("1 browse a topic");
    System.out.println("2 insert a new topic before another one");
    System.out.println("3 insert a new topic after another one");
    System.out.println("4 remove a topic");
    System.out.println("5 modify a topic");
    System.out.println("6 search topics for a word");
    System.out.println("7 load from a file");
    System.out.println("8 show all words starting with a given letter");
    System.out.println("9 save to file");
    System.out.println("0 exit");
    System.out.println("-----------------------------");
    System.out.print("Enter Your Choice: ");
}
/**
 * Handles user input based on the selected choice. The method performs various operations
 * depending on the user's input, such as browsing topics, inserting new topics, removing topics,
 * modifying topics, searching topics for a word, loading from a file, showing words starting with
 * a specific letter, saving to a file, or exiting the program.
 *
 * @param choice The user's choice selected from the menu.
 * @throws IOException If an I/O error occurs while performing file operations.
 */
public void handleUserInput(String choice) throws IOException {
    Scanner scanner = new Scanner(System.in);
    
    switch (choice) {
        case "1":
            if (!fileLoaded) {
                System.out.println("Please load a file first (Option 7)!");
                break;
            }
            browseTopic();
            break;
        case "2":
            if (!fileLoaded) {
                System.out.println("Please load a file first (Option 7)!");
                break;
            }
            insertBefore();
            break;
        case "3":
            if (!fileLoaded) {
                System.out.println("Please load a file first (Option 7)!");
                break;
            }
            insertAfter();
            break;
        case "4":
            if (!fileLoaded) {
                System.out.println("Please load a file first (Option 7)!");
                break;
            }
            
            displayTopics();
            
            while (true) {
                System.out.print("Enter your choice: ");
                try {
                    int topicNumber = Integer.parseInt(scanner.nextLine());
                    if (topicNumber < 0 || topicNumber > topicCount) {
                        System.out.println("Invalid topic number. Please choose a valid topic number.");
                    } else if (topicNumber == 0) {
                        break;
                    } else {
                        removeTopic(topicNumber);
                        break; // Exit the loop if a valid integer input is provided
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            }
            break;
        case "5":
            if (!fileLoaded) {
                System.out.println("Please load a file first (Option 7)!");
                break;
            }
            
            displayTopics();
            System.out.print("Enter your choice: ");
            
            while (true) {
                String modifyTopicChoice = scanner.nextLine();
                try {
                    int choiceInt = Integer.parseInt(modifyTopicChoice);
                    if (choiceInt < 0 || choiceInt > topicCount) {
                        System.out.println("Invalid topic number. Please choose a valid topic number.");
                        break;
                    } else if (choiceInt == 0) {
                        break;
                    } else {
                        String modifyTopicName = getTopicNameByNumber(choiceInt);
                        if (modifyTopicName != null) {
                            modifyTopic(modifyTopicName);
                        }
                        break; // Exit the loop if a valid integer input is provided
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    break;
                }
            }
            break;
        case "6":
            if (!fileLoaded) {
                System.out.println("Please load a file first (Option 7)!");
                break;
            }
            searchTopicsForWord();
            break;
        case "7":
            // load from a file
            System.out.println("Enter the name of the input file: ");
            loadFromFile(scanner.nextLine());
            break;
        case "8":
            if (!fileLoaded) {
                System.out.println("Please load a file first (Option 7)!");
                break;
            }
            showWordsStartingWithLetter();
            break;
        case "9":
            if (!fileLoaded) {
                System.out.println("Please load a file first (Option 7)!");
                break;
            }
            saveToFile();
            break;
        case "0":
            // exit
            System.out.println("\nGoodbye!");
            System.exit(0);
            break;
        default:
            System.out.println("Invalid choice. Please try again.");
            break;
    }
}

/**
 * The main method of the program, which initializes the VocabularyControlCenter
 * instance and continuously displays the menu for user interaction.
 *
 * @param args Command-line arguments (not used).
 * @throws IOException If an I/O error occurs while performing file operations.
 */
public static void main(String[] args) throws IOException {
    VocabularyControlCenter controlCenter = new VocabularyControlCenter();
    Scanner scanner = new Scanner(System.in);
    
    while (true) {
        System.out.println("");
        controlCenter.displayMenu();
        String choice = scanner.nextLine();
        controlCenter.handleUserInput(choice);
    }
}

}