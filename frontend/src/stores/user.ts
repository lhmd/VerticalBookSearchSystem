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
    interest: "",
  }),
  actions: {
    setUserCredentials(
      username: string,
      password: string,
      email: string,
      phone: string,
      gender: string,
      address: string,
      interest: string
    ) {
      this.username = username;
      this.password = password;
      this.email = email;
      this.phone = phone;
      this.gender = gender;
      this.address = address;
      this.interest = interest;
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
      this.interest = "";
      // console.log("clear all user data.");
    },
  },
});
