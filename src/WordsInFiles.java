import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;

public class WordsInFiles {
    private HashMap<String, ArrayList<String>> wordLists;

    public WordsInFiles() {
        wordLists = new HashMap<>();
    }

    public void addWordsFromFile(File f) {
        FileResource fr = new FileResource(f);
        String filename = f.getName();

        for(String word : fr.words()) {
            if(!wordLists.containsKey(word)) {
                wordLists.put(word, new ArrayList<>());
            }
            if(!wordLists.get(word).contains(filename)) {
                wordLists.get(word).add(filename);
            }
        }
    }

    public void buildWordFileMap() {
        wordLists.clear();
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            addWordsFromFile(f);
        }
    }

    public int maxNumber(){
        int maxOccurrence = 0;
        for(String word : wordLists.keySet()){
            if(wordLists.get(word).size() > maxOccurrence) maxOccurrence = wordLists.get(word).size();
        }
        return maxOccurrence;
    }

    public ArrayList<String> wordsInNumFiles(int num){
        ArrayList<String> words = new ArrayList<>();
        for(String word : wordLists.keySet()){
            if(wordLists.get(word).size() == num) words.add(word);
        }

        return words;
    }

    public void printFilesIn(String word) {
        if(wordLists.containsKey(word)) {
            for(String fileName : wordLists.get(word)) System.out.println(fileName);
        } else {
            System.out.println("File not found");
        }
    }

    public void get_occurence(int num) {
        int maxOccurrence = 0;
        for(String f : wordLists.keySet()) {
            if(wordLists.get(f).size() == num) {
               maxOccurrence++;
            }
        }

        System.out.println("The number of words that occur in " + num + " files: " + maxOccurrence);
    }

    public void tester() {
        buildWordFileMap();
        System.out.println("The number of word's most occurrence in files: " + maxNumber());
        int wordCount = 0;
        for(String w : wordLists.keySet()) {
            if(wordLists.get(w).size() == 5){
                wordCount++;
                //printFilesIn(w);
            }
        }
        System.out.println(wordCount + " words occurs 5 times in the following files: ");
        for(String word : wordsInNumFiles(maxNumber())) {
            System.out.println(word + " has the max occurrence: " + maxNumber());
            printFilesIn(word);
        }

    }

    public void tester2() {
        buildWordFileMap();
        get_occurence(4);
        for(String f : wordLists.get("red")) {
            System.out.println(f + " has the word 'red'");
        }
    }

    public void tester3() {
        buildWordFileMap();
        int maxOccurrence = 0;
        for(String w : wordLists.keySet()) {
            if(wordLists.get(w).size() == 4) {
                maxOccurrence ++;
            }
        }
        System.out.println("The number of words that occur in 4 files: " + maxOccurrence);
    }

    public void tester4() {
        buildWordFileMap();
        for(String f : wordLists.get("tree")) {
            System.out.println(f + " has the word 'tree'");
        }
    }
}
