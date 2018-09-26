<#assign  sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<!DOCTYPE html>
  <head>

    <title>添加网点</title>
<#include "/common/header.ftl">
  </head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="signupForm">
							<div class="form-group">
								<label class="col-sm-3 control-label">上级网点：</label>
								<div class="col-sm-8">
									<input id="parentId" name="parentId" value="${pId}"
										class="form-control hidden" type="text">
								</div>
								<div class="col-sm-8">
									<input id="pName" name="pName" value="${pName}"
										class="form-control" type="text" readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">网点名称：</label>
								<div class="col-sm-8">
									<input id="plazaName" name="plazaName" class="form-control" type="text">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">排序：</label>
								<div class="col-sm-8">
									<input id="orderNum" name="orderNum" class="form-control"
										type="text">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">状态：(0-在用;1-停用)</label>
								<div class="col-sm-8">
									<input id="delFlag" name="delFlag" class="form-control"
										type="text">
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
$().ready(function() {
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/sysplaza/savePlaza",
		data : $('#signupForm').serialize(),// formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功",{icon:1});
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
			} else {
				parent.layer.alert(data.msg,{icon:0})
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
            plazaName : {
				required : true
			}
		},
		messages : {
            plazaName : {
				required : icon + "请输入网点名称"
			}
		}
	})
}
</script>
</body>
</html>
