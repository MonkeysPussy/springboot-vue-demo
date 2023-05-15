<template>
    <div class="info-card">
        <el-card style="width: 40%; margin: 10px">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item style="text-align: center" label-width="0">
                    <el-upload
                            style="padding-left: 150px"
                            class="avatar-uploader"
                            action="http://localhost:8888/files/upload"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                    >
                        <img v-if="form.profilePhoto" :src="form.profilePhoto" class="profilePhoto">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>

                <el-form-item label="昵称">
                    <el-input v-model="form.nickName"></el-input>
                </el-form-item>
                <el-form-item label="年龄">
                    <el-input v-model="form.age"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-input v-model="form.sex"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.address"></el-input>
                </el-form-item>

            </el-form>
            <div style="text-align: center">
                <el-button type="primary" @click="update">保存</el-button>
            </div>

        </el-card>
        <el-card class="box-card">
            <h1 style="font-style: initial">{{form.username}}</h1>

        </el-card>

    </div>


</template>

<script>
    import request from "../utils/request";

    export default {
        name: "UserInfor",
        data() {
            return {
                form: {}
            }
        },
        created() {
            let str = sessionStorage.getItem("user") || "{}"
            this.form = JSON.parse(str)
        },
        methods: {
            handleAvatarSuccess(res) {
                this.form.profilePhoto = res.data
                this.$message.success("上传成功")
                // this.update()
            },
            update() {
                request.put("/user", this.form).then(res => {
                    console.log(res)
                    if (res.code === '0') {
                        this.$message({
                            type: "success",
                            message: "更新成功"
                        })
                        sessionStorage.setItem("user", JSON.stringify(this.form))
                        // 触发Layout更新用户信息
                        this.$emit("userInfo")
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                    }
                })
            }

        }
    }
</script>

<style scoped>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 120px;
        height: 120px;
        line-height: 120px;
        text-align: center;
    }

    .box-card {
        float: left;

        margin-top: 10px;
        width: 700px;
        height: 200px;
    }


    .info-card {
        width: 100%;
        display: flex;
        flex-direction: row;
    }

    .profilePhoto {
        width: 178px;
        height: 178px;
        display: block;
    }


</style>