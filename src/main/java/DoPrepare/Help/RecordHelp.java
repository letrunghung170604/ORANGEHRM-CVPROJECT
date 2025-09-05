package DoPrepare.Help;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class RecordHelp extends ScreenRecorder {

    // Project root path
    static String projectPath = System.getProperty("user.dir") + "/";

    // ThreadLocal to store ScreenRecorder instance per thread
    private static ThreadLocal<ScreenRecorder> screenRecorder = new ThreadLocal<>();
    private String name;

    // Constructor
    public RecordHelp(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat,
                      Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
        PropertiesHelp.setPropertiesFile();
    }

    // Override to customize movie file creation
    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        // Create directory if it doesn't exist
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        return new File(movieFolder,
                name + "_" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
    }

    // Start recording video
    public static void startRecord(String methodName) throws Exception {
        PropertiesHelp.setPropertiesFile();
        // Create directory for video files
        File file = new File(projectPath + PropertiesHelp.getPropValue("exportVideoPath") + "/" + methodName + "/");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0, 0, width, height);

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
                .getDefaultConfiguration();
        ScreenRecorder recorder = new RecordHelp(gc, captureSize,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
                        Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                null, file, methodName);
        screenRecorder.set(recorder);
        recorder.start();
    }

    // Stop recording video
    public static void stopRecord() throws Exception {
        ScreenRecorder recorder = screenRecorder.get();
        if (recorder != null) {
            recorder.stop();
            screenRecorder.remove();
        } else {
            System.out.println("No ScreenRecorder instance found to stop.");
        }
    }
}