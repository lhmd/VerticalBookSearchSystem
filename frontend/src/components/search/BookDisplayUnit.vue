<template>
  <!-- 展示书中具体内容 -->
  <div class="book-info">
    <h2>{{ book.name }}</h2>
    <div class="content">
      <div class="imageInformation">
        <img :src="book.imageUrl" class="book-cover" />
      </div>
      <div class="text-information">
        <div class="categories">Category: {{ book.category }}</div>
        <div class="publisher">Publisher: {{ book.publisher }}</div>
        <div class="pages">Pages: {{ book.pages }}</div>
        <div class="publish-year">Publish Year: {{ book.publishYear }}</div>
        <div class="language">Language: {{ book.BookLanguage }}</div>
        <div class="categories">ISBN: {{ book.ISBN }}</div>
        <div class="categories">Source: {{ book.source }}</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useBookStore } from "@/stores/book";
import { onBeforeMount } from "vue";
import router from "@/router";

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
const bookStore = useBookStore();
let book: Book;

function loadBook(name: string) {
  // 测试
  bookStore.addBook(
    "The Art of Computer Programming, Vol. 1: Fundamental Algorithms",
    "Computer Science",
    "Addison-Wesley Professional",
    672,
    1997,
    "English",
    "9780201896831",
    "https://images-na.ssl-images-amazon.com/images/I/41p1WtVbKZL._SX379_BO1,204,203,200_.jpg",
    "https://files.catbox.moe/03zgjn.jpg"
  )
  name = "The Art of Computer Programming, Vol. 1: Fundamental Algorithms";

  // console.log(bookStore.Books);
  for (let i = 0; i < bookStore.Books.length; i++) {
    if (bookStore.Books[i].name === name) {
      book = bookStore.Books[i];
      break;
    }
  }
  console.log(book);
}

onBeforeMount(() => {
  let name = router.currentRoute.value.query.name;
  // console.log("aa", name);
  loadBook(name as string);
});
</script>

<style scoped>
.book-info {
  display: flex;
  flex-direction: column;
  border-radius: 5px;
  padding: 10px;
  margin: 15px 0;
}
.content {
  display: flex;
}
.text-information {
  flex: 1;
  margin-left: 50px;
}

.book-info h2 {
  font-size: 24px;
  margin-bottom: 10px;
}

.categories {
  font-size: 1.6rem;
  margin-bottom: 20px;
}

.publisher,
.pages,
.publish-year,
.language,
.isbn,
.source {
  font-size: 14px;
  margin-bottom: 5px;
}

.imageInformation {
  flex: 1;
  display: flex;
  justify-content: center;
}

.book-cover {
  width: 350px;
  height: 500px;
  object-fit: cover;
  border: 1px solid #ccc;
  border-radius: 5px;
}
</style>
