package org.books.util;

import org.books.config.ApplicationConfig;
import org.books.config.HibernateConfig;
import org.books.config.RootConfig;
import org.books.config.application.WebConfig;
import org.books.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationConfig.class, RootConfig.class, WebConfig.class, HibernateConfig.class})
public class BookUtilTest {

    @Autowired
    private BookUtil bookUtil;

    @Test
    public void equalsTest() {
        Book first = new Book("Thinking in Java",10.1f,"desc","jjy",1100);
        Book same = new Book(
                first.getTitle(),
                first.getPrice(),
                first.getDescription(),
                first.getIsbn(),
                first.getPages());
        Book another = new Book("Spring in Action", 15.2f, "description","rryhh",600);

        assertEquals(first, same);
        assertNotEquals(first, another);
    }

    @Test
    @Rollback
    @Transactional
    public void saveAndLoadTest() {
        Book original = new Book("Thinking in Java",10.1f,"desc","jjy",1100);
        bookUtil.add(original);

        Book obtained = bookUtil.get(original.getId());
        assertEquals(original, obtained);
    }

    @Test
    @Rollback
    @Transactional
    public void updateTest() {
        Book original = new Book("Thinking in Java",10.1f,"desc","jjy",1100);
        Book same = new Book(
                original.getTitle(),
                original.getPrice(),
                original.getDescription(),
                original.getIsbn(),
                original.getPages());
        Book another = new Book("Spring in Action", 15.2f, "description","rryhh",600);

        bookUtil.add(original);
        another.setId(original.getId());
        bookUtil.update(another);

        same.setId(another.getId());
        assertEquals(another, bookUtil.get(original.getId()));
        assertNotEquals(same, bookUtil.get(original.getId()));
    }

    @Test
    @Rollback
    @Transactional
    public void deleteTest() {
        Book book = new Book("Thinking in Java",10.1f,"desc","jjy",1100);
        bookUtil.add(book);

        bookUtil.delete(book.getId());
        Book removed = bookUtil.get(book.getId());
        assertNull(removed);
    }

    @Test
    @Rollback
    @Transactional
    public void gettingAllTest() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Thinking in Java",10.1f,"desc","jjy",1100));
        books.add(new Book("Head First HTML5",18.3f,"html","jjy",800));
        books.add(new Book("Spring in Action", 15.2f, "description","rryhh",600));

        for (Book book : books) {
            bookUtil.add(book);
        }

        List<Book> obtainedBooks = bookUtil.getAll();
        for (Book book : obtainedBooks) {
            if (!books.contains(book)) fail();
        }
    }
}