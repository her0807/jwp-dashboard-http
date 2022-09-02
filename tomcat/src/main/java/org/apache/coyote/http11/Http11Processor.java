package org.apache.coyote.http11;

import static org.apache.coyote.http11.UrlGenerator.getUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextstep.jwp.exception.UncheckedServletException;

public class Http11Processor implements Runnable, Processor {

	private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

	private final Socket connection;
	private final Controller controller;

	public Http11Processor(final Socket connection) {
		this.connection = connection;
		this.controller = new Controller(new Response(), new UserService(log));
	}

	@Override
	public void run() {
		process(connection);
	}

	@Override
	public void process(final Socket connection) {
		try (final var inputStream = connection.getInputStream();
			 final var outputStream = connection.getOutputStream()) {
			var response = "";
			final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String url = getUrl(inputStream);

			final HttpRequest request = HttpRequest.from(reader.readLine());

			if (url == null)
				return;
			response = controller.run(response, url);

			outputStream.write(response.getBytes());
			outputStream.flush();

		} catch (IOException | UncheckedServletException e) {
			log.error(e.getMessage(), e);
		}
	}
}

