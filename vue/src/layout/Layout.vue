<template>
    <div>
        <!--        头部-->
        <Header :user="user"/>

        <!--        主体-->
        <div style="display:flex">
            <!--            侧边栏-->
            <Aside/>
            <!--            内容区域 -->
            <router-view style="flex: 1" @userInfo="refreshUser"/>
        </div>
    </div>
</template>

<script>
    import Header from "../components/Header";
    import Aside from "../components/Aside";
    import request from "../utils/request";

    export default {
        name: "Layout",
        components: {
            Aside,
            Header
        },
        data() {
            return {
                user: {}
            }
        },
        created() {
            this.refreshUser()
        },
        methods: {
            refreshUser() {
                let userJson = sessionStorage.getItem("user")
                if (!userJson) {
                    return
                }
                let userId = JSON.parse(userJson).id
                // 从后台取出更新后的最新信息
                request.get("/user/" + userId).then(res => {
                    this.user = res.data
                })
            }
        }
    }
</script>

<style scoped>

</style>