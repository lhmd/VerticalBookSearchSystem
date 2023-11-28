<script setup lang="ts">
import { Search } from "@element-plus/icons-vue";
import { ref, onMounted, onBeforeUnmount, reactive } from "vue";
import { useUserStore } from "@/stores/user";
import { useBookStore } from "@/stores/book";
import { ElMessage } from "element-plus";
import router from "@/router";
import axios from "axios";

let category = ref([]);
let title = ref("推荐书籍");

const userStore = useUserStore();
const bookStore = useBookStore();

const bookSearch = reactive({
  name: "",
  category: "",
  publisher: "",
  pages: "",
  startYear: "",
  endYear: "",
  BookLanguage: "",
  ISBN: "",
  source: "",
  imageUrl: "",
  isFuzzy: false,
});

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
// 数组，每次只存三个书籍
interface threeBooks {
  first: Book;
  second: Book;
  third: Book;
}
let Books: threeBooks[] = [];

let selectAllCategory = ref(false);

// 设置Books
const setBooks = (books: Book[]) => {
  Books = [];
  for (var i = 0; i < books.length; i += 3) {
    Books.push({
      first: books[i],
      second: books[i + 1],
      third: books[i + 2],
    });
  }
};

// 提交
const submitSearch = () => {
  // 测试
  bookStore.clearBooks();
    for (var i = 0; i < 10; i++) {
      bookStore.addBook(
        "测试书籍",
        "测试分类",
        "测试出版社",
        12,
        1999,
        "测试语言",
        "测试ISBN",
        "测试来源",
        "https://files.catbox.moe/03zgjn.jpg"
      );
    }
    setBooks(bookStore.Books);
    title.value = "搜索结果";
  // axios
  //   .post("http://localhost:6034/search", bookSearch)
  //   .then((response) => {
  //     // console.log("后端返回的消息：", response.data);
  //     var isSearch = response.data.success;
  //     if (isSearch) {
  //       // console.log(response.data.books);
  //       bookStore.clearBooks();
  //       for (var i = 0; i < response.data.books.length; i++) {
  //         bookStore.addBook(
  //           response.data.books[i].name,
  //           response.data.books[i].category,
  //           response.data.books[i].publisher,
  //           response.data.books[i].pages,
  //           response.data.books[i].publishYear,
  //           response.data.books[i].BookLanguage,
  //           response.data.books[i].ISBN,
  //           response.data.books[i].source,
  //           response.data.books[i].imageUrl
  //         );
  //       }
  //       setBooks(response.data.books);
  //       title.value = "搜索结果";
  //     } else {
  //       ElMessage.error("搜索失败"); // Use ElMessage for error message
  //     }
  //   })
  //   .catch((error) => {
  //     ElMessage.error("搜索失败");
  //     console.error("请求出错：", error);
  //   });
};

// 加载推荐书籍
async function loadBook() {
  try {
    // 测试
    bookStore.clearBooks();
    for (var i = 0; i < 10; i++) {
      bookStore.addBook(
        "测试书籍",
        "测试分类",
        "测试出版社",
        12,
        1999,
        "测试语言",
        "测试ISBN",
        "测试来源",
        "https://files.catbox.moe/03zgjn.jpg"
      );
    }
    setBooks(bookStore.Books);
    // const send = {
    //   interest: userStore.interest,
    // };
    // const response = await axios.post("http://localhost:6034/interest", send);
    // // console.log("后端返回的消息：", response.data);
    // var isLoad = response.data.success;
    // if (isLoad) {
    //   // console.log(response.data.books);
    //   const bookStore = useBookStore();
    //   for (var i = 0; i < response.data.books.length; i++) {
    //     bookStore.addBook(
    //       response.data.books[i].name,
    //       response.data.books[i].category,
    //       response.data.books[i].publisher,
    //       response.data.books[i].pages,
    //       response.data.books[i].publishYear,
    //       response.data.books[i].BookLanguage,
    //       response.data.books[i].ISBN,
    //       response.data.books[i].source,
    //       response.data.books[i].imageUrl
    //     );
    //   }
    //   setBooks(response.data.books);
    // } else {
    //   ElMessage.error("加载书籍失败"); // Use ElMessage for error message
    // }
  } catch (error) {
    ElMessage.error("加载书籍失败");
  }
}

async function loadCategory() {
  try {
    const response = await axios.get("http://localhost:6034/category");
    // console.log("后端返回的消息：", response.data);
    var isLoad = response.data.success;
    if (isLoad) {
      // console.log(response.data.category);
      const bookStore = useBookStore();
      category.value = response.data.category;
    } else {
      ElMessage.error("加载分类失败"); // Use ElMessage for error message
    }
  } catch (error) {
    ElMessage.error("加载分类失败");
  }
}

onMounted(() => {
  loadCategory();
  loadBook();
});
</script>

<template>
  <el-form :model="bookSearch" label-width="120px" class="sidebar">
    <el-form-item label="书籍类别：">
      <el-checkbox-group v-model="bookSearch.category">
        <!-- 全选，如果选中，后面的选项都选中 -->
        <el-checkbox
          label="全部"
          @change="selectAllCategory = !selectAllCategory"
        />
        <el-checkbox
          v-for="item in category"
          :label="item"
          :key="item"
          v-model="bookSearch.category"
          :disabled="selectAllCategory"
        />
      </el-checkbox-group>
    </el-form-item>
    <el-form-item label="书籍名称：">
      <el-input v-model="bookSearch.name"></el-input>
    </el-form-item>
    <el-form-item label="出版社：">
      <el-input v-model="bookSearch.publisher"></el-input>
    </el-form-item>
    <el-form-item label="页数：">
      <el-input v-model="bookSearch.pages"></el-input>
    </el-form-item>
    <el-form-item label="语言：">
      <el-input v-model="bookSearch.BookLanguage"></el-input>
    </el-form-item>
    <el-form-item label="ISBN：">
      <el-input v-model="bookSearch.ISBN"></el-input>
    </el-form-item>
    <el-form-item label="来源：">
      <el-input v-model="bookSearch.source"></el-input>
    </el-form-item>
    <el-form-item label="出版日期：">
      <el-row :span="11">
        <el-form-item prop="date1">
          <el-date-picker
            v-model="bookSearch.startYear"
            type="date"
            label="Pick a date"
            placeholder="Start time"
            style="width: 100%"
          />
        </el-form-item>
      </el-row>
      <el-row :span="11">
        <el-form-item prop="date2">
          <el-date-picker
            v-model="bookSearch.endYear"
            type="date"
            label="Pick a date"
            placeholder="End time"
            style="width: 100%"
          />
        </el-form-item>
      </el-row>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitSearch">提交</el-button>
    </el-form-item>
  </el-form>

  <div class="books">
    <h2>{{ title }}</h2>
    <!-- 将Books中的内容展示出来 -->
    <el-col>
      <el-row class="book" v-for="book in Books" :key="book.first.name">
        <el-card shadow="hover" style="width: 200px; margin: 10px">
          <img
            :src="book.first.imageUrl"
            class="image"
            style="width: 160px; height: 200px"
          />
          <div style="padding: 14px">
            <span>{{ book.first.name }}</span>
            <div class="bottom clearfix">
              <time class="time">{{ book.first.publishYear }}</time>
              <el-button type="primary" size="mini"> 加入购物车 </el-button>
            </div>
          </div>
        </el-card>
        <el-card shadow="hover" style="width: 200px; margin: 10px">
          <img
            :src="book.second.imageUrl"
            class="image"
            style="width: 160px; height: 200px"
          />
          <div style="padding: 14px">
            <span>{{ book.second.name }}</span>
            <div class="bottom clearfix">
              <time class="time">{{ book.second.publishYear }}</time>
              <el-button type="primary" size="mini"> 加入购物车 </el-button>
            </div>
          </div>
        </el-card>
        <el-card shadow="hover" style="width: 200px; margin: 10px">
          <img
            :src="book.third.imageUrl"
            class="image"
            style="width: 160px; height: 200px"
          />
          <div style="padding: 14px">
            <span>{{ book.third.name }}</span>
            <div class="bottom clearfix">
              <time class="time">{{ book.third.publishYear }}</time>
              <el-button type="primary" size="mini"> 加入购物车 </el-button>
            </div>
          </div>
        </el-card>
      </el-row>
    </el-col>
  </div>
</template>

<style>
.sidebar {
  width: 35vw;
  height: 100vh;
  background-color: #b3b6b9;
}

.books {
  display: flex;
  margin-left: 2vw;
}
</style>
