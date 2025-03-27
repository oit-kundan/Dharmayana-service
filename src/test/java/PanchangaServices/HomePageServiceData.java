package PanchangaServices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HomePageServiceData {

    public  static List<String> getExpectedData(){

        return Arrays.stream(name_List.split("\\R"))
                .map(String :: trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static final String name_List = """

panchanga
Panchanga
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/panchanga.png
muhurta
Muhurta
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/muhurta.png
shubh_dina
Find a Shubh Dina
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/shubh_din.png
festivals
Festivals
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/festivals.png
janam_kundli
Janma Kundali
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/janam_kundali.png
prarthana
Prarthana
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/prarthana.png
learn_more
Learn More
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/learn_more.png
all_services
All Services
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/all_services.png





PANCHANGA & MUHURTA
FESTIVALS & OBSERVANCES
PRARTHANA & POOJA
OTHERS






panchanga
Panchanga
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/panchanga.png
muhurta
Muhurta
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/muhurta.png
shubh_dina
Find a Shubh Dina
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/shubh_din.png
janam_kundli
Get Your Janam Kundali
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/janam_kundali.png
festivals
Festivals
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/festivals.png
observances
Observances
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/observances.png
prarthana
Prarthana
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/prarthana.png
learn_more
Learn More
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/learn_more.png
settings
Settings
https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/settings.png


""";


}
