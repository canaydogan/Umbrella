package net.canaydogan.umbrella;

import java.io.File;
import java.io.IOException;

public interface FileUpload {

	public String getFilename();
	
	public File getFile() throws IOException;
	
}
