package org.my4x.utilities.svg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

public class SVGWriter {
	
	private final String content;
	
	public SVGWriter(String content) {
		super();
		this.content = content;
	}
	public void toStream(OutputStream os){
		try {
			os.write(content.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public void toFile(File file){
		try{
			if(!file.exists()){
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			FileOutputStream outputStream = new FileOutputStream(file);
			IOUtils.write(content, outputStream);
			IOUtils.closeQuietly(outputStream);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	public String toString(){
		return content;
	}
}
