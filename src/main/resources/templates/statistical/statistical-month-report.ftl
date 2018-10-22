<#assign  sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<!DOCTYPE html>
  <head>
      <title>车辆信息年统计导出</title>
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
        <#--<div class="columns pull-left">
            车牌号：<input id="plateNo" class="form-control" name="plateNo" type="text" placeholder="车牌号" value="${param.plateNo!""}">
        </div>-->
           月份:<input id="logmin" name="dateTime" type="text" value="${param.dateTime!''}" runat="server"
                   onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM'})" class="inputDate form-control Wdate"
                   style="width:120px;"/>
        </div>

        <div class="ibox">
            <div class="ibox-body">
                <div id="exampleToolbar" role="group">
                    <div class="columns pull-left">
                        <button class="btn btn-success" onclick="reLoad()"> 查 询</button>
                    </div>
                    <div class="columns pull-right">
                        <button class="btn btn-danger" onclick="export_excel()"> 导出</button>
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

    $(function () {
        load();
    });

    function load() {
        var myColumnsStr=getColumns();
        $('#exampleTable').bootstrapTable({
            method: 'get', // 服务器数据的请求方式 get or post
            url: "/report/MonthPageReport", // 服务器数据的加载地址
            iconSize: 'outline',
            toolbar: '#exampleToolbar',
            striped: true, // 设置为true会有隔行变色效果
            dataType: "json", // 服务器返回的数据类型
            pagination: true, // 设置为true会在底部显示分页条
            // queryParamsType : "limit",
            // //设置为limit则会发送符合RESTFull格式的参数
            singleSelect: false, // 设置为true将禁止多选
            pageSize: 20, // 如果设置了分页，每页数据条数
            pageList: [20],        //可供选择的每页的行数（*）
            pageNumber: 1, // 如果设置了分布，首页页码
            // search : true, // 是否显示搜索框
            sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者
            // "server"
            queryParams: function (params) {
                return {
                    // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                    limit: params.limit,
                    offset: params.offset,
                    dateTime: $("input[name='dateTime']").val(),
                    plazaId:$('#plazaId').val(),

                };
            },
            columns: myColumnsStr
        });
    }

    function reLoad() {
        $('#exampleTable').bootstrapTable('refresh');
    }

    function getColumns() {
        var myColumns=new Array();
        // 加载动态表格
        $.ajax({
            url: "/baseinformation/vehicleTypeList",
            type: 'get',
            dataType: "json",
            async: false,
            success: function (returnValue) {
                // 未查询到相应的列，展示默认列
                var arr = returnValue;
                if (arr.length == 0) {
                    //没查到列的时候把之前的列再给它
                    myColumns = $table.bootstrapTable('getOptions').columns[0];
                } else {
                    // 异步获取要动态生成的列

                    var myColumnsBase=new Array();
                    var myColumnsChild=new Array();
                    myColumnsBase.push({
                        "field":'plazaName',
                        "title":'单位',
                        align: 'center',
                        valign: 'middle',
                        colspan: 1,
                        rowspan: 2
                    });
                    $.each(arr, function (i, item) {
                        myColumnsBase.push({
                            title: item.biDescription,
                            align: 'center',
                            valign: 'middle',
                            colspan: 2,
                            rowspan: 1
                        });
                        myColumnsChild.push({
                            field:'result',
                            title: '免征车辆(辆)',
                            align: 'center',
                            valign: 'middle',
                            formatter:function (value,row,index) {
                                var str="num"+item.biValue;
                                //alert(value[str]);
                                return value[str];
                            }
                        },{
                            field:'result',
                            title: '免征金额(元)',
                            align: 'center',
                            valign: 'middle',
                            formatter:function (value,row,index) {
                                var str="fee"+item.biValue;

                                return value[str];
                            }

                        });
                    });
                    myColumns.push(myColumnsBase,myColumnsChild)
                }


            }
        });
        return myColumns;
    }

    function export_excel() {
        var dateTime=$("input[name='dateTime']").val();
        var plazaId=$('#plazaId').val();
        param = "?dateTime=" + dateTime + "&plazaId="+plazaId;


        $.ajax({
            cache: true,
            type: "GET",
            url:  "/report/monthExport",
            data: {
                dateTime: $("input[name='dateTime']").val(),
                plazaId:$('#plazaId').val(),
            },// formid
            async: false,
            error: function (request) {
                layer.alert("Connection error");
            },
            success: function (data) {
                if (data) {
                    window.location.href = "/report/monthExport" + param;
                    layer.msg("导出成功", {icon: 1});
                } else {
                    layer.msg("导出失败或无数据", {icon: 1});

                }
            }
        });

    }

</script>
</body>
</html>
