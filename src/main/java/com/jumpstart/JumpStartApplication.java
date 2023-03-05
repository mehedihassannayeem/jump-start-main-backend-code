package com.jumpstart;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.jumpstart.config.AppConstants;
import com.jumpstart.config.AppProperties;
import com.jumpstart.entities.Role;
import com.jumpstart.repository.RoleRepository;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class JumpStartApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(JumpStartApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	public void run(String... args) throws Exception {

		try {

			Role admin = new Role();
			admin.setRid(AppConstants.ADMIN_USER);
			admin.setName("ROLE_ADMIN");

			Role user = new Role();
			user.setRid(AppConstants.NORMAL_USER);
			user.setName("ROLE_NORMAL");

			List<Role> roles = List.of(admin, user);

			this.roleRepository.saveAll(roles);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
