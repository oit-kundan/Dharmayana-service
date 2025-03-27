package PanchangaServices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleRecommendationData {

    public  static List<String> getExpectedName(){

        return Arrays.stream(name_List.split("\\R"))
                .map(String :: trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static final String name_List = """

                        Understanding Vaara (Day Of The Week)
                        https://dharmayana.in/learn-more-mobile/5/#Introduction
                        The Concept And Significance Of Tithi (Lunar Day)
                        https://dharmayana.in/learn-more-mobile/4/#Introduction
                        Understanding Masa (Lunar Month )
                        https://dharmayana.in/learn-more-mobile/2/#Introduction
            
""";
}
