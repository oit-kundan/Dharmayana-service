package PanchangaServices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RashiListData {



    public  static List<String> getExpectedRshi(){

        return Arrays.stream(name_List.split("\\R"))
                .map(String :: trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static final String name_List = """
                Mesha
                Aries
                aries
                Vrishabha
                Taurus
                taurus
                Mithuna
                Gemini
                gemini
                Karka
                Cancer
                cancer
                Simha
                Leo
                leo
                Kanya
                Virgo
                virgo
                Tula
                Libra
                libra
                Vrishchika
                Scorpio
                scorpio
                Dhanu
                Sagittarius
                sagittarius
                Makara
                Capricorn
                capricorn
                Kumbha
                Aquarius
                aquarius
                Meena
                Pisces
                pisces
                
    
    """;
}

