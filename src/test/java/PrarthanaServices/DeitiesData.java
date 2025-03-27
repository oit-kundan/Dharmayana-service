package PrarthanaServices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DeitiesData {
    public static List<String> getExpectedDeities() {
        return Arrays.stream(DEITY_NAMES.split("\\R"))
                .map(String::trim)
                .filter(s -> !s.isEmpty()) // in case any empty line is there
                .collect(Collectors.toList());
    }

    private static final String DEITY_NAMES = """
            Shiva
            Krishna
            Ganesha
            Hanuman
            Durga
            Rama
            Vishnu
            Lakshmi
            Venkateshwara
            Sai Baba
            Navagraha
            Parvathi
            Saraswathi
            Surya
            Narasimha
            Guru
            Godevata
            Subramanya
            Hayagriva
            Tulasi
            Guru Raghavendra
            Ayyappa
            Shani Mahatma
            Dhanvantari
            Kubera
            Sri Panduranga
            Shri Sita
            Bhairavi
            Dattatreya
            Pratyangira Devi
            Varaha
            Annapurna
            Mahakali
            Tripura Sundari
            Tara
            Chandra
            Budha
            Mangala
            Lalitha
            Bhuvaneshwari
            Ranganayaki
            Ganga
            Garuda
            Ketu
            Rahu
            Vastu Purusha
            Shukra
            Rajarajeshwari
            Brihaspati
            Manikarnika
            Sri Chodeswari
            Shri Kamakshi
            Shri Chinnamasta
            Chamundeshwari
            Dakini
            Shyamala Devi
            Valli Bhuvaneshvari
            Vasuki
            Matangi
            Bhavani
            Goda Devi
            Shitala Devi
            Manasa Devi
            Kumari
            Chandi
            Vindhya Vasini
            Santoshi Mata
            Sri Kanyaka Parameshwari
            Renuka Devi
            Ananta
            Siddha Kunjika
            Vamana
            Devi Chandraghanta
            Bhagavati
            Bhuvaneshvari
            Badrinath
            Mahamaya
            Matsya
            Shodashi
            Kamala
            Maha Bali
            Dharmambika
            Pitambara
            Yama
            Narayani
            Tulja Bhavani
            Mookambika
            Goddess Gayatri
            Katyayani
            Bhuvaneswari
                 """;


}
