package PanchangaServices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AppConfigData {

    public  static List<String>getExpectedName(){

        return Arrays.stream(name_List.split("\\R"))
                .map(String :: trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static final String name_List = """
            morning
            Morning
            afternoon
            Afternoon
            evening
            Evening
            night
            Night
            not_sure
            Not Sure
            
            """;
}
