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
    clearBooks() {
      this.Books = [];
    },
  },
});
