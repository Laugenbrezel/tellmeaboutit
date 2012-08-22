package de.tellmeaboutit.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.tellmeaboutit.model.Questionnaire;

@RequestScoped
public class QuestionnaireListProducer {
	@Inject
	private EntityManager em;

	private List<Questionnaire> questionnaires;

	// @Named provides access the return value via the EL variable name
	// "members" in the UI (e.g.,
	// Facelets or JSP view)
	@Produces
	@Named
	public List<Questionnaire> getQuestionnaires() {
		return questionnaires;
	}

	public void onQuestionnaireListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final Questionnaire questionnaire) {
		retrieveAllQuestionnairesOrderedByName();
	}

	@PostConstruct
	public void retrieveAllQuestionnairesOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Questionnaire> criteria = cb
				.createQuery(Questionnaire.class);
		Root<Questionnaire> questionnaire = criteria.from(Questionnaire.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		criteria.select(questionnaire).orderBy(
				cb.asc(questionnaire.get("name")));
		questionnaires = em.createQuery(criteria).getResultList();
	}
}
