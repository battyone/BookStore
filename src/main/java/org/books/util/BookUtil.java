package org.books.util;

import org.books.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/* DAO class for working with entities */
@Repository
@Transactional
public class BookUtil {
    private SessionFactory sessionFactory;

    @Autowired
    public BookUtil(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Saves new book in database
    public void add(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
        session.flush();
    }

    // Finds book by it's id
    public Book get(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    // Updates already existed book
    public void update(Book book) {
        Book oldOne = sessionFactory.getCurrentSession().get(Book.class, book.getId());
        oldOne.setTitle(book.getTitle());
        oldOne.setDescription(book.getDescription());
        oldOne.setPrice(book.getPrice());
        oldOne.setIsbn(book.getIsbn());
        oldOne.setPages(book.getPages());
        sessionFactory.getCurrentSession().update(oldOne);
        sessionFactory.getCurrentSession().flush();
    }

    // Removes book by it's id
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Book willDelete = this.get(id);
        session.delete(willDelete);
        session.flush();
    }

    // Gives list of all books
    @SuppressWarnings("unchecked")
    public List<Book> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Book.class).list();
    }
}
