package ir.saverin.kafshdoozak.converter.api;

import java.io.IOException;
import java.util.List;

/**
 * @author Mahmood
 * @since 8/29/2015
 */
public interface Converter {

    /**
     * This function convert list of images to video. images should be in JPG format. images can be in several segments.
     * The general format of images should be in PREIFIX_i_dddddd.jpg format. PREFIX is optional string choose by user.
     * 'i' is the segment number and 'dddddd' is number of image in segment. for example for 3 segment video and for each
     * segment 4 images, names can be in the following format :
     * <br>cartoon_0_000000.jpg
     * <br>cartoon_0_000001.jpg
     * <br>cartoon_0_000002.jpg
     * <br>cartoon_0_000003.jpg
     * <br>cartoon_1_000000.jpg
     * <br>cartoon_1_000001.jpg
     * <br>cartoon_1_000002.jpg
     * <br>cartoon_1_000003.jpg
     * <br>cartoon_2_000000.jpg
     * <br>cartoon_2_000001.jpg
     * <br>cartoon_2_000002.jpg
     * <br>cartoon_2_000003.jpg
     * Also you can attach audio files to the videos. audio files should be in the image folder and file name should be
     * in audioFileName parameter. audio files
     * <br>
     * call example : <br>
     * <code> ArrayList voices = new ArrayList() <br>
     * voices.add("1.mp3");<br>
     * voices.add("2.mp3");<br>
     * voices.add("3.mp3");<br>
     * converter.convert("C:\\Program Files\\ffmpeg\\win64-static\\bin\\ffmpeg.exe", "", "", "E:\\pics", "final",4, voices, "Sec", 3);<br>
     * </code>
     * @param ffmpegPath indicate ffmpeg path
     * @param ffmpegOptions ffmpeg option for convert images to video
     * @param ffmpegConcatOptions ffmpeg option for concat video results
     * @param imagesPath images folder path
     * @param videoFileName final video file name
     * @param framerate framerate of video (for 100 images and framerate 25, the result will be 4 seconds video)
     * @param audioFileName names of audio files, audio files must be placed in images folder, also time length of each audio section file should be as same as video section result.
     * @param segmentPrefix image name must be in PREFIX_i_dddddd.jpg format, i is the segment number started from 0, and dddddd
     *                      is the number of image of that segment started from 000000, for each segment numbers must be reset.
     * @param segmentCount segment count of videos,
     * @throws IOException {@link IOException} may be occurred in read and write files
     * @throws InterruptedException {@link java.io.InterruptedIOException} may be occurred in running threads for handle InputStream and
     * ErrorStream of {@link Process}
     */
    void convert(String ffmpegPath, String ffmpegOptions, String ffmpegConcatOptions, String imagesPath,
                 String videoFileName, int framerate, List<String> audioFileName, String segmentPrefix, int segmentCount)
            throws IOException, InterruptedException;

}
