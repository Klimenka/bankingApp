package nl.inholland;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = { "nl.inholland", "nl.inholland.controller", "nl.inholland.configuration"})
public class BankingApp implements CommandLineRunner {

    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    public static void main(String[] args) throws Exception {
        new SpringApplication(BankingApp.class).run(args);
      /*  int i=0;
        String filename="result.csv";
        Path pathToFile = Paths.get(filename);
        System.out.println(pathToFile.getAbsolutePath());*/
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
