package code.shubham.commons;

import code.shubham.commons.models.Event;

public class CommonTestEventUtils {

	public static Event getEmptyEvent() {
		return Event.builder().eventName("name").eventType("type").build();
	}

}
