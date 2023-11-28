<script setup lang="ts">
import { reactive, ref, onMounted, onBeforeUnmount } from "vue";
import type { FormProps } from "element-plus";
import { useRouter } from "vue-router";
import axios from "axios";
import { ElMessage } from "element-plus";

import { useUserStore } from "@/stores/user";

const userStore = useUserStore();
const Router = useRouter();
const labelPosition = ref<FormProps["labelPosition"]>("top");
const size = ref("large");

const UserLogin = reactive({
  username: "",
  password: "",
});

async function onSubmit() {
  try {
    // 验证用户名和密码是否为空
    if (!UserLogin.username || !UserLogin.password) {
      ElMessage.error("用户名和密码不能为空"); // Use ElMessage for error message
      return;
    }
    // console.log(UserLogin);
    const response = await axios.post(
      "http://localhost:6034/auth/login",
      UserLogin
    );
    // console.log("后端返回的消息：", response.data);
    var isLogin = response.data.success;
    if (isLogin) {
      userStore.setUserCredentials(
        response.data.user.username,
        response.data.user.password,
        response.data.user.email,
        response.data.user.phone,
        response.data.user.gender,
        response.data.user.address,
        response.data.user.interest
      );
      userStore.setAuthenticationStatus(true);
      ElMessage.success("登录成功，欢迎使用书籍垂直搜索系统！"); // Use ElMessage for success message
      Router.push("/search");
    } else {
      ElMessage.error("用户名或密码错误"); // Use ElMessage for error message
    }
  } catch (error) {
    ElMessage.error("登录失败");
    console.error("请求出错：", error);
  }
}

function onRegister() {
  Router.push("/register");
}
</script>

<template>
  <div class="login_bckg">
    <img src="../assets/login_bckg.png" alt="" />
  </div>
  <div class="login_form">
    <h3>Sign in</h3>
    <el-form
      :label-position="labelPosition"
      label-width="100px"
      :model="UserLogin"
      :size="size"
    >
      <el-form-item
        label="用户名"
        :rules="[{ required: true, message: '请输入用户名', trigger: 'blur' }]"
      >
        <el-input v-model="UserLogin.username" />
      </el-form-item>
      <el-form-item
        label="密码"
        :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]"
        type="password"
      >
        <el-input v-model="UserLogin.password" type="password" show-password />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">登录</el-button>
        <el-button type="success" @click="onRegister">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped>
.login_bckg {
  display: flex;
  width: 100%;
  height: 100vh;
}

.login_bckg img {
  object-fit: cover;
  width: 100%;
  height: 100%;
}
@media screen and (min-width: 1024px) {
  .login_form {
    position: absolute;
    top: 55vh;
    left: 25vw;
    transform: translate(-50%, -50%);
    width: 30vw;
    height: 60vh;
    padding: 20px;
    border-radius: 10px; /* 圆角半径 */
    background-color: rgba(
      255,
      255,
      255,
      0.5
    ); /* 使用rgba来设置背景颜色并控制半透明度 */
    max-width: 40vw;
    min-width: 25vw;
  }
}

@media screen and (max-width: 1024px) {
  .login_form {
    position: absolute;
    top: 50vh;
    left: 50vw;
    transform: translate(-50%, -50%);
    width: 80vw;
    height: 70vh;
    padding: 20px;
    border-radius: 10px; /* 圆角半径 */
    background-color: rgba(
      255,
      255,
      255,
      0.5
    ); /* 使用rgba来设置背景颜色并控制半透明度 */
  }
}

.login_form h3 {
  text-align: center;
  margin-bottom: 8vh;
}
</style>
