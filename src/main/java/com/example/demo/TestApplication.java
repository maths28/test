package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@SpringBootApplication
@RestController
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@RequestMapping("/")
	public String test() throws IOException{
		String text = "ceci est un test";
		File f = new File("uploads");
		f.mkdir();
		f = new File(f.getPath() + "/e");
		PrintWriter printWriter = new PrintWriter(f);
		printWriter.println(text);
		printWriter.close();
		return f.getAbsolutePath();
	}
}
