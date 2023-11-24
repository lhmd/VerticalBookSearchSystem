<script setup lang="ts">
import { ref } from "vue";
import { Search } from "@element-plus/icons-vue";
import BookInfo from "@/components/search/bookInfo.vue";

const input = ref("");
const select = ref("");
// 假数据
const bookList = ref([]);

// 提交
const submitSearch = () => {
  // 接口参数
  let data = {
    input: input.value,
    select: select.value,
  };
  // 调接口获取返回的参数，给store.js中的book赋值
  if (input.value == "") {
    bookList.value = [];
  } else {
    let list = [
      {
        name: "1111111111111",
        imageUrl: "",
        categories: "1",
        publisher: "1",
        pages: "1",
        publishYear: "1",
        bookLanguage: "1",
        isbn: "1",
        source: "1",
      },
      {
        name: "2",
        imageUrl: "",
        categories: "2",
        publisher: "2",
        pages: "2",
        publishYear: "4",
        bookLanguage: "32",
        isbn: "2222222",
        source: "2",
      },
      {
        name: "3333333",
        imageUrl: "",
        categories: "3",
        publisher: "4",
        pages: "3",
        publishYear: "3",
        bookLanguage: "3",
        isbn: "333333333",
        source: "3",
      },
    ];
    bookList.value = list;
  }
};
</script>

<template>
  <div class="books-center" v-if="bookList.length == 0">
    <div class="mt-4">
      <el-input
        v-model="input"
        placeholder="Please input"
        class="input-with-select"
      >
        <template #prepend>
          <el-select v-model="select" placeholder="Select" style="width: 115px">
            <el-option label="Restaurant" value="1" />
            <el-option label="Order No." value="2" />
            <el-option label="Tel" value="3" />
          </el-select>
        </template>
        <template #append>
          <el-button :icon="Search" @click="submitSearch" />
        </template>
      </el-input>
    </div>
  </div>
  <div class="books" v-if="bookList.length != 0">
    <div class="mt-4 center">
      <el-input
        v-model="input"
        placeholder="Please input"
        class="input-with-select"
      >
        <template #prepend>
          <el-select v-model="select" placeholder="Select" style="width: 115px">
            <el-option label="Restaurant" value="1" />
            <el-option label="Order No." value="2" />
            <el-option label="Tel" value="3" />
          </el-select>
        </template>
        <template #append>
          <el-button :icon="Search" @click="submitSearch" />
        </template>
      </el-input>
    </div>
    <!-- 搜索出来的图书列表 -->
    <div v-if="bookList.length != 0">
      <div v-for="item in bookList" :key="item">
        <BookInfo :book="item" />
      </div>
    </div>
  </div>
</template>

<style>
.center {
  display: flex;
  justify-content: center;
  margin-top: 2%;
}
.books-center {
  margin-top: 20%;
}
.input-with-select .el-input-group__prepend {
  background-color: var(--el-fill-color-blank);
}
.books {
  display: flex;
  flex-direction: column;
}
</style>