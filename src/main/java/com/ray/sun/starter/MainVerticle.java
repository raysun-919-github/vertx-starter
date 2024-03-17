package com.ray.sun.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		Router router = Router.router(vertx);

		router.get("/api/hello").handler(ctx -> {
			ctx.request().response().end("Hello, Ray");
		});

		router.get("/api/vertx").handler(ctx -> {
			ctx.response().putHeader("content-type", "text/plain").end("Hello from Vert.x!");
		});
		router.post("/api/file").handler(BodyHandler.create().setMergeFormAttributes(true).setPreallocateBodyBuffer(false));
		router.post("/api/file").handler(MyBodyHandler.build(vertx));

		vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
			if (http.succeeded()) {
				startPromise.complete();
				System.out.println("HTTP server started on port 8888");
			} else {
				startPromise.fail(http.cause());
			}
		});

	}
}
