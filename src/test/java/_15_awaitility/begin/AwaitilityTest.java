package _15_awaitility.begin;

import _15_awaitility.ThreadedFileCreator;
import com.google.common.base.Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class AwaitilityTest {

    @Test
    public void canWaitOnOtherStuff() throws IOException {

        // Create the file in another thread

        File outputFolder = new File(System.getProperty("user.dir"),"temp");
        File outputFile = new File(outputFolder,System.currentTimeMillis()+".txt");

        ThreadedFileCreator w = new ThreadedFileCreator(
                                            outputFolder, outputFile, 3000 );
        new Thread(w).start();

        // Wait for file to be created

        Function<File, Boolean> fileIsReady = new Function<File, Boolean>() {
            @Override
            public Boolean apply(File file) {
                return file.exists();
            }
        };

        new FluentWait<File>(outputFile).
                withTimeout(Duration.ofMillis(5000)).
                until(fileIsReady);

        // Check File Contents

        final byte[] contents = Files.readAllBytes(outputFile.toPath());
        Assertions.assertEquals("File Contents", new String(contents));
    }
}
