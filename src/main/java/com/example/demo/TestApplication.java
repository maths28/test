package com.example.demo;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
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
	public String test(@RequestParam(name = "file", required = false)MultipartFile file) throws Exception{
		if(this.response == null){
			this.response = this.uploadService.upload(file);
		}
		DecimalFormat df = new DecimalFormat("0.00");

		return df.format(this.getProgress())+"<a href='/upload'>Rafr</a>";
//		response.sendRedirect("/progress");
	}

	@GetMapping("/getfile/{fileName}")
	public void getFile(@PathVariable("fileName") String fileName, HttpServletResponse response) throws Exception{
		FileInputStream fis = new FileInputStream(new File("uploads/"+fileName));
		IOUtils.copy(fis, response.getOutputStream());
		response.flushBuffer();
	}

	private Double getProgress() throws Exception{
		if(this.response.isDone()){
			return this.response.get();
		} else {
			return this.uploadService.getProgress();
		}
	}

}
