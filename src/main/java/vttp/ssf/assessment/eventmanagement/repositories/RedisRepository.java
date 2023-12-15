package vttp.ssf.assessment.eventmanagement.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;

import jakarta.annotation.Resource;
import vttp.ssf.assessment.eventmanagement.models.Event;

public class RedisRepository {

	// TODO: Task 2

	private String hashRef = "events";
	@Resource(name="redisEventTemplate")
	private HashOperations<String, String, Event> hashOps;

	public void saveRecord(Event event) {
		hashOps.put(hashRef, event.getEventId().toString(), event);
	}

	// TODO: Task 3
	public int getNumberOfEvents(){
		Map<String, Event> mapList = hashOps.entries(hashRef);
		return mapList.size();
	}

	// TODO: Task 4
	public Event getEvent(Integer index) {
		String indexString = Integer.toString(index);
		Event event = hashOps.get(hashRef, indexString);
		return event;
	}

	public List<Event> getAllEvents() {
		Map<String, Event> mapList = hashOps.entries(hashRef);
		List<Event> list = new ArrayList<Event>(mapList.values());
		return list;
	}
}
