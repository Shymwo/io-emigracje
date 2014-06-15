package put.poznan.io.beans;

import java.io.Serializable;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.service.ServiceRegistry;

import put.poznan.io.models.Message;

public class SendFormBean implements Serializable {

	private static final long serialVersionUID = -6281067095755248517L;

	private Message message;

	private Session session;

	private static SessionFactory sessionFactory = null;
	private static ServiceRegistry serviceRegistry = null;

	private static SessionFactory configureSessionFactory() throws HibernateException {
	    Configuration configuration = new Configuration();
	    configuration.configure();

	    Properties properties = configuration.getProperties();

		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);

	    return sessionFactory;
	}

	public SendFormBean() {
		configureSessionFactory();
		session = sessionFactory.openSession();
		this.message = new Message();
	}

	public void sendMail() {

		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			// Saving to the database
			Integer num = 0;
			try {
				num = ((Number) session.createCriteria("Message").setProjection(Projections.rowCount()).uniqueResult()).intValue();
			} catch (Exception e) {}
			message.setId(num+1);
			session.save(message);

			// Committing the change in the database.
			session.flush();
			tx.commit();

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Wysłano pomyślnie."));

		} catch (Exception ex) {
			ex.printStackTrace();

			tx.rollback();
		} finally{
			if(session != null) {
				session.close();
			}
		}

	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
