package put.poznan.io.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import put.poznan.io.common.HibernateUtil;
import put.poznan.io.models.Group;

@ManagedBean
@RequestScoped
public class GroupsBean implements Serializable {
	
	private static final long serialVersionUID = -8138100679764684351L;
	public static Group group = new Group();

	String name;
	Session session = null;

	public GroupsBean() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	public Group getGroup() {
		return group;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void submit() {
		System.out.println("works");
		Transaction tx = session.beginTransaction();
		System.out.println("works");
			Random r = new Random();
			group.setId(r.nextInt(100) + 1);
			System.out.println(group.getField());
			System.out.println(group.getMaxlimit());
            session.save(group);
              
            session.flush();
            tx.commit();
			List<Group> groups = session.createQuery("from Groups").list();
             
            System.out.println("works");
            for (Group g : groups) {
                System.out.println("Name: "  + g.getName());
            }
	}
	
}