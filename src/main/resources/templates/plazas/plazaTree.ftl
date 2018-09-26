<#assign  sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<!DOCTYPE html>
  <head>
    <base href="<%=basePath%>">
    <title>网点管理</title>
<#include "/common/header.ftl">
  </head>
  <body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox-content">
					<div id="SysPlazaTree"></div>
				</div>
			</div>
		</div>
	</div>

<#include "/common/footer.ftl">
<script type="text/javascript">
	$(document).ready(function() {
		getTreeData()
	});
	function getTreeData() {
		$.ajax({
			type : "GET",
			url : "/sysplaza/tree",
			success : function(tree) {
				loadTree(tree);
			}
		});
	}
	function loadTree(tree) {
		$('#SysPlazaTree').jstree({
			'core' : {
				'data' : tree
			},
			"plugins" : [ "search" ]
		});
		$('#SysPlazaTree').jstree().open_all();
	}
	$('#SysPlazaTree').on("changed.jstree", function(e, data) {
		if (data.node.id != -1) {
			parent.loadSysplaza(data.node.id, data.node.text);
			var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
			parent.layer.close(index);
		}
	});
</script>
</body>
</html>
