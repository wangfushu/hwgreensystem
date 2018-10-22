<#assign  sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<!DOCTYPE html>
<html>
<head>
<title>图片详情</title>
<link href="/global/H-ui-admin/lib/lightbox2/2.8.1/css/lightbox.css" rel="stylesheet" type="text/css" >
<#include "/common/hui_header.ftl">
</head>
<body>
<div class="page-container">
	<div class="row cl" style="margin: 50px;margin-left: 100px">


    <label class="form-label col-xs-4 col-sm-2">图片：</label>
	<div class="cl portfolio-content">
		<ul class=" portfolio-area">
			<li class="item">
				<div class="portfoliobox">
					<div class="picbox"><a href="/${(vmVehicle.imageDirectory)!""}车头.jpg" data-lightbox="gallery" data-title="车头"><img src="/${(vmVehicle.imageDirectory)!""}车头.jpg" onerror="this.src='/global/images/车头.png'"></a></div>
					<div class="textbox">车头</div>
				</div>
			</li>
            <li class="item">
                <div class="portfoliobox">
                    <div class="picbox"><a href="/${(vmVehicle.imageDirectory)!""}车尾.jpg" data-lightbox="gallery" data-title="车尾"><img src="/${(vmVehicle.imageDirectory)!""}车尾.jpg" onerror="this.src='/global/images/车尾.png'"></a></div>
                    <div class="textbox">车尾 </div>
                </div>
            </li>
            <li class="item">
                <div class="portfoliobox">
                    <div class="picbox"><a href="/${(vmVehicle.imageDirectory)!""}货物.jpg" data-lightbox="gallery" data-title="货物"><img src="/${(vmVehicle.imageDirectory)!""}货物.jpg" onerror="this.src='/global/images/货物.png'"></a></div>
                    <div class="textbox">货物 </div>
                </div>
            </li>
            <li class="item">
                <div class="portfoliobox">
                    <div class="picbox"><a href="/${(vmVehicle.imageDirectory)!""}运营证.jpg" data-lightbox="gallery" data-title="运营证"><img src="/${(vmVehicle.imageDirectory)!""}运营证.jpg" onerror="this.src='/global/images/货物.png'"></a></div>
                    <div class="textbox">运营证 </div>
                </div>
            </li>
		</ul>
	</div>

    </div>
    <form class="form form-horizontal" id="form-vehicle-add">
		<input type="hidden" name="vehicleNo" value="${(vmVehicle.vehicleNo)!""}">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">车牌：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${(vmVehicle.plateNo)!""}" name="plateNo" required="required"  data-msg-isMobile="车牌不能为空">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">车牌颜色：</label>
            <div class="formControls col-xs-8 col-sm-9">
                  <span class="select-box inline">
            <select name="plateColor" class="select" style="width: 100px" >
                    <option value="蓝"
                            <#if vmVehicle.plateColor??&&vmVehicle.plateColor=="蓝">selected</#if>>蓝</option>
                    <option value="黄"
                            <#if vmVehicle.plateColor??&&vmVehicle.plateColor=="黄">selected</#if>>黄</option>
                    <option value="黑"
                            <#if vmVehicle.plateColor??&&vmVehicle.plateColor=="黑">selected</#if>>黑</option>
                    <option value="绿"
                            <#if vmVehicle.plateColor??&&vmVehicle.plateColor=="绿">selected</#if>>绿</option>
            </select>
            </span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">车型：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <#--<input type="text" class="input-text" value="${(vmVehicle.type)!""}"  name="type" required="required">-->
                <select name="type" class="select" style="width: 100px" required="required">
                     <#list sysBaseInformations as vehicletype>
                         <option value="${vehicletype.biValue?string}"

						 <#if vmVehicle.type?? &&vehicletype.biValue=="${vmVehicle.type?string}">selected</#if>

						 >${vehicletype.biDescription}</option>
					 </#list>
                </select>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">产品名称：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${(vmVehicle.productName)!""}"  name="productName">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">载量：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="number" class="input-text" value="${(vmVehicle.capacity)!""}"  name="capacity">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">实际载量：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="number" class="input-text" value="${(vmVehicle.calCapacity)!""}" name="calCapacity">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">免征金额：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="number" class="input-text" value="${(vmVehicle.freeFee)!""}" name="freeFee">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">补缴金额：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="number" class="input-text"value="${(vmVehicle.realFee)!""}" name="realFee">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">车主姓名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${(vmVehicle.customerName)!""}" name="customerName">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">用户手机：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${(vmVehicle.transactorPhone)!""}" name="transactorPhone">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">不符合条件：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${(vmVehicle.condition)!""}" name="condition">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">备注：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${(vmVehicle.remark)!""}" name="remark">
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
                <button onClick="" class="btn btn-primary radius" type="submit"><i
                        class="Hui-iconfont">&#xe632;</i> 提交
                </button>
            </div>
        </div>
</div>
<#include "/common/hui_footer.ftl">
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="/global/H-ui-admin/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="/global/H-ui-admin/lib/lightbox2/2.8.1/js/lightbox.min.js"></script>
<script type="text/javascript">
$(function(){
	$(".portfolio-area li").Huihover();
});

/*$().ready(function () {
    $(".portfolio-area li").Huihover();
    validateRule();
});*/


    $("#form-vehicle-add").validate({
        onkeyup: false,
        focusCleanup: true,
        success: "valid",
        submitHandler: function (form) {
            $.ajax({
                cache: true,
                type: "POST",
                url:  "/report/saveVehicle",
                data: $(form).serialize(),// formid
                async: false,
                error: function (request) {
                    parent.layer.alert("Connection error");
                },
                success: function (data) {
                    if (data.code == 0) {
                        parent.layer.msg("更新数据成功", {icon: 1});
                        parent.reLoad();
                        var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                        parent.layer.close(index);
                    } else {
                        parent.layer.alert(data.msg, {icon: 0})
                    }
                }
            });
        }
    });



function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            plateNo: {
                required: true,
            },

        },
        messages: {
            plateNo: {
                required: icon + "车牌不能为空",

            }
        }
    })
}

</script>
</body>
</html>