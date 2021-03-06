package sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SBApplication {

	public static final String ERROR_MSG = "Erro de sistema.";

	public static final String EXPIRED_MSG = "A sua sessão expirou. Por favor faça novamente login.";
	
	public static void main(String[] args) {
		SpringApplication.run(SBApplication.class, args);
	}

}