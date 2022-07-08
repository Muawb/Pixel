package me.muawb.project.manager;

import me.muawb.project.gui.StartPage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import javax.persistence.Query;
import javax.swing.*;
import java.awt.*;

public class CreateFactory {

    private String cmd = "from Users where login =:param";

    public void createConnection(String name, String pass, JButton bu, JFrame jf){
        new Thread(() -> {
            SessionFactory sessionFactory = buildSessionFactory();
            Session session = sessionFactory.openSession();
            try {
                session.getTransaction().begin();

                Query query = session.createQuery(cmd);
                query.setParameter("param", name);
                Users users = (Users) query.getSingleResult();

                if (name.equals(users.getLogin()) && (pass.equals(users.getPassword()))) {
                    jf.setVisible(false);
                    StartPage page = new StartPage("Pixel");
                    page.createFrame();
                } else {
                    bu.setBackground(new Color(153, 0, 0));
                    bu.setEnabled(true);
                }

                session.getTransaction().commit();
            } catch (Exception e) {
                System.err.println(e.getCause());
            } finally {
                session.close();
            }
        })
                .start();
    }


    public static SessionFactory buildSessionFactory(){
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                configure("data/hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
        return metadata.getSessionFactoryBuilder().build();
    }
}
