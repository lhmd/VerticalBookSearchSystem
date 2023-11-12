import { defineStore } from "pinia";
import { ref, computed } from "vue";

export const useUserStore = defineStore("user", {
  state: () => ({
    username: "",
    password: "",
    isAuthenticated: false, // To track user's authentication status
    email: "",
    phone: "",
    gender: "",
    address: "",
  }),
  actions: {
    setUserCredentials(
      username: string,
      password: string,
      email: string,
      phone: string,
      gender: string,
      address: string,
    ) {
      this.username = username;
      this.password = password;
      this.email = email;
      this.phone = phone;
      this.gender = gender;
      this.address = address;
    },
    setAuthenticationStatus(isAuthenticated: boolean) {
      this.isAuthenticated = isAuthenticated;
    },
    clearUserCredentials() {
      this.username = "";
      this.password = "";
      this.email = "";
      this.phone = "";
      this.gender = "";
      this.address = "";
      // console.log("clear all user data.");
    },
  },
});
