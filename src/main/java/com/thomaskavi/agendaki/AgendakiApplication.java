package com.thomaskavi.agendaki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thomaskavi.agendaki.services.S3Service;

@SpringBootApplication
public class AgendakiApplication implements CommandLineRunner {

	@Autowired
	private S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(AgendakiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\Users\\thoma\\Desktop\\print.png");
	}

}
