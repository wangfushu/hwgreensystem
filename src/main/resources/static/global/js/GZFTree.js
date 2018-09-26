
/**  
 * 树控件 
 * @module 树控件
 * @author 郭志峰
 */ 

 /** 
 * GZFTREE_OBJECTS_ARRAY类描述
 * @class GZFTREE_OBJECTS_ARRAY
 * @classdesc  记录页面树对象存为map,避免重复创建 2014-7-23 修改
 */
function GZFTREE_OBJECTS_ARRAY(){
	
}

var gzfTreeObjsArray = new GZFTREE_OBJECTS_ARRAY(); 


 /** 
 * GZFTree类描述
 * @class GZFTree
 * @param {String} elementId 树控件的id 即页面UL的id
 * @classdesc  GZFTree类描述
 */ 



function GZFTree(elementId) {
	// 如果已经创建过直接返回2014-7-23 修改
	if(gzfTreeObjsArray[elementId]!=undefined)
		return gzfTreeObjsArray[elementId];
	
	/** 
     * @public    
	 * @type {boolean} 
	  
     * @description 是否支持拖拽功能（默认false不支持）
	 * @default false
     */
	 this.isDrag = false;
	/** 
     * @public
	 * @type {boolean}   
     * @description 是否启用选择框 
	 * @default true
     */
	this.isCheckBox=true;
	/** 
     * @public 
	 * @type {boolean}   
     * @description 是否关联选择节点 this.isCheckBox=true时有效，用于在选择某节点时是否也选择其父节点和子节点 
	 * @default true
     */
	this.isCheckRelation=true;
	/** 
     * @public 
	 * @type {string}   
     * @description 目录关闭时图标 （-号）
	 * @default null
     */
	this.collapsedImg =  "images/collapsed.gif";
	/** 
     * @public 
	 * @type {string}    
     * @description 目录展开时图标 （+号）
	 * @default null
     */
	this.expandedImg = "images/expanded.gif";
	/** 
     * @public 
	 * @type {string}   
     * @description 非目录时前的图标 
	 * @default null
     */
	this.spaceImg = "images/transparent.gif";
	/** 
     * @public  
	 * @type {string}   
     * @description 叶子图标 
	 * @default null
     */
	this.leafImg = "images/htmlIcon.gif";
	/** 
     * @public  
	 * @type {string}    
     * @description 文件夹图标 
	 * @default null
     */
    this.forderImg = "images/folderIcon.gif";
	/** 
     * @public    
	 * @type {string} 
     * @description 加载图标 
	 * @default null
     */
	this.busingDivImg = 'images/loading.gif';
	/** 
     * @public  
	 * @type {boolean}   
     * @description 是否展开树节点 调用iniDatas 初始化时使用，初始化数据后，是否展开节点
	 * @default true
     */
	this.isExpand=true;//是否展开树
	/** 
     * @public  
	 * @type {boolean}    
     * @description 拖拽节点到其它节点时是否调用远程方法。<br/>
	 *  默认为false<br/>
	 *  如果值为true,要求isCangeForderRemote不可为空<br/>
	 *  放开鼠标后调用isCangeForderRemote 方法但不会马上放入新文件夹而由回调函数时再调用移动的方法。
	 * @default false
    */
	this.isCangeForderRemote=false;//是否调用远程代码
	/** 
     * @public
	 * @type {string}    
     * @description 拖拽节点到其它节点时调用的远程方法。值为函数或代码<br/>
	 *  isCangeForderRemote为true时有效<br/>
	 *  放开鼠标后调用isCangeForderRemote 的方法但不会马上放入新文件夹，而由回调函数时再调用移动的方法。<br/>
	 *  回调时可能用到的几个方法 getDragToPlaceNode、getDragNode、getDragNodes
	 * @default ''
    */
	this.changeFolderRemote = ';';//调用远程代码


	this.wordLength = 16;
    this.liTop = 'li_';
	this.checkboxTop = 'treeCheckBox_';//checkbox的id前缀
	this.isBusy = false;

	//移动用
    //是否按下鼠标键
	this.mouseCode; //mouseCode用于判断是否有按在鼠标或放开鼠标
	this.isMouseDown = false;//是否有按下A节点。准备拖拽
	//已经选择过的节点
	this.hadClickAs=new Array();
	this.forderType=1;
	this.hadClickA=null;
	this.changeA = null;
	this.keyCode;
	
	//拖动提示信息层
	this.moveDiv = null;
	this.busingDiv = null;
	this.H=window.screen.height;

	this.datas = null; //js加载的数据
    //this.k=0;//原用于默认打开第一级节点下的树
  
	this.element = document.getElementById(elementId);
	
	if(this.element.getAttribute('collapsedImg'))
		this.collapsedImg =  this.element.getAttribute('collapsedImg');;
    if(this.element.getAttribute('expandedImg'))
		this.expandedImg = this.element.getAttribute('expandedImg');
    if(this.element.getAttribute('spaceImg'))
		this.spaceImg = this.element.getAttribute('spaceImg');
	if(this.element.getAttribute('leafImg'))
		this.leafImg = this.element.getAttribute('leafImg');
	if(this.element.getAttribute('forderImg'))
		this.forderImg = this.element.getAttribute('forderImg');
	if(this.element.getAttribute('isCheckBox'))
		this.isCheckBox = this.element.getAttribute('isCheckBox');
	if(this.element.getAttribute('isCheckRelation'))
		this.isCheckRelation = this.element.getAttribute('isCheckRelation');	
	//收集客户端信息
	
	//this.W=screen.Width;
	if(elementId!=null  && document.getElementById(elementId))
		this.element = document.getElementById(elementId);
	
	this.initEvent();
    //存入面树对象创建数组中 避免重复创建 2014-7-23 修改
	gzfTreeObjsArray[elementId] = this;

}


/** 
* @private
* @method initEvent
* @description 注册事件 跟据属生isDrag true或flase 注册是否拖拽
* @see GZFTree#isDrag 
*/ 

GZFTree.prototype.initEvent=function(){
	var eleTree = this;
	if(window.addEventListener){ // Mozilla, Netscape, Firefox
        this.element.addEventListener('click',function(){GZFTree_menuOnClick(eleTree);}, false);
        this.element.addEventListener('mousedown',function(){GZFTree_elementOnMouseDown(eleTree);}, false);
		//移动用
		if(this.isDrag==true || this.isDrag=='true'){
			this.element.addEventListener('mouseover',function(){GZFTree_elementOnMouseOver(eleTree);}, false);
			//this.element.addEventListener('mousedown',function(){GZFTree_elementOnMouseDown(eleTree);}, false); 2014/7/15 移至if前解决单击样式不变的问题
			this.element.addEventListener('mouseup',function(){GZFTree_elementOnMouseUp(eleTree);}, false);
			this.element.addEventListener('mouseout',function(){GZFTree_elementOnMouseOut(eleTree);}, false);
			this.element.addEventListener('mousemove',function(){GZFTree_elementOnMouseMove(eleTree);}, false);
			this.element.addEventListener('selectstart',function(){return false;}, false);
			document.addEventListener('mousedown',
			function(){eleTree.mouseCode = event.button;}, false);
			document.addEventListener('mouseup',
			function(){eleTree.mouseCode = null;if(eleTree.moveDiv!=null) eleTree.unclinchStatus();}, false);

			document.addEventListener('keydown',function(){eleTree.keyCode = event.keyCode;}, false);
			document.addEventListener('keyup',function(){eleTree.keyCode = null;}, false);
		}
	
    } else { // IE
		
        this.element.attachEvent('onclick', function(){GZFTree_menuOnClick(eleTree);});
        this.element.attachEvent('onmousedown', function(){GZFTree_elementOnMouseDown(eleTree);});
		//移动用
		if(this.isDrag==true || this.isDrag=='true'){
			this.element.attachEvent('onmouseover', function(){GZFTree_elementOnMouseOver(eleTree);});
			//this.element.attachEvent('onmousedown', function(){GZFTree_elementOnMouseDown(eleTree);}); 2014/7/15 移至if前解决单击样式不变的问题
			this.element.attachEvent('onmouseup', function(){GZFTree_elementOnMouseUp(eleTree);});
			this.element.attachEvent('onmouseout', function(){GZFTree_elementOnMouseOut(eleTree);});
			this.element.attachEvent('onmousemove', function(){GZFTree_elementOnMouseMove(eleTree);});
			this.element.attachEvent('onselectstart', function(){return false;});
			document.attachEvent('onmousedown', function(){eleTree.mouseCode = event.button;} );
			document.attachEvent('onmouseup', 
			function(){eleTree.mouseCode = null;if(eleTree.moveDiv!=null) eleTree.unclinchStatus();} );

			document.attachEvent('onkeydown', function(){eleTree.keyCode = event.keyCode;} );
			document.attachEvent('onkeyup', function(){eleTree.keyCode = null;} );
		}
		
    }

}


/** 
* @public 
* @method iniDatas
* @param {Object[]} pdatas 数据
* @description 传点数据，生成树 
*/ 

GZFTree.prototype.iniDatas=function(pdatas){
   this.datas = pdatas;
   this.element.innerHTML = "";
   var dataLen = this.datas.length;
   this.makeTreeForArray(null,0);
}


/** 
* @private 
* @method makeTreeForArray
* @param {object} vA 节点（界面元素A）
* @param {String} pId 父节点id
* @description 对象组生成树 
*/ 
GZFTree.prototype.makeTreeForArray=function(vA,pId){
   if(!this.datas || !this.datas.length)
	return;
   var newPids = new Array();
   var i=0;
   //alert('datas.lenth:' + datas.length);
   while(i<this.datas.length){
	if(this.datas[i] && this.datas[i].parentId == pId){
	   //alert("i:"+i);
	   var dataId = this.datas[i].id;
	   var dataIcon = this.datas[i].icon;
	   var ul = null;
	   var nli = null;
	    
	   //父节点转为有子节点的节点
	   if(vA==null){
	     ul = this.element;
	     //this.k = 1;
	   }else{
	     
	     var vpLi = vA.parentElement;
	     vpLi.getElementsByTagName('IMG')[1].src = this.element.getAttribute('forderImg');
	     ul = this.makeUL(vpLi);
	     if(this.isExpand)
	      this.expand(vpLi);
	     //this.k=0;
	   }
	   //生成节点
	   nli = this.newLi(this.datas[i].id,this.datas[i].name,this.datas[i].alt,this.datas[i].icon,this.datas[i].type,this.datas[i].fun,this.datas[i].checkBox);
	   ul.appendChild(nli);
	   //记录下节点的Id做为下次寻找的父节点
	   var newA = nli.getElementsByTagName('A')[0];
	   newPids[newPids.length] = {id:this.datas[i].id,ele:newA};
	   //原始数组中删除该数据 使计数i 减1
	   
	   this.datas.splice(i,1);
	   i--;
	   
	}	
	
	i++;
	
   }
   
   var newLen = newPids.length;
   for(var j=0;j<newLen;j++){
	this.makeTreeForArray(newPids[j].ele,newPids[j].id);
   }
   //alert('k is :' + k);
}



/** 
* @private 
* @method unclinchStatus
* @description 隐藏被拖动对象的影子（移动时使用）
*/ 

GZFTree.prototype.unclinchStatus=function(){
    //element.style.cursor = 'hand';
    if(this.moveDiv!=null){
		this.moveDiv.style.display = 'none';
    }
}




/** 
* @public 
* @method busying
* @description 开启加载遮罩层 
*/ 
GZFTree.prototype.busying= function(){
	if(this.busingDiv==null){
		this.busingDiv = document.createElement('DIV');
		var busingImg = document.createElement('IMG');
		busingImg.src = this.busingDivImg;
		busingImg.className = 'TreeBusyingImg';
		var panel = this.element.parentElement;
		this.busingDiv.appendChild(busingImg);
		panel.insertBefore(this.busingDiv,this.element);
		this.busingDiv.className = 'TreeBusying';
		this.busingDiv.style.zIndex = 100;
		this.busingDiv.style.width = '100%';
		this.busingDiv.style.height = '100%';
		this.busingDiv.style.top = this.H/2;
	}
	this.busingDiv.style.display = 'block';
	this.isBusy = true;
	this.element.cursor = 'wait';
};


/** 
* @public 
* @method getDragToPlaceNode
* @description （拖动时用）获取被拖拽的节点 
*/ 
GZFTree.prototype.getDragToPlaceNode=function(){
    return this.changeA;
}
/** 
* @public 
* @method getDragNodes
* @description （拖动时用）获取被拖拽的节点 
*/
GZFTree.prototype.getDragNodes=function(){
    return hadClickAs;
}


/** 
* @private 
* @method dragNode
* @description （拖动时用）拖拽节点 
*/
GZFTree.prototype.dragNode=function(){
    if(this.hadClickAs.length==0)
	return;
	
    if(this.moveDiv==null)
	this.moveDiv = document.createElement('div');
    this.moveDiv.style.display = 'block';
    this.moveDiv.style.position = 'absolute';
    this.element.appendChild(this.moveDiv);
    if(this.hadClickAs.length==1){
		this.moveDiv.innerHTML = '<FONT  COLOR="#3300FF">'+(this.hadClickAs[0].innerText||this.hadClickAs[0].textContent)+'</FONT>';
    }else
    	this.moveDiv.innerHTML = '<FONT  COLOR="#FF0000">'+this.hadClickAs.length +'</FONT>'+ '个对象';
    this.moveDiv.style.top = window.event.clientY + 14;
    this.moveDiv.style.left = window.event.clientX +40;

	//将要拖动的节点关闭 避免拖到该节点的下级节点
	for(var i=0;i< this.hadClickAs.length;i++){
		var dragLi = this.hadClickAs[i].parentElement;
		if(dragLi.getElementsByTagName('UL').length>0)
			this.close(dragLi);
	}
}



/** 
* @private 
* @method addHadClickA
* @param {object} p_ele 节点（界面元素A）
* @description （拖动时用）新加入要拖拽的节点 
*/
GZFTree.prototype.addHadClickA = function(p_ele){
     if(this.hadClickAs.length>0){
		if(this.hadClickAs[0].parentElement.parentElement!= p_ele.parentElement.parentElement){
			p_ele.className = ''; 
			return ;
		}
     }
     if(!this.isHadClickA(p_ele)){
			this.hadClickAs[this.hadClickAs.length]= p_ele;
			p_ele.className = "clsSelect" ;
     }
}


/** 
* @private 
* @method clearHadClickA
* @param {object} p_ele 节点（界面元素A）
* @description （拖动时用）从拖拽的节点删除某个节点
*/
GZFTree.prototype.clearHadClickA = function(p_ele){
    var len = this.hadClickAs.length;
    for(var i=0;i<len;i++){
		if(p_ele==this.hadClickAs[i]){
			this.hadClickAs[i].className = '';
			this.hadClickAs.splice(i,1);
			return true;
		}	
    }
}

/** 
* @public 
* @method changeParent
* @param {object} p_ele 放入的父节点（界面元素A）
* @description （远程拖拽时用）将拖拽的节点放入新节点（转入的参数）<br/>
* @description  一般在远程方法回调函数中调用 放入的父节点可以从getDragToPlaceNode方法获取
*/

GZFTree.prototype.changeParent=function(p_ele){
	var len = this.hadClickAs.length;
	for(var i=0;i<len;i++){
		
		var oldLi = this.hadClickAs[i].parentElement.parentElement.parentElement;
		var oldParentA =  oldLi.getElementsByTagName('A')[0];
		var oldUL = oldLi.getElementsByTagName('UL')[0];
		var oldULlis = oldUL.getElementsByTagName('LI');

		var newLi = p_ele.parentElement;
		if(newLi.getElementsByTagName('UL').length==0){
			this.makeUL(newLi);
		}
		var returnNode = newLi.getElementsByTagName('UL')[0].appendChild(this.hadClickAs[i].parentElement);
		this.expand(newLi);
		if(oldULlis.length==0){
			oldLi.getElementsByTagName('IMG')[0].src = this.spaceImg;
			oldUL.style.display = 'none';
			close(oldLi);
			
		}
		oldParentA.className = '';
	}
	p_ele.className = '';
}


/** 
* @public 
* @method clearAllHadClickA
* @description （远程拖拽时用）清空拖拽的节点内存<br/>
* @description  一般在远程方法回调函数中调用 在节点在操作完成后使用
*/
GZFTree.prototype.clearAllHadClickA=function(){
    var len = this.hadClickAs.length;
    for(var i=0;i<len;i++){
	this.hadClickAs[i].className = '';	
    }
    this.hadClickAs.length=0;
   
    if(this.hadClickA!=null) 
    	this.hadClickA.className = '';	
}


/** 
* @private 
* @method isAllowPlace
* @param {object} ele_A 放入的父节点（界面元素A）
* @description （拖拽时用）判断是否可以拖动。<br/>
* @description  目前只限制同一个父节点的节点才能多个拖动
*/
GZFTree.prototype.isAllowPlace = function(ele_A){
	if(ele_A.tagName && ele_A.tagName.toUpperCase()!='A')
	  return false;
	
    if(ele_A.type==undefined || ele_A.type=='' || ele_A.type==null)
	 return true;
     if(ele_A.type!=this.forderType){
	return false;
     }
     return true;
}

//移动
GZFTree.prototype.mouseOverIco = function(ele_A){
     if(this.isAllowPlace(ele_A)){
	ele_A.className = "clsMouseAllowPut" ;
     }else{
        ele_A.className = '';
	ele_A.style.cursor = 'not-allowed';
     }
}
//移动
GZFTree.prototype.isHadClickA=function(p_ele){
    var len = this.hadClickAs.length;
    for(var i=0;i<len;i++){
	if(p_ele==this.hadClickAs[i])
		return true;
    }
    return false;
}

GZFTree.prototype.checkNode=function(vsrcObj){
		
	if(this.isCheckRelation=='false')
		return;
        //var vobj = vsrcObj.parentNode;
	//alert(vobj.childNodes.length);
        this.checkChildren(vsrcObj);
		this.checkParent(vsrcObj);
}



GZFTree.prototype.selectNode=function(tagID,isMark){	
	
		if(tagID!=null && tagID.tagName.toUpperCase()=='LI')
		{		
				var ulChildNode;
				var isUl = false;
				
//				if(navigator.appName.toUpperCase()=='NETSCAPE'){
				//因为IE10的UL也是在下一节点，而不是子节点
					if(tagID.nextSibling && 
							tagID.nextSibling.nextSibling && tagID.nextSibling.nextSibling.tagName && tagID.nextSibling.nextSibling.tagName.toUpperCase()=='UL'){
						isUl = true;
						ulChildNode = tagID.nextSibling.nextSibling;
					}
//				}else{
					
					
					if (!isUl) {					    
					    for(var i=0;i < tagID.childNodes.length;i++)
					    {
						if(tagID.children[i] && tagID.children[i].tagName && tagID.children[i].tagName.toUpperCase() == 'UL')
						{
						    ulChildNode = tagID.children[i];
						    i = tagID.childNodes.length;
						    isUl = true;
						}
					    }
					}
//				}
				if(isUl){
					if(ulChildNode.style.display=='none' || ulChildNode.style.display=='')
					{   
						tagID.children[0].src = this.expandedImg;
						//ulChildNode.filters.revealTrans.apply();
						ulChildNode.style.display='list-item';
						ulChildNode.style.visibility='visible';
						//ulChildNode.filters.revealTrans.play();
						//tagID.children[3].filters.item(0).play();
					}else{
						tagID.children[0].src = this.collapsedImg;
						//ulChildNode.filters.revealTrans.stop();
						ulChildNode.style.visibility='hidden';
						ulChildNode.style.display='none';
					}
				}
		}
		
}



GZFTree.prototype.checkChildren=function(vsrcObj){

	var vobj = vsrcObj.parentNode;
	var ischeck = vsrcObj.checked;

	var childUrls = vobj.getElementsByTagName("UL");
	var vUlLength = childUrls.length;
        for(var i=0;i < vUlLength;i++){
	        var inputEles = childUrls[i].getElementsByTagName("INPUT");
	        for(var j=0;j<inputEles.length;j++){
			if(inputEles[j].type!=undefined && inputEles[j].type.toUpperCase()=='CHECKBOX'){
				inputEles[j].checked = ischeck;
			
			}
		}
	}
}





/** 
* @public 
* @method setActiveNode
* @param {object} vsNode 要在界面显示的节点A
* @description 找到某个节点，将某节点显示出来。<br/>
*/
GZFTree.prototype.setActiveNode= function(vsNode){
	try{
	  vsNode.click();
	  this.clearAllHadClickA();
	  this.addHadClickA(vsNode);
	  var expandNode =  this.getParent(vsNode);
	  
	  while(expandNode!=null){
		this.expand(expandNode);
		expandNode =  this.getParent(expandNode);
	  }
	  vsNode.scrollIntoView();
	}catch(e){alert(e.message);}
};

/** 
* @public 
* @method unBusying
* @description 遮罩层关闭<br/>
*/
GZFTree.prototype.unBusying= function(){
	if(this.busingDiv==null){
		return;
	}
	this.busingDiv.style.display = 'none';
	this.isBusy = false;
	this.element.cursor = 'default';
	this.unclinchStatus();

};
//生成子节点的ul标签（私有）
GZFTree.prototype.makeUL=function(vli){
  
  if(vli.children!=null)
	  for(var k=0;k<vli.children.length;k++){
	    if(vli.children[k].tagName.toUpperCase()=='UL'){
		return vli.children[k];
	    }
	  }
  var vul = document.createElement('UL');
 
  vli.appendChild(vul);
  vli.getElementsByTagName('IMG')[0].src=this.collapsedImg;	
  vli.getElementsByTagName('IMG')[1].src=this.forderImg;	
  return vul;
}


//向上勾选关联的父节点（针对配置有选择框的树）（私有）
GZFTree.prototype.checkParent = function(vsrcObj){
			var vobj = vsrcObj.parentNode;
			//alert(vobj.tagName);
			var ischeck = vsrcObj.checked;
			//alert(vobj.childNodes.length);
			if(vobj.parentNode !=null 
			   && vobj.parentNode != undefined 
			   && vobj.parentNode.parentNode != null 
			   && vobj.parentNode.parentNode != undefined
			   && vobj.parentNode.parentNode.tagName.toUpperCase() == 'LI' )
			{
			  var vParentLi = vobj.parentNode.parentNode;
			  if(vsrcObj.checked == true){
				 vParentLi.children[1].checked = true;
				 this.checkParent(vParentLi.children[1])
			  }else{
				 var bordersUL = vParentLi.children[4];
				 if(vParentLi.children[3].tagName.toUpperCase()=='UL')
				bordersUL = vParentLi.children[3];
				 var bordersLength = bordersUL.children.length;
				 var isParentCheck = false;
				 vParentLi.children[1].checked = false;
				 if(bordersLength > 0)
				   for(var i = 0; i < bordersLength;i++){
				  if(bordersUL.children[i].children[1].checked == true){
					isParentCheck = true;
					i = bordersLength;
					//vParentLi.children[1].checked = true;
				  }
				   }
				   
				   if(
				   isParentCheck 
				   && vParentLi.children[1].tagName.toUpperCase()=='INPUT' 
				   && vParentLi.children[1].type.toUpperCase() =='CHECKBOX'
				   )
					 vParentLi.children[1].checked = true;
				this.checkParent(vParentLi.children[1])
			  }
			}else
			  return;
}
	
/** 
* @public 
* @method setActiveChecks
* @param {string[]} ids 要在选择框打勾的节点id组
* @description 勾选多个节点（针对配置有选择框的树）<br/>
*/

GZFTree.prototype.setActiveChecks= function(ids){
   if(!ids || !ids.length)
	return;
	
   var cboxs = this.element.getElementsByTagName('INPUT');
   for(var i=0;i < cboxs.length;i++){
	if(cboxs[i].type.toUpperCase()=='CHECKBOX'){
	   var cbox = cboxs[i];
	   for(var j=0;j<ids.length;j++){
		if(cbox.id == this.checkboxTop + ids[j]){
		        cbox.checked = true;
			//checkNode(cbox);//连子节占一起打勾
			if(this.element.getAttribute('isCheckRelation')=='true'){//add by zjf 2013-4-25 8:40
				this.checkParent(cbox);
			}
		}
	   }
	}
   }

};


/** 
* @public 
* @method isLastNode
* @description 判断是否是最末层的节点<br/>
*/
GZFTree.prototype.isLastNode= function(vA){
	var vli = vA.parentElement;
    return !vli.getElementsByTagName('UL')[0];
};

/** 
* @public 
* @method clearChecks
* @description 清空所选节点（针对配置有选择框的树）<br/>
*/
GZFTree.prototype.clearChecks=function(){
   var inps = this.element.getElementsByTagName('INPUT');
   if(inps && inps.length){
	for(var i=0;i<inps.length;i++){
	   var inp = inps[i];
	   if(inp.type.toUpperCase()=='CHECKBOX')
	      inp.checked = false;
	}
   }
}

//获取li下的ul (私)
GZFTree.prototype.getUL = function(vli){
  if(vli.children!=null)
	  for(var k=0;k<vli.children.length;k++){
	    if(vli.children[k].tagName.toUpperCase()=='UL'){
		return vli.children[k];
	    }
	  }
  return null;
}


/** 
* @public 
* @method cleanChildren
* @param {object} vli 要清空的节点(A)
* @description 清除了某节点的所有子节点。<br/>
*/
GZFTree.prototype.cleanChildren=function(vli){

   if(vli.tagName.toUpperCase()=='A')
     vli = vli.parentElement;
   var ul = this.getUL(vli);
   if(ul==null) return;
   ul.innerHTML = '';
   vli.removeChild(ul);
   var imgs = vli.getElementsByTagName('IMG');
   imgs[0].src = this.element.getAttribute('spaceImg');
}

//=============================================================================================================================================

/** 
* @public 
* @method getParent
* @param {object} pElement 节点(A)
* @description 取得某节点的父节点。<br/>
*/
GZFTree.prototype.getParent = function(pElement){
     var vli = pElement.parentElement;
     var vUl = vli.parentElement;
     if(vUl.className == 'GZFTree')
      return null;
     var vpli = vUl.parentElement;
     var as = vpli.getElementsByTagName('A');
     return as[0];
}

/** 
* @public 
* @method getLiPath
* @param {object} pElement 节点(A)
* @param {string} pSign 分隔字符
* @description 取得某节点的路径字符串<br/>
* @returns {String} 
* @example 用分隔符->来描述节点 ”叶子A“ 的树路径，显示  根目录->子目录->叶子A
*/
GZFTree.prototype.getLiPath=function(pA,pSign){
    var vparent = this.getParent(pA);
    if(vparent==null) return (pA.innerText||pA.textContent) + pSign;
    return this.getLiPath(vparent,pSign) +  (pA.innerText||pA.textContent) + pSign;
}

/** 
* @public 
* @method getParentsStr
* @param {object} pElement 节点(A)
* @param {string} pSign 分隔字符
* @description 取得某节点的父路径字符串<br/>
* @returns {String} 
* @example <caption>用分隔符->来描述节点 ”叶子A“ 的父树路径，显示  根目录->子目录</caption>
*/
GZFTree.prototype.getParentsStr = function(pElement,pSign){
    if(!pElement) return "";
    var eText = (pElement.innerText||pElement.textContent);	
    var vparent = this.getParent(pElement);
    if(vparent==null) return "";
    return this.getLiPath(vparent,pSign);
}

/** 
* @public 
* @method isRoot
* @param {object} pElement 节点(A)
* @returns {boolean} 
* @description 判断某节点是否是根节点<br/>
*/
GZFTree.prototype.isRoot=function(pElement){
   if((pElement.tagName).toUpperCase()!='A')
     return false;
   var li = pElement.parentElement;
   if((li.tagName).toUpperCase()!='LI')
     return false;
   var ul = li.parentElement;
   if((ul.tagName).toUpperCase()!='UL')
     return false;
   
   if(ul.className == 'GZFTree'){
	return true;
   }
   return false;
}

/** 
* @public 
* @method remove
* @param {object} pElement 节点(A)
* @description 删除某节点<br/>
*/
GZFTree.prototype.remove = function(pElement){
    var vli = pElement.parentElement;
    var vul = vli.parentElement;
    
    vli.removeNode(vli);
 
    if(vul.getElementsByTagName('LI').length>0) return;
    var vpLi = vul.parentElement;
    if(vpLi.tagName.toUpperCase()!='LI') return;
    
    vpLi.removeChild(vul);
    
    var imgs = vpLi.getElementsByTagName('IMG');
    imgs[0].src = this.element.getAttribute('spaceImg');
}


/** 
* @public 
* @method move
* @param {object} vA 节点(A)
* @param {object} vNewParentA 新的父节点(A)
* @description 移动某节点<br/>
*/
GZFTree.prototype.move=function(vA,vNewParentA){
     var vli = vA.parentElement;
    //var vOldParentA = getParent(vA)
    //if(vOldParentA!=null){
	this.remove(vA);
    //}
    var vNewParentli = vNewParentA.parentElement;
    var vNewUL = this.getUL(vNewParentli);
   
    if(vNewUL==null)
       vNewUL = this.makeUL(vNewParentli);
    vNewUL.appendChild(vli);
	this.expand(vNewParentli);
}



/** 
* @public 
* @method changeImg
* @param {object} vA 节点(A)
* @param {string} img 新的图标
* @description 改变某节点图标<br/>
*/
GZFTree.prototype.changeImg= function(vA,img){
    if(!vA) return;
    var vpLi = vA.parentElement;
    var icon = vpLi.getElementsByTagName('IMG')[1];
    icon.src = img;
};





GZFTree.prototype.newLi=function(pId,pTitle,palt,img,type,linkfun,haveCheck){
   var li = document.createElement('LI');
   li.id = this.liTop + pId;
   var firstImg = document.createElement('IMG');
   var secondImg = document.createElement('IMG');
   var atag = document.createElement('A');
   firstImg.className = 'icon';
   secondImg.className = 'pict';
   firstImg.src = this.spaceImg;	
   li.appendChild(firstImg);
   if(img)
     secondImg.src = img;
   else
     secondImg.src = this.element.getAttribute('leafImg');
   if(haveCheck || this.element.getAttribute('isCheckBox')=='true'){
		var newCheckBox = document.createElement('INPUT');
		newCheckBox.type = 'checkbox';
		newCheckBox.align = 'top';
		if(pId!=undefined && pId != null){
		  newCheckBox.id = this.checkboxTop  + pId;
		  
		  newCheckBox.value = pId;
		 }
		li.appendChild(newCheckBox);
   }
   li.appendChild(secondImg);
   if(linkfun){
	atag.onclick = linkfun;
   }

   if(pId!=undefined && pId != null)
	    atag.id = pId;
   
   atag.innerHTML = (pTitle || pTitle==0)?pTitle:"";
   atag.type = (type) ?type:0;

   atag.alt = palt?palt:"";
   var textLen = (atag.innerText.length||atag.textContent.length);
   atag.style.width = this.wordLength*textLen - (0.2*textLen*textLen) + 'px';
   li.appendChild(atag);
   var chLen = 0;
   if(haveCheck || this.isCheckBox=='true')
	   chLen = 13;
   li.style.width = chLen + this.wordLength*textLen - (0.2*textLen*textLen) + 48 + 'px';

   return li;
}


/** 
* @public 
* @method addChildren
* @param {object} vA 节点(A)
* @param {string} pId 父节点id
* @param {string} pTitle 节点名称
* @param {string} palt 提示信息
* @param {string} img 图标
* @param {number} type 类型属性
* @param {function} linkfun 点击函数
* @description 新增节点<br/>
*/
GZFTree.prototype.addChildren=function(vA,pId,pTitle,palt,img,type,linkfun){
   if(vA==null || vA==undefined){
     vul = this.element;
     var nli = this.newLi(pId,pTitle,palt,img,type,linkfun);
     vul.appendChild(nli);
   }else{
	   var vpLi = vA.parentElement;
	   var vul = this.makeUL(vpLi);
	   if(vul!=null){
		var nli = this.newLi(pId,pTitle,palt,img,type,linkfun);
		vul.appendChild(nli);
		this.expand(vpLi);
	   }
   }
}

/** 
* @public 
* @method update
* @param {object} vA 节点(A)
* @param {string} pTitle 节点名称
* @param {string} palt 提示信息
* @param {number} type 类型属性
* @param {object} parentEle 父节点
* @description 修改节点<br/>
*/
GZFTree.prototype.update=function(vA,pTitle,palt,type,parentEle){
	if(vA==null || vA==undefined) return;
	var vpLi = vA.parentElement;
	vA.innerText = pTitle;
	vA.textContent = pTitle;
	vA.alt = palt;
	var textLen = (vA.innerText.length||vA.textContent.length);
	vA.style.width = this.wordLength*textLen - (0.2*textLen*textLen) + 'px';
	if(type && type!=0)
	  vA.type = type;
	var chLen = 0;
	if(this.isCheckBox=='true')
	   chLen = 13;
	vpLi.style.width = chLen + this.wordLength*textLen - (0.2*textLen*textLen) + 48 + 'px';
	var oldParent  = this.getParent(vA);
	if(parentEle!=undefined){
	   if(oldParent==null || oldParent.id!=parentEle.id)
			this.move(vA,parentEle);
	}
}


/** 
* @public 
* @method addUpdateNode
* @param {object} vParent 修改时为父节点，新增时为要加子节点的父节点
* @param {object} vele 节点(A) 要修改的子节点，新增时不需要
* @param {string} pId 新增时的节点id，修改时不需要
* @param {string} pTitle 节点名称
* @param {string} palt 提示信息
* @param {string} img 图片
* @param {number} type 类型属性
* @param {function} linkfun 点击函数
* @description 修改节点<br/>
*/
GZFTree.prototype.addUpdateNode=function(vParent,vele,pId,pTitle,palt,img,type,linkfun){
	
		    if(vele!=undefined && vele!=null){
		    	this.update(vele,pTitle,palt,type,vParent);
		    }else{
		    	this.addChildren(vParent,pId,pTitle,palt,img,type,linkfun);
		    }
}


/** 
* @public 
* @method expand
* @param {object} vli 节点(A)
* @description 展开文件夹节点<br/>
*/
GZFTree.prototype.expand=function(vli){
	if(vli.tagName.toUpperCase()!='LI') vli = vli.parentElement;
	vli.getElementsByTagName('IMG')[0].src=this.element.getAttribute('expandedImg');	
	var ulChildNode = this.getUL(vli);
	if(ulChildNode!=null && ulChildNode.length){
		//ulChildNode.filters.revealTrans.apply();
		ulChildNode.style.display='list-item';
		ulChildNode.style.visibility='visible';
		//ulChildNode.filters.revealTrans.play();
		//tagID.children[3].filters.item(0).play();
		//initImg(ulChildNode);
	}
}


/** 
* @public 
* @method close
* @param {object} vli 节点(A)
* @description 关闭文件夹节点<br/>
*/
GZFTree.prototype.close=function(vli){
    if(vli.tagName.toUpperCase()!='LI') 
		vli = vli.parentElement;
	vli.children[0].src=this.element.getAttribute('collapsedImg');
	var ulChildNode = this.getUL(vli);
	if(ulChildNode!=null){
		//ulChildNode.filters.revealTrans.stop();
		ulChildNode.style.visibility='hidden';
		ulChildNode.style.display='none';
	}
}


/** 
* @public 
* @method getLeaveValue
* @param {string} pType 节点类型如无值则所有子节点
* @description 获取选择框勾取的叶子节点值,isCheckBox=true时有效<br/>
* @returns {string[]} 
*/
GZFTree.prototype.getLeaveValue= function(pType){
	if(this.element.getAttribute('isCheckBox')==null || this.element.getAttribute('isCheckBox') != 'true') return null;
	var j = 0;
	var temp = this.element.getElementsByTagName('INPUT');
	var tl = temp.length;
	var lvalues = new Array();
	
	for(var i=0;i<tl;i++){
	   var tagA = temp[i].parentElement.getElementsByTagName('A')[0];
	   var isLast = this.isLastNode(tagA);
	   
	   if(temp[i].checked == true && isLast){
	        if(pType==undefined)
			lvalues[lvalues.length] = temp[i].value;
		else
			if(tagA.type == pType)
				lvalues[lvalues.length] = temp[i].value;
		
	    }
	}
	return lvalues;
}




/** 
* @public 
* @method getAllValue
* @description 获取选择框勾取所有节点值包括目录,isCheckBox=true时有效<br/>
* @returns {string[]} 
*/
GZFTree.prototype.getAllValue= function(){
	if(this.element.getAttribute('isCheckBox')==null || this.element.getAttribute('isCheckBox') != 'true') return null;
	var j = 0;
	var temp = this.element.getElementsByTagName('INPUT');
	var tl = temp.length;
	var lvalues = new Array();
	
	for(var i=0;i<tl;i++){

	   if(temp[i].checked == true){
	       
		lvalues[lvalues.length] = temp[i].value;
		
	    }
	}
	return lvalues;

};


/** 
* @public 
* @method isCheckBox
* @param {string} pType 节点类型如无值则所有子节点
* @description 获取选择框勾取的叶子节点文本数组,isCheckBox=true时有效<br/>
* @returns {string[]} 
*/
GZFTree.prototype.getLeaveText= function(pType){
	if(this.element.getAttribute('isCheckBox')==null || this.element.getAttribute('isCheckBox') != 'true') return null;
	var j = 0;
	var temp = this.element.getElementsByTagName('INPUT');

	var t1 = temp.length;
	var lvalues = new Array();
	
	for(var i=0;i<t1;i++){
	   var va = temp[i].parentElement.getElementsByTagName('A')[0];
	   var isLast = this.isLastNode(va);
	   if(temp[i].checked == true && isLast){
	   
		if(pType==undefined){
			//var li = temp[i].parentElement;
			lvalues[lvalues.length] = (va.innerText||va.textContent);
		}else{
		      if(va.type == pType){
				//var li = temp[i].parentElement;
				lvalues[lvalues.length] = (va.innerText||va.textContent);
		      }
		}
	    }
	}
	return lvalues;


};




















//以下定义6个树事件
//树控件点击事件
function  GZFTree_menuOnClick(tree){
	
	if(tree.isBusy) return;
	
	var temp = window.event.srcElement||eventTag.target;
   
	with (temp) {
		switch ((tagName).toUpperCase()) {
			case "LI":
				tree.selectNode(temp, false) ;
			case "A":
				tree.selectNode(parentNode, false) ;
				//alert();
				//temp.className = 'clsMouseDown';
				break ;
			case "IMG":
			        //if(temp.className.toUpperCase()=='ICON' || temp.className.toUpperCase()=='PICT'){
					tree.selectNode(parentNode, false) ;
				//}
				break;
			case "INPUT":
			        if(type == 'checkbox')
				  tree.checkNode(temp);
				break;
			default:
				break ;
		}
	}
}


//鼠标指针位于树节点上方
function GZFTree_elementOnMouseOver(tree) {
	if(tree.isBusy) return;
	
	var overA = window.event.srcElement;
	if ((overA.tagName).toUpperCase() == "A") {
		if(!tree.isHadClickA(overA)){
			if(tree.isMouseDown){
				tree.mouseOverIco(overA);
				
			}else
				overA.style.cursor = 'default';
		}else{
			
		}
		  
		return;
	 }else{
		overA = overA.parentElement;
	 }
}

//鼠标树控件中按下
function GZFTree_elementOnMouseDown(tree) {
	
	if(tree.isBusy) return;
	//tree.mouseCode =event.button;  //加入movegzftree.js
	var clickA = window.event.srcElement;
		if ((clickA.tagName).toUpperCase() == "A") {
				tree.isMouseDown = true;
				
				if(tree.keyCode==undefined || tree.keyCode!=17){
					
					tree.clearAllHadClickA();
				
				}
				
				clickA.className = "clsMouseDown" ;
				tree.hadClickA = clickA;
				tree.unclinchStatus();
				
	}
}



//树控件中放开鼠标
function GZFTree_elementOnMouseUp(tree) {
    if(tree.isBusy) return;
	tree.unclinchStatus();
	var clickA = window.event.srcElement;
	
	if ((clickA.tagName).toUpperCase() == "A") {
	//在节点时放开	
		if(tree.hadClickA!=clickA){
		
		//在拖动时放开	
		//隐藏提示,恢复鼠标,放入目录
			if(clickA.className=='clsMouseAllowPut'){
			    if(tree.isCangeForderRemote==false)
					tree.changeParent(clickA);
			    else{
					tree.changeA = clickA;
					tree.busying();
					//alert(changeFolderRemote);
					eval(tree.changeFolderRemote);
					tree.unBusying();
			    }
			}
		}
		if(tree.hadClickA==clickA){
		//不在拖动时放开
			if(tree.keyCode==17){
			 //如果是按下CTRL按键
				if(tree.isHadClickA(clickA)){	
					tree.clearHadClickA(clickA); 
				}else{
					tree.addHadClickA(clickA);
				}
			}else{
			//如果没按下CTRL按键
				tree.clearAllHadClickA();
				tree.addHadClickA(clickA);	
			}
		}
	}else{
	//在节点之外放开
	//隐藏提示,恢复鼠标
		tree.unclinchStatus();
	}
	tree.isMouseDown = false;
	tree.hadClickA = null;
}

//鼠标移出树控件
function GZFTree_elementOnMouseOut(tree){
	if(tree.isBusy) return;
	
	var overA = window.event.srcElement;
	
	if ((overA.tagName).toUpperCase() == "A") {
	
		if(overA.className == "clsMouseOver" || overA.className == "clsMouseAllowPut"){
		
			overA.className = "" ;
		}
		return;
	 }else{
		if(overA.id==tree.element.id)
			tree.unclinchStatus();
			//tree.isMouseDown = false;
			//tree.mouseCode = null;
	 }

}
//鼠标在树控件上移动
function GZFTree_elementOnMouseMove(tree){
	if(tree.isBusy) return;
	
	if(tree.mouseCode!=1){
		tree.isMouseDown = false;
		tree.unclinchStatus();
	}else{
		if(tree.isMouseDown){
		
		    if(tree.hadClickA!=null){
			tree.addHadClickA(tree.hadClickA);
			
			tree.hadClickA = null;
		    }
		    
		    tree.dragNode();
		}else{
		    tree.unclinchStatus();
		}	
	}
	
}



