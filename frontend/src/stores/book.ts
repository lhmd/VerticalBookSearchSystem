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
    updateBook(name: string, BookInfo: Partial<Book>) {
      const BookIndex = this.Books.findIndex((d) => d.name === name);
      if (BookIndex !== -1) {
        this.Books[BookIndex] = {
          ...this.Books[BookIndex],
          ...BookInfo,
        };
      }
    },
    removeBook(name: string) {
      const BookIndex = this.Books.findIndex((d) => d.name === name);
      if (BookIndex !== -1) {
        this.Books.splice(BookIndex, 1);
      }
    },
    clearBooks() {
      this.Books = [];
    },
  },
});
