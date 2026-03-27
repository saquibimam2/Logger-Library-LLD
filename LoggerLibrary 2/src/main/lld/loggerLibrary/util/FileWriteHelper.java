package main.lld.loggerLibrary.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Date;

import main.lld.loggerLibrary.enums.LogLevel;

public class FileWriteHelper {

	// Please change the default address after creating the output file locally
	private static final String DEFAULT_FILE_PATH = String.valueOf(
			Paths.get("/Users/saquibimam/eclipse-workspace/LoggerLibrary/src/main/lld/loggerLibrary/util/output"));

    private static String currentFilePath = DEFAULT_FILE_PATH;
    private static Integer MAX_FILE_SIZE = 1024 * 100; // ~100kb

	
	public static void writeOutputToFile(LogLevel level, Date date, String timeFormat, String message, String path) {
		try {
            Path filePath = Paths.get(currentFilePath);

            // Ensure file exists
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
            }

            // Rotate if needed
            if (Files.size(filePath) > MAX_FILE_SIZE) {
            	// find the name of the new file that has to be created w.r.t. autonumberring
                filePath = createNextFile(filePath);
                currentFilePath = filePath.toString(); // update active file
            }

            // Write log
            Files.write(
                filePath,
                Collections.singleton(MessageFormatter.format(level, date, timeFormat, message)),
                StandardOpenOption.APPEND
            );

        } catch (Exception e) {
            System.out.println(e);
        }
	}

	private static Path createNextFile(Path originalPath) throws IOException {
		String fileName = originalPath.getFileName().toString();
	    Path dir = originalPath.getParent();

	    String baseName;
	    String extension = "";
	    int counter = 0;

	    // Step 1: Separate extension (.txt)
	    int dotIndex = fileName.lastIndexOf(".");
	    if (dotIndex != -1) {
	        extension = fileName.substring(dotIndex);
	        fileName = fileName.substring(0, dotIndex); // remove extension
	    }

	    // Step 2: Extract counter if present (output_3)
	    int underscoreIndex = fileName.lastIndexOf("_");
	    if (underscoreIndex != -1) {
	        baseName = fileName.substring(0, underscoreIndex);
	        String counterPart = fileName.substring(underscoreIndex + 1);
	        counter = Integer.parseInt(counterPart);
	    } else {
	        baseName = fileName;
	    }

	    Path newPath;

	    // Step 3: Find next available file
	    do {
	        counter++;
	        String newFileName = baseName + "_" + counter + extension;
	        newPath = dir.resolve(newFileName);
	    } while (Files.exists(newPath));

	    return Files.createFile(newPath);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
