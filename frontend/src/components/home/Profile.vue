<script setup lang="ts">
import { useUserStore } from "@/stores/user";
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import axios from "axios";
import router from "@/router";

const userStore = useUserStore();
const user = reactive({
  username: userStore.username,
  email: userStore.email,
  phone: userStore.phone,
  gender: userStore.gender,
  address: userStore.address,
});
const password = reactive({
  password: "",
  checkPassword: "",
});
const dialogPasswordVisible = ref(false);
const dialogInforVisible = ref(false);
const formLabelWidth = "140px";

async function modifyInfor() {
  try {
    const send = {
      username: userStore.username,
      newUsername: user.username,
      email: user.email,
      phone: user.phone,
      gender: user.gender,
      address: user.address,
    };
    // console.log("发送给后端的消息：", send);
    const response = await axios.post(
      "http://localhost:6034/modifyInfor",
      send
    );
    // console.log("后端返回的消息：", response.data);
    var isModified = response.data.success;
    if (isModified) {
      userStore.setUserCredentials(
        user.username,
        userStore.password,
        user.email,
        user.phone,
        user.gender,
        user.address,
        userStore.interest
      );
      // console.log("修改后的用户信息：", userStore);
      ElMessage.success("修改成功！");
    } else {
      ElMessage.error(response.data.message);
    }
  } catch (error) {
    ElMessage.error("修改失败");
    console.error("请求出错：", error);
  }
}

async function modifyPassword() {
  try {
    if (password.checkPassword != password.password) {
      ElMessage.error("两次密码不一致");
      return;
    }
    const send = {
      username: userStore.username,
      password: password.password,
    };
    const response = await axios.post(
      "http://localhost:6034/modifyPassword",
      send
    );
    var isModified = response.data.success;
    if (isModified) {
      userStore.setAuthenticationStatus(false);
      userStore.clearUserCredentials();
      router.push("/login");
      ElMessage.success("修改成功，请重新登录！");
    } else {
      ElMessage.error(response.data.message);
    }
  } catch (error) {
    ElMessage.error("修改失败");
    console.error("请求出错：", error);
  }
}
</script>

<template>
  <div class="profile-container">
    <el-descriptions title="| 个人信息 |" :column="1" border>
      <template #extra>
        <el-button type="primary" @click="dialogInforVisible = true"
          >修改信息</el-button
        >
        <el-button type="danger" @click="dialogPasswordVisible = true"
          >修改密码</el-button
        >
      </template>
      <el-descriptions-item label="用户名">{{
        user.username
      }}</el-descriptions-item>
      <!-- <el-descriptions-item label="" :span="2"><el-button type="primary" @click="modifyUsername">修改</el-button></el-descriptions-item> -->
      <el-descriptions-item label="性别">{{
        user.gender
      }}</el-descriptions-item>
      <!-- <el-descriptions-item label="" :span="2"><el-button type="primary" @click="modifyGender">修改</el-button></el-descriptions-item> -->
      <el-descriptions-item label="邮箱">{{ user.email }}</el-descriptions-item>
      <!-- <el-descriptions-item label="" :span="2"><el-button type="primary" @click="modifyEmail">修改</el-button></el-descriptions-item> -->
      <el-descriptions-item label="电话">{{ user.phone }}</el-descriptions-item>
      <!-- <el-descriptions-item label="" :span="2"><el-button type="primary" @click="modifyPhone">修改</el-button></el-descriptions-item> -->
      <el-descriptions-item label="地址">{{
        user.address
      }}</el-descriptions-item>
      <!-- <el-descriptions-item label="" :span="2"><el-button type="primary" @click="modifyAddress">修改</el-button></el-descriptions-item> -->
    </el-descriptions>
  </div>

  <el-dialog v-model="dialogPasswordVisible" title="修改密码">
    <el-form :model="password">
      <el-form-item
        label="新密码"
        :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]"
        type="password"
      >
        <el-input v-model="password.password" type="password" show-password />
      </el-form-item>
      <el-form-item
        label="确认密码"
        type="password"
        :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]"
      >
        <el-input
          v-model="password.checkPassword"
          type="password"
          autocomplete="off"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogPasswordVisible = false">取消</el-button>
        <el-button @click="modifyPassword">提交</el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog v-model="dialogInforVisible" title="修改信息">
    <el-form :model="user">
      <el-form-item label="username" :label-width="formLabelWidth">
        <el-input v-model="user.username" autocomplete="off" />
      </el-form-item>
      <el-form-item label="gender" :label-width="formLabelWidth">
        <el-input v-model="user.gender" autocomplete="off" />
      </el-form-item>
      <el-form-item label="email" :label-width="formLabelWidth">
        <el-input v-model="user.email" autocomplete="off" />
      </el-form-item>
      <el-form-item label="phone" :label-width="formLabelWidth">
        <el-input v-model="user.phone" autocomplete="off" />
      </el-form-item>
      <el-form-item label="address" :label-width="formLabelWidth">
        <el-input v-model="user.address" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogInforVisible = false">取消</el-button>
        <el-button @click="modifyInfor">提交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style>
.profile-container {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  top: 5vh;
}

@media screen and (min-width: 1024px) {
  .profile-container .el-button {
    margin: 2vh;
  }
}

@media screen and (max-width: 1024px) {
  .profile-container .el-button {
    margin: 0vh;
    margin-right: 0vh;
  }
}

.el-descriptions {
  width: 60vw;
  height: 50vh;
  background-color: #f5f7fa;
  top: 5vh;
  align-self: center;
}

.el-button--text {
  margin-right: 15px;
}
.el-select {
  width: 300px;
}
.el-input {
  width: 300px;
}
.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>
