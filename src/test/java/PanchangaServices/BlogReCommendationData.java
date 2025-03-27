package PanchangaServices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BlogReCommendationData {

    public  static List<String> getExpectedName(){

        return Arrays.stream(name_List.split("\\R"))
                .map(String :: trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static final String name_List = """
            
            
            Ganesha and the Paradox of Perfection: The Broken Tusk
            Mahabharatha
            Ganesha
            Vyasa
            
            The Veena: Stringed Sound of India's Soul\s
            Saraswati
            Rig Veda
            Music
            Ravana
            Narada
            
            Reaching for the Sun: Pursuing Dreams with Childlike Curiosity
            
            HanumanTeachings
            LifeLessons
            Fun Activities for Children during Navratri
            NavratriWithKids
            CelebrateNavratri
            NavratriTraditions
            FestiveActivities
            Ancient Wisdom for Modern Life: The Power of Prarthana
            DailyPrarthana
            GratitudePractice
            SpiritualRoutine
            PrayerBenefits
            Shlokas
            SpiritualWellbeing
            
""";
}
