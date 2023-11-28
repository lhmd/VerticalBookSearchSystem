<template>
  <div class="book-info">
    <div>
      <h1>name: {{ book.name }}</h1>
    </div>
    <div class="content">
      <div class="imageInformation">
        <img :src="book.imageUrl" alt="Book Cover" class="book-cover" />
      </div>
      <div class="text-information">
        <div class="categories">categories: {{ book.categories }}</div>
        <div class="categories">Publisher: {{ book.publisher }}</div>
        <div class="categories">Pages: {{ book.pages }}</div>
        <div class="categories">Published Year: {{ book.publishYear }}</div>
        <div class="categories">Language: {{ book.bookLanguage }}</div>
        <div class="categories">ISBN: {{ book.isbn }}</div>
        <div class="categories">Source: {{ book.source }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    book: {
      type: Object,
      required: true,
    },
  },
  mounted() {
    console.log("-----", this.book);
  },
};
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
