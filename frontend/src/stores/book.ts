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
interface threeBooks {
  first: Book;
  second: Book;
  third: Book;
}

export const useBookStore = defineStore("Book", {
  state: () => ({
    Books: [] as threeBooks[],
    // Buffer是一个threeBooks类型的对象,初始化
    Buffer: {
      first: {
        name: "",
        category: "",
        publisher: "",
        pages: 0,
        publishYear: 0,
        BookLanguage: "",
        ISBN: "",
        source: "",
        imageUrl: "",
      },
      second: {
        name: "",
        category: "",
        publisher: "",
        pages: 0,
        publishYear: 0,
        BookLanguage: "",
        ISBN: "",
        source: "",
        imageUrl: "",
      },
      third: {
        name: "",
        category: "",
        publisher: "",
        pages: 0,
        publishYear: 0,
        BookLanguage: "",
        ISBN: "",
        source: "",
        imageUrl: "",
      },
    },
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
      // 如果Buffer为空，就把这个Book放进去
      if (this.Buffer.first.name === "") {
        this.Buffer.first = {
          name,
          category,
          publisher,
          pages,
          publishYear,
          BookLanguage,
          ISBN,
          source,
          imageUrl,
        };
      } else if (this.Buffer.second.name === "") {
        this.Buffer.second = {
          name,
          category,
          publisher,
          pages,
          publishYear,
          BookLanguage,
          ISBN,
          source,
          imageUrl,
        };
      } else if (this.Buffer.third.name === "") {
        this.Buffer.third = {
          name,
          category,
          publisher,
          pages,
          publishYear,
          BookLanguage,
          ISBN,
          source,
          imageUrl,
        };
      } else {
        this.Books.push(this.Buffer);
        this.Buffer = {
          first: {
            name,
            category,
            publisher,
            pages,
            publishYear,
            BookLanguage,
            ISBN,
            source,
            imageUrl,
          },
          second: {
            name: "",
            category: "",
            publisher: "",
            pages: 0,
            publishYear: 0,
            BookLanguage: "",
            ISBN: "",
            source: "",
            imageUrl: "",
          },
          third: {
            name: "",
            category: "",
            publisher: "",
            pages: 0,
            publishYear: 0,
            BookLanguage: "",
            ISBN: "",
            source: "",
            imageUrl: "",
          },
        };
      }
    },
    clearBooks() {
      this.Books = [];
    },
  },
});
