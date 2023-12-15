package vttp.ssf.assessment.eventmanagement.models;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationForm {

    @NotBlank (message = "Full name is required")
    @Size(min=5, max=25, message = "Full Name must have 5 to 25 characters")
    private String fullName;

    @Past(message="Birth date cannot be greater than or equals to present date")
    private Date birthDate;

    @NotBlank (message = "Email is required")
    @Size(max=50, message = "Email cannot exceed 50 characters")
    private String email;

    @Pattern(regexp="[89]{8}", message = "Mobile number must start with 8 or 9 and must be 8 digits")
    private String mobileNumber;

    @Pattern(regexp = "123", message = "You can only request for a minimum of 1 and maximum of 3 tickets")
    @NotBlank
    private String noOfTickets;



    
}
