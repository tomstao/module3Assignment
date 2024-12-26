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

    public void tester() {
        buildWordFileMap();
        System.out.println("The number of word's most occurrence in files: " + maxNumber());

        for(String word : wordsInNumFiles(maxNumber())) {
            System.out.println(word + " has the max occurrence: " + maxNumber());
            printFilesIn(word);
        }

    }
}
