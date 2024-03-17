package com.ray.sun.starter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.FileUpload;

public class FileServiceImpl {
	public FileServiceImpl(Vertx vertx) {
		super();
		this.vertx = vertx;
	}
	

	private Vertx vertx;
	
	public Vertx getVertx() {
		return vertx;
	}

	public void setVertx(Vertx vertx) {
		this.vertx = vertx;
	}

	public String saveFile(List<FileUpload> fileUploadSet) {
		String fileName = null;
        Iterator<FileUpload> fileUploadIterator = fileUploadSet.iterator();
        while (fileUploadIterator.hasNext()){
          FileUpload fileUpload = fileUploadIterator.next();

          // To get the uploaded file do
          Buffer uploadedFile = vertx.fileSystem().readFileBlocking(fileUpload.uploadedFileName());
          
          // Uploaded File Name
          try {
            fileName = URLDecoder.decode(fileUpload.fileName(), "UTF-8");
          } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          }
        }
		return fileName;
	}
}
