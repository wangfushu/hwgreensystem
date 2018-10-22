<#assign  sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<!DOCTYPE html>
  <head>

    <title>网点管理</title>
<#include "/common/header.ftl">
  </head>
  <body class="gray-bg">
<#--  <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 基础信息管理 <span
          class="c-gray en">&gt;</span> 网点管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
                                                href="javascript:location.replace(location.href);" title="刷新"><i
          class="Hui-iconfont">&#xe68f;</i></a></nav>-->
<div class="col-sm-12"> <a class="btn  btn-primary" style="line-height:1.6em;margin-top:3px;float: right;"
         href="javascript:location.replace(location.href);" title="刷新"><i class="fa fa-refresh"></i></a></div>
	<div class="wrapper wrapper-content ">
		<div class="col-sm-12">
			<div class="ibox">
				<div class="ibox-body">
					<div id="exampleToolbar" role="group">
						<button type="button" class="btn  btn-primary" onclick="free_add()">
							<i class="fa fa-plus hidden" aria-hidden="true"></i>添加网点</button>
					</div>
					<table id="exampleTable" data-mobile-responsive="true">
					</table>
				</div>
			</div>
		</div>
	</div>
		<div>
			<script type="text/javascript">
				var s_add_h = 'hidden';
				var s_edit_h = 'hidden';
				var s_remove_h = 'hidden';
			</script>
		</div>
		<div>
			<script type="text/javascript">
				s_add_h = '';
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
<#include "/common/footer.ftl">
<script type="text/javascript">
	$(function() {
	load();
});
function load() {
	$('#exampleTable')
		.bootstrapTreeTable(
			{
				id : 'plazaId',
				code : 'plazaId',
                parentCode : 'parentId',
				type : "GET", // 请求数据的ajax类型
				url : '/sysplaza/listSysPlaza', // 请求数据的ajax的url
				ajaxParams : {}, // 请求数据的ajax的data属性
				expandColumn : '1', // 在哪一列上面显示展开按钮
				striped : true, // 是否各行渐变色
				bordered : true, // 是否显示边框
				expandAll : true, // 是否全部展开
				// toolbar : '#exampleToolbar',
				columns : [
					{
						title : '网点编号',
						field : 'plazaId',
						visible : false,
						align : 'center',
						valign : 'middle',
						width : '50px'
					},
					{
						field : 'plazaName',
						title : '网点名称'
					},{
                        field : 'level',
                        title : '级别'
                    },
					{
						field : 'orderNum',
						title : '排序'
					},
					{
						field : 'delFlag',
						title : '状态',
						align : 'center',
						formatter : function(item, index) {
							if (item.delFlag == '0') {
								return '<span class="label label-danger">禁用</span>';
							} else if (item.delFlag == '1') {
								return '<span class="label label-primary">正常</span>';
							}
						}
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(item, index) {
							var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '"  mce_href="#" title="编辑" onclick="edit(\''
								+ item.plazaId
								+ '\')"><i class="fa fa-edit"></i></a> ';
							var a = '<a class="btn btn-primary btn-sm ' + s_add_h + '"  title="增加下級"  mce_href="#" onclick="add(\''
								+ item.plazaId
								+ '\')"><i class="fa fa-plus"></i></a> ';
							var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '"  title="删除"  mce_href="#" onclick="removeone(\''
								+ item.plazaId
								+ '\')"><i class="fa fa-remove"></i></a> ';
							var f = '<a class="btn btn-success btn-sm＂  title="备用"  mce_href="#" onclick="resetPwd(\''
								+ item.plazaId
								+ '\')"><i class="fa fa-key"></i></a> ';
                            if(item.plazaId==1) {
                                return  a;
                            }
                            else{
                                return e + a+d;
							}

						}
					} ]
			});
}

	function reLoad() {
		load();
	}
    var addIndex;
    function add(plazaId) {
        addIndex = layer.open({
            type : 2,
            title : '增加网点',
            zIndex:1989,
            area: ['800px', '420px'],
            content : '/sysplaza/addSysPlaza/' + plazaId
        });
    }
    function free_add() {
        layer.open({
            type : 2,
            title : '增加网点',
            area : [ '800px', '520px' ],
            content : '/sysplaza/freeAddSysPlaza'
        });
    }

    function edit(id) {
        layer.open({
            type : 2,
            title : '修改网点',
            maxmin : true,
            shadeClose : false, // 点击遮罩关闭层
            area : [ '800px', '520px' ],
            content : '/sysplaza/editSysPlaza/' + id // iframe的url
        });
    }
    function removeone(id) {
        layer.confirm('确定要删除选中的记录？', {
            btn : [ '确定', '取消' ]
        }, function() {
            $.ajax({
                url : "/sysplaza/removeSysPlaza",
                type : "post",
                data : {
                    'plazaId' : id
                },
                success : function(r) {
                    if (r.code == 0) {
                        layer.msg(r.msg,{icon:1});
                        reLoad();
                    } else {
                        layer.msg(r.msg,{icon:0});
                    }
                }
            });
        })
    }
</script>
</body>
</html>
