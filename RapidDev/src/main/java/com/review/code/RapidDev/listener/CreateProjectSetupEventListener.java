package com.review.code.RapidDev.listener;

import com.review.code.RapidDev.event.CreateProjectSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CreateProjectSetupEventListener  implements ApplicationListener<CreateProjectSetupEvent>
{
	private Logger LOG = LoggerFactory.getLogger(CreateProjectSetupEventListener.class);

	@Autowired
	protected ProjectSetupService projectSetupService;

	/**
	 * Handle an application event.
	 *
	 * @param event the event to respond to
	 */
	@Override
	public void onApplicationEvent(final CreateProjectSetupEvent event)
	{
		LOG.info("Event received to create project setup");

		final boolean projectSetupStatus = projectSetupService.createProjectSetup(event.getProjectSetupData());
		if(!projectSetupStatus) {
			LOG.error("Project setup failed");
		}

	}
}
