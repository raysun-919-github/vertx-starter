package com.ray.sun.starter;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.configuration.injection.scanner.MockScanner;
import org.mockito.junit.jupiter.MockitoExtension;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

@ExtendWith(MockitoExtension.class)
class MyBodyHandlerTest {

	private Vertx vertx;

	FileServiceImpl service;

	@Mock
	RoutingContext event;

	@Mock
	HttpServerRequest request;

	@Mock
	HttpServerResponse response;

	@BeforeEach
	public void setup() {
		vertx = Vertx.vertx();
		doReturn(request).when(event).request();
		doReturn(response).when(event).response();
		service = Mockito.mock(FileServiceImpl.class);
	}

	@Test
	public void shouldSetStatusTo200whenNoError() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		MyBodyHandler handler = MyBodyHandler.build(vertx);
				
		Field field = handler.getClass().getDeclaredField("fileService");
		field.setAccessible(true);
		field.set(handler, service);

		// arrange
		doReturn("demo.zip").when(service).saveFile(anyList());
		// act
		handler.handle(event);
		// assert
		response.bodyEndHandler(body->{
			Assertions.assertEquals("demo.zip", body.toString());
		});
		Mockito.verify(response).setStatusCode(200);
	}
}
