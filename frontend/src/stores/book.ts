import { th } from "element-plus/es/locales.mjs";
import { defineStore } from "pinia";
import { ref, computed } from "vue";

interface Book {
  name: string;
  category: string;
  publisher: string;
  pages: number;
  publishYear: number;
  BookLanguage: string;
  ISBN: string;
  source: string;
  imageUrl: string;
}

export const useBookStore = defineStore("Book", {
  state: () => ({
    Books: [] as Book[],
    similarBooks: [] as Book[],
  }),
  actions: {
    addBook(
      name: string,
      category: string,
      publisher: string,
      pages: number,
      publishYear: number,
      BookLanguage: string,
      ISBN: string,
      source: string,
      imageUrl: string
    ) {
      this.Books.push({
        name,
        category,
        publisher,
        pages,
        publishYear,
        BookLanguage,
        ISBN,
        source,
        imageUrl,
      });
    },
    addSimilarBook(
      name: string,
      category: string,
      publisher: string,
      pages: number,
      publishYear: number,
      BookLanguage: string,
      ISBN: string,
      source: string,
      imageUrl: string
    ) {
      this.similarBooks.push({
        name,
        category,
        publisher,
        pages,
        publishYear,
        BookLanguage,
        ISBN,
        source,
        imageUrl,
      });
    },
    clearBooks() {
      this.Books = [];
    },
    clearSimilarBooks() {
      this.similarBooks = [];
    },
    // 去除重复的书籍
    removeDuplicateBooks() {
      const newBooks = this.Books.filter(
        (book, index, self) =>
          index ===
          self.findIndex(
            (t) =>
              t.name === book.name &&
              t.category === book.category &&
              t.publisher === book.publisher &&
              t.pages === book.pages &&
              t.publishYear === book.publishYear &&
              t.BookLanguage === book.BookLanguage &&
              t.ISBN === book.ISBN &&
              t.source === book.source &&
              t.imageUrl === book.imageUrl
          )
      );
      this.Books = newBooks;
    },
  },
});
