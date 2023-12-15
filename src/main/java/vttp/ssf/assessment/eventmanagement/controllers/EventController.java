package vttp.ssf.assessment.eventmanagement.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Controller
@RequestMapping("/events")
public class EventController {

	//TODO: Task 5

	@Autowired
	RedisRepository redisRepository;

	private static List<Event> events = new ArrayList<Event>();


	@GetMapping("/listing")
	public String displayEvent(HttpSession session, Model model, BindingResult result){
		
		List<Event> events = redisRepository.getAllEvents();
		model.addAttribute("events", events);
		
		return "view0";
	}
}
