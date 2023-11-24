<template>
  <div class="book-info">
    <h2>{{ book.name }}</h2>
    <div class="text-information">
      <div class="categories">{{ book.categories }}</div>
      <div class="publisher">Publisher: {{ book.publisher }}</div>
      <div class="pages">Pages: {{ book.pages }}</div>
      <div class="publish-year">Published Year: {{ book.publishYear }}</div>
      <div class="language">Language: {{ book.bookLanguage }}</div>
      <div class="isbn">ISBN: {{ book.isbn }}</div>
      <div class="source">Source: {{ book.source }}</div>
    </div>
    <div class="imageInformation">
      <img :src="book.imageUrl" alt="Book Cover" class="book-cover" />
    </div>
  </div>
</template>

<script>
export default {
  props: {
    book: {
      type: Object,
      required: true
    }
  }
};
</script>

<style>
.book-info {
  display: flex;
  align-items: center;
  border: 2px solid #ccc;
  border-radius: 5px;
  padding: 10px;
  margin-bottom: 10px;
}

.text-information {
  flex: 1;
  margin-right: 20px;
}

.book-info h2 {
  font-size: 24px;
  margin-bottom: 10px;
}

.categories {
  font-size: 16px;
  margin-bottom: 5px;
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
  width: 200px;
  height: 300px;
  object-fit: cover;
  border: 1px solid #ccc;
  border-radius: 5px;
}
</style>