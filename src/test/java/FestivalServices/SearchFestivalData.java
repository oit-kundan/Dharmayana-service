package FestivalServices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchFestivalData {

    public static List<String>getExpectedFestival(){
        return Arrays.stream(Festival_Name.split("\\R"))
                .map(String :: trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

    }

    private static final String Festival_Name = """
            
            Naraka Chaturdashi
            Diwali
            Dev Diwali
            """;
}
