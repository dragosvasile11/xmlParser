package org.example.services;

import utils.ConfigFileReader;
import utils.ExceptionsMessages;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Logger;

/***
 * This class is responsible for verifying new files in the input folder.
 * <p>Validates file name to have the correct pattern.</p>
 * @author dragos
 */
public class DirectoryWatcher {
    private final Logger LOG = Logger.getLogger(DirectoryWatcher.class.getName());

    /***
     * The method is constantly watching after new files to be received in the input folder.
     * @return String. This method returns a String representation of the file name added in the directory.
     * @throws InterruptedException - if interrupted while waiting.
     */
    public String watchDirectory() throws InterruptedException {

        WatchService watchService;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(ConfigFileReader.properties.getProperty("inputPath"));
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        } catch (IOException e) {
            throw new RuntimeException(ExceptionsMessages.RUNTIME_EXCEPTION_MESSAGE, e);
        }

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                String fileName = event.context().toString();
                if (validateInput(fileName)) {
                    return fileName;
                }
            }
            key.reset();
        }
        throw new NullPointerException(ExceptionsMessages.NULL_MESSAGE);
    }

    /***
     * The method is validating the fileName received as a parameter, if it has the correct pattern.
     * @param fileName - It should be a String.
     * @return Boolean. This method returns a boolean (true or false).
     */
    public boolean validateInput(String fileName) {

        if (fileName.startsWith("orders")
                && Character.isDigit(fileName.charAt(6))
                && Character.isDigit(fileName.charAt(7))
                && fileName.substring(8).equals(".xml")
        ) {
            LOG.info("Received orders input file: " + fileName + ".");
            return true;
        } else {
            LOG.warning("Incorrect file name! It should be orders##.xml, where ## are digits.");
        }
        return false;
    }
}
