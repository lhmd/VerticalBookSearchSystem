<script setup lang="ts">
import { Search } from "@element-plus/icons-vue";
import { ref, onBeforeMount, reactive } from "vue";
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
  category: [] as string[],
  publisher: "",
  pages: 0,
  publishYear: "",
  bookLanguage: "",
  isbn: "",
  source: "",
  imageUrl: "",
  isFuzzy: [false, false, false, false], // 是否模糊查询，0-5分别对应书籍名称、出版社、页数、来源
});

const checkAll = ref(false);
const isIndeterminate = ref(true);

// 提交
const submitSearch = () => {
  // 测试
  // console.log(bookSearch);
  // bookStore.clearBooks();
  // for (var i = 0; i < 10; i++) {
  //   bookStore.addBook(
  //     "测试书籍",
  //     "测试分类",
  //     "测试出版社",
  //     12,
  //     1999,
  //     "测试语言",
  //     "测试ISBN",
  //     "测试来源",
  //     "https://files.catbox.moe/03zgjn.jpg"
  //   );
  // }
  // title.value = "搜索结果";

  // 提取publishYear的开始和结束年份，格式为Tue Dec 12 2023 00:00:00 GMT+0800，将年份转换为整数
  var publishYear = [];
  // console.log(bookSearch.publishYear[0].toString().split(" ")[3]);
  // 提取以空格分隔的字符串，取第四个元素，即年份
  if (bookSearch.publishYear[0] != null) {
    publishYear.push(parseInt(bookSearch.publishYear[0].toString().split(" ")[3]));
  } else {
    publishYear.push(0);
  }
  if (bookSearch.publishYear[1] != null) {
    publishYear.push(parseInt(bookSearch.publishYear[1].toString().split(" ")[3]));
  } else {
    publishYear.push(9999);
  }
  // console.log(publishYearStart);

  const send = {
    name: bookSearch.name,
    category: bookSearch.category,
    publisher: bookSearch.publisher,
    pages: bookSearch.pages,
    publishYear: publishYear,
    bookLanguage: bookSearch.bookLanguage,
    isbn: bookSearch.isbn,
    source: bookSearch.source,
    isFuzzy: bookSearch.isFuzzy,
  };
  // console.log(send);
  axios
    .post("http://10.73.103.130:1212/api/search", send)
    .then((response) => {
      // console.log("后端返回的消息：", response.data);
      var isSearch = response.data.success;
      if (isSearch) {
        // console.log(response.data.books);
        bookStore.clearBooks();
        for (var i = 0; i < response.data.books.length; i++) {
          bookStore.addBook(
            response.data.books[i].name,
            response.data.books[i].category,
            response.data.books[i].publisher,
            response.data.books[i].pages,
            response.data.books[i].publishYear,
            response.data.books[i].BookLanguage,
            response.data.books[i].ISBN,
            response.data.books[i].source,
            response.data.books[i].imageUrl
          );
        }
        title.value = "搜索结果";
        // 将isFuzzy转换为字符串
        var isFuzzyString = "";
        for (var i = 0; i < bookSearch.isFuzzy.length; i++) {
          if (bookSearch.isFuzzy[i]) {
            isFuzzyString += "1";
          } else {
            isFuzzyString += "0";
          }
        }
        // 将category转换为字符串
        var categoryString = "";
        for (var i = 0; i < bookSearch.category.length; i++) {
          categoryString += bookSearch.category[i];
          if (i != bookSearch.category.length - 1) {
            categoryString += ",";
          }
        }
        router.push({
          name: "search",
          query: {
            name: bookSearch.name,
            category: bookSearch.category,
            publisher: bookSearch.publisher,
            pages: bookSearch.pages,
            publishYear: bookSearch.publishYear,
            bookLanguage: bookSearch.bookLanguage,
            isbn: bookSearch.isbn,
            source: bookSearch.source,
            isFuzzy: isFuzzyString,
          },
        });
      } else {
        ElMessage.error("搜索失败"); // Use ElMessage for error message
      }
    })
    .catch((error) => {
      ElMessage.error("搜索失败");
      console.error("请求出错：", error);
    });
};

async function loadBook() {
  // 测试
  // bookStore.clearBooks();
  // for (var i = 0; i < 12; i++) {
  //   bookStore.addBook(
  //     "测试书籍",
  //     "测试分类",
  //     "测试出版社",
  //     12,
  //     1999,
  //     "测试语言",
  //     "测试ISBN",
  //     "测试来源",
  //     "https://files.catbox.moe/03zgjn.jpg"
  //   );
  // }
  // console.log(bookStore.Books);
  // title.value = "推荐书籍";
  try {
    console.log(bookStore.Books);
    if (bookStore.Books.length > 0) {
      title.value = "搜索结果";
      bookSearch.name = router.currentRoute.value.query.name as string;
      // category解码
      var categoryString = router.currentRoute.value.query.category as string;
      var categoryArray = categoryString.split(",");
      bookSearch.category = categoryArray;
      bookSearch.publisher = router.currentRoute.value.query
        .publisher as string;
      bookSearch.pages = parseInt(router.currentRoute.value.query.pages as string);
      bookSearch.publishYear = router.currentRoute.value.query
        .publishYear as string;
      bookSearch.bookLanguage = router.currentRoute.value.query
        .bookLanguage as string;
      bookSearch.isbn = router.currentRoute.value.query.isbn as string;
      bookSearch.source = router.currentRoute.value.query.source as string;
      // isFuzzy解码
      var isFuzzyString = router.currentRoute.value.query.isFuzzy as string;
      for (var i = 0; i < isFuzzyString.length; i++) {
        if (isFuzzyString[i] === "1") {
          bookSearch.isFuzzy[i] = true;
        } else {
          bookSearch.isFuzzy[i] = false;
        }
      }
      return;
    }
    const send = {
      category: userStore.interest,
    };
    console.log(send);
    const response = await axios.post("http://10.73.103.130:1212/api/interest", send);
    // console.log("后端返回的消息：", response.data);
    var isLoad = response.data.success;
    if (isLoad) {
      // console.log(response.data.books);
      const bookStore = useBookStore();
      for (var i = 0; i < response.data.books.length; i++) {
        bookStore.addBook(
          response.data.books[i].name,
          response.data.books[i].category,
          response.data.books[i].publisher,
          response.data.books[i].pages,
          response.data.books[i].publishYear,
          response.data.books[i].BookLanguage,
          response.data.books[i].ISBN,
          response.data.books[i].source,
          response.data.books[i].imageUrl
        );
      }
    } else {
      ElMessage.error("加载书籍失败"); // Use ElMessage for error message
    }
  } catch (error) {
    ElMessage.error("加载书籍失败");
  }
}

async function loadCategory() {
  // 测试
  // for (var i = 0; i < 4; i++) {
  //   category.value.push("测试分类" + i);
  // }

  try {
    const response = await axios.post("http://10.73.103.130:1212/api/category");
    // console.log("后端返回的消息：", response.data);
    var isLoad = response.data.success;
    if (isLoad) {
      // console.log(response.data);
      const bookStore = useBookStore();
      category.value = response.data.categories;
    } else {
      ElMessage.error("加载分类失败"); // Use ElMessage for error message
    }
  } catch (error) {
    ElMessage.error("加载分类失败");
  }
}

onBeforeMount(() => {
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
    text: "Last year",
    value: () => {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 365);
      return [start, end];
    },
  },
  {
    text: "Last 3 years",
    value: () => {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 365 * 3);
      return [start, end];
    },
  },
  {
    text: "Last 10 years",
    value: () => {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 365 * 10);
      return [start, end];
    },
  },
];

function goToBook(name: string) {
  router.push({
    name: "unit",
    query: {
      name: name,
    },
  });
}
</script>

<template>
  <el-form :model="bookSearch" label-width="100px" class="sidebar">
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
      <el-input v-model="bookSearch.name" style="width: 15vw"></el-input>
      <el-switch
        v-model="bookSearch.isFuzzy[0]"
        active-color="#13ce66"
        inactive-color="#ff4949"
        active-text="模糊"
        inactive-text="精确"
        style="margin-left: 1vw"
      ></el-switch>
    </el-form-item>
    <el-form-item label="出版社：">
      <el-input v-model="bookSearch.publisher" style="width: 15vw"></el-input>
      <el-switch
        v-model="bookSearch.isFuzzy[1]"
        active-color="#13ce66"
        inactive-color="#ff4949"
        active-text="模糊"
        inactive-text="精确"
        style="margin-left: 1vw"
      ></el-switch>
    </el-form-item>
    <el-form-item label="页数：">
      <el-input v-model="bookSearch.pages" style="width: 15vw"></el-input>
      <el-switch
        v-model="bookSearch.isFuzzy[2]"
        active-color="#13ce66"
        inactive-color="#ff4949"
        active-text="模糊"
        inactive-text="精确"
        style="margin-left: 1vw"
      ></el-switch>
    </el-form-item>
    <el-form-item label="来源：">
      <el-input v-model="bookSearch.source" style="width: 15vw"></el-input>
      <el-switch
        v-model="bookSearch.isFuzzy[3]"
        active-color="#13ce66"
        inactive-color="#ff4949"
        active-text="模糊"
        inactive-text="精确"
        style="margin-left: 1vw"
      ></el-switch>
    </el-form-item>
    <el-form-item label="语言：">
      <el-input
        v-model="bookSearch.bookLanguage"
        style="width: 15vw"
      ></el-input>
    </el-form-item>
    <el-form-item label="ISBN：">
      <el-input v-model="bookSearch.isbn" style="width: 15vw"></el-input>
    </el-form-item>
    <el-form-item label="出版日期：">
      <el-date-picker
        v-model="bookSearch.publishYear"
        type="monthrange"
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
      <el-row class="book">
        <el-card
          shadow="hover"
          style="width: 200px; margin: 10px"
          v-for="book in bookStore.Books"
        >
          <img
            :src="book.imageUrl"
            class="image"
            style="width: 160px; height: 200px"
          />
          <div style="padding: 14px">
            <span>{{ book.name }}</span>
            <div class="bottom clearfix">
              <time class="time">出版日期：{{ book.publishYear }}</time>
              <el-button type="primary" @click="goToBook(book.name)">
                查看详情
              </el-button>
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
  background-color: #b3b6b9;
}

.books {
  display: flex;
  margin-left: 2vw;
}

.el-input-wrapper {
  width: 5vw;
}
</style>
