package vttp.ssf.assessment.eventmanagement.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import vttp.ssf.assessment.eventmanagement.models.Event;

@SuppressWarnings("unchecked")
@Service
public class DatabaseService {

    // TODO: Task 1
    public List<Event> readFile(String fileName) throws IOException, ParseException {

        File file = new File(fileName);
        InputStream is = new FileInputStream(file);

        StringBuilder resultStringBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line);
            }
        }

        String data = resultStringBuilder.toString();
        System.out.println(data);

        JSONParser jsonParser = new JSONParser();
        Object object = jsonParser.parse(data);

        JSONArray jsonArray = (JSONArray) object;

        List<Event> events = new ArrayList<>();
        jsonArray.forEach(e -> {
            Event event = parseEventObject((JSONObject) e);
            events.add(event);
        });

        events.forEach(System.out::println);

        return events;
    }

    private Event parseEventObject(JSONObject jsonEvent) {
        Event event = new Event();

            event.setEventId(Integer.parseInt(jsonEvent.get("eventId").toString()));
            event.setEventName(jsonEvent.get("eventName").toString());
            event.setEventSize(Integer.parseInt(jsonEvent.get("eventSize").toString()));
        
            Object dateObject = jsonEvent.get("eventDate");
            Long dateLong = (Long) dateObject;
            Date date = new Date(dateLong);
    
            event.setEventDate(date);
            event.setParticipants(Integer.parseInt(jsonEvent.get("participants").toString()));
        return event;
    }
}

// "eventId": 1,
// "eventName": "Christmas Eve Party",
// "eventSize": 20,
// "eventDate": 1703415600000,
// "participants": 0