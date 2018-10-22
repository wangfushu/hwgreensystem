<#assign  sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<!DOCTYPE html>
<head>
    <title>用户管理</title>
<#include "/common/header.ftl">
</head>
<body class="gray-bg">
<div class="row">
    <div class="wrapper wrapper-content ">
        <div class="col-sm-3">
            <div class="ibox ibox-body">
                <div class="ibox-title">
                    <h5>选择网点</h5>
                </div>
                <div class="ibox-content">
                    <div id="jstree"></div>
                </div>
            </div>
        </div>
        <div class="col-sm-8">
            <div class="ibox">
                <div class="ibox-body">
                    <div id="exampleToolbar" role="group">
                        <button type="button" class="btn btn-primary" onclick="add()">
                            <i class="fa fa-plus" aria-hidden="true"></i>添加
                        </button>
                        <button type="button" class="btn btn-danger" onclick="batchRemove()">
                            <i class="fa fa-trash" aria-hidden="true"></i>删除
                        </button>
                    </div>
                    <div class="columns pull-right">
                        <button class="btn btn-success" onclick="reLoad()">查询</button>
                    </div>
                    <div class="columns pull-right col-md-2 nopadding">
                        <input id="searchName" type="text" class="form-control" placeholder="姓名or工号">
                    </div>
                    <table id="exampleTable" data-mobile-responsive="true" class="table-condensed">
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div>
        <script type="text/javascript">
            var s_edit_h = 'hidden';
            var s_remove_h = 'hidden';
            var s_resetPwd_h = 'hidden';
        </script>
    </div>
    <div>
        <script type="text/javascript">
            s_edit_h = '';
        </script>
    </div>
    <div>
        <script type="text/javascript">
            var s_remove_h = '';
        </script>
    </div>
    <div>
        <script type="text/javascript">
            var s_resetPwd_h = '';
        </script>
    </div>
</div>

<#include "/common/footer.ftl">
<script type="text/javascript">
    $(function () {
        var deptId = '';
        getTreeData();
        load(deptId)
    });

    function load(plazaId) {
        $('#exampleTable').bootstrapTable({
            method : 'get', // 服务器数据的请求方式 get or post
            url :  "/user/listUser", // 服务器数据的加载地址
            iconSize : 'outline',
            toolbar : '#exampleToolbar',
            striped : true, // 设置为true会有隔行变色效果
            dataType : "json", // 服务器返回的数据类型
            pagination : true, // 设置为true会在底部显示分页条
            // queryParamsType : "limit",
            // //设置为limit则会发送符合RESTFull格式的参数
            singleSelect : false, // 设置为true将禁止多选
            pageSize : 20, // 如果设置了分页，每页数据条数
            pageNumber : 1, // 如果设置了分布，首页页码
            // search : true, // 是否显示搜索框
            showColumns : false, // 是否显示内容下拉框（选择显示的列）
            sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
            // "server"
            queryParams : function(params) {
                return {
                    // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                    limit : params.limit,
                    offset : params.offset,
                    name : $('#searchName').val(),
                    plazaId : plazaId
                };
            },
            columns : [
                {
                    checkbox : true
                },
                {
                    field : 'userNo', // 列字段名
                    title : '工号' // 列标题
                },
                {
                    field : 'userName',
                    title : '姓名'
                },
                {
                    field : 'telphone',
                    title : '手机号'
                },
                {
                    field : 'roleName',
                    title : '角色'
                },
                {
                    field : 'remark',
                    title : '备注'
                },
                {
                    field : 'status',
                    title : '状态',
                    align : 'center',
                    formatter : function(value, row, index) {
                        if (value == '0') {
                            return '<span class="label label-danger">离职</span>';
                        } else if (value == '1') {
                            return '<span class="label label-primary">在职</span>';
                        }
                    }
                },
                {
                    title : '操作',
                    field : 'id',
                    align : 'center',
                    formatter : function(value, row, index) {
                        var e = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '"  mce_href="#" title="编辑" onclick="edit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit "></i></a> ';
                        var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" " title="删除"  mce_href="#" onclick="remove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                        var f = '<a class="btn btn-success btn-sm ' + s_resetPwd_h + '" title="重置密码"  mce_href="#" onclick="resetPwd(\''
                                + row.id
                                + '\')"><i class="fa fa-key"></i></a> ';
                        return e + d + f;
                    }
                } ]
        });
    }

    function reLoad() {
        $('#exampleTable').bootstrapTable('refresh');
    }


    //获取部门数据
    function getTreeData() {
        $.ajax({
            type: "GET",
            url: "/sysplaza/tree",
            success: function (tree) {
                loadTree(tree);
            }
        });
    }
    //加载部门数据到jstree
    function loadTree(tree) {
        $('#jstree').jstree({
            'core': {
                'data': tree
            },
            "plugins": ["search"]
        });
        $('#jstree').jstree().open_all();
    }


    //添加操作
    function add() {
        // iframe层
        layer.open({
            type : 2,
            title : '新增用户',
            maxmin : true,
            shadeClose : false, // 点击遮罩关闭层
            area : [ '800px', '480px' ],
            content : '/user/addUser' // iframe的url
        });
    }
    //删除操作
    function remove(id) {
        layer.confirm('确定要删除选中的记录？', {btn : [ '确定', '取消' ]
        }, function() {
            $.ajax({
                url : "/user/removeUser",
                type : "post",
                data : {
                    'id' : id
                },
                success : function(r) {
                    if (r.code == 0) {
                        layer.msg("删除成功",{icon:1});
                        reLoad();
                    } else {
                        layer.msg(r.msg,{icon:0});
                    }
                }
            });
        })
    }
    //编辑操作
    function edit(id) {
        layer.open({
            type : 2,
            title : '修改用户',
            maxmin : true,
            shadeClose : true, // 点击遮罩关闭层
            area : [ '800px', '480px' ],
  /*          offset:'100px',*/
            content : '/user/editUser/' + id // iframe的url
        });
    }
    //批量删除操作
    function batchRemove() {
        var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
        if (rows.length == 0) {
            layer.msg("请选择要删除的数据",{icon:3});
            return;
        }
        layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?",{btn : [ '确定', '取消' ]
        }, function() {
            var ids = new Array();
            $.each(rows, function(i, row) {
                ids[i] = row['id'];
            });
            console.log(ids);
            $.ajax({
                type : 'POST',
                data : {
                    "ids" : ids
                },
                url : '/user/batchRemoveUser',
                success : function(r) {
                    if (r.code == 0) {
                        layer.msg(r.msg,{icon:1});
                        reLoad();
                    } else {
                        layer.msg(r.msg,{icon:0});
                    }
                }
            });
        }, function() {
            layer.msg("取消操作",{icon:1});
        });
    }
    //重置密码
    function resetPwd(id) {
        layer.open({
            type : 2,
            title : '重置密码',
            maxmin : true,
            shadeClose : false, // 点击遮罩关闭层
            area : [ '400px', '260px' ],
            content : '/user/resetPwd/' + id // iframe的url
        });
    }

    //渲染数据到页面
    $('#jstree').on("changed.jstree", function(e, data) {
        if (data.selected == -1) {
            var opt = {
                query : {
                    plazaId : '',
                }
            }
            $('#exampleTable').bootstrapTable('refresh', opt);
        } else {
            var opt = {
                query : {
                    plazaId : data.selected[0],
                }
            }
            $('#exampleTable').bootstrapTable('refresh', opt);
        }

    });

</script>
</body>
</html>
