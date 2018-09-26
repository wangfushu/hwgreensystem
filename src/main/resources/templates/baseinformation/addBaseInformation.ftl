<#assign  sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<!DOCTYPE html>
<head>
<title>添加基础信息</title>
<#include "/common/header.ftl">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content ">
    <table id="dg" class="easyui-datagrid" title="基础信息编辑" style="width:805px;height:auto"
           data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',
				onClickRow: onClickRow
			">
        <thead>
        <tr>
            <th data-options="field:'biType',width:150,align:'center'">类型</th>
            <th data-options="field:'biTypeId',width:150,align:'center',hidden: true">biTypeId</th>
            <th data-options="field:'biValue',width:60,align:'center',editor:{type:'numberbox',options:{required:true}}">类型值</th>
            <th data-options="field:'biDescription',width:250,align:'center',editor:{type:'textbox',options:{required:true}}">描述</th>
            <th data-options="field:'sort',width:60,align:'center',editor:{type:'numberbox',options:{required:true}}">排序</th>
            <th data-options="field:'id',width:60,align:'center',hidden: true">id</th>
            <th data-options="field:'aMemo',width:250,align:'center',editor:{type:'checkbox',options:{on:'1',off:'0'}}">是否禁用（0-禁用/1-启用）</th>
        </tr>
        </thead>
        <tbody>
        <#list  sysBaseInformations  as sysBaseInformation>
            <tr>
                <td>${sysBaseInformation.biType}</td>
                <td>${sysBaseInformation.biTypeId}</td>
                <td>${sysBaseInformation.biValue!""}</td>
                <td>${sysBaseInformation.biDescription!""}</td>
                <td>${sysBaseInformation.sort!""}</td>
                <td>${sysBaseInformation.id}</td>
                <td>${sysBaseInformation.aMemo!""}</td>
            </tr>
        </#list>
        </tbody>
    </table>
    <div id="tb" style="height:auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
<#--        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">移除</a>-->
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="accept()">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="save();return false">提交</a>
 </div>
</div>
<#include "/common/footer.ftl">
<!--easyUI的引入-->
<link rel="stylesheet" type="text/css" href="/global/easyui/1.5.4.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/global/easyui/1.5.4.5/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/global/easyui/1.5.4.5/demo/demo.css">
<script type="text/javascript" src="/global/easyui/1.5.4.5/jquery.easyui.min.js"></script>

<script type="text/javascript">

    var editIndex = undefined;
    function endEditing(){
        if (editIndex == undefined){return true}
        if ($('#dg').datagrid('validateRow', editIndex)){
            $('#dg').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    function onClickRow(index){
        if (editIndex != index){
            if (endEditing()){
                $('#dg').datagrid('selectRow', index)
                        .datagrid('beginEdit', index);
                editIndex = index;
            } else {
                $('#dg').datagrid('selectRow', editIndex);
            }
        }
    }

    function append(){
        if (endEditing()){
            var rows=$('#dg').datagrid('getRows');
            editIndex = $('#dg').datagrid('getRows').length-1;
            $('#dg').datagrid('appendRow',{biType:rows[editIndex]['biType'],biTypeId:rows[editIndex]['biTypeId'],biValue:Number(rows[editIndex]['biValue'])+1,sort:Number(rows[editIndex]['sort'])+1,aMemo:'1'});
/*            $('#dg').datagrid('appendRow',{biValue:rows[editIndex]['biValue']});
            $('#dg').datagrid('appendRow',{sort:rows[editIndex]['sort']});*/
            editIndex = $('#dg').datagrid('getRows').length-1;
            $('#dg').datagrid('selectRow', editIndex)
                    .datagrid('beginEdit', editIndex);
        }
    }
    function removeit(){
        if (editIndex == undefined){return}
        $('#dg').datagrid('cancelEdit', editIndex)
                .datagrid('deleteRow', editIndex);
        editIndex = undefined;
    }
    function accept(){
        if (endEditing()){
            $('#dg').datagrid('acceptChanges');
        }
    }


   /* $().ready(function () {
        validateRule();
    });
    $.validator.setDefaults({
        submitHandler: function () {
            save();
        }
    });*/




    function save() {
        if(!endEditing()){
            layer.msg("请先完成编辑内容",{icon:1});
            return false;
        }
        var rows = $('#dg').datagrid('getRows');
        var entities = "";

        for(i = 0;i < rows.length;i++)
        {
            entities = entities  + JSON.stringify(rows[i])+",";
        }
        /*entities=entities.trim();*/
        entities=entities.substring(0,entities.length-1);
        /*entities.substring(0,entities.length-1);*/
        entities="["+entities+"]"

        $.ajax({
            cache: true,
            type: "POST",
            url:  "/baseinformation/saveBaseInformation",
            data: {'entities': entities},// formid
            async: true,
            error: function (request) {
                parent.layer.alert("Connection error");
            },
            success: function (data) {
                if (data.code == 0) {
                    parent.layer.msg("更新成功", {icon: 1});
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
                    minlength: 3,
                    maxlength: 40
                },
                pName: {
                    required: true,
                },
                roleId:{
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
                    maxlength: icon+"姓名必须40个字符之下"

                },
                pName: {
                    required: icon + "请选择网点",
                },
                roleId: {
                    required: icon + "请选择角色",
                },
                telphone: icon + "请输入您的手机号码",
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

    function  openSysplaza() {
        layer.open({
            type : 2,
            title : "选择网点",
            area : [ '300px', '450px' ],
            offset:['15px'],
            content : "/sysplaza/treeView"
        })
    }

    function loadSysplaza(parentId, pName) {
        $("#plazaId").val(parentId);
        $("#pName").val(pName);
    }
</script>
</body>
</html>
