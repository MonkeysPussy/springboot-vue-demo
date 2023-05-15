<template>
    <div>
        <el-menu
                default-active="user"
                class="el-menu-vertical-demo"
                style="width: 200px; min-height: calc(100vh - 50px)"
                router
        >
            <el-sub-menu index="1" v-if="user.role === 1">
                <template #title>
                    <el-icon>
                        <Stopwatch/>
                    </el-icon>
                    <span>系统管理</span>
                </template>


                <el-menu-item index="user">
                    <el-icon>
                        <User/>
                    </el-icon>
                    用户管理
                </el-menu-item>

            </el-sub-menu>
            <el-menu-item index="travel">
                <el-icon>
                    <Ship/>
                </el-icon>
                打卡点管理
            </el-menu-item>
            <el-menu-item index="news">
                <el-icon>
                    <Promotion/>
                </el-icon>
                讯息管理
            </el-menu-item>

        </el-menu>

    </div>

</template>

<script>
    import request from "../utils/request";

    export default {
        name: "Aside",
        data() {
            return {
                user: {}
            }
        },
        created() {
            let str = sessionStorage.getItem("user") || "{}"
            this.user = JSON.parse(str)


            // 请求服务器，确认当前登录用户的合法信息
            request.get("/user/" + this.user.id).then(res => {
                if (res.code == '0') {
                    this.user = res.data
                }
            })
        }

    }

</script>

<style scoped>


</style>