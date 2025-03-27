package FestivalServices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VratData {

    public static List<String>getExpectedName(){
        return Arrays.stream(Data_Name.split("\\R"))
                .map(String ::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static final String Data_Name = """
            Melukote Vairamudi Utsava
            Amalaki Ekadashi
            Ekadashi
            Rangabhari Ekadashi
            Pradosha
            Bhaumavara Shukla Pradosa
            Masi Magam
            Attukal Pongal
            Holika Dahan
            Purnima
            Holi
            Chaitanya Mahaprabhu Jayanti
            Chandra Grahana
            Masik Sankranti
            Purnima
            Chhatrapati Shivaji Maharaj Jayanti
            Sankasta Chaturthi
            Sheetal Ashtami
            Ekadashi
            Ekadashi
            Vaishnava Papamochani Ekadashi
            Pradosha
            Surya Grahana
            Amavasya
            Ugadi
            Matsya Jayanti
            Gangaur
            Kushmanda Puja
            Skandamata Puja
            Lakshmi Panchami
            Yamuna Jayanti
            Kalaratri Puja
            Maha Gauri Puja
            Ashok Ashtami
            Ram Navami
            Swaminarayan Jayanti
            Tara Jayanti
            Ekadashi
            Kamada Ekadashi
            Mahavira Jayanti
            Pradosha
            Hanuman Jayanti
            Purnima
            Pana Sankranti
            Vishu
            Puthandu
            Baisakhi
            Masik Sankranti
            
            """;
}
