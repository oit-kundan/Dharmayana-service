package FestivalServices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UpcomingFestivalData {

    public static List<String> getExpectedFestivals(){
        return Arrays.stream(Festival_name.split("\\R"))
                .map(String :: trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());


    }

  private static final String Festival_name ="""
       Vishu
          Masi Magam
          Attukal Pongal
          Holika Dahan
          Purnima
          Holi
          Chaitanya Mahaprabhu Jayanti
          Purnima
          Chandra Grahana
          Masik Sankranti
          Chhatrapati Shivaji Maharaj Jayanti
          Sankasta Chaturthi
          Sheetal Ashtami
          Ekadashi
          Ekadashi
          Vaishnava Papamochani Ekadashi
          Pradosha
          Amavasya
          Surya Grahana
          Ugadi
          Gangaur
          Matsya Jayanti
          Kushmanda Puja
          Skandamata Puja
          Lakshmi Panchami
          Yamuna Jayanti
          Kalaratri Puja
          Maha Gauri Puja
          Ashok Ashtami
          Ram Navami
          Tara Jayanti
          Swaminarayan Jayanti
          Ekadashi
          Kamada Ekadashi
          Mahavira Jayanti
          Pradosha
          Hanuman Jayanti
          Purnima
          Pana Sankranti
          
          Puthandu
          Baisakhi
          Masik Sankranti
          Pohela Boishakh
          Sankasta Chaturthi
          """;
}
