package vttp.ssf.assessment.eventmanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@SpringBootApplication
public class EventmanagementApplication implements CommandLineRunner{

	@Autowired
	private DatabaseService databaseService;
	@Autowired
	private RedisRepository redisRepository;


	public static void main(String[] args) {
		SpringApplication.run(EventmanagementApplication.class, args);
	}
	// TODO: Task 1
	
	public void run(String... args) throws Exception {
		
		//service.readFile
		String fileName = "events.json";
		List<Event> events = databaseService.readFile(fileName);

		for(Event event : events) {
			redisRepository.saveRecord(event);
		}

	}
}
