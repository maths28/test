package com.example.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.concurrent.Future;

@SpringBootApplication
@RestController
@EnableAsync
public class TestApplication {

	private Future<Double> response;

	@Autowired
	private UploadService uploadService;

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@RequestMapping("/upload")
	public String test(@RequestParam(name = "file", required = false)MultipartFile file, HttpServletResponse response) throws Exception{
		if(this.response == null){
			this.response = this.uploadService.upload(file);
		}
		DecimalFormat df = new DecimalFormat("0.00");

		return df.format(this.getProgress())+"<a href='/upload'>Rafr</a>";
//		response.sendRedirect("/progress");
	}

	private Double getProgress() throws Exception{
		if(this.response.isDone()){
			return this.response.get();
		} else {
			return this.uploadService.getProgress();
		}
	}

}
