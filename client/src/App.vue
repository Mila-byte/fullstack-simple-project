<script setup lang="ts">
import { onMounted, reactive } from 'vue';

const res = reactive<any[]>([]);

const setRandomQuestion = async () => {
  const result = await fetch('http://localhost:8080/api/questions/random').then(res => res.json());
  res.push(result);
};

onMounted(async () => {
  await setRandomQuestion()
});
</script>

<template>
  <header>
    <div v-for="item in res" class="wrapper">
      <p>{{ item.text }}</p>
    </div>
    <button @click="setRandomQuestion">add</button>
  </header>
</template>

<style scoped>
</style>
