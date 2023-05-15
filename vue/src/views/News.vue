<template>
    <div style="padding: 10px">
        <!--    功能区域-->


        <!--    搜索区域-->
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入你要查找的地方" suffix-icon="Search" style="width: 25%" clearable/>
            <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>

            <el-button type="primary" @click="toAddNews">新增</el-button>

        </div>
        <el-table :data="tableData" stripe style="width: 100%">
            <el-table-column prop="id" label="ID" sortable/>
            <el-table-column prop="title" label="标题" sortable/>

            <el-table-column prop="author" label="作者"/>
            <el-table-column prop="time" label="时间"/>

            <el-table-column width="200px" label="操作">
                <template #default="scope">
                    <el-button @click="details(scope.row)" size="small">详情</el-button>
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


                <el-dialog v-model="dialogVisible" title="提示" width="65%" :before-close="handleClose">


                    <el-form :model="form" label-width="120px">
                        <el-form-item label="标题">
                            <el-input v-model="form.title" style="width: 70%"/>
                        </el-form-item>

                        <!--                        <el-form-item label="内容 ">-->
                        <!--                            <el-input v-model="form.content" style="width: 70%"/>-->
                        <!--                        </el-form-item>-->

                        <div id="div1"></div>
                    </el-form>

                    <template #footer>
                          <span class="dialog-footer">
                              <el-button @click="dialogVisible = false">拉倒</el-button>
                               <el-button type="primary" @click="doAddNews">
                                  好滴
                               </el-button>
                          </span>
                    </template>
                </el-dialog>
            </div>
        </div>

        <el-dialog v-model="detailsVisible" title="详情" width="65%" :before-close="handleClose">
            <el-card style="min-height: 200px;">
                <div v-html="this.detail.content"></div>
            </el-card>
        </el-dialog>


    </div>
</template>

<script>
    // @ is an alias to /src

    import request from "../utils/request";
    import E from "wangeditor";

    let editor;


    export default {
        name: "News",

        components: {},
        data() {
            return {
                form: {},
                dialogVisible: false,
                detailsVisible: false,
                search: '',
                currentPage: 1,
                pageSize: 10,
                total: 0,
                tableData: [],
                img: '',
                detail: {}

            }
        },
        created() {
            this.load()
        },
        methods: {
            load() {
                request.get("/news", {
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

            toAddNews() {
                this.dialogVisible = true
                this.form = {}
                this.$nextTick(() => {
                    editor = new E("#div1")

                    // 配置 server 接口地址
                    editor.config.uploadImgServer = 'http://localhost:8888/files/eidtor/upload'
                    editor.config.uploadFileName = "file"
                    editor.create()
                })


            },
            doAddNews() {
                this.form.content = editor.txt.html()       // 获取编辑对象的值，然后赋予到实体中
                if (this.form.id) {      //更新
                    request.put("/news", this.form).then(res => {
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
                    let userStr = sessionStorage.getItem("user") || {}
                    let user = JSON.parse(userStr)
                    this.form.author = user.username
                    request.post("/news", this.form).then(res => {
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
                    editor = new E("#div1")
                    editor.create()
                    editor.txt.html(row.content)
                })
            },
            details(row) {
                this.detail = row
                this.detailsVisible = true
            },
            handleDelete(id) {
                console.log(id)
                request.delete("/news/" + id).then(res => {
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
                this.form.img = res.data
                console.log(this.form.img)
            }
        }

    }
</script>

<style scoped>

</style>

