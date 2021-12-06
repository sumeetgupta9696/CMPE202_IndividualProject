package application.Handler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public interface Output {
	void writeToFile( ArrayList<String> content );
	void save( Path path ) throws IOException;
}
