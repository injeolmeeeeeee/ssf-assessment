package vttp.ssf.assessment.eventmanagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@SpringBootApplication
public class EventmanagementApplication implements CommandLineRunner{

	private DatabaseService databaseService;
	private RedisRepository redisRepository;
	private Event event;
	public static void main(String[] args) {
		SpringApplication.run(EventmanagementApplication.class, args);
	}
	// TODO: Task 1
	
	public void run(String... args) throws Exception {
		
		//service.readFile
		String fileName = "ssf-assessment/events.json";
		
		databaseService.readFile(fileName);
		redisRepository.saveRecord(event);

	}
}
