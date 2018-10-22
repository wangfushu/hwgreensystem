<#assign  sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<!DOCTYPE html>
  <head>
    <title>车辆信息报表</title>
<#include "/common/header.ftl">
<style>
</style>
</head>
<body class="gray-bg">
<#--<div class="col-sm-12"> <a class="btn  btn-primary" style="line-height:1.6em;margin-top:3px;float: right;"
         href="javascript:location.replace(location.href);" title="刷新"><i class="fa fa-refresh"></i></a></div>-->
	<div class="wrapper wrapper-content ">
		<div class="col-sm-12">
            <div class="columns pull-left">
                <div class="columns pull-left">
                    车牌号：<input id="plateNo" class="form-control" name="plateNo" type="text" placeholder="车牌号" value="${param.plateNo!""}">
                </div>
<#--                <div class="columns pull-left">
                    网点：<input id="plazaName" name="plazaName" class="form-control" type="text" placeholder="网点名称" value="${param.plazaName!""}">
                </div>-->
                <div class="columns pull-left">
                    网点：
                    <select class="inputSelect form-control"  name="type" id="plazaId">
                        <option value=""
						        <#if param.plazaId?? &&""=="${param.plazaId?long}">selected</#if>
                        >当前权限下所有站点</option>
                         <#list sysPlazaList as sysPlaza>
                             <option value="${sysPlaza.plazaId?long}"
						        <#if param.plazaId?? &&sysPlaza.plazaId=="${param.plazaId?long}">selected</#if>
                             >${sysPlaza.plazaName}</option>
                         </#list>
                    </select>
                </div>

                <div class="columns pull-left">
                    车辆类型：
                    <select class="inputSelect form-control"  name="type" id="vehicleType">
                        <option value=""
						        <#if param.type?? &&""=="${param.type?string}">selected</#if>
                        >所有</option>
                         <#list sysBaseInformations as vehicletype>
                             <option value="${vehicletype.biValue?string}"
						        <#if param.type?? &&vehicletype.biValue=="${param.type?string}">selected</#if>
                             >${vehicletype.biDescription}</option>
                         </#list>
                    </select>
                </div>
                <div class="columns pull-left">
                    出口站时间：
                    <div class="form-inline">
                        <input type="text"  name="timefrom" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'logmax\')||\'%y-%M-%d\'}' })"
                               id="logmin" value="${param.timefrom!''}"
                               class="inputDate form-control Wdate" style="width:120px;">
                        -
                        <input type="text"   onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'logmin\')}',maxDate:'%y-%M-%d' })"
                               id="logmax"
                               value="${param.timeto!''}"
                               class="inputDate form-control Wdate" name="timeto" style="width:120px;">
                    </div>

                </div>

            </div>

			<div class="ibox">
				<div class="ibox-body">
                    <div id="exampleToolbar" role="group">
                        <div class="columns pull-left ">
                            <button class="btn btn-success" onclick="reLoad()"> 查 询</button>
                        </div>
                    </div>
					<table id="exampleTable" data-mobile-responsive="true" class="table-condensed">
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
<script type="text/javascript" src="/global/H-ui-admin/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		load();
    });

    function load() {
        $('#exampleTable').bootstrapTable({
            method : 'get', // 服务器数据的请求方式 get or post
            url : "/report/listVehicle", // 服务器数据的加载地址
            iconSize : 'outline',
            toolbar : '#exampleToolbar',
            striped : true, // 设置为true会有隔行变色效果
            dataType : "json", // 服务器返回的数据类型
            pagination : true, // 设置为true会在底部显示分页条
            // queryParamsType : "limit",
            // //设置为limit则会发送符合RESTFull格式的参数
            singleSelect : false, // 设置为true将禁止多选
            pageSize : 20, // 如果设置了分页，每页数据条数
            pageList: [20],        //可供选择的每页的行数（*）
            pageNumber : 1, // 如果设置了分布，首页页码
            // search : true, // 是否显示搜索框
            showColumns : true, // 是否显示内容下拉框（选择显示的列）
            sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
            // "server"
            queryParams : function(params) {
                return {
                    // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                    limit : params.limit,
                    offset : params.offset,
                    plateNo:$('#plateNo').val(),
                    plazaId:$('#plazaId').val(),
                    timefrom: $("input[name='timefrom']").val(),
                    timeto: $("input[name='timeto']").val(),
                    type: $('#vehicleType').val()

                };
            },
            columns : [
                {
                    field: 'Number',
                    width:25,
                    formatter: function (value, row, index) {
                        return index+1;
                    }
                },
                {
                    field : 'plazaName', // 列字段名
                    title : '出口站', // 列标题,
                    width:120
                },{
                    field : 'plateNo',
                    title : '车牌',
                    width:80
                },{
                    field : 'plateColor',
                    title : '车牌颜色',
                    width:50,
                },{
                    field : 'typeName',
                    title : '车型',
                    width:120,
                },{
                    field : 'productName',
                    title : '产品名称',
                    width:120,
                    formatter:function (value,row,index) {
                        return value;
                    }
                },{
                    field : 'capacity',
                    title : '载量',
                    width:80,
                    visible:false,
                },{
                    field : 'calCapacity',
                    title : '实际载量',
                    width:80,
                    visible:false,
                    formatter:function(value,row,index){

                        return value;
                    }
                },{
                    field : 'peccNum',
                    title : '违章次数',
                    width:80,
                    visible:false,
                },{
                    field : 'freeFee',
                    title : '免征金额',
                    width:80,
                    formatter:function (value,row,index) {
                        return value;
                    }
                },{
                    field : 'realFee',
                    title : '补缴金额',
                    width:80,
                    visible:false,
                    formatter:function (value,row,index) {
                            return value;
                    }
                },{
                    field : 'customerName',
                    title : '车主姓名',
                    width:80,
                },{
                    field : 'transactorPhone',
                    title : '用户手机',
                    width:80,
                    visible:false,
                },{
                    field : 'condition',
                    title : '不符合条件',
                    width:80,
                    visible:false,
                },{
                    field : 'remark',
                    title : '备注',
                    width:100,
                    formatter:function (value,row,index) {
                        if(null==value||value.length<10){
                            return value;
                        }else{
                            var values = value.substring(0,10)+"...";
                            return values;
                        }
                    }
                },{
                    field : 'shiftName',
                    title : '班组',
                    width:50,
                    formatter:function (value,row,index) {
                        return value;
                    }
                },{
                    field : 'shiftDateStr',
                    title : '出口站时间',
                    width:120,
                    formatter:function (value,row,index) {
                        return value;
                    }
                },{
                    title : '操作',
                    field : 'vehicleNo',
                    align : 'center',
                    width:150,
                    formatter : function(value, row, index) {
                        var e = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '"  title="查看详情" onclick="view(\''+ row.vehicleNo+ '\')"><i class="fa fa-edit "></i></a> ';
                        var d = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '"  title="查看详情" onclick="onlyview(\''+ row.vehicleNo+ '\')"><i class="fa fa-list-alt "></i></a> ';
                    <@sec.authorize ifAnyGranted = 'ROLE_SUPERADMIN,ROLE_CENTER_ADMIN,ROLE_AREA_ADMIN,ROLE_SITE_ADMIN'>
                         return e;
                    </@sec.authorize>
                    <@sec.authorize ifAnyGranted = 'ROLE_USER'>
                         return d;
                    </@sec.authorize>
                    }
                } ]
        });
    }
    function reLoad() {
        $('#exampleTable').bootstrapTable('refresh');
    }



    function view(vehicleNo){
        var index = layer.open({
            type: 2,
            title: "图片详情",
            content: "/report/picture-show/"+vehicleNo
        });
       layer.full(index);
    }

    /**
     * 只展示
     * @param vehicleNo
     */
    function onlyview(vehicleNo){
        var index = layer.open({
            type: 2,
            title: "图片展示",
            content: "/report/only_photo_show/"+vehicleNo
        });
        layer.full(index);
    }

</script>
</body>
</html>
