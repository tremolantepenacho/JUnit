package org.cefire.library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


public class BookCollectionTest {
    @Test
    public void shouldFindABookByISBN() {
        final String isbnToLocate = "un-isbn-2";
        BookCollection books = new BookCollection(new Book[]{
                new Book("un-isbn-1", "un titulo 1", "un autor 1"),
                new Book(isbnToLocate, "un titulo 2", "un autor 2"),
                new Book(isbnToLocate, "un titulo 2", "un autor 2"),
                new Book("un-isbn-3", "un titulo 3", "un autor 3"),
        });
        books.find(isbnToLocate).forEach((Book book) ->
                Assertions.assertEquals(book.getISBN(), isbnToLocate));
    }
    @Test
    public void shouldFindBookByTitle(){
        final String titleToLocate="título";
        BookCollection books =new BookCollection(new Book[]{
                new Book("un-isbn-1", titleToLocate, "un autor 1"),
                new Book("un-isbn-2"," "+titleToLocate, "un autor 2"),
                new Book("un-isbn-3",titleToLocate+" ", "un autor 2"),
                new Book("un-isbn-4", "un titulo 3", "un autor 3"),
        });

        books.find(titleToLocate).forEach((Book book) ->
                Assertions.assertTrue(book.getTitle().contains(titleToLocate)));
    }

    @Test
    public void shouldFindNothingEmptyCollection(){
        Book[] nothing={};
        final String titleToLocate = "";
        BookCollection empty=new BookCollection(nothing);
        Assertions.assertTrue(empty.find(titleToLocate).isEmpty());
    }

    @Test
    public void shouldFindCopiesOfABook() {
        final Book bookToBeFound = new Book("libro-que-debe-encontrase", "un titulo 1", "un autor 1");
                BookCollection books = new BookCollection(new Book[]{
                new Book("libro-que-debe-encontrase", "un titulo 1", "un autor 1"),
                new Book("libro-que-debe-encontrase", "un titulo 1", "un autor 1"),
                new Book("un-isbn-2", "un titulo 2", "un autor 2"),
                new Book("un-isbn-3", "un titulo 3", "un autor 3"),
            });
        List<Book> foundBooks = books.findCopies(bookToBeFound);
        Assertions.assertFalse(foundBooks.isEmpty());
        foundBooks.forEach((Book book) -> Assertions.assertEquals(book, bookToBeFound));

        }

    @Test
    public void shouldGetExceptionWhenUsingFindOrFailWithANonExistentEntry() {
        final String isbnToLocate = "un-texto-que-no-existe-como-isbn-o-author";
        BookCollection books = new BookCollection(new Book[]{
                new Book("un-isbn-1", "un titulo 1", "un autor 1"),
                new Book("un-isbn-2", "un titulo 2", "un autor 2"),
                new Book("un-isbn-3", "un titulo 3", "un autor 3"),
        });
        Assertions.assertThrows(BookCollection.ExpectedToFindAtLeastABook.class, () -> books.findOrFail(isbnToLocate));
        }
    }
