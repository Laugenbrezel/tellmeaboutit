package de.tellmeaboutit.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.tellmeaboutit.model.Questionnaire;
import de.tellmeaboutit.service.QuestionnaireRegistration;

@Model
public class QuestionnaireController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private QuestionnaireRegistration questionnaireRegistration;

	private Questionnaire newQuestionnaire;

	@Produces
	@Named
	public Questionnaire getNewQuestionnaire() {
		return newQuestionnaire;
	}

	public void create() throws Exception {
		questionnaireRegistration.create(newQuestionnaire);
		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Created!",
				"Registration successful"));
		initNewQuestionnaire();
	}

	@PostConstruct
	public void initNewQuestionnaire() {
		newQuestionnaire = new Questionnaire();
	}
}
