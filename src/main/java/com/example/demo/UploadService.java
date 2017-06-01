package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Future;

/**
 * Created by mathieu on 29/05/17.
 */

@Service("uploadService")
public class UploadService {

    private double progress;

    private Logger log = LoggerFactory.getLogger(UploadService.class);


    @Async
    public Future<Double> upload(MultipartFile file) throws IOException {
        File f = new File("uploads/");
        f.mkdirs();
        f = new File("uploads/"+file.getOriginalFilename());
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        byte[] read = new byte[1024];
        double count = 0;
        double total = file.getSize();
        int readed = 0;
        InputStream is = file.getInputStream();
        while ((readed = is.read(read)) != -1){
            fos.write(read);
            count+=readed;
            float countF = (float)count;
            this.progress = countF / total * 100;
        }
        fos.close();
        f = new File("uploads/");
        for(String s : f.list()){
            log.info(s);
        }
        return new AsyncResult<Double>((double)100);
    }

    public double getProgress() {
        return this.progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
