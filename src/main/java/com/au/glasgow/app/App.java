package com.au.glasgow.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class App
{
    public static void main(String[] args)
    {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("Done");
    }
}