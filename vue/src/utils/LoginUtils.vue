<template>

</template>

<script>
    import {reactive, ref} from "@vue/reactivity";

    export default {
        name: "LoginUtils"
    }

    const loginForm = reactive({
        username: '',
        password: ''
    })
    const RegisterForm = reactive({
        username: '',
        password: '',
        confirmPassword: ''
    })
    const loginFormRef = ref('')
    const RegisterFormRef = ref('')
    const rules = reactive({
        username: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {min: 3, max: 5, message: '长度应该在3-5个字符之间', trigger: 'blur'},
        ],
        password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min: 3, message: '长度应该大于3字符', trigger: 'blur'},
        ],
        confirmPassword: [
            {required: true, message: '请确认密码', trigger: 'blur'},
            {min: 3, message: '长度应该大于3字符', trigger: 'blur'},
        ],
    })


    // const {ref} = require("@vue/reactivity");
    const preRef = ref('')
    const imgList = ref([require('@/assets/img/wuwu.jpeg'), require('@/assets/img/waoku.jpg')])
    let flag = ref(true)
    const mySwitch = () => {
        if (flag.value) {
            preRef.value.style.background = "#c9e0ed"
            preRef.value.style.transform = "translateX(100%)"
        } else {
            preRef.value.style.background = "#edd4dc"
            preRef.value.style.transform = "translateX(0%)"
        }
        flag.value = !flag.value
    }

    // const login = () => {
    //     loginFormRef.value.validate((valid) => {
    //         if (valid){
    //             request.post("/user/login", this.loginForm).then(request=>{
    // }




</script>

<style scoped>

</style>