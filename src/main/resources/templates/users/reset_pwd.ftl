<#assign  sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<!DOCTYPE html>
<html>
  <head>
    <title>重置密码</title>
<#include "/common/header.ftl">
  </head>
  <body class="gray-bg">
  	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox-content">
					<form class="form-horizontal m-t" id="signupForm">
						<input id="id" name="id" value="${user.id}" type="hidden">
						<div class="form-group">
							<label class="col-sm-3 control-label">输入密码：</label>
							<div class="col-sm-8">
								<input id="password" name="password" class="form-control" type="password" value="${user.password!""}">
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
<#include "/common/footer.ftl">
<script type="text/javascript">

	$(document).ready(function() {
		validateRule();
	});
	$.validator.setDefaults({
		submitHandler : function() {
			update();
		}
	});
	function update() {
		$.ajax({
			cache : true,
			type : "POST",
			url : "/user/resetPwd",
			data : $('#signupForm').serialize(),// formid
			async : false,
			error : function(request) {
				parent.layer.msg("系统错误，联系管理员");
			},
			success : function(data) {
				if (data.code == 0) {
					parent.layer.msg(data.msg,{icon:1});
					parent.reLoad();
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);
				} else {
					parent.layer.msg(data.msg,{icon:0});
				}

			}
		});

	}

	function validateRule() {
		var icon = "<i class='fa fa-times-circle'></i> ";
		$("#signupForm").validate({
			rules : {
				password : {
					required : true,
					minlength : 6
				}
			},
			messages : {
				password : {
					required : icon + "请输入您的密码",
					minlength : icon + "密码必须6个字符以上"
				}
			}
		})
	}
</script>
</body>
</html>
