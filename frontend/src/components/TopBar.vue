<template>
  <el-container>
    <el-header class="topbar">
      <div class="logo">
        <el-icon :size="40" color="#409EFC" class="no-inherit-top">
          <connection />
        </el-icon>
        <h2>书籍垂直搜索系统</h2>
      </div>
      <el-menu
        class="el-menu-demo"
        mode="horizontal"
        :ellipsis="false"
        router
        @select="handleSelect"
      >
        <div class="flex-grow"></div>
        <el-menu-item index="/search" v-if="!isMobile" class="item-menu"
          >Search</el-menu-item
        >
        <el-menu-item index="/home" v-if="!isMobile" class="item-menu"
          >Home</el-menu-item
        >
        <el-menu-item index="logout" v-if="!isMobile" class="item-menu"
          >Logout</el-menu-item
        >
        <el-sub-menu index="4" v-if="isMobile">
          <template #title>
            <el-icon :size="30" color="#409EFC" class="no-inherit">
              <operation />
            </el-icon>
          </template>
          <el-menu-item index="search">Search</el-menu-item>
          <el-menu-item index="/home">Home</el-menu-item>
          <el-menu-item index="logout">Logout</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-header>
  </el-container>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from "vue";
import { useUserStore } from "@/stores/user";
import { ElMessage } from "element-plus";
import router from "@/router";
import { useBookStore } from "@/stores/book";

const userStore = useUserStore();
const bookStore = useBookStore();
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

const handleSelect = (index: string) => {
  if (index === "logout") {
    if (!userStore.isAuthenticated) {
      ElMessage.warning("您还未登录！");
    } else {
      userStore.setAuthenticationStatus(false);
      userStore.clearUserCredentials();
      bookStore.clearBooks();
      ElMessage.success("成功退出登录！");
    }
    router.push("/login");
  } else if (index === "/search") {
    bookStore.clearBooks();
    router.push("/search");
  }
};
</script>

<style scoped>
.topbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background: linear-gradient(90deg, #409efc 0%, #ec2f4b 100%);
}

.logo {
  display: flex;
  align-items: center;
  white-space: nowrap;
}

.no-inherit-top {
  color: #fff;
}

.logo h2 {
  margin-left: 2vw;
}

.el-menu-demo {
  background: transparent;
  color: #fff;
  font-size: 1.2rem;
}

.item-menu {
  font-family: "Noto Sans SC", sans-serif;
  color: #fff;
  font-size: large;
}
</style>
