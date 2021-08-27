package _15_awaitility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ThreadedFileCreator implements Runnable{
    private final long millisToWait;
    private final File outputFolder;
    private final File outputFile;

    public ThreadedFileCreator(final File outputFolder, final File outputFile, long millisToWait) {

        this.outputFolder = outputFolder;
        this.outputFile = outputFile;
        this.millisToWait = millisToWait;

    }

    @Override
    public void run() {

        try {
            Thread.sleep(this.millisToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        outputFolder.mkdirs();

        try {
            Files.write(outputFile.toPath(), "File Contents".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
