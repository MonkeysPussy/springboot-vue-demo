<template>
    <div style="height: 50px; line-height:50px; border-bottom:1px solid #ccc; display:flex">
        <div style="width: 200px; padding-left: 30px; font-weight: bold; color: deepskyblue">后台管理</div>
        <div style="flex: 1"></div>


        <div style=" width: 300px; padding-right: 50px; padding-top: 5px">
            <el-dropdown split-button size="large" type="default" @click="handleClick">
                <!--                <div style="padding-top: 3px;padding-right: 10px;">-->
                <!--                    <el-image-->
                <!--                            style="width: 35px; height: 35px"-->
                <!--                            :src="user.profilePhoto"-->
                <!--                            :preview-teleported="true"-->
                <!--                            :preview-src-list="[user.profilePhoto]"-->

                <!--                    />-->
                <!--                </div>-->
                <el-avatar :size="36" :src="user.profilePhoto" style="position: relative; top: 0px;"></el-avatar>
                {{user.username}}

                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="$router.push('/userinfor')" >个人信息 &nbsp&nbsp<el-icon><EditPen /></el-icon></el-dropdown-item>
                        <el-dropdown-item @click="$router.push('/login')" >退出登录 &nbsp&nbsp<el-icon><SwitchButton /></el-icon></el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>


</template>

<script>

    export default {
        name: 'Header',
        // 代替了data里面的 为实现组建联动
        props: ['user'],
        data() {
            return {
                // user: JSON.parse(sessionStorage.getItem('user'))
            }
        }
    }

</script>


<style scoped>

</style>
