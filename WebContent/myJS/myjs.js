//批量删除取派员
	function doDelete(){
		var Allselected = $("#grid").datagrid("getSelections");
		if(Allselected.length == 0){
			//没有选择，提示
			$.messager.alert("提示信息","请选择需要删除的记录","warning");
		}else{
			
			var array = new Array();
			for (var int = 0; int < Allselected.length; int++) {
				var id = Allselected[int].id;
				array.push(id);
			}
			
			var ids = array.join(",");
			/* 这种请求相当于输入地址按下回车 */
			window.location.href = "${pageContext.request.contextPath }/staffAction_delete.action?ids="+ids;
		}
	}