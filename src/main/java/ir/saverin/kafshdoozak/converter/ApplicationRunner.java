package ir.saverin.kafshdoozak.converter;

import ir.saverin.kafshdoozak.converter.api.Converter;
import ir.saverin.kafshdoozak.converter.impl.ConverterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Mahmood
 * @since 8/15/2015
 */
public class ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    public static void main(String[] args) {

        Converter converter = new ConverterImpl();
        try {
            ArrayList<String> voices = new ArrayList<>();
            voices.add("1.mp3");
            voices.add("2.mp3");
            voices.add("3.mp3");
            converter.convert("C:\\Program Files\\ffmpeg\\win64-static\\bin\\ffmpeg.exe", "", "", "E:\\pics", "final",
                    4, voices, "Sec", 3);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }


}