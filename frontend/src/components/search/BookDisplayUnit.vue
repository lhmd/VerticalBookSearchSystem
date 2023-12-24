<script setup lang="ts">
import { useBookStore } from "@/stores/book";
import { onBeforeMount } from "vue";
import router from "@/router";
import axios from "axios";
import { ElMessage } from "element-plus";

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
  // console.log(bookStore.Books);
  for (let i = 0; i < bookStore.Books.length; i++) {
    if (bookStore.Books[i].name === name) {
      book = bookStore.Books[i];
      return;
    }
  }
  for (let i = 0; i < bookStore.similarBooks.length; i++) {
    if (bookStore.similarBooks[i].name === name) {
      book = bookStore.similarBooks[i];
      return;
    }
  }
}

async function loadSimilarBooks(category: string) {
  try {
    // console.log(book);
    const send = {
      category: category,
    };
    const response = await axios.post(
      "http://10.73.103.130:1212/api/interest",
      send
    );
    console.log("后端返回的消息：", response.data);
    var isLoad = response.data.success;
    if (isLoad) {
      bookStore.clearSimilarBooks();
      for (let i = 0; i < 4; i++) {
        bookStore.addSimilarBook(
          response.data.books[i].name,
          response.data.books[i].category,
          response.data.books[i].publisher,
          response.data.books[i].pages,
          response.data.books[i].publishYear,
          response.data.books[i].BookLanguage,
          response.data.books[i].isbn,
          response.data.books[i].source,
          response.data.books[i].imageUrl
        );
      }
    } else {
      ElMessage.error("加载相似书籍失败");
    }
  } catch (error) {
    ElMessage.error("加载相似书籍失败");
  }
}

function goToBook(book: Book) {
  router.push({
    path: "/unit",
    query: {
      name: book.name,
    },
  });
  loadBook(book.name as string);
  loadSimilarBooks(book.category);
}

onBeforeMount(() => {
  let name = router.currentRoute.value.query.name;
  // console.log("aa", name);
  loadBook(name as string);
  loadSimilarBooks(book.category);
});
</script>

<template>
  <div class="book-page">
    <div class="book-info">
      <div style="font-size: 2rem; font-weight: 600; margin-bottom: 15px">
        {{ book.name }}
      </div>
      <div class="content">
        <div class="imageInformation">
          <img :src="book.imageUrl" alt="Book Cover" class="book-cover" />
        </div>
        <div class="text-information">
          <div class="categories">categories: {{ book.category }}</div>
          <div class="categories">Publisher: {{ book.publisher }}</div>
          <div class="categories">Pages: {{ book.pages }}</div>
          <div class="categories">Published Year: {{ book.publishYear }}</div>
          <div class="categories">Language: {{ book.BookLanguage }}</div>
          <div class="categories">ISBN: {{ book.ISBN }}</div>
          <div class="categories">
            <a :href="book.source" target="_blank">Source</a>
          </div>
        </div>
      </div>
    </div>
    <!-- 展示相似书籍 -->
    <div class="similar-books">
      <div class="text">相似书籍</div>
      <div class="content">
        <div class="similar-book" v-for="book in bookStore.similarBooks" :key="book.name">
          <img
            :src="book.imageUrl"
            alt="Book Cover"
            class="book-cover"
            @click="goToBook(book)"
          />
          <!-- <div class="similar-book-name">{{ book.name }}</div> -->
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.text {
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 15px;
  color: cadetblue;
  border-bottom: 1px solid;
  line-height: 50px;
}

.book-page {
  padding: 0;
  margin: auto;
}

.book-info {
  display: flex;
  flex-direction: column;
  border-radius: 5px;
  margin: 15px 0;
}

.content {
  display: flex;
  width: 100%;
  justify-content: center;
}

.text-information {
  /* flex: 1; */
  margin-left: 29px;
}

.book-info h2 {
  font-size: 24px;
  margin-bottom: 10px;
}

.categories {
  font-size: 1rem;
  margin-bottom: 18px;
}

.imageInformation {
  flex: 0.8;
  display: flex;
  justify-content: end;
}

.book-cover {
  width: 200px;
  height: 250px;
  object-fit: cover;
  border: 1px solid #ccc;
  border-radius: 5px;
  display: flex;
  margin-top: 2vh;
  margin-left: 1vw;
  margin-right: 1vw;
  margin-bottom: 1vw;
  flex-direction: row;
}

.content {
  display: flex;
  flex-direction: row;
  justify-content: left;
}
</style>
