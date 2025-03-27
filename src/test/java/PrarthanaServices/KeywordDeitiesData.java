package PrarthanaServices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KeywordDeitiesData {

    public static List<String>getExpectedName(){
            return Arrays.stream(Deities_Name.split("\\R"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
    }

    private static final  String Deities_Name ="""

      Shiva
      Shitala Devi
""";
}
