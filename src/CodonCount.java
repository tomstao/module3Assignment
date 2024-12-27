import java.util.ArrayList;
import java.util.HashMap;
import edu.duke.*;


public class CodonCount {
    private HashMap<String,Integer> dnaMap;
    private HashMap<String, Integer> condonVariety;
    public CodonCount() {
        dnaMap = new HashMap<>();
        condonVariety = new HashMap<>();
        condonVariety.put("Position 1", 0);
        condonVariety.put("Position 2", 0);
        condonVariety.put("Position 3", 0);
    }

    public void buildCodonMap(int start, String dna) {
        dnaMap.clear();
        for(int i=start; i <= dna.length() - 3; i += 3) {
            String codon = dna.substring(i, i+3);
            if(!Character.isAlphabetic(codon.charAt(2))) {continue;}
            if(dnaMap.containsKey(codon)) {
                dnaMap.put(codon, dnaMap.get(codon)+1);
            } else {
                dnaMap.put(codon, 1);
            }
        }

        if(condonVariety.containsKey("Position " + (start + 1))) {
            condonVariety.put("Position " + start, dnaMap.size());
        }

    }
    public String getMostCommonCodon() {
        int maxOccurence = 0;
        String mostCommonCodon = null;
        for(String codon : dnaMap.keySet()) {
            if(dnaMap.get(codon) > maxOccurence) {
                maxOccurence = dnaMap.get(codon);
                mostCommonCodon = codon;
            }
        }
        System.out.println("Most common codon: " + mostCommonCodon + " appears " + maxOccurence + " times");
        return mostCommonCodon;
    }

    public void printCodonCounts (int start, int end) {
        System.out.println("CodonCount between " + start + " and " + end + ':');
        for(String codon : dnaMap.keySet()) {
            if(dnaMap.get(codon) >= start && dnaMap.get(codon) <= end) {
                System.out.println(codon + "\t" + dnaMap.get(codon));
            }
        }
    }

    public void tester(){
            System.out.println("Choose a file of DNA: ");
            FileResource fr = new FileResource();
            String dna = fr.asString().toUpperCase();

            System.out.println("DNA starting with position 0: ");
            buildCodonMap(0,dna);
            System.out.println("Codon variety: " + condonVariety.get("Position 0"));
            System.out.println("The most common codon is: " + getMostCommonCodon());
            System.out.println("Codons occur between 0 and 5: ");
            printCodonCounts(0,6);
            for(String s : dnaMap.keySet()) {
                if(dnaMap.get(s) == 7) {
                    System.out.println(s + " appears 7 times");
                }
            }
            System.out.println("\n");

            System.out.println("DNA starting with position 1: ");
            buildCodonMap(1,dna);
            System.out.println("Codon variety: " + condonVariety.get("Position 1"));
            System.out.println("The most common codon is: " + getMostCommonCodon());
            System.out.println("Codons occur between 0 and 5:");
            printCodonCounts(0,5);
            System.out.println("\n");

            System.out.println("DNA starting with position 2: ");
            buildCodonMap(2,dna);
            System.out.println("Codon variety: " + condonVariety.get("Position 2"));
            System.out.println("The most common codon is: " + getMostCommonCodon());
            System.out.println("Codons occur between 0 and 5: ");
            printCodonCounts(0,5);

    }
}
