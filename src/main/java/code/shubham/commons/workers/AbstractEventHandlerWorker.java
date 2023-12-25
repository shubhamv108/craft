package code.shubham.commons.workers;

import code.shubham.commons.exceptions.WorkerException;
import code.shubham.commons.handlers.EventHandler;
import code.shubham.commons.models.Event;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractEventHandlerWorker extends AbstractWorker {

	@Override
	protected void process(final Event event) {
		final EventHandler eventHandler = this.applicationContext.getBean(event.getEventName() + "EventHandler",
				EventHandler.class);
		if (eventHandler == null)
			throw new WorkerException("No event handler available for event name: ", event.getEventName());
		eventHandler.handle(event);
	}

}
