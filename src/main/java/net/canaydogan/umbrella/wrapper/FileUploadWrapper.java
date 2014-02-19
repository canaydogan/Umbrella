package net.canaydogan.umbrella.wrapper;

import java.io.File;
import java.io.IOException;

import net.canaydogan.umbrella.FileUpload;

public class FileUploadWrapper implements FileUpload {

	protected final io.netty.handler.codec.http.multipart.FileUpload fileUpload;	
	
	public FileUploadWrapper(io.netty.handler.codec.http.multipart.FileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}

	@Override
	public String getFilename() {
		return fileUpload.getFilename();
	}

	@Override
	public File getFile() throws IOException {
		return fileUpload.getFile();
	}
	
}
