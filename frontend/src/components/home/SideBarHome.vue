<template>
  <div v-if="isMobile">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      router
    >
      <el-menu-item index="/home/profile">个人中心</el-menu-item>
      <el-menu-item index="/about">系统信息</el-menu-item>
    </el-menu>
  </div>

  <div class="sidebar" v-if="!isMobile">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-vertical-demo"
      popper-effect="light"
      router
    >
      <el-menu-item index="/home/profile">
        <template #title>
          <el-icon><user /></el-icon>
          <span>个人中心</span>
        </template>
      </el-menu-item>
      <el-menu-item index="/about">
        <template #title>
          <el-icon><monitor /></el-icon>
          <span>系统信息</span>
        </template>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from "vue";

const activeIndex = ref("1");
const isMobile = ref(false);

const checkMobile = () => {
  isMobile.value = window.innerWidth < 1024;
};

onMounted(() => {
  checkMobile();
  window.addEventListener("resize", () => {
    checkMobile();
  });
});

onBeforeUnmount(() => {
  window.removeEventListener("resize", () => {
    checkMobile();
  });
});
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 20vw;
  min-height: 4vh;
}

.sidebar {
  display: flex;
  width: 20vw;
  height: 100vh;
  background-color: #545c64;
}
</style>
