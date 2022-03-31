package com.au.glasgow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InterviewOrganiserApplication {

	public static void main(String[] args) {

		try {
			SpringApplication.run(InterviewOrganiserApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
