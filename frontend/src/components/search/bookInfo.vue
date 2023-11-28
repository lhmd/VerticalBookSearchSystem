<!-- <script setup lang="ts">

import { ref, onMounted, onBeforeUnmount } from "vue";
import { useBookStore } from "@/stores/book";
import { ElMessage } from "element-plus";
import axios from "axios";

const bookStore = useBookStore();

// onMounted(() => {
//   load
// });

</script>

<template>
  <div class="book-info">
    <div class="book-info__title">
      <h1>{{ bookStore.Book.name }}</h1>
    </div>
    <div class="book-info__content">
      <div class="book-info__content__left">
        <img :src="bookStore.Book.imageUrl" alt="book" />
      </div>
      <div class="book-info__content__right">
        <div class="book-info__content__right__item">
          <span class="book-info__content__right__item__title">作者：</span>
          <span class="book-info__content__right__item__content"
            >{{ bookStore.Book.author }}</span
          >
        </div>
        <div class="book-info__content__right__item">
          <span class="book-info__content__right__item__title">出版社：</span>
          <span class="book-info__content__right__item__content"
            >{{ bookStore.book.publisher }}</span
          >
        </div>
        <div class="book-info__content__right__item">
          <span class="book-info__content__right__item__title">出版日期：</span>
          <span class="book-info__content__right__item__content"
            >{{ bookStore.book.publishYear }}</span
          >
        </div>
        <div class="book-info__content__right__item">
          <span class="book-info__content__right__item__title">页数：</span>
          <span class="book-info__content__right__item__content"
            >{{ bookStore.book.pages }}</span
          >
        </div>
        <div class="book-info__content__right__item">
          <span class="book-info__content__right__item__title">语言：</span>
          <span class="book-info__content__right__item__content"
            >{{ bookStore.book.BookLanguage }}</span
          >
        </div>
        <div class="book-info__content__right__item">
          <span class="book-info__content__right__item__title">ISBN：</span>
          <span class="book-info__content__right__item__content"
            >{{ bookStore.book.ISBN }}</span
          >
        </div>
        <div class="
</template>

<style>

</style> -->
