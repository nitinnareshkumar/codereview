package com.review.code.RapidDev.event;

import com.review.code.RapidDev.dto.ProjectSetupData;
import org.springframework.context.ApplicationEvent;

public class CreateProjectSetupEvent  extends ApplicationEvent
{
	private ProjectSetupData projectSetupData;

	/**
	 * Create a new {@code ApplicationEvent}.
	 *
	 * @param source the object on which the event initially occurred or with
	 *               which the event is associated (never {@code null})
	 */
	public CreateProjectSetupEvent(final Object source, final ProjectSetupData projectSetupData)
	{
		super(source);
		this.projectSetupData = projectSetupData;
	}

	public ProjectSetupData getProjectSetupData()
	{
		return projectSetupData;
	}

	public void setProjectSetupData(ProjectSetupData projectSetupData)
	{
		this.projectSetupData = projectSetupData;
	}
}
