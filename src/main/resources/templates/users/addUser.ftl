<#assign  sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<!DOCTYPE html>
<head>
    <title>添加用户</title>
<#include "/common/header.ftl">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content ">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="signupForm">
                        <input id="id" name="id" type="hidden">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">工号：</label>
                            <div class="col-sm-8">
                                <input id="userNo" name="userNo" class="form-control" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">姓名：</label>
                            <div class="col-sm-8">
                                <input id="userName" name="userName" class="form-control" type="text">
                            </div>
                        </div>
                        <div class="form-group">

                            <label class="col-sm-3 control-label">密码：</label>
                            <div class="col-sm-8">
                                <input id="password" name="password" class="form-control" type="password">
                            </div>
                        </div>
                        <div class="form-group">

                            <label class="col-sm-3 control-label">确认密码：</label>
                            <div class="col-sm-8">
                                <input id="confirm_password" name="confirm_password" class="form-control"
                                       type="password">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">网点：</label>
                            <div class="col-sm-8">
                                <input id="plazaId" name="sysPlaza.plazaId" class="hidden"> <input
                                    id="pName" name="pName" class="form-control" type="text"
                                    style="cursor: pointer;" onclick="openSysplaza()"
                                    readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态:</label>
                            <div class="col-sm-8">
                                <label class="radio-inline"> <input type="radio"
                                                                    name="status" value="1" checked/> 在职
                                </label> <label class="radio-inline"> <input type="radio"
                                                                             name="status" value="0"/> 离职
                            </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">角色:</label>
                            <div class="col-sm-8">
                                <#list roles as role>
                                    <label class="radio-inline"> <input name="roleId"
                                                                        type="radio" value="${role.id}">${role.remark}
                                    </label>
                                </#list>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">电话号码：</label>
                            <div class="col-sm-8">
                                <input id="telphone" name="telphone" class="form-control">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <input id="remark" name="remark" class="form-control"
                                >
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button type="submit" class="btn btn-primary">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "/common/footer.ftl">
<script type="text/javascript">
    $().ready(function () {
        validateRule();
    });
    $.validator.setDefaults({
        submitHandler: function () {
            save();
        }
    });


    function save() {
        $.ajax({
            cache: true,
            type: "POST",
            url: "/user/saveUser",
            data: $('#signupForm').serialize(),// formid
            async: false,
            error: function (request) {
                parent.layer.alert("Connection error");
            },
            success: function (data) {
                if (data.code == 0) {
                    parent.layer.msg("添加成功", {icon: 1});
                    parent.reLoad();
                    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                    parent.layer.close(index);
                } else {
                    parent.layer.alert(data.msg, {icon: 0})
                }
            }
        });
    }

    function validateRule() {
        var icon = "<i class='fa fa-times-circle'></i> ";
        $("#signupForm").validate({
            rules: {
                userNo: {
                    required: true,
                    minlength: 2,
                    maxlength: 40,
                    remote: {
                        url: "/user/usernameNotExist", // 后台处理程序
                        type: "post", // 数据发送方式
                        dataType: "json", // 接受数据格式
                        data: { // 要传递的数据
                            userNo: function () {
                                return $("#userNo").val();
                            }
                        }
                    }
                },
                userName: {
                    required: true,
                    minlength: 2,
                    maxlength: 40
                },
                pName: {
                    required: true,
                },
                roleId: {
                    required: true,
                },
                password: {
                    required: true,
                    minlength: 6
                },
                confirm_password: {
                    required: true,
                    minlength: 6,
                    equalTo: "#password"
                },
                telphone: {
                    maxlength: 11,
                },
                status:{
                    required: true,
                }
            },
            messages: {
                userNo: {
                    required: icon + "请输入您的工号",
                    minlength: icon + "工号必须两个字符以上",
                    remote: icon + "工号已经存在"
                },
                userName: {
                    required: icon + "请输入您的姓名",
                    minlength: icon + "姓名必须两个字符以上",
                    maxlength: icon + "姓名必须40个字符之下"

                },
                pName: {
                    required: icon + "请选择网点",
                },
                roleId: {
                    required: icon + "请选择角色",
                },
                status:{
                    required: icon + "请选择在职状态",
                },
                telphone: {
                    maxlength: icon + "手机号过长",
                },
                password: {
                    required: icon + "请输入您的密码",
                    minlength: icon + "密码必须6个字符以上"
                },
                confirm_password: {
                    required: icon + "请再次输入密码",
                    minlength: icon + "密码必须6个字符以上",
                    equalTo: icon + "两次输入的密码不一致"
                }
            }
        })
    }

    function openSysplaza() {
        layer.open({
            type: 2,
            title: "选择网点",
            area: ['300px', '450px'],
            offset: ['15px'],
            content: "/sysplaza/treeView"
        })
    }

    function loadSysplaza(parentId, pName) {
        $("#plazaId").val(parentId);
        $("#pName").val(pName);
    }
</script>
</body>
</html>
