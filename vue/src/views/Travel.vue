<template>
    <div style="padding: 10px">
        <!--    功能区域-->


        <!--    搜索区域-->
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入你要查找的地方" suffix-icon="Search" style="width: 25%" clearable/>
            <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>

            <el-button type="primary" @click="toAddTravel">新增</el-button>

        </div>
        <el-table :data="tableData" stripe style="width: 100%">
            <el-table-column width="70px" prop="pid" label="ID" sortable/>
            <el-table-column width="100px" prop="pname" label="地点" sortable/>
            <el-table-column prop="img" label="照片">
                <template #default="scope">
                    <el-image
                            style="width: 200px; height: 120px"
                            :src="scope.row.img"
                            :preview-teleported="true"
                            :preview-src-list="[scope.row.img]"

                    />
                </template>
            </el-table-column>
            <el-table-column prop="ptype" label="类型"/>
            <el-table-column prop="province" label="省份"/>
            <el-table-column prop="traffic" label="交通线路"/>
            <el-table-column prop="paddress" label="地址"/>
            <el-table-column width="300px" prop="details" label="描述"/>
            <el-table-column width="132px" label="操作">
                <template #default="scope">
                    <el-button @click="handleEdit(scope.row)" size="small">编辑</el-button>
                    <el-popconfirm width="220" confirm-button-text="开删！！" cancel-button-text="算了" :icon="InfoFilled"
                                   icon-color="#626AEF" title="确认删除吗？" @confirm="handleDelete(scope.row.pid)">
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


                <el-dialog v-model="dialogVisible" title="提示" width="30%" :before-close="handleClose">


                    <el-form :model="form" label-width="120px">
                        <el-form-item label="地点">
                            <el-input v-model="form.pname" style="width: 70%"/>
                        </el-form-item>
                        <el-form-item label="类别">
                            <el-radio-group v-model="form.ptype" class="ml-4">
                                <el-radio label="商场" size="large">商场</el-radio>
                                <el-radio label="步行街" size="large">步行街</el-radio>
                                <el-radio label="公园" size="large">公园</el-radio>
                                <el-radio label="历史景点" size="large">历史景点</el-radio>
                                <el-radio label="建筑" size="large">建筑</el-radio>
                                <el-radio label="网红打卡点" size="large">网红打卡点</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="省份">
                            <el-input v-model="form.province" style="width: 70%"/>
                        </el-form-item>

                        <el-form-item label="交通路线">
                            <el-input type="textarea" v-model="form.traffic" style="width: 70%"/>
                        </el-form-item>
                        <el-form-item label="地址">
                            <el-input type="textarea" v-model="form.paddress" style="width: 70%"/>
                        </el-form-item>
                        <el-form-item label="描述">
                            <el-input type="textarea" v-model="form.details" style="width: 70%"/>
                        </el-form-item>
                        <el-form-item label="上传照片" v-model="form.img">
                            <el-upload
                                    ref="upload"
                                    action="http://localhost:8888/files/upload"
                                    :on-preview="handlePreview"
                                    :on-success="filesUploadSuccess"
                            >
                                <el-button type="success">上传</el-button>
                            </el-upload>
                        </el-form-item>
                    </el-form>

                    <template #footer>
                          <span class="dialog-footer">
                              <el-button @click="dialogVisible = false">拉倒</el-button>
                               <el-button type="primary" @click="doAddTravel">
                                  好滴
                               </el-button>
                          </span>
                    </template>
                </el-dialog>
            </div>
        </div>


    </div>
</template>

<script>
    // @ is an alias to /src

    import request from "../utils/request";

    export default {
        name: "Travel",

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
                request.get("/travel", {
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

            toAddTravel() {
                this.dialogVisible = true
                this.form = {}
                this.$refs['upload'].clearFiles()  //清除历史文件列表

            },
            doAddTravel() {
                if (this.form.pid) {      //更新
                    request.put("/travel", this.form).then(res => {
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
                    request.post("/travel", this.form).then(res => {
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
            handleDelete(pid) {
                console.log(pid)
                request.delete("/travel/" + pid).then(res => {
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

