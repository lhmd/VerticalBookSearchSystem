<script setup lang="ts">
import TopBar from "@/components/TopBar.vue";
import BookInfo from "@/components/search/BookInfo.vue";
</script>

<template>
  <TopBar />
  <!-- <div class="home-container"><BookInfo /></div> -->
</template>

<style scoped>
@media screen and (min-width: 1024px) {
  .home-container {
    display: flex;
    flex-direction: row;
  }
}

@media screen and (max-width: 1024px) {
  .home-container {
    flex-direction: column;
  }
}
</style>
