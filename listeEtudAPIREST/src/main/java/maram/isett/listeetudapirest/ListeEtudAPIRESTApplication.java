package maram.isett.listeetudapirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ListeEtudAPIRESTApplication {

    public static void main(String[] args) {
        SpringApplication.run(ListeEtudAPIRESTApplication.class, args);
    }
}