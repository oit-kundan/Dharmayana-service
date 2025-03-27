package FestivalServices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FestivalData {

    public static List<String> getExpectedFestivals(){
        return Arrays.stream(Festival_Name.split("\\R"))
                .map(String :: trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());


    }
    private static final  String Festival_Name  ="""
Ekadashi
Rangabhari Ekadashi
Amalaki Ekadashi
Bhaumavara Shukla Pradosa
Pradosha
Holika Dahan
Purnima
Holi
Chaitanya Mahaprabhu Jayanti
Masik Sankranti
Purnima
Chandra Grahana
Sankasta Chaturthi
Ekadashi
Ekadashi
Vaishnava Papamochani Ekadashi
Pradosha
Amavasya
Surya Grahana
Ugadi
Gangaur
Maha Gauri Puja
Ram Navami
Ekadashi
Kamada Ekadashi
Mahavira Jayanti
Pradosha
Purnima
Masik Sankranti




""";


}
