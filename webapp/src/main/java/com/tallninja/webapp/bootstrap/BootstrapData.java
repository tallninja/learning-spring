package com.tallninja.webapp.bootstrap;

import com.tallninja.webapp.domain.Author;
import com.tallninja.webapp.domain.Book;
import com.tallninja.webapp.domain.Publisher;
import com.tallninja.webapp.repositories.AuthorRepository;
import com.tallninja.webapp.repositories.BookRepository;
import com.tallninja.webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author author1 = new Author();
        author1.setFirstName("Ernest");
        author1.setLastName("Wambua");

        Author author2 = new Author();
        author2.setFirstName("George");
        author2.setLastName("Orwell");

        Book book1 = new Book();
        book1.setTitle("To Kill A Mocking Bird");
        book1.setIsbn("0446310786");

        Book book2 = new Book();
        book2.setTitle("1984");
        book2.setIsbn("0446310787");

        Publisher publisher = new Publisher();
        publisher.setName("Pearson Publishers");

        // persist the data to our database
        Book newBook1 = bookRepository.save(book1);
        Author newAuthor1 = authorRepository.save(author1);
        Book newBook2 = bookRepository.save(book2);
        Author newAuthor2 = authorRepository.save(author2);
        Publisher newPublisher = publisherRepository.save(publisher);

        newAuthor1.getBooks().add(newBook1);
        newAuthor2.getBooks().add(newBook2);
        newBook1.getAuthors().add(author1);
        newBook2.getAuthors().add(author2);
        newPublisher.getBooks().add(newBook1);
        newPublisher.getBooks().add(newBook2);
        newBook1.setPublisher(publisher);
        newBook2.setPublisher(publisher);

        // persist the associations
        authorRepository.save(newAuthor1);
        authorRepository.save(newAuthor2);
        bookRepository.save(newBook1);
        bookRepository.save(newBook2);
        publisherRepository.save(publisher);

        System.out.println("Bootstrapping:");
        System.out.println("Number of books: " + bookRepository.count());
        System.out.println("Number of authors: " + authorRepository.count());
        System.out.println("Number of publishers: " + publisherRepository.count());
    }
}
