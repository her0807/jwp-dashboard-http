package study;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javassist.ClassPath;

/**
 * 웹서버는 사용자가 요청한 html 파일을 제공 할 수 있어야 한다.
 * File 클래스를 사용해서 파일을 읽어오고, 사용자에게 전달한다.
 */
@DisplayName("File 클래스 학습 테스트")
class FileTest {

	/**
	 * File 객체를 생성하려면 파일의 경로를 알아야 한다.
	 * 자바 애플리케이션은 resource 디렉터리에 정적 파일을 저장한다.
	 * resource 디렉터리의 경로는 어떻게 알아낼 수 있을까?
	 */
	@Test
	void resource_디렉터리에_있는_파일의_경로를_찾는다() {
		final String fileName = "nextstep.txt";
		final URL resource = getClass().getClassLoader().getResource(fileName);
		final File file = new File(resource.getPath());
		String actual = file.getName();
		assertThat(actual).endsWith(fileName);
	}

	/**
	 * 읽어온 파일의 내용을 I/O Stream을 사용해서 사용자에게 전달 해야 한다.
	 * File, Files 클래스를 사용하여 파일의 내용을 읽어보자.
	 */
	@Test
	void 파일의_내용을_읽는다() throws IOException {
		final String fileName = "nextstep.txt";
		final URL resource = getClass().getClassLoader().getResource(fileName);

		final File file = new File(resource.getPath());
		final byte[] bytes = Files.readAllBytes(file.toPath());
		final OutputStream outputStream = new ByteArrayOutputStream(bytes.length);
		outputStream.write(bytes); //:todo : -> did
		List<String> actual = new ArrayList<>();
		actual.add(outputStream.toString());

		assertThat(actual).containsOnly("nextstep");
	}
}
