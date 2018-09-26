/**
* @fileoverview 本文档是rbi脚本工具的说明
* JavaScript.
*
* @author gzf,smh
* @version 1.0
*/

var xmrbiParams = [];

/**
* 传入URL参数名取得URL参数值.
* @param {String} URL参数名称.
* @return 返回传给页面URL参数值.
* @type String
*/

function getUrlParam(vpname){
	
	var plen = xmrbiParams.length;
	
	if(plen == 0) 
		iniUrlParameter();
    plen = xmrbiParams.length;
    
    if(plen == 0) 
		return null;
	
	for(var i=0;i<plen;i++){
		var v = getParamValue(xmrbiParams[i],vpname);

		if(v!=null)
			return v;
	}
	return null;

}

function getParamValue(vnv,vpname){

	var temp = vnv.split('=');
	if(temp.length<1) return null;
    if(temp[0]==vpname)
		return temp[1];
}

/**
* 取得列表框 对应值的文本.
* @param {Object} Select 控件.
* @param {String} 值
* @return 返回Select 控件中option值对应的text.
* @type String
*/
function getOptionText(ele,opid){
	if(!ele.options.length) return null;
	if(opid){
		var len = ele.options.length;
		for(var i=0;i<len;i++){
			
			if(ele.options[i].value == opid)
			  return ele.options[i].text;
		}
	}else{
		if(ele.selectedIndex>-1)
			return ele.options[ele.selectedIndex].text;
		else 
			return null;
	}
	return "";
}


/**
* 设置列表框要选中与传入值对等的项.
* @param {Object} Select 控件.
* @param {String} 值
* @param {int} 如果没有与该相同的默认选项 不传入则无操作
*/
function setOptionText(ele,opvalue,def){
	var len = ele.options.length;
	
	for(var i=0;i<len;i++){
		
		if(ele.options[i].value == opvalue){
			
		   ele.selectedIndex = i;
		   
		   return;
		}
	}

	if(def)
	 ele.selectedIndex = def;
	else
		if(def==0)
		   ele.selectedIndex = def;
	
}

/**
* 过滤字符串左右空格.
* @param {String} 值
* @return 返回过滤后的字符串.
* @type String
*/
function trim(v){

  return v.replace(/^\s*/g,"").replace(/\s*$/g,"");
}


/**
* 插入行.
* @param {Object} 表格对象
* @param {Array} 字符串组合的数组,数组元素为列里的HTML
*/
function insertRow(elTable,datas,binRowNum){
	if(binRowNum==undefined) binRowNum=-1;
   var newRow = elTable.insertRow(binRowNum);
   for(var i=0;i < datas.length;i++){
	var newCell = newRow.insertCell(i);
	newCell.innerHTML = datas[i];
   }
}


/**
* 空字符转成null.
* @param {String} 字符串
* @return 转换后的字符串.
* @type String
*/
function empStrToNull(vstr){
	
	if(vstr==undefined || vstr==null)
		return null;
	if(trim(vstr)=="" || trim(vstr)=="null")
		return null;
	
	return trim(vstr);
}

/**
* 空字符转成0.
* @param {String} 字符串
* @return 转换后的字符串或0.
* @type String or 0
*/
function empStrToZero(vstr){
	
	if(vstr==undefined || vstr==null)
		return 0;
	if(trim(vstr)=="" || trim(vstr)=="null" || trim(vstr)=="undefined")
		return 0;
	
	return vstr;
}

/**
* null转成空字符.
* @param {String} 字符串
* @return 转换后的字符串.
* @type String
*/
function nullToEmp(vstr){
	
	if(vstr==undefined || vstr==null || vstr=='null')
		return "";
	return vstr;
}





/**
* 将字浮点数分整数与小数分别填入两个文本框或控件,主要用于桩号.
* @param {Object} 表格对象
* @param {Array} 字符串组合的数组,数组元素为列里的HTML
*/
function floatToInput(value,input1,input2,defalut){
    
	if(!defalut && defalut!=0) defalut = '';
	if(!value){
		setValue(input1,defalut);
		setValue(input2,defalut);
		return;
	}
	var str = value +'';
	
    var strArray = str.split('.');
    setValue(input1,strArray[0]?strArray[0]:defalut);
	setValue(input2,strArray[1]?strArray[1]:defalut);
}
/**
* 设置控件值.
* @param {Object} 控件对象
* @param {Object,String,int....} 值
*/
function setValue(ele,text){
	if(ele.value!=undefined)
		ele.value = text;
	if(ele.innerText!=undefined)
		ele.innerText = text;
	
}

function getValue(ele){
	if(ele.value!=undefined)
		return ele.value;
	if(ele.innerText!=undefined)
		return ele.innerText;
	return '';
}
/**
* 取得某年 某季(或某月)的第一天.
* @param {int} 年份
* @param {int} 季度
* @param {int} 月
* @param {int} 类型 1.月 2.季 3.年
* @return 返回第一天.
* @type Date
*/
function getBeginDate(vyear,vquarter,vmonth,vtype){
  var vbegin = null;
  if(vtype == 1){
     vbegin = new Date(vyear,vmonth-1,1);
   }
   if(vtype == 2){
     vbegin = new Date(vyear,(vquarter-1)*3,1);
   }
   if(vtype ==3){
     vbegin = new Date(vyear,0,1);
   }
   return vbegin;
}



/**
* 取得某年 某季(或某月)的最后一天.
* @param {int} 年份
* @param {int} 季度
* @param {int} 月
* @param {int} 类型 1.月 2.季 3.年
* @return 返回最后一天.
* @type Date
*/
function getEndDate(vyear,vquarter,vmonth,vtype){
  var vend = null;
    if(vtype == 1){
     var vend = new Date(vyear,vmonth,0);
	 

   }
   if(vtype == 2){
     var vend = new Date(vyear,vquarter*3,0);

   }
   if(vtype ==3){
     vend = new Date(vyear+1,1,0);
   }
   return vend;

}


/**
* 删除该点击控件所在的行.
* @param {Object} 点击的控件
*/
function removeGrade(obj)
{
    try{
	//var deleteRow = obj.parentElement;
	var deleteRow = obj.parentElement.parentElement;
	if(deleteRow.tagName.toUpperCase()!='TR')
		deleteRow = deleteRow.parentElement;
	var tableContext = deleteRow.parentElement;
	tableContext.deleteRow(deleteRow.rowIndex);
	}catch(e){
		alert(e.message);
	}
}


/**
* 字符串转成日期 字符串格式 getDateFormYymd(YYYY.MM.DD,'.')
* @param {String} 日期字符串
* @param {String} 分隔符
* @return 日期.
* @type Date
*/
function getDateFormYymd(dateString,mark){
    if(dateString==null)
		return null;
	if(dateString=='')
		return null;
    var dates = new Array();

	dates = dateString.split(mark);

	var y = parseInt(dates[0],10);
	var m = parseInt(dates[1],10);
	var d = parseInt(dates[2],10);
	var returnDate = new Date(y,m-1,d);
	//alert(y+'年 ======'+ dates[1]+ '月'+d);
	//alert(returnDate.getYear()+'年 '+ (returnDate.getMonth()+1)+ '月'+returnDate.getDate());

    return returnDate;
}


/**
* 字符串 YYYY.MM.DD 转成日期 getDateDefault(YYYY.MM.DD)
* @param {String} 日期字符串
* @return 日期.
* @type Date
*/
function getDateDefault(dateString){
	
    if(!dateString) return null;
     
    return getDateFormYymd(dateString,'.');
}


function getTimeDefaul(timeString){
	
	if(!timeString) return null;
	var dates = timeString.split(' ');
	
	if(!dates.length) return;
	
	var newDate = getDateFormYymd(dates[0],'-');

	if(dates.length > 1){
		var times = dates[1].split(':')
			
		if(times[0])
			newDate.setHours(parseInt(times[0],10));
		if(times[1])
			newDate.setMinutes(parseInt(times[1],10));
		if(times[2])
			newDate.setSeconds(parseInt(times[2],10));
	}

	return newDate;
}



function dateCNFormat(vdate){
  if(vdate==null) return null;
  return vdate.getFullYear()+"年"+(vdate.getMonth()+1)+"月"+vdate.getDate()+"日";
}


function deteDefaultFormat(vdate){
	 if(vdate==null) return null;
  return dateFormat(vdate,'.');
}

function getNowDateStr(){
	return deteDefaultFormat(new Date());
}
var dateYearBingNum = null;
function getDateYear(vdate){
	
	if(dateYearBingNum==null){
		dateYearBingNum = 1900;
		var ua = navigator.userAgent.toLowerCase(); 
		var browserArray = ua.match(/msie ([\d.]+)/);
		if(browserArray){
			var browserVersion = browserArray[1]
			var iev = (parseInt(browserVersion,10));
			if(iev<=8)
				dateYearBingNum = 0;
		}
	}
	return dateYearBingNum + vdate.getYear();
}
function dateFormat(vdate,vsin){
  if(vdate==null) return null;
  return getDateYear(vdate) + vsin + getNumFormat(2,(vdate.getMonth()+1)) + vsin + getNumFormat(2,vdate.getDate());
}

function timeFormat(vdate,vsin){
	if(vdate==null) return null;
	if(vsin==null) vsin='.';
	return getDateYear(vdate.getYear())+vsin+getNumFormat(2,(vdate.getMonth()+1))+vsin+getNumFormat(2,vdate.getDate())+' '+getNumFormat(2,vdate.getHours())+':'+getNumFormat(2,vdate.getMinutes());
}

function getNumFormat(formatCount,pNum){
	var str = pNum +'';
	var len = str.length;
	if(len<formatCount){
		var addLen = formatCount - len;
		for(var i=0;i<addLen;i++){
			str = '0' + str;
		}
	}
	return str;
}






//函数名：chknum
//功能介绍：检查是否为数字
//参数说明：要检查的数字
//返回值：1为是数字，0为不是数字
function chknum(NUM)
{
	var i,j,strTemp;
	strTemp="0123456789";
	if ( NUM.length== 0)
		return 0
	for (i=0;i<NUM.length;i++)
	{
		j=strTemp.indexOf(NUM.charAt(i));
		if (j==-1)
		{
		alert("输入数据不是数值");
        return false;
		}
	}
	//说明是数字
	return true;
}






function makeBig(o,howbig)
{
    o.width=o.width*howbig;
    o.height=o.height*howbig;


}
function imageBig(o){

  if(event.button==1)
  {
     makeBig(o,1.2)

    return true;
  }
  if(event.button==2)
  {
    if(o.width<200)
    return false;
     makeBig(o,0.8)

    return true;
  }

}







function keydownevent(votherObj)
{
/* 数字检验 */
 var vobj = event.srcElement;
 vobj.value = trim(vobj.value);
 votherObj.value = trim(votherObj.value);
 if(window.event.keyCode==13)
 {
    if(vobj.value.length == 0){
     alert('该项不可为空!');
     vobj.focus();
     return false;
    }
    if(votherObj.value.length == 0){
     votherObj.focus();
     return false;
    }
    vobj.form.submit();
    return true;
 }
 else {

  return true;
 }
}





function ReturnData(vid,vname)
{
	this.id = vid;
	this.name = vname;
}




function openModelWindow(url,w,h,inputid,placeid)
{
  var returnDatas = new Array();
  //url = url +  '&userIdLink='+inputid.value;
  var r = Math.random().toString().substring(5);
  url=url+'&Mathnum='+ r;
  //window.open(url,'_blank','menubars=no,status=1,width=800,height=600')
  returnDatas  = window.showModalDialog(url,'new','dialogWidth:'+w+'px;dialogHeight:'+h+'px;center:1;scroll:1;menubar:0;resizable:1;status:0;help:0');
  if(returnDatas==undefined) return undefined;
  if(returnDatas == null)
    {
	  setValue(placeid,'');
	  setValue(inputid,'');
	  //placeid.innerText='';
	  //inputid.value='';
	return null;      
    }else{
		 setValue(placeid,'');
		 setValue(inputid,'');
		 //placeid.innerText='';
		 //inputid.value='';
		 for(var i=0;i<returnDatas.name.length;i++){
           
		    
			setValue(placeid,getValue(placeid)+returnDatas.name[i]);
	        
			if(i<returnDatas.name.length-1)
				//placeid.innerText += ',';
			    setValue(placeid,getValue(placeid)+',');
		 }
		 for(var i=0;i<returnDatas.id.length;i++){
			 
			setValue(inputid,getValue(inputid)+returnDatas.id[i]);
	        
            //inputid.value +=returnDatas.id[i];

			if(i<returnDatas.id.length-1)
				setValue(inputid,getValue(inputid)+',');
		 }
    }
	return returnDatas;
}



function iniUrlParameter(){
	/**
	 * 20131108 解决url中带有#号的特殊字符。获取 search时会取不到#后面的串。
	 */
	 var urlQstrArray = window.location.href.split('?'); 
	
	 var urlQstr = null;
	 if(urlQstrArray.length>1){
		 urlQstr = '?'+urlQstrArray[1];
	 }else{
		 return;
	 }
	  
	 var qstr = window.location.search;
	 if(urlQstr.length>qstr.length)
		 qstr = urlQstr;
	 if(qstr==null) return null;
		qstr = trim(qstr);
	
	 if(qstr.length <=2) return null;
	 
	 var beginStr = qstr.slice(0,1);
	 
	 if(beginStr=='?')
		 qstr = qstr.slice(1,qstr.length);
	 
     xmrbiParams = qstr.split('&');
	 
}


function getRadioValue(inputName){
	
	var radios = document.getElementsByName(inputName);
	
	if(radios){
		for(var i=0;i<radios.length;i++){
			if(radios[i].checked) 
				return radios[i].value;
		}
	}
	return null;
}

function setRadioValue(inputName,value){
	
	var radios = document.getElementsByName(inputName);
	
	if(!radios) return;
	
	if(radios.length){
		
		for(var i=0;i<radios.length;i++){
			
			if(radios[i].value==value) 
				return radios[i].checked = true;
		}
	}else{
		if(radios.value==value) 
				return radios.checked = true;
	}
}


function addOption(sel,opId,opValue){
	
	var opt = new Option(opValue,opId);
	sel.options.add(opt);
}
/**
 * 移动多选框先中的元素 step<0 下移 step>0 上移
*/
function moveMultipleOption(ele,step){
	step *= -1;
	var selIndex = ele.selectedIndex;
	if(selIndex==-1){
		return;
	}
	var ops = ele.options;
	if(selIndex==0 && step<0)
	 return;
	if((selIndex==ops.length-1) && step>0)
	 return;
	var changeIndex = selIndex + step;
	if(changeIndex<0) changeIndex=0;
	if(changeIndex>ops.length-1) changeIndex=ops.length-1;

	var selOption = ops[selIndex];
	var tempText = selOption.text;
	var tempValue = selOption.value;
	ops[selIndex].value = ops[changeIndex].value;
	ops[selIndex].text = ops[changeIndex].text;
	ops[changeIndex].value = tempValue;
	ops[changeIndex].text = tempText;
	ele.selectedIndex = changeIndex;
}

function clearTr(vtr){
	var cls = vtr.cells;
	while(cls.length>0){
		vtr.removeChild(cls(cls.length-1));
	}
}
/**清除表的内容行*/
function clearTable(tableId){
	var t = document.getElementById(tableId);
	   while(t.rows.length>1){
	     t.deleteRow(t.rows.length-1);
	   }
}
/**清空重置表单内容*/
function clearForm(){
		var form = document.forms[0];
		var obj ;
		for(var i = 0; i < form.length; i++){
			obj = form.elements(i);
			if(obj.type == "select-one")
				obj.selectedIndex = 0;
			if(obj.type == "text")
				obj.value = "";
	    }
}

function insertCell(vtr,datas){
	
	for(var i=0;i < datas.length;i++){
		var newCell = vtr.insertCell(vtr.cells.length);
		newCell.innerHTML = datas[i];
	   }
}


///////////////////////////////////////////////////////
// yyyy-MM-dd hh:mm
////////////////////////////////////////////////////////
function changeTimeToString(DateIn)
{	
	if(DateIn==null)
		return null;
    var Year=0;
    var Month=0;
    var Day=0;
    var Hour = 0;
    var Minute = 0;
    var CurrentDate="";


    Year      = getDateYear(DateIn.getYear());
    Month     = DateIn.getMonth()+1;
    Day       = DateIn.getDate();
    Hour      = DateIn.getHours();
    Minute    = DateIn.getMinutes();
    

    CurrentDate = Year + "-";
    if (Month >= 10 )
    {
        CurrentDate = CurrentDate + Month + "-";
    }
    else
    {
        CurrentDate = CurrentDate + "0" + Month + "-";
    }
    if (Day >= 10 )
    {
        CurrentDate = CurrentDate + Day ;
    }
    else
    {
        CurrentDate = CurrentDate + "0" + Day ;
    }
    
     if(Hour >=10)
    {
        CurrentDate = CurrentDate + " " + Hour ;
    }
    else
    {
        CurrentDate = CurrentDate + " 0" + Hour ;
    }
    if(Minute >=10)
    {
        CurrentDate = CurrentDate + ":" + Minute ;
    }
    else
    {
        CurrentDate = CurrentDate + ":0" + Minute ;
    }      
    return CurrentDate;
}


function openWin(url){
	parent.openLocation(new Array(url, null, null, null, null, null));
}




function getMile(pValue){


	try{
		var kStr = '';
		var hStr = '';
		if(pValue == undefined
		 || pValue == null
		 || isNaN(parseInt(pValue))
		   ){
			kStr = '0000';
			hStr = '000';
			return 'K '+ kStr + '+' + hStr;
		}
		kStr = getMileKValue(pValue);
		hStr = getMileHValue(pValue);
		return 'K '+ kStr +  ' + ' + hStr;
	}catch(e){
		return 'K0000.000';
	}
}





function getMileKValue(pValue){
	var kvalue = parseInt(pValue/1000,10);
	var kstr = kvalue + '';
	var klen = 4 - kstr.length;
	for(var i=0;i<klen;i++){
		kstr = '0'+kstr;
	}
	return kstr;
}
/**
function getMileHValue(pValue){
	var kvalue = parseInt(pValue%1000,10);
	var kstr = kvalue + '';
	var klen = 3 - kstr.length;
	for(var i=0;i<klen;i++){
		kstr = '0'+kstr;
	}
	return kstr;
}
*/
function getMileHValue(pValue){
	var valueStr = pValue + '';
	var tempArray = valueStr.split(".");
	var len = tempArray[0].length;
	var hstr = tempArray[0].substring(len-3,len);
	len = 3- hstr.length;
	for(var i=0;i<len;i++){
		hstr = '0'+hstr;
	}
	if(tempArray[1] && tempArray[1].length)
	  hstr += '.'+ tempArray[1];
	return hstr;
}

function setMileToInput(pValue,pKInput,pHInput){
    try{
	if(pValue == undefined
	 || pValue == null
	 || isNaN(parseInt(pValue))
	   ){
		pKInput.value = '0000';
		pHInput.value = '000';
		return;
	}
	pKInput.value = getMileKValue(pValue);
	pHInput.value = getMileHValue(pValue);
	}catch(e){

	}
}


function getMileFromInput(kstr,hstr){
    var tempNum = parseInt(kstr,10);
	var str1 = kstr;
	if(isNaN(tempNum)
	|| !tempNum)
		str1 = '';
	else
		str1 = kstr;

	var str2 = hstr;
	if(hstr){
		var tempArray = hstr.split(".");
		var len = 3 - tempArray[0].length;
		var tempstr = tempArray[0];
		//var len = 3 - hstr.length;
		for(var i=0;i<len;i++){

			tempstr = '0'+tempstr;
		}
		
		if(tempArray[1])
			str2 = tempstr + '.' + tempArray[1];
		else
			str2 = tempstr;
		
	}else
		str2 = '000';
   
	var str = str1  + str2;
	return str;
}



function strToIntArray(pStr,pSign){
	if(!pStr) return null;
	var ints = new Array();
	var ids = zoneIds.split(pSign);
	if(ids && ids.length){
		var len = ids.length;
		
		for(var i=0;i<len;i++){
			var id = parseInt(ids[i],10);   
			if(!isNaN(id)){
				ints[ints.length] = ids[i];
			}
		}
		return ints;
	}else{
		return null
	}

}



function checkBoxValueStr(eleName){
	var eles = document.getElementsByName(eleName);
	var len = eles.length;
	var str = null;
	if(len){
		str = '';
		for(var i=0;i<len;i++){
			var ele = eles[i];
			if(ele.checked){
				str += ele.value + ',';
			}
		}
		 
		return removeLastSing(str);
	}
	return null;
}

function checkBoxElesValueStr(eles){
	
	var len = eles.length;
	var str = null;
	if(len){
		str = '';
		for(var i=0;i<len;i++){
			var ele = eles[i];
			if(ele.checked){
				str += ele.value + ',';
			}
		}
		 
		return removeLastSing(str);
	}
	return null;
}

//comma
function removeLastSing(str,sign){
	if(!str) return str;
	if(!sign){
		sign = ',';
	}
	var s = str.lastIndexOf(sign);
	if(s!=-1 && s==str.length-1)
		str = str.substring(0, str.length-1);  
	return str;
}



function arrayToStr(pArray,pSign){
	var str = '';
	if(pArray && pArray.length){
		var len = pArray.length;
		for(var i=0;i<len;i++){
			str += pArray[i] + ',';
		}
	    str = removeLastSing(str,',');
	}	
	return str;
}


/**
* 校检一组控件是否不为空是否不为空.
* @param {[{ele:document.getElementById('year'),message:'年份'},{}.....]}
* @return 是否不为空.
* @type Boolean
*/
function checkNotEmpty(pEles){
	if(pEles && pEles.length){
		var len = pEles.length;
		for(var i=0;i<len;i++){
			var data = pEles[i];
			//alert(data.ele);
			var value = data.ele.value ? data.ele.value:data.ele.innerText;
			if(trim(value)==''){
				alert(data.message + '为必填项');
				try{
					data.ele.focus();
				}catch(e){

				}
				return false;
			}
		}
	}
	return true;
}


/**
* 显示报表
* @param reportName
* @return 
*/
function viewReport(reportName,width,height,para){

		var url ='../../frameset?__report=reports/';
			url = url+reportName+'.rptdesign';
		if(width==null || width=="" || width==undefined){
			var width=900;
		}
		if(height==null || height=="" || height==undefined){
			var height=660;
		}
		if(para!=null && para!="" && para!=undefined){
			url = url +"&queryIds="+para;
		}
		var openWin = "height="+height+",width="+width+",top=20,left=20,status=no,toolbar=no, menubar=no,location=no,center=1,resizable=yes";
		window.open(url,'new',openWin); 
}
/**
* 显示报表
* @param reportName
* @return 
*/
function viewReportParaUrl(reportName,width,height,paraUrl){
		var url ='../../frameset?__report=reports/';
			url = url+reportName+'.rptdesign';
		if(width==null || width=="" || width==undefined){
			var width=900;
		}
		if(height==null || height=="" || height==undefined){
			var height=660;
		}
		if(paraUrl!=null && paraUrl!="" && paraUrl!=undefined){
			url = url +paraUrl;
		}
		var openWin = "height="+height+",width="+width+",top=20,left=20,status=no,toolbar=no, menubar=no,location=no,center=1,resizable=yes";
		window.open(url,'new',openWin); 
}
/**
* 取得报表url
* @param reportName
* @return 
*/
function getReportParaUrl(reportName,paraUrl){
		var url ='../../frameset?__report=reports/';
			url = url+reportName+'.rptdesign';
		
		if(paraUrl!=null && paraUrl!="" && paraUrl!=undefined){
			url = url +paraUrl;
		}
		return url;
}

function closeWin3k(){
	 var wid = parent.document.activeElement.parentElement.id;
	 parent.document.all('2k3WindowsObject').closeWindow(wid);
 }
function closeWindow(){
   try{
	closeWin3k();
   }catch(e){
		try{
			window.close();
		}catch(e){}
   }
}
//关闭打开的层或页面
function closeWin(){
	try{
		window.close();
		var pobj = parent;
		var i=0;
		if(window.htmlmainactive){
				do{
					if(pobj!=null){
						if(typeof pobj.closeWin == "function"){
							pobj.closeWin();
							pobj = null;
						}else{
							pobj = pobj.parent;
						}
					}
					i++;
				}while(pobj!=null && i<5)
		}
	}catch(e){
	}
	
}
//关闭打开的层或页面
function closeMaxWin(obj){
	try{
		window.close();
		parent.parent.closeMaxWin();
	}catch(e){
		
	}
	
	if(obj){//android客户端打开
		window.history.back();
	}
}
/**
* 取得四舍五入后的数以文本返回
* @param p_value 数值
* @param p_num 保留几位
* @return 
*/
 function getFloatString(p_value,p_num){
	 if(p_num==undefined)
		 p_num = 2;
	try{
		var v_value = p_value;
	if(p_value<0){
		v_value = -1*p_value;
	}
	var v_num = p_num+1;
	var v_add = 5;
	for(var i=1;i<=v_num;i++){
		v_add = v_add/10;
	}
	v_value = (v_value + v_add) +'';
	var singPlace = v_value.indexOf('.');
	if(singPlace==-1)
		return v_value*(p_value<0?-1:1);
	else
		return (v_value.substring(0, singPlace) + v_value.substring(singPlace,(singPlace+3)))*(p_value<0?-1:1);
	}catch(e){
		alert(e.message);
	}
}

/**使界面中所有某种类型的控件全变灰或全恢复,如disabledElements('INPUT',true,document)*/
function disabledElements(type,flag,obj){
	try{
		if(obj==null||obj=='undefined') obj=document;
		var ele = obj.getElementsByTagName(type);
		if(ele==null||ele=='undefined') return ;
		if(ele.length=='undefined') ele.disabled=flag;
		else
		   for(var i=0;i<ele.length;i++) ele[i].disabled=flag;
	}catch(e){alert(e.message);}
}

/**将本fieldset下的INPUT根据复选框是否选中来改变INPUT全变灰或全恢复*/
function changeDisabled(obj){
	try{
		var flag = (obj.checked==true)?false:true;
		var parentObj = obj.parentElement.parentElement;
		var childrenObjs = parentObj.children;
		var len = childrenObjs.length;
		for(var i=1;i<len;i++){
			disabledElements('INPUT',flag,childrenObjs[i]);
			disabledElements('TEXTAREA',flag,childrenObjs[i]);
		}
	}catch(e){alert(e.message);}
}
/**获得随机数*/
function getRandom(){
 return Math.random().toString().substring(5);
}

/**验证必填框*/
function checkInput(objId,objLable){
    /**例：
     var objId = new Array();
     var objLable = new Array();
     objId[0]="inputId_1";
     objLable[0]="输入1！";
     objId[1]="inputId_2";
     objLable[1]="输入2！";
    */
      var fields = new Array();
      var ale=''
      for(var i=0;i<objId.length;i++){
        if(document.getElementById(objId[i]).value==''){
                fields[fields.length]=objLable[i];
        }
      }
      for(var i=0 ;i<fields.length;i++){
        ale +=  '“'+fields[i]+'”\n<br>';
      }
      if(fields.length>0){
        //alert(ale+'以上输入项不能为空，请填写！');
    	  ale +='以上输入项不能为空，请填写！'
    	  $.messager.alert("提示",ale,"");
        return false;
      }
      return true
}
/**去除数字串的符号*/
function delFormat(str){
  return str.replace(/,/g,"");
}
/**全角转半角,用法如：onkeyup="CtoH(this);" */
function CtoH(obj){ 
　　var str=obj.value;
　　var result="";
　　for (var i = 0; i < str.length; i++){
　　　if (str.charCodeAt(i)==12288){
　　　　result+= String.fromCharCode(str.charCodeAt(i)-12256);
　　　　continue;
　　　}
　　　if (str.charCodeAt(i)>65280 && str.charCodeAt(i)<65375) result+= String.fromCharCode(str.charCodeAt(i)-65248);
　　　else result+= String.fromCharCode(str.charCodeAt(i));
　　}
　　obj.value=result;
} 	  
var ip=null;
var mac=null;
/**初始化IP和MAC地址*/
function initIpMac(){
    var locator = new ActiveXObject ("WbemScripting.SWbemLocator"); 
	var service = locator.ConnectServer("."); //连接本机服务器
	var properties = service.ExecQuery("SELECT * FROM Win32_NetworkAdapterConfiguration"); 
	//查询使用SQL标准
	var e = new Enumerator (properties);
	for (;!e.atEnd();e.moveNext ()){ 
		var p = e.item ();
		if(p.IPEnabled != null && p.IPEnabled != "undefined" && p.IPEnabled == true){
             ip=p.IPAddress(0);
			 mac=p.MACAddress;
			 break;
		}
	}
}   
 /**复选框实现单选功能,参数为触发该函数的元素本身 */ 
function chooseOne(cb){
    var cbName = cb.name;
    //先取得同name的chekcBox的集合  
    var obj = document.getElementsByName(cbName);  
    for (i=0; i<obj.length; i++){  
        //判断obj集合中的i元素是否为cb，若否则表示未被选中  
        if (obj[i]!=cb) obj[i].checked = false;   
        //若是 但原先未被勾选 则变成勾选；反之 则变成未勾选  
        //else  obj[i].checked = cb.checked;  
        //若要至少勾选一个的话，则把上面那行else拿掉，换用下面那行  
        //else obj[i].checked = true;  
    }  

}  
/**延时提交，避免跳出确认提交框*/
function delayReload(){
	 setTimeout('reload()',500);// 
}
function reload(){
	document.forms[0].submit();
}

function ReutrnData(vid,vname)
{
	this.id = vid;
	this.name = vname;
}

function Browser() {
   var ua, s, i;
   this.isIE     = false;   // Internet Explorer
   this.isNS     = false;   // Netscape
   this.version = null;
   ua = navigator.userAgent;
   s = "MSIE";
   if ((i = ua.indexOf(s)) >= 0) {
     this.isIE = true;
     this.version = parseFloat(ua.substr(i + s.length));
     return;
   }
   s = "Netscape6/";
   if ((i = ua.indexOf(s)) >= 0) {
     this.isNS = true;
     this.version = parseFloat(ua.substr(i + s.length));
     return;
   }
   // Treat any other "Gecko" browser as NS 6.1.
   s = "Gecko";
   if ((i = ua.indexOf(s)) >= 0) {
     this.isNS = true;
     this.version = 6.1;
     return;
   }
}

var browser = new Browser();


function isPositiveNum(p_value){
	var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
	return re.test(p_value);
}

function isPositiveInt(p_value){
	var re = /^[0-9]+?[0-9]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
	return re.test(p_value);
}

//字符串多处替换
String.prototype.replaceAll=function(s1,s2){
	var str=this
	while(str.indexOf(s1)!=-1)
	str=str.replace(s1,s2);
	return str;
}

//把不要的字符都移除掉
String.prototype.removeChar = function () {
    var str = this;
    if (arguments) {
	for (var i=0;i<arguments.length ;i++ ) {
	    str = str.replaceAll(arguments[i], '');
	}
    }
    return str;
}

/**
 * 打开最大化窗口
 */	
function openMaxWindow(url,name,obj) {
    if (name) name = name.removeChar("（", "）", "(", ")");//因为特殊字符的名称，打开窗口会报错
	var fulls = "left=0,screenX=0,top=0,screenY=0,scrollbars=1";    //定义弹出窗口的参数
	if (window.screen) {
		var ah = screen.availHeight - 30;
		var aw = screen.availWidth - 10;
		fulls += ",height=" + ah;
		fulls += ",innerHeight=" + ah;
		fulls += ",width=" + aw;
		fulls += ",innerWidth=" + aw;
		fulls += ",resizable"
	} else {
		fulls += ",resizable"; // 对于不支持screen属性的浏览器，可以手工进行最大化。 manually
	}
	if(obj != null){
		try{
			var flag = true;
			var obj = parent;
			do{
				if(obj.openFullWin !=null){
					obj.openFullWin(name,url);
					flag = false;
				}else{
					obj = obj.parent;
				}
			}while(flag)
		}catch(e){
		}
	}else{
		var win = window.open(url,name,fulls);
		return win;
	}
}

function getModelWinIframeUrl(rootPath,url){
	return rootPath + '/common/modelWindow.jsp?'+rootPath +'/'+ url;
}
/**
 * 根据ip地址改变系统名称
 */
function getSysName(){
	var sys_name='福建省高速公路机电设备管理系统';

	var host = window.location.host;
	if(host.indexOf('192.168.4.50')>-1){
		document.getElementById('sysImg').src="images/tex-emms.png";
		document.title = sys_name;
	}
}

/**返回
 * @param flag
 */
function goback(flag){
	if(flag)
		window.history.go(flag);
	else
		window.history.back();
}

/**
 * 打开附件下载
 * @param url
 * @param fileName
 */
function viewFile(url,fileName){
	window.open(url);
}

/**
 *  iframe自适应内容高度
 * 参数：ifmObj为iframe的id; minHeight为最小高度
 * example: <iframe onload="javascript:dyniframesize('iframeId',780);" /> or <iframe onload="javascript:dyniframesize('iframeId');" />
 */
function dyniframesize(ifmId, minHeight,maxWidth) {
	var pTar = null;
	eval('pTar = document.' + ifmId + ';'); 
	if(typeof(pTar) == "undefined"){
		pTar = document.getElementById(ifmId); 
	}
	/*if (document.getElementById){
		pTar = document.getElementById(ifmId); 
	} else {
		eval('pTar = ' + ifmId + ';'); 
	}*/
	//
	if(document.getElementById(ifmId))
		maxWidth = maxWidth?maxWidth:document.getElementById(ifmId).parentElement.clientWidth;
	if(typeof(minHeight) == "undefined")
		minHeight = 0;
	
	if (pTar && !window.opera){
		
		//begin resizing iframe
		//pTar.style.display = "block";
		if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){
			//ns6 syntax
			pTar.width = 300;
			pTar.height = 760;
			
			//pTar.width = pTar.contentDocument.body.scrollWidth + 20;
			if(maxWidth){
				pTar.width =  (pTar.contentDocument.body.scrollWidth>maxWidth)?maxWidth-10:(pTar.contentDocument.body.scrollWidth+30);
				
			} else {
				pTar.width =  pTar.contentDocument.body.scrollWidth+30;
			}
			pTar.height = pTar.contentDocument.body.offsetHeight + 50;
		} else if (pTar.document && pTar.document.body.scrollHeight){
			//ie5+ syntax
			document.getElementById(ifmId).width = 300;
			document.getElementById(ifmId).height = 760;
			
			if(maxWidth){
				document.getElementById(ifmId).width =  (pTar.document.body.scrollWidth>maxWidth)?maxWidth-10:(pTar.document.body.scrollWidth+30);
				
			}else{
				document.getElementById(ifmId).width =  pTar.document.body.scrollWidth+30;
			}
			document.getElementById(ifmId).height = pTar.document.body.scrollHeight < minHeight ? minHeight : (pTar.document.body.scrollHeight+50);
			
			//pTar.height = pTar.Document.body.scrollHeight;
			//pTar.width = pTar.document.body.scrollWidth;
		}
	}
}

Array.prototype.hasEle = function(v){
	for(var i=0;i<this.length;i++){
		if(this[i] == v) return true;
	}
	return false;
}
Array.prototype.isEqual = function(arr) {
	for (var i=0;i<arr.length;i++) {
		if (!this.hasEle(arr[i])) return false;
	}
	
	for (var i=0;i<this.length;i++) {
		if (!arr.hasEle(this[i])) return false;
	}
	return true;
}

function loading(msg, fn) {
    $("<div class=\"datagrid-mask\" style='z-index:1000000'></div>").css({
	display:"block",
	width:"100%",
	height:$(window).height() || $(document.body).height()
    }).appendTo("body"); 
    $("<div class=\"datagrid-mask-msg\" style='z-index:100000'></div>").html(msg || "正在处理，请稍候...").appendTo("body").css({
	display:"block",
	left:($(document.body).outerWidth(true) - 190) / 2,top:(($(window).height() || $(document.body).height()) - 100) / 2
    });
    if (fn) fn();
}

function loaded(fn) {
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
    if (fn) fn();
}

/**
 * 设置cookie
 * @param name
 * @param value
 * @param expiredays 有效期天数（可选）
 * @param path 路径范围（可选）
 * @param domain 域名（可选）
 * @param isSecure 是否安全传输（可选）
 * 例 setCookie('username','test',30) 或 setCookie('username','test',30) 
 */

function setCookie(pName, value, expiredays,path,domain,isSecure){
	var exdate=new Date();
	exdate.setDate(exdate.getDate() + expiredays);
	var cookieStr = pName + "=" + escape(value) + ((expiredays) ? ";expires="+exdate.toGMTString():"")
				  + (path ? (";path="+path):"")
				  + (domain ? (";domain="+domain):"")
				  + (isSecure ? (";secure"):"")
	
	document.cookie = cookieStr;
}

/**
 * 获取cookie
 * @param pName
 * 例 getCookie('username') 
 */
function getCookie(pName){
　　　　if (document.cookie.length>0){　　//先查询cookie是否为空，为空就return ""
　　　　　　var v_start=document.cookie.indexOf(pName + "=")　　//通过String对象的indexOf()来检查这个cookie是否存在，不存在就为 -1　　
　　　　　　if (v_start!=-1){ 
　　　　　　　　v_start=v_start + pName.length+1　　//最后这个+1其实就是表示"="号啦，这样就获取到了cookie值的开始位置
　　　　　　　　var v_end=document.cookie.indexOf(";",v_start)　　//其实我刚看见indexOf()第二个参数的时候猛然有点晕，后来想起来表示指定的开始索引的位置...这句是为了得到值的结束位置。因为需要考虑是否是最后一项，所以通过";"号是否存在来判断
　　　　　　　　if (v_end==-1) v_end=document.cookie.length　　
　　　　　　　　return unescape(document.cookie.substring(v_start,v_end))　　//通过substring()得到了值。想了解unescape()得先知道escape()是做什么的，都是很重要的基础，想了解的可以搜索下，在文章结尾处也会进行讲解cookie编码细节
　　　　　　} 
　　　　}
　　　　return ""
　　}　



