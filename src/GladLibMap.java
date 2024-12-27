import edu.duke.FileResource;
import edu.duke.URLResource;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GladLibMap {

    private HashMap<String, ArrayList<String>> wordsMap;

    private ArrayList<String> usedWordList;

    private Random myRandom;

    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "datalong" +
            "";

    public GladLibMap(){
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
    }

    public GladLibMap(String source){
        initializeFromSource(source);
        myRandom = new Random();
    }

    private void initializeFromSource(String source) {
        wordsMap = new HashMap<>();
        String [] labels = {"adjectiveList", "nounList", "colorList", "countryList", "nameList", "animalList", "timeList", "verbList",
                "fruitList"};
        for(String label : labels){
            ArrayList<String> list = readIt(source + "/" + label.substring(0,label.indexOf("List")) + ".txt");
            wordsMap.put(label, list);
        }
        usedWordList = new ArrayList<>();
    }

    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }

    private String getSubstitute(String label) {
//        if (label.equals("country")) {
//            return randomFrom(countryList);
//        }
        label = label.concat("List");
        if(label.contains("number")){
            return "" + myRandom.nextInt(50) + 5;
        }
        for(String key : wordsMap.keySet()){
            if(key.equals(label)){
                return randomFrom(wordsMap.get(key));
            }
        }
//
        return "**UNKNOWN**";
    }

    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0, first);
        String suffix = w.substring(last + 1);
        String sub = getSubstitute(w.substring(first + 1, last));
        while (usedWordList.contains(sub)){
            sub = getSubstitute(w.substring(first + 1, last));
            usedWordList.add(sub);
        }
        return prefix+sub+suffix;
    }

    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }

    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }

    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }

    public void makeStory(){
        usedWordList.clear();
        System.out.println("\n");
        String story = fromTemplate("data" +
                "/madtemplate.txt");
        System.out.println("Total words used: " + get_word_count());

        usedWordList.clear();
        String story2 = fromTemplate("data" +
                "/madtemplate2.txt");
        System.out.println("Total words used: " + get_word_count());
        printOut(story, 60);
        System.out.println(" ");
        System.out.println(" ");
        printOut(story2, 60);
        System.out.println("\n");


    }

    public void printMap(){
        for(String label : wordsMap.keySet()){
            System.out.println(label + ": " + wordsMap.get(label).size());
        }
    }

    public int get_word_count(){
        return usedWordList.size();
    }

}
