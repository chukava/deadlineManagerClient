package cz.cvut.fit.tjv.chukavol.client;

import cz.cvut.fit.tjv.chukavol.client.dto.StudentCreateDTO;
import cz.cvut.fit.tjv.chukavol.client.dto.StudentDTO;
import cz.cvut.fit.tjv.chukavol.client.resource.StudentResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.HypermediaRestTemplateConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class ClientApplication implements ApplicationRunner {

    @Autowired
    private StudentResource studentResource;


    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    RestTemplateCustomizer hypermediaRestTemplateCustomizer(HypermediaRestTemplateConfigurer configurer) {
        return restTemplate -> {
            configurer.registerHypermediaTypes(restTemplate);
        };
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.getOptionValues("action") == null){
            return;
        }
        try {
            if(args.getOptionValues("action").contains("findById")){
                int id = Integer.parseInt(args.getOptionValues("id").get(0));
                StudentDTO studentDTO = studentResource.readById(id);
                printStudent(studentDTO);
            }
            if(args.getOptionValues("action").contains("create")) {
                String username = args.getOptionValues("username").get(0);
                String password = args.getOptionValues("password").get(0);
                int grade = Integer.parseInt(args.getOptionValues("grade").get(0));

                StudentCreateDTO createDto = new StudentCreateDTO(username,password,grade);
                System.out.println(studentResource.create(createDto));
            }
            if(args.getOptionValues("action").contains("update")) {
                int id = Integer.parseInt(args.getOptionValues("id").get(0));
                String username = args.getOptionValues("username").get(0);
                String password = args.getOptionValues("password").get(0);
                int grade = Integer.parseInt(args.getOptionValues("grade").get(0));

                StudentCreateDTO studentCreateDTO = new StudentCreateDTO(username,password,grade);
                studentResource.update(id,studentCreateDTO);
                System.out.println("updated");
            }
            if(args.getOptionValues("action").contains("delete")) {
                int id = Integer.parseInt(args.getOptionValues("id").get(0));
                studentResource.delete(id);
                System.out.println("Deleted");
            }

        }
        catch (HttpClientErrorException e){
            System.out.println(helpError(e));
        }
    }


    private String helpError(HttpClientErrorException e){
        if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
            return "Not found";
        if (e.getStatusCode().equals(HttpStatus.CONFLICT))
            return "Already exists!";
        return "ERROR";
    }

    private void printStudent(StudentDTO studentDTO) {
        System.out.println("student id: " + studentDTO.getStudentId()
                + "\nusername: " + studentDTO.getStudentUsername()
                + "\npassword: " + studentDTO.getPassword()
                + "\ngrade: " + studentDTO.getGrade());
    }

}
