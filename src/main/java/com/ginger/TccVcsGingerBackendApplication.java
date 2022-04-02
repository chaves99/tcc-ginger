package com.ginger;

import com.ginger.core.project.tags.Tags;
import com.ginger.core.project.tags.TagsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class TccVcsGingerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccVcsGingerBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(TagsRepository tagsRepository) {
        List<Tags> tagsList = List.of(
                Tags.builder().id(1L).description("Ciência da Computação").build(),
                Tags.builder().id(2L).description("Engenharia da Computação").build(),
                Tags.builder().id(3L).description("Direito").build(),
                Tags.builder().id(4L).description("Engenharia Civil").build(),
                Tags.builder().id(5L).description("Psicologia").build(),
                Tags.builder().id(6L).description("Pedagogia").build(),
                Tags.builder().id(7L).description("Administração").build(),
                Tags.builder().id(8L).description("Educação Física").build()
        );
        return x -> {
                tagsRepository.saveAll(tagsList);
        };
    }

}
