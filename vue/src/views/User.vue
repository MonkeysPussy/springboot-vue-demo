<template>
    <div style="padding: 10px">
        <!--    功能区域-->
        <div style="margin: 10px 0">
            <el-button type="primary" @click="toAddUser">新增</el-button>
            <el-button type="primary">导入</el-button>
            <el-button type="primary">导出</el-button>

        </div>
        <div>
            <el-upload
                    action="http://localhost:8888/files/upload"
                    :on-preview="handlePreview"
                    :on-success="filesUploadSuccess"
            >
                <el-button type="primary">Click to upload</el-button>
            </el-upload>
            <el-image
                    style="width: 100px; height: 100px"
                    :src="this.img"
                    :preview-teleported="true"
                    :preview-src-list="[this.img]"

            />

        </div>

        <!--    搜索区域-->
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入关键字" suffix-icon="Search" style="width: 25%" clearable/>
            <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
        </div>
        <el-table :data="tableData" stripe style="width: 100%">
            <el-table-column prop="id" label="ID" sortable/>
            <el-table-column prop="username" label="姓名" sortable/>
            <el-table-column prop="nickName" label="昵称"/>
            <el-table-column prop="age" label="年龄"/>
            <el-table-column prop="sex" label="性别"/>
            <el-table-column prop="address" label="住址"/>
            <el-table-column  label="角色">
                <template #default="scope">
                    <span v-if="scope.row.role ==1">管理员</span>
                    <span v-if="scope.row.role ==2">普通用户</span>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button @click="handleEdit(scope.row)" size="small">编辑</el-button>
                    <el-popconfirm width="220" confirm-button-text="开删！！" cancel-button-text="算了" :icon="InfoFilled"
                                   icon-color="#626AEF" title="确认删除吗？" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button size="small" type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <div style="margin: 10px 0">
            <el-pagination
                    v-model:current-page="currentPage"
                    v-model:page-size="pageSize"
                    :page-sizes="[5,10,20]"
                    :small="small"
                    :disabled="disabled"
                    :background="background"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
            />

            <div>


                <el-dialog v-model="dialogVisible" title="那么就新增一位用户吧！" width="30%" :before-close="handleClose">


                    <el-form :model="form" label-width="120px">
                        <el-form-item label="用户名">
                            <el-input v-model="form.username" style="width: 70%"/>
                        </el-form-item>
                        <el-form-item label="昵称">
                            <el-input v-model="form.nickName" style="width: 70%"/>
                        </el-form-item>
                        <el-form-item label="年龄">
                            <el-input v-model="form.age" style="width: 70%"/>
                        </el-form-item>
                        <el-form-item label="性别">
                            <el-radio-group v-model="form.sex" class="ml-4">
                                <el-radio label="男" size="large">男</el-radio>
                                <el-radio label="女" size="large">女</el-radio>
                                <el-radio label="未知" size="large">未知</el-radio>
                            </el-radio-group>
                        </el-form-item>

                        <el-form-item label="住址">
                            <el-input type="textarea" v-model="form.address" style="width: 70%"/>
                        </el-form-item>
                        <el-form-item label="上传照片" v-model="form.profilePhoto">
                            <el-upload
                                    ref="upload"
                                    action="http://localhost:8888/files/upload"
                                    :on-preview="handlePreview"
                                    :on-success="profilePhotoUploadSuccess"
                            >
                                <el-button type="success">上传</el-button>
                            </el-upload>
                        </el-form-item>
                    </el-form>

                    <template #footer>
                          <span class="dialog-footer">
                              <el-button @click="dialogVisible = false">拉倒</el-button>
                               <el-button type="primary" @click="doAddUser">
                                  好滴
                               </el-button>
                          </span>
                    </template>
                </el-dialog>
            </div>
        </div>

        金砂GoldSand


        <el-button type="primary">澳門</el-button>
        <el-button type="success">Success</el-button>
        <el-button type="info">Info</el-button>
        <el-button type="warning">Warning</el-button>
        <el-button type="danger">Danger</el-button>

        <div>
            <el-carousel indicator-position="outside">
                <el-carousel-item v-for="item in 4" :key="item">
                    <!--                    <h3 text="2xl" justify="center">{{ item }}</h3>-->
                </el-carousel-item>
            </el-carousel>
        </div>

    </div>
</template>

<script>
    // @ is an alias to /src

    import request from "../utils/request";

    export default {
        name: 'User',
        components: {},
        data() {
            return {
                form: {},
                dialogVisible: false,
                search: '',
                currentPage: 1,
                pageSize: 10,
                total: 0,
                tableData: [],
                img: ''

            }
        },
        created() {
            this.load()
        },
        methods: {
            load() {
                request.get("/user", {
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        search: this.search
                    }
                }).then(res => {
                    console.log(res)
                    this.tableData = res.data.records
                    this.total = res.data.total
                })
            },

            toAddUser() {
                this.dialogVisible = true
                this.form = {}
                this.$refs['upload'].clearFiles()  //清除历史文件列表

            },
            doAddUser() {
                if (this.form.id) {      //更新
                    request.put("/user", this.form).then(res => {
                        console.log(res)
                        if (res.code == '0') {
                            this.$message({
                                type: "success",
                                message: "更新成功"
                            })
                        } else {
                            this.$message({
                                type: "error",
                                message: res.msg
                            })
                        }
                        this.load()     //刷新表格数据
                        this.dialogVisible = false
                    })
                } else {        //新增
                    request.post("/user", this.form).then(res => {
                        console.log(res)
                        if (res.code == '0') {
                            this.$message({
                                type: "success",
                                message: "新增成功"
                            })
                        } else {
                            this.$message({
                                type: "error",
                                message: res.msg
                            })
                        }
                        this.load()     //刷新表格数据
                        this.dialogVisible = false

                    })
                }

            },
            handleEdit(row) {
                this.form = JSON.parse(JSON.stringify(row))
                this.dialogVisible = true
                this.$nextTick(() => {
                    this.$refs['upload'].clearFiles()  //清除历史文件列表
                })
            },
            handleDelete(id) {
                console.log(id)
                request.delete("/user/" + id).then(res => {
                    if (res.code == '0') {
                        this.$message({
                            type: "success",
                            message: "删除成功"
                        })
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                    }
                })
                this.load()
            },
            handleSizeChange(pageSize) {        //改变页码触发
                this.pageSize = pageSize
                this.load()
            },
            handleCurrentChange(pageNum) {     //改变当前页码触发
                this.currentPage = pageNum
                this.load()
            },
            filesUploadSuccess(res) {
                console.log(res)
                this.img = res.data
                console.log(this.img)
            },
            profilePhotoUploadSuccess(res) {
                console.log(res)
                this.form.profilePhoto = res.data
            }
        }

    }
</script>

<style scoped>

</style>
