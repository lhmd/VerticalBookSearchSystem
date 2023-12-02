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
let similarBooks: Book[] = [];

function loadBook(name: string) {
  // bookStore.addBook(
  //   "The Art of Computer Programming, Vol. 1: Fundamental Algorithms",
  //   "Computer Science",
  //   "Addison-Wesley Professional",
  //   672,
  //   1997,
  //   "English",
  //   "9780201896831",
  //   "https://images-na.ssl-images-amazon.com/images/I/41p1WtVbKZL._SX379_BO1,204,203,200_.jpg",
  //   "https://files.catbox.moe/03zgjn.jpg"
  // );
  // book = bookStore.Books[0];
  // name = "The Art of Computer Programming, Vol. 1: Fundamental Algorithms";

  // console.log(bookStore.Books);
  for (let i = 0; i < bookStore.Books.length; i++) {
    if (bookStore.Books[i].name === name) {
      book = bookStore.Books[i];
      break;
    }
  }
}

async function loadSimilarBooks(category: string) {
  try {
    // 测试
    // similarBooks = [
    //   {
    //     name: "The Art of Computer Programming, Vol. 2: Seminumerical Algorithms",
    //     category: "Computer Science",
    //     publisher: "Addison-Wesley Professional",
    //     pages: 784,
    //     publishYear: 1997,
    //     BookLanguage: "English",
    //     ISBN: "9780201896848",
    //     source:
    //       "https://www.amazon.com/Computer-Programming-Vol-Seminumerical-Algorithms/dp/0201896842",
    //     imageUrl:
    //       "https://files.catbox.moe/03zgjn.jpg",
    //   },
    //   {
    //     name: "The Art of Computer Programming, Vol. 3: Sorting and Searching",
    //     category: "Computer Science",
    //     publisher: "Addison-Wesley Professional",
    //     pages: 800,
    //     publishYear: 1997,
    //     BookLanguage: "English",
    //     ISBN: "9780201896855",
    //     source:
    //       "https://www.amazon.com/Computer-Programming-Vol-Sorting-Searching/dp/0201896850",
    //     imageUrl:
    //       "https://files.catbox.moe/03zgjn.jpg",
    //   },
    // ];
    // console.log(book);
    const send = {
      category: category,
    };
    const response = await axios.post("http://10.73.103.130:1212/api/interest", send);
    // console.log("后端返回的消息：", response.data);
    var isLoad = response.data.success;
    if (isLoad) {
      similarBooks = response.data.books;
      console.log(similarBooks);
    } else {
      ElMessage.error("Failed to load similar books");
    }
  } catch (error) {
    ElMessage.error("Failed to load similar books");
  }
}

function goToBook(book: Book) {
  router.push({
    path: "/unit",
    query: {
      name: book.name,
    },
  });
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
          <div class="categories">Source: {{ book.source }}</div>
        </div>
      </div>
    </div>
    <!-- 展示相似书籍 -->
    <div class="similar-books">
      <div class="text">相似书籍</div>
      <div class="content">
        <div class="similar-book" v-for="book in similarBooks" :key="book.name">
          <img
            :src="book.imageUrl"
            alt="Book Cover"
            class="book-cover"
            @click="goToBook(book)"
          />
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
  flex-direction: row;
}

.content {
  display: flex;
  flex-direction: row;
  justify-content: left;
}
</style>
