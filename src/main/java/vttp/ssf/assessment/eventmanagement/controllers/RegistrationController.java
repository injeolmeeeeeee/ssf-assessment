package vttp.ssf.assessment.eventmanagement.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.models.RegistrationForm;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Controller
public class RegistrationController {
    
    private RedisRepository redisRepository;
    // private RegistrationForm registrationForm;

    // TODO: Task 6

    @GetMapping("/register/2")
	public String registration(Model model) {

	    RegistrationForm registrationForm = new RegistrationForm();
        model.addAttribute("registrationForm", registrationForm);
		
        return "eventregister";
	}

    // TODO: Task 7
    @PostMapping("/register/2")
    public String processRegistration(@RequestParam Integer eventId,
                                    @Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm, 
                                    BindingResult bindingResult, Model model) {
        //bindingresult to check for validation
        //if age (need to calculate age) > 21 , ok
        //if participants > eventsSize, fail, return message and error page
        
        Event event = redisRepository.getEvent(eventId);

        if (bindingResult.hasErrors()){
            return "eventregister";
        }

        if (!checkAge(registrationForm.getBirthDate())) {
            model.addAttribute("error", "You must be above 21 years old to register for the event");
            return "ErrorRegistration";
        }

        int participants = event.getParticipants() + Integer.parseInt(registrationForm.getNoOfTickets());

        if (participants > event.getEventSize()) {
            model.addAttribute("error", "Your request for tickets has exceeded the event size");
            return "ErrorRegistration";
        } else {
            event.setParticipants(participants);
            model.addAttribute("event", event);
            return "SuccessRegistration";
        }
    }

    public boolean checkAge(Date dobEntered) {
        LocalDate dob = dobEntered.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now(); 
        int age = today.getYear() - dob.getYear();

        return age>=21;
    }
}
