package net.canaydogan.umbrella.util;

import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.canaydogan.umbrella.FileUpload;
import net.canaydogan.umbrella.FileUploadCollection;
import net.canaydogan.umbrella.wrapper.FileUploadWrapper;

public class DefaultFileUploadCollection implements FileUploadCollection {

	protected Map<String, FileUpload> fileUploadCollection = new HashMap<>();
	
	public DefaultFileUploadCollection() {}
	
	public DefaultFileUploadCollection(List<InterfaceHttpData> nettyData) {		
		for (InterfaceHttpData data : nettyData) {
			if (data.getHttpDataType() == HttpDataType.FileUpload) {			
				io.netty.handler.codec.http.multipart.FileUpload fileUpload = (io.netty.handler.codec.http.multipart.FileUpload) data;
				if (fileUpload.isCompleted()) {
					fileUploadCollection.put(fileUpload.getName(), new FileUploadWrapper(fileUpload));	
				}				
			}
		}
	}
	
	@Override
	public FileUpload get(String name) {
		return fileUploadCollection.get(name);
	}

	@Override
	public boolean contains(String name) {
		return fileUploadCollection.containsKey(name);
	}

}