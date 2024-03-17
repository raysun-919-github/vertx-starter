package com.ray.sun.starter;

import java.util.List;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

public class MyBodyHandler implements Handler<RoutingContext> {
	
	public static MyBodyHandler build(Vertx vertx) {
		MyBodyHandler handler = new MyBodyHandler().setVertx(vertx);
		return handler;
		
	}
	public MyBodyHandler() {
		super();
	}

	private FileServiceImpl fileService;


	public MyBodyHandler setVertx(Vertx vertx) {
		this.fileService = new FileServiceImpl(vertx);
		return this;
	}

	@Override
	public void handle(RoutingContext event) {
        List<FileUpload> fileUploadSet = event.fileUploads();
        event.response().end(fileService.saveFile(fileUploadSet));
	}
	
	

}
