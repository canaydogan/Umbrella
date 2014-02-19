package net.canaydogan.umbrella;

public interface FileUploadCollection {

	public FileUpload get(String name);
	
	public boolean contains(String name);
	
}
