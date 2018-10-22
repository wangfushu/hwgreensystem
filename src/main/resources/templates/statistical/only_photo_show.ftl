<!DOCTYPE HTML>
<html>
<head>
<#--    <link href="/global/H-ui-admin/lib/lightbox2/2.8.1/css/lightbox.css" rel="stylesheet" type="text/css" >-->
<#include "/common/hui_header.ftl">
    <title>图片展示</title>
<style>
        /*图片预览*/
        .portfolio-area{ margin-right: -20px;}
        .portfolio-area li{position: relative; float: left; margin-right: 20px; width:534px; height:332px;margin-top: 20px;}
        .portfolio-area li.hover{ z-index:9}
        .portfolio-area li .portfoliobox{ position: absolute; top: 0; left: 0; width: 524px; height: 332px;padding:5px;border: solid 1px #eee; background-color: #fff;}
        .portfolio-area li .checkbox{position: absolute; top: 10px; right: 5px; cursor:pointer}
        .portfolio-area li.hover .portfoliobox{ height:auto;padding-bottom:10px;box-shadow:0 1px 3px rgba(68, 68, 68,0.3);-moz-box-shadow:0 1px 3px rgba(68, 68, 68,0.3);-webkit-box-shadow:0 1px 3px rgba(68, 68, 68,0.3)}
        .portfolio-area li .picbox{width: 522px; height: 330px;overflow: hidden;text-align: center;vertical-align:middle;display:table-cell; line-height:150px;}
        .portfolio-area li .picbox img{max-width:520px; max-height:328px;vertical-align:middle;}
        .portfolio-area li .textbox{ display: none; margin-top: 5px;}
        .portfolio-area li.hover .textbox{ display: block;}
        .portfolio-area li label{ display:block; cursor:pointer}
</style>
</head>
<body>
<div class="page-container">
    <div class="portfolio-content">
        <ul class="cl portfolio-area">
            <li class="item">
                <div class="portfoliobox">
                    <div class="picbox"><img src="/${(vmVehicle.imageDirectory)!""}车头.jpg" onerror="this.src='/global/images/车头.png'" title="车头"></div>
                    <div class="textbox">车头</div>
                </div>
            </li>
            <li class="item" >
                <div class="portfoliobox" >
                    <div class="picbox"><img src="/${(vmVehicle.imageDirectory)!""}车尾.jpg" onerror="this.src='/global/images/车尾.png'" title="车尾"></div>
                    <div class="textbox">车尾 </div>
                </div>
            </li>
            <li class="item">
                <div class="portfoliobox">
                    <div class="picbox"><img src="/${(vmVehicle.imageDirectory)!""}货物.jpg" onerror="this.src='/global/images/货物.png'" title="货物"></div>
                    <div class="textbox">货物 </div>
                </div>
            </li>
            <li class="item">
                <div class="portfoliobox">
                    <div class="picbox"><img src="/${(vmVehicle.imageDirectory)!""}运营证.jpg" onerror="this.src='/global/images/运营证.png'"   title="运营证"></div>
                    <div class="textbox">运营证 </div>
                </div>
            </li>
        </ul>
    </div>
</div>
<#include "/common/hui_footer.ftl">
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="/global/H-ui-admin/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="/global/H-ui-admin/lib/lightbox2/2.8.1/js/lightbox.min.js"></script>

<script type="text/javascript">


</script>
</body>
</html>