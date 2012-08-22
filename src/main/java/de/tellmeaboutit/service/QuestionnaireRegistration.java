package de.tellmeaboutit.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.tellmeaboutit.model.Questionnaire;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class QuestionnaireRegistration {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Questionnaire> questionnaireEventSrc;

	public void create(Questionnaire questionnaire) throws Exception {
		log.info("Registering " + questionnaire.getName());
		em.persist(questionnaire);
		questionnaireEventSrc.fire(questionnaire);
	}
}
