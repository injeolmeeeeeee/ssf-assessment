package vttp.ssf.assessment.eventmanagement.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import vttp.ssf.assessment.eventmanagement.models.Event;

// @SuppressWarnings("unchecked")
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
        // System.out.println("jsonArray size: " + jsonArray.size());
        System.out.println("jsonArray list of objects: " + jsonArray);

        List<Event> events = new ArrayList<>();
        jsonArray.forEach((event -> {
            Event event1 = parseEventObject((JSONObject) event);
            events.add(event1);
        }));

        return events;
    }

    private Event parseEventObject(JSONObject jsonEvent) {
        Event event = new Event();

        JSONObject jsonEventObject = (JSONObject) jsonEvent.get("event");
        System.out.println(jsonEventObject);

        // "eventId": 1,
        // "eventName": "Christmas Eve Party",
        // "eventSize": 20,
        // "eventDate": 1703415600000,
        // "participants": 0

        event.setEventId(Integer.parseInt(jsonEventObject.get("eventId").toString()));
        event.setEventName(jsonEventObject.get("eventName").toString());
        event.setEventSize(Integer.parseInt(jsonEventObject.get("eventSize").toString()));
        // event.setEventDate(Integer.parseInt(jsonEventObject.get("eventDate").toString()));

        long milliseconds = (long) jsonEventObject.get("eventDate");
        Instant instant = Instant.ofEpochMilli(milliseconds);
        Date date = Date.from(instant);
        event.setEventDate(date);
        event.setParticipants(Integer.parseInt(jsonEventObject.get("participants").toString()));

        return event;
    }
}