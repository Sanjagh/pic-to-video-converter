package ir.saverin.kafshdoozak.converter.impl;

import ir.saverin.kafshdoozak.converter.api.Converter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Mahmood
 * @since 8/24/2015
 */
public class ConverterImpl implements Converter {

    private static final Logger logger = LoggerFactory.getLogger(ConverterImpl.class);

    @Override
    public void convert(String ffmpegPath, String ffmpegOptions, String ffmpegConcatOptions, String imagesPath,
                        String videoFileName, int framerate, List<String> audioFileName, String segmentPrefix, int segmentCount)
            throws IOException, InterruptedException {
        for (int i = 0; i < segmentCount; i++) {
            String audioPath = (audioFileName != null && audioFileName.size() - 1 >= i) ? (" -i " + audioFileName.get(i)) : "";
            Process exec = Runtime.getRuntime().exec(ffmpegPath + audioPath
                    + " -framerate " + framerate + " -i " + segmentPrefix + "_" + i + "_%06d.jpg -r " + framerate +
                    " -c:v libx264 " + ffmpegOptions + " " + videoFileName + "_" + i + ".mp4", null, new File(imagesPath));

            new Thread(new StreamHandler(exec.getErrorStream()), Thread.currentThread().getName() + "**ConvertStream").start();
            new Thread(new StreamHandler(exec.getInputStream()), Thread.currentThread().getName() + "**InputStream").start();

            int waitResultVideo = exec.waitFor();
            logger.info("wait result for video creation : {}", waitResultVideo);
            if (waitResultVideo != 0) {
                logger.error("error occurred in ffmpeg video conversion, result is {}", waitResultVideo);
                throw new RuntimeException("error occurred in ffmpeg video conversion, result is " + waitResultVideo);
            }
        }

        createVideoList(imagesPath, videoFileName, segmentCount);
        Process concat = Runtime.getRuntime().exec(ffmpegPath + " -f concat -i video.txt -c copy " +
                        ffmpegConcatOptions + " " + videoFileName + ".mp4",
                null, new File(imagesPath));
        new Thread(new StreamHandler(concat.getErrorStream()), Thread.currentThread().getName() + "**Stream").start();
        new Thread(new StreamHandler(concat.getInputStream()), Thread.currentThread().getName() + "**InputStream").start();
        int waitResultAudio = concat.waitFor();
        if (waitResultAudio != 0) {
            logger.error("error occurred in ffmpeg audio conversion, result is {}", waitResultAudio);
            throw new RuntimeException("error occurred in ffmpeg audio conversion, result is " + waitResultAudio);
        }
        logger.info("wait result for final video : {}", waitResultAudio);
        logger.info("video is created for cartoon : {}", videoFileName);

        deleteUnnecessaryFiles(videoFileName, segmentCount, imagesPath);

    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void deleteUnnecessaryFiles(String videoFileName, int segmentCount, String path) {

        for (int i = 0; i < segmentCount; i++) {
            File file = new File(path + "/" + videoFileName + "_" + i + ".mp4");
            if (file.exists()) {
                file.delete();
            }
        }
        File file = new File(path + "/" + "video.txt");
        if (file.exists()) {
            file.delete();
        }

    }

    private void createVideoList(String path, String cartoonId, int segmentCount) {

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(path + "/video.txt", "UTF-8");
            for (int i = 0; i < segmentCount; i++) {
                writer.println("file '" + cartoonId + "_" + i + ".mp4'");
            }
        } catch (Exception e) {
            logger.error("cannot create audio file ", e);
            throw new RuntimeException("cannot create audio file ", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private class StreamHandler implements Runnable {
        private final Logger logger = LoggerFactory.getLogger(StreamHandler.class);
        private InputStream inputStream;

        public StreamHandler(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            try {
                String s = IOUtils.toString(inputStream, "UTF-8");
                logger.info(s);
            } catch (IOException e) {
                throw new RuntimeException("IOException exception in convert video", e);
            }
        }
    }

}


