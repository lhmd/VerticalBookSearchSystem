<script setup lang="ts">
import { ref } from "vue";
import { Search } from "@element-plus/icons-vue";
const input = ref("");
const select = ref("");
</script>

<template>
  <!-- <div class="mt-4">
    <el-input
      v-model="input"
      placeholder="Please input"
      class="input-with-select"
    >
      <template #prepend>
        <el-select v-model="select" placeholder="Select" style="width: 115px">
          <el-option label="Restaurant" value="1" />
          <el-option label="Order No." value="2" />
          <el-option label="Tel" value="3" />
        </el-select>
      </template>
      <template #append>
        <el-button :icon="Search" />
      </template>
    </el-input>
  </div> -->
</template>

<style>
.input-with-select .el-input-group__prepend {
  background-color: var(--el-fill-color-blank);
}
</style>
