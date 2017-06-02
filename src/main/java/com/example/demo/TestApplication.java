package com.example.demo;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@EnableScheduling
public class TestApplication {

	private Future<Double> response;

	@Autowired
	private UploadService uploadService;

	private Logger log = LoggerFactory.getLogger(TestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@RequestMapping("/upload")
	public String test(@RequestParam(name = "file", required = false)MultipartFile file) throws Exception{
		if(this.response == null && file!=null){
			this.response = this.uploadService.upload(file);
		}
		DecimalFormat df = new DecimalFormat("0.00");

		return (this.response != null ? df.format(this.getProgress()) : "") +"<a href='/upload'>Rafr</a>";
//		response.sendRedirect("/progress");
	}

	@GetMapping("/getfile/{fileName}")
	public void getFile(@PathVariable("fileName") String fileName, HttpServletResponse response) throws Exception{
		File f = new File("uploads/");
		for (String s : f.list()){
			log.info(s);
		}
		File fichier = new File("uploads/"+fileName);
		if(fichier.exists()){
			FileInputStream fis = new FileInputStream(fichier);
			IOUtils.copy(fis, response.getOutputStream());
			response.flushBuffer();
		} else {
			response.getOutputStream().println("ok");
		}

	}

	private Double getProgress() throws Exception{
		if(this.response.isDone()){
			Double response = this.response.get();
			this.response = null;
			return response;
		} else {
			return this.uploadService.getProgress();
		}
	}


	@GetMapping("/active")
	public boolean active(){
		log.info("Request active received");
		return true;
	}

}
