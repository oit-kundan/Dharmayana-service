package Shubhkarya;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogueData {

    public static List<String> getExpectedTitles(){
        return Arrays.stream(Titles_Name.split("\\R"))
                .map(String :: trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static final String Titles_Name = """
            Shubh Muhurta for everyone
            Shubh Muhurta for your Rashi
            
            """;
}
