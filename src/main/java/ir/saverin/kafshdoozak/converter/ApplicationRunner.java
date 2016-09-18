package ir.saverin.kafshdoozak.converter;

import ir.saverin.kafshdoozak.converter.api.Converter;
import ir.saverin.kafshdoozak.converter.impl.ConverterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Mahmood
 * @since 8/15/2015
 */
public class ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    public static void main(String[] args) {

        Converter converter = new ConverterImpl();
        try {
            converter.convert("C:\\Program Files\\ffmpeg\\win64-static\\bin\\ffmpeg.exe", "", "", "E:\\pics", "final", 4, null, "Sec", 3);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }


}