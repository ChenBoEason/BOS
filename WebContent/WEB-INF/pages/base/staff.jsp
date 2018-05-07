<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath }/js/outOfBounds.js"></script>
<script type="text/javascript">
	function doAdd(){
		//alert("增加...");
		$('#addStaffWindow').window("open");
	}
	
	function doView(){
		
		$('#searchStaffWindow').window("open");
	}
	//批量删除取派员
	function doDelete(){
		var Allselected = $("#grid").datagrid("getSelections");
		if(Allselected.length == 0){
			//没有选择，提示
			$.messager.alert("提示信息","请选择需要删除的记录","warning");
		}else{
			<%-- 定义一个数组 将所有ID放入数组--%>
			var array = new Array();
			for (var int = 0; int < Allselected.length; int++) {
				var id = Allselected[int].id;
				array.push(id);
			}
			<%-- 将数组中的所有元素取出来以逗号隔开--%>
			var ids = array.join(",");
			/* 这种请求相当于输入地址按下回车 */
			window.location.href = "${pageContext.request.contextPath }/staffAction_delete.action?ids="+ids;
		}
	}
	
	function doRestore(){
		//获取列表所选中的所有信息
		var Allselected = $("#grid").datagrid("getSelections");
		if(Allselected.length == 0){
			$.messager.alert("提示信息","请选择你所要还原的记录","warning");
		}else{
			//用数组存放所勾选的id
			var array = new Array();
			
			for (var i = 0; i < Allselected.length; i++) {
				array.push(Allselected[i].id); 
				
			}
			<%-- 将数组中的所有元素取出来以逗号隔开--%>
			var ids = array.join(",");
			window.location.href = "${pageContext.request.contextPath}/staffAction_restore.action?ids="+ids;
		}
	}
	//工具栏
	var toolbar = [ {
		id : 'button-view',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '作废',
		iconCls : 'icon-cancel',
		handler : doDelete
	},{
		id : 'button-save',
		text : '还原',
		iconCls : 'icon-save',
		handler : doRestore
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'name',
		title : '姓名',
		width : 120,
		align : 'center'
	}, {
		field : 'telephone',
		title : '手机号',
		width : 120,
		align : 'center'
	}, {
		field : 'haspda',
		title : '是否有PDA',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="1"){
				return "有";
			}else{
				return "无";
			}
		}
	}, {
		field : 'deltag',
		title : '是否作废',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="0"){
				return "正常使用"
			}else{
				return "已作废";
			}
		}
	}, {
		field : 'standard',
		title : '取派标准',
		width : 120,
		align : 'center'
	}, {
		field : 'station',
		title : '所谓单位',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 取派员信息表格
		$('#grid').datagrid( {
			fit : true,
			border : false,
			rownumbers : true,   //显示行号
			striped : true,
			pageList: [3,5,10],
			pagination : true,    // 显示分页
			toolbar : toolbar,   //工具栏${pageContext.request.contextPath}/staffAction_pageQuery.action
			url : "${pageContext.request.contextPath}/staffAction_pageQuery.action",   //发送请求地址
			idField : 'id',     // 
			columns : columns,
			onDblClickRow : doDblClickRow  //指定数据行双击时作出的函数反应
		});
		
		// 添加取派员窗口
		$('#addStaffWindow').window({
	        title: '添加取派员',
	        width: 400,
	        modal: true,  	//遮罩效果
	        shadow: true,	//遮阴效果	 
	        closed: true,	//关闭状态 
	        height: 400,	
	        resizable:false	//是否可以调整大小
	    });
		// 修改取派员窗口
		$('#editStaffWindow').window({
	        title: '修改取派员信息',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		// 查询取派员窗口
		$('#searchStaffWindow').window({
	        title: '查询取派员信息',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
	});

	//双击事件处理函数
	function doDblClickRow(rowIndex, rowData){
		//打开修改窗口
		$("#editStaffWindow").window("open");
		//根据form表单中name名字进行匹配对应  loda将rowData数据加载进去
		$("#editStaffForm").form("load",rowData);
	}
	
	$(function(){
		var reg = /^1[3|4|5|7|8|9][0-9]{9}$/;//正则表达式
		$.extend($.fn.validatebox.defaults.rules, {    
		    TelNumber: {    
		        validator: function(value, param){    
		            return reg.test(value);    
		        },    
		        message: '你输入的电话号码误!'   
		    }    
		});  
	});
	
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="对收派员进行添加或者修改" id="addStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
				<script type="text/javascript">
					$("#save").click(function(){
						//验证表单是否正确
						var v =$("#addStaffForm").form("validate");
						if(v){
							//验证正确提交表单
							$("#addStaffForm").submit();
							
						}
					});
				</script>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addStaffForm" action="${pageContext.request.contextPath }/staffAction_add.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone" class="easyui-validatebox" 
						required="true" data-options="validType:'TelNumber'"/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-validatebox" required="true"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>	
	</div>
	
	<!--修改信息  -->
	<div class="easyui-window" title="对收派员进行添加或者修改" id="editStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
				<script type="text/javascript">
					$("#edit").click(function(){
						//验证表单是否正确
						var v =$("#editStaffForm").form("validate");
						if(v){
							//验证正确提交表单
							$("#editStaffForm").submit();
							
						}
					});
				</script>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editStaffForm" action="${pageContext.request.contextPath }/staffAction_edit.action" method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone" class="easyui-validatebox" 
						required="true" data-options="validType:'TelNumber'"/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-validatebox" required="true"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>	
		<!-- 条件查询 -->
		<div region="center" id="searchStaffWindow" style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone" class="easyui-validatebox" /></td>
					</tr>
					
					<!-- <tr>
						<td colspan="2">
						<input type="checkbox" name="deltag" value="1" />
						是否作废</td>
					</tr> -->
					<tr>
					<td colspan="2"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
					<script type="text/javascript">
					$.fn.serializeJson=function(){  
			            var serializeObj={};  
			            var array=this.serializeArray();
			            $(array).each(function(){  
			                if(serializeObj[this.name]){  
			                    if($.isArray(serializeObj[this.name])){  
			                        serializeObj[this.name].push(this.value);  
			                    }else{  
			                        serializeObj[this.name]=[serializeObj[this.name],this.value];  
			                    }  
			                }else{  
			                    serializeObj[this.name]=this.value;   
			                }  
			            });  
			            return serializeObj;  
			        }; 
					$("#btn").click(function(){
						var p = $("#searchForm").serializeJson();
						//重新发起ajax请求，提交参数
						$("#grid").datagrid("load",p);
						$("#searchStaffWindow").window("close");
						});		
					</script>
					</td>
					</tr>
					</table>
			</form>
		</div>	
		
		
	</div>
</body>
</html>	