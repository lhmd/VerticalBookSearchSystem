
<template>
  <div class="book-info" @click="gotoBookDisplayUnit">
    <div class="content">
      <div class="imageInformation">
        <img :src="book.imageUrl" alt="Book Cover" class="book-cover" />
      </div>
    </div>
    <div class="book-name">
      <h2>name: {{ book.name }}</h2>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();

export default {
  props: {
    book: {
      type: Object,
      required: true,
    },
  },
  methods: {
    gotoBookDisplayUnit() {
      this.$router.push({
        path: "/unit",
        query: { data: JSON.stringify(this.book) },
      });
    },
  },
};
</script>

<style scoped>
.book-name {
  margin-left: 20px;
}
.book-info {
  display: flex;
  border: 2px solid #ccc;
  border-radius: 5px;
  padding: 10px;
  margin: 15px 0;
}
.content {
  display: flex;
}
.text-information {
  flex: 1;
  margin-left: 20px;
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
  height: 200px;
  object-fit: cover;
  border: 1px solid #ccc;
  border-radius: 5px;
}
</style>