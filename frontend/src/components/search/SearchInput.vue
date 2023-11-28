<script setup lang="ts">
import { Search } from "@element-plus/icons-vue";
import { ref, onMounted, onBeforeUnmount, reactive } from "vue";
import { useBookStore } from "@/stores/book";
import { ElMessage } from "element-plus";
import router from "@/router";
import axios from "axios";

let category = ref([]);
let title = ref("推荐书籍");

const bookStore = useBookStore();

const bookSearch = reactive({
  name: "",
  category: [],
  publisher: "",
  pages: "",
  publishYear: "",
  bookLanguage: "",
  isbn: "",
  source: "",
  imageUrl: "",
  isFuzzy: false,
});


const checkAll = ref(false);
const isIndeterminate = ref(true);

// // 设置Books
// interface Book {
//   name: string;
//   category: string;
//   publisher: string;
//   pages: number;
//   publishYear: number;
//   BookLanguage: string;
//   ISBN: string;
//   source: string;
//   imageUrl: string;
// }
// // 数组，每次只存三个书籍
// interface threeBooks {
//   first: Book;
//   second: Book;
//   third: Book;
// }
// let Books: threeBooks[] = [];
// const setBooks = (books: Book[]) => {
//   Books = [];
//   for (var i = 0; i < books.length; i += 3) {
//     Books.push({
//       first: books[i],
//       second: books[i + 1],
//       third: books[i + 2],
//     });
//   }
// };

// 提交
const submitSearch = () => {
  // 测试
  console.log(bookSearch);
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
  console.log(bookStore.Books);
  title.value = "推荐书籍";
  // try {
  //   const send = {
  //     interest: userStore.interest,
  //   };
  //   const response = await axios.post("http://localhost:6034/interest", send);
  //   // console.log("后端返回的消息：", response.data);
  //   var isLoad = response.data.success;
  //   if (isLoad) {
  //     // console.log(response.data.books);
  //     const bookStore = useBookStore();
  //     for (var i = 0; i < response.data.books.length; i++) {
  //       bookStore.addBook(
  //         response.data.books[i].name,
  //         response.data.books[i].category,
  //         response.data.books[i].publisher,
  //         response.data.books[i].pages,
  //         response.data.books[i].publishYear,
  //         response.data.books[i].BookLanguage,
  //         response.data.books[i].ISBN,
  //         response.data.books[i].source,
  //         response.data.books[i].imageUrl
  //       );
  //     }
  //     setBooks(response.data.books);
  //   } else {
  //     ElMessage.error("加载书籍失败"); // Use ElMessage for error message
  //   }
  // } catch (error) {
  //   ElMessage.error("加载书籍失败");
  // }
}

async function loadCategory() {
  // 测试
  for (var i = 0; i < 4; i++) {
    category.value.push("测试分类" + i);
  }

  // try {
  //   const response = await axios.get("http://localhost:6034/category");
  //   // console.log("后端返回的消息：", response.data);
  //   var isLoad = response.data.success;
  //   if (isLoad) {
  //     // console.log(response.data.category);
  //     const bookStore = useBookStore();
  //     category.value = response.data.category;
  //   } else {
  //     ElMessage.error("加载分类失败"); // Use ElMessage for error message
  //   }
  // } catch (error) {
  //   ElMessage.error("加载分类失败");
  // }
}

onMounted(() => {
  loadBook();
  loadCategory();
});

const handleCheckAllChange = (val: boolean) => {
  bookSearch.category = val ? category.value : [];
  isIndeterminate.value = false;
};
const handleCheckedChange = (value: string[]) => {
  const checkedCount = value.length;
  checkAll.value = checkedCount === category.value.length;
  isIndeterminate.value =
    checkedCount > 0 && checkedCount < category.value.length;
};

const shortcuts = [
  {
    text: 'Last week',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
      return [start, end]
    },
  },
  {
    text: 'Last month',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      return [start, end]
    },
  },
  {
    text: 'Last 3 months',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
      return [start, end]
    },
  },
]

function goToBook(name: string) {
  router.push({
    name: "unit",
    params: {
      name: name,
    },
  });
}
</script>

<template>
  <el-form :model="bookSearch" label-width="120px" class="sidebar">
    <el-form-item label="书籍类别：">
      <el-checkbox
        v-model="checkAll"
        :indeterminate="isIndeterminate"
        @change="handleCheckAllChange"
        >全选</el-checkbox
      >
      <el-checkbox-group
        v-model="bookSearch.category"
        @change="handleCheckedChange"
      >
        <el-checkbox v-for="item in category" :label="item" :key="item">{{
          item
        }}</el-checkbox>
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
      <el-input v-model="bookSearch.bookLanguage"></el-input>
    </el-form-item>
    <el-form-item label="ISBN：">
      <el-input v-model="bookSearch.isbn"></el-input>
    </el-form-item>
    <el-form-item label="来源：">
      <el-input v-model="bookSearch.source"></el-input>
    </el-form-item>
    <el-form-item label="出版日期：">
      <el-date-picker
        v-model="bookSearch.publishYear"
        type="daterange"
        unlink-panels
        range-separator="To"
        start-placeholder="Start date"
        end-placeholder="End date"
        :shortcuts="shortcuts"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitSearch">提交</el-button>
    </el-form-item>
  </el-form>

  <div class="books">
    <h2>{{ title }}</h2>
    <!-- 将bookStore中的内容展示出来 -->
    <el-col>
      <el-row class="book" v-for="book in bookStore.Books" :key="book.first.name">
        <el-card shadow="hover" style="width: 200px; margin: 10px">
          <img
            :src="book.first.imageUrl"
            class="image"
            style="width: 160px; height: 200px"
          />
          <div style="padding: 14px">
            <span>{{ book.first.name }}</span>
            <div class="bottom clearfix">
              <time class="time">出版日期：{{ book.first.publishYear }}</time>
              <el-button type="primary" size="mini" @click="goToBook(book.first.name)"> 查看详情 </el-button>
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
              <time class="time">出版日期：{{ book.second.publishYear }}</time>
              <el-button type="primary" size="mini" @click="goToBook(book.second.name)"> 查看详情 </el-button>
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
              <time class="time">出版日期：{{ book.third.publishYear }}</time>
              <el-button type="primary" size="mini" @click="goToBook(book.third.name)"> 查看详情 </el-button>
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
