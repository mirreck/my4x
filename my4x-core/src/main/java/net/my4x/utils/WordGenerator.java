package net.my4x.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mirreck.RandomUtils;

public class WordGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordGenerator.class);    
    
    private Random rnd;
    
    private Map<Character, Map<Character, Integer>> rootMap = new HashMap<Character, Map<Character, Integer>>();

    private Set<Character> startChars = new HashSet<Character>();
    private Set<Character> endChars = new HashSet<Character>();

    public WordGenerator(){
        try {
            rnd = new Random(System.currentTimeMillis());
            initialize();
        } catch (IOException e) {
            throw new RuntimeException("Unable to initalize WordGenerator", e);
        }
    }
    
    
    public void initialize() throws IOException {
        InputStream iStream = WordGenerator.class.getResourceAsStream("sample-words.txt");
        String[] words = IOUtils.toString(iStream).split(" ");
        for (String word : words) {
            analyse(word);
        }
    }

    private void analyse(String word) {
        if (StringUtils.isNotBlank(word)) {
            Character first = word.charAt(0);
            startChars.add(first);
            endChars.add(word.charAt(word.length()-1));
            Character previous = first;
            for (int i = 1; i < word.length(); i++) {
                Character c = word.charAt(i);
                Map<Character, Integer> charLink = rootMap.get(previous);
                if (charLink == null) {
                    charLink = new HashMap<Character, Integer>();
                    rootMap.put(previous, charLink);
                }
                Integer num = charLink.get(c);
                if(num == null){
                    charLink.put(c,1);
                } else{
                    charLink.put(c,num+1);
                }
                previous = c;
                

            }
        }
    }
    
    public String generate(){
        StringBuilder sb = new StringBuilder();
        Character current = RandomUtils.randomElement(rnd,startChars);
        sb.append(current);
        while(sb.length() < 7 || !endChars.contains(current)){
            Map<Character, Integer> map = rootMap.get(current);
            Character next = RandomUtils.randomWeightedElement(rnd,map);
            sb.append(next);
            current = next;
           
        }
        return sb.toString();
    }
}
