<script setup lang="ts">
import { reactive, ref, onBeforeMount, onBeforeUnmount } from "vue";
import type { FormProps } from "element-plus";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import axios from "axios";
import { useBookStore } from "@/stores/book";

const Router = useRouter();
const labelPosition = ref<FormProps["labelPosition"]>("right");

const UserRegister = reactive({
  username: "",
  password: "",
  checkPassword: "",
  email: "",
  phone: "",
  gender: "",
  address: "",
  interest: "",
});

function onRegister() {
  Router.push("/login");
}

async function onSubmit() {
  try {
    if (!UserRegister.username) {
      ElMessage.error("用户名不能为空");
      return;
    } else if (!UserRegister.password) {
      ElMessage.error("密码不能为空");
      return;
    } else if (!UserRegister.email) {
      ElMessage.error("邮箱不能为空");
      return;
    } else if (UserRegister.checkPassword != UserRegister.password) {
      ElMessage.error("两次密码不一致");
      return;
    }

    console.log(UserRegister);
    const response = await axios.post(
      "http://10.73.103.130:1212/auth/register",
      UserRegister
    );
    // console.log("后端返回的消息：", response.data);
    var isLogin = response.data.success;
    if (isLogin) {
      Router.push("/login");
      ElMessage.success("注册成功，请重新登录！");
    } else {
      ElMessage.error(response.data.message);
    }
  } catch (error) {
    ElMessage.error("注册失败");
    console.error("请求出错：", error);
  }
}

let category = ref([]);
async function loadCategory() {
  // 测试
  // for (var i = 0; i < 4; i++) {
  //   category.value.push("测试分类" + i);
  // }

  try {
    const response = await axios.post("http://10.73.103.130:1212/api/category");
    // console.log("后端返回的消息：", response.data);
    var isLoad = response.data.success;
    if (isLoad) {
      // console.log(response.data);
      const bookStore = useBookStore();
      category.value = response.data.categories;
    } else {
      ElMessage.error("加载分类失败"); // Use ElMessage for error message
    }
  } catch (error) {
    ElMessage.error("加载分类失败");
  }
}

const gender = [
  {
    value: "MALE",
    label: "男",
  }, 
  {
    value: "FEMALE",
    label: "女",
  }, 
  {
    value: "UNKNOWN",
    label: "其他",
  },
]

onBeforeMount(() => {
  loadCategory();
});

</script>

<template>
  <div class="login_bckg">
    <img src="../assets/login_bckg.png" alt="" />
  </div>
  <div class="register_form">
    <h3>Sign up</h3>
    <el-form
      :label-position="labelPosition"
      label-width="100px"
      :model="UserRegister"
    >
      <el-form-item
        label="用户名"
        :rules="[{ required: true, message: '请输入用户名', trigger: 'blur' }]"
      >
        <el-input v-model="UserRegister.username" />
      </el-form-item>
      <el-form-item
        label="密码"
        :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]"
        type="password"
      >
        <el-input
          v-model="UserRegister.password"
          type="password"
          show-password
        />
      </el-form-item>
      <el-form-item
        label="确认密码"
        type="password"
        :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]"
      >
        <el-input
          v-model="UserRegister.checkPassword"
          type="password"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item
        label="邮箱"
        :rules="[{ required: true, message: '请输入邮箱', trigger: 'blur' }]"
      >
        <el-input v-model="UserRegister.email" />
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="UserRegister.phone" />
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="UserRegister.gender">
          <el-option v-for="item in gender" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="UserRegister.address" />
      </el-form-item>
      <el-form-item label="兴趣">
        <el-select v-model="UserRegister.interest">
          <el-option v-for="item in category" :key="item" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="onSubmit" size="large" round
          >提 交</el-button
        >
        <el-button type="primary" @click="onRegister" size="large" round
          >返 回</el-button>
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
  .register_form {
    position: absolute;
    top: 55vh;
    left: 25vw;
    transform: translate(-50%, -50%);
    width: 30vw;
    height: 80vh;
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
  .register_form {
    position: absolute;
    top: 60vh;
    left: 50vw;
    transform: translate(-50%, -50%);
    width: 80vw;
    height: 90vh;
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

.register_form h3 {
  text-align: center;
  margin-bottom: 8vh;
}
</style>
