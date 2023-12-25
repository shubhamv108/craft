package code.shubham.commons.handlers;

import code.shubham.commons.models.Event;

public interface EventHandler {

	Object handle(Event event);

}
