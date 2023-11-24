<script setup lang="ts">
import TopBar from "@/components/TopBar.vue";
import BookDisplayUnit from "@/components/search/BookDisplayUnit.vue";
import { useRouter } from "vue-router";
const router = useRouter();
const queryData = router.currentRoute.value.query;
const data = JSON.parse(queryData.data);
</script>

<template>
  <div class="home-container"><BookDisplayUnit :book="data" /></div>
</template>

<style scoped>
@media screen and (min-width: 1024px) {
  .home-container {
    display: flex;
    flex-direction: row;
    justify-content: center;
  }
}

@media screen and (max-width: 1024px) {
  .home-container {
    flex-direction: column;
    justify-content: center;
  }
}
</style>
