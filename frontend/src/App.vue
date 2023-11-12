<script setup lang="ts">
import { useRouter } from "vue-router";
const Router = useRouter();
</script>

<template>
  <div class="App">
    <router-view />
  </div>
</template>

<style>
html,
body {
  margin: 0;
  padding: 0;
  min-height: 100vh;
  background-color: #fff;
}

.App {
  width: 100%;
  height: 100%;
  position: relative;
}

.Header {
  width: 100%;
  height: 5vh;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
}

@media screen and (min-width: 1024px) {
  .Header {
    background-color: rgb(217, 145, 145);
    min-height: 10vh;
    display: flex;
    align-items: center;
    flex-direction: row;
  }
}

@media screen and (max-width: 1024px) {
  .Header {
    background-color: rgb(145, 157, 217);
    min-height: 10vh;
    display: flex;
    align-items: center;
    flex-direction: column;
  }
}
</style>
