package kr.ac.hansung.ilsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IndoorLocationServiceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndoorLocationServiceServerApplication.class, args);
	}

}





/**
// WAR 사용 시
@SpringBootApplication
public class IndoorLocationServiceServerApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(IndoorLocationServiceServerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(IndoorLocationServiceServerApplication.class, args);
	}

}
*/