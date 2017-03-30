<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>subSystem</title>
<#include "/include/header.ftl">
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox ">
					<div class="ibox-content">
						<form role="form" class="form-inline" id="queryForm"
							onsubmit="return false;">
							<button type="button" id="seachBtn"
								class="btn btn-sm btn-primary">
								查找<i class="fa fa-search icon-on-right bigger-110"></i>
							</button>
							<button class="btn btn-sm" type="button" id="resetBtn">
								<i class="fa fa-undo bigger-110"></i> 重 置
							</button>
							<button id="addBtn" class="btn btn-sm btn-success" type="button"> <i class="ace-icon fa fa-hand-o-right white"></i>
								新增
							</button>
						</form>
						<hr>
						<div class="jqGrid_wrapper">
							<table id="grid-table"></table>
							<div id="grid-pager"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--  新增form  -->
	<div id="add-form-modal" class="modal" role="dialog" tabindex="-1">
		<div class="modal-dialog">
			<form class="form-horizontal" role="form" id="add-form" onsubmit="return false;">
				<input type="hidden" name="updId"  id="updId"/>
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="blue bigger">新增/修改</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right" for="subSystemId">
								<span class="red">*</span>
								子系统Id:
							</label>
							<div class="col-sm-5">
								<input type="text" name="subSystemId" id="subSystemId" placeholder="子系统Id" class="form-control input-sm" />
							</div>
						</div>
							 <div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right" for="subSystemName">
								<span class="red">*</span>
								子系统名称:
							</label>
							<div class="col-sm-5">
								<input type="text" name="subSystemName" id="subSystemName" placeholder="子系统名称" class="form-control input-sm" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right" for="subSystemDesc">
								子系统描述:
							</label>
							<div class="col-sm-5">
								<input type="text" name="subSystemDesc" id="subSystemDesc" placeholder="子系统描述" class="form-control input-sm" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right" for="hopDomain">
								子系统外部地址:
							</label>
							<div class="col-sm-5">
								<input type="text" name="hopDomain" id="hopDomain" placeholder="子系统地址" class="form-control input-sm" />
							</div>
						</div>
							<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right" for="interfaceDomain">
								子系统内部地址:
							</label>
							<div class="col-sm-5">
								<input type="text" name="interfaceDomain" id="interfaceDomain" placeholder="子系统内部地址" class="form-control input-sm" />
							</div>
						</div>
							<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right" for="deployMode">
																<span class="red">*</span>
															发布模式:
							</label>
							<div class="col-sm-5">
								<input type="text" name="deployMode" value="Remote" id="deployMode" placeholder="deploy_mode" class="form-control input-sm" />
							</div>
						</div>
												 										<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right" for="domain">
																<span class="red">*</span>
															子系统所属的域:
							</label>
							<div class="col-sm-5">
								<input type="text" name="domain" value="admin" id="domain" placeholder="子系统所属的域" class="form-control input-sm" />
							</div>
						</div>
																</div>

					<div class="modal-footer">
						<button class="btn btn-sm" type="button"  data-dismiss="modal">
							<i class="ace-icon fa fa-times"></i>
							取消
						</button>

						<button id="save-role-btn" class="btn btn-sm btn-success" type="submit">
							<i class="ace-icon fa fa-check"></i>
							保存
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<!--  详情查看  -->
	<div id="view-form-modal" class="modal" role="dialog" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="blue bigger">详情</h4>
				</div>

				<div class="modal-body">
					<form class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-sm-5 control-label no-padding-right text-right">
							子系统ID:
						</label>
						<div class="col-sm-7">
							<p name="subSystemId" class="form-control-static"></p>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-5 control-label no-padding-right text-right">
							子系统名称:
						</label>
						<div class="col-sm-7">
							<p name="subSystemName" class="form-control-static"></p>
						</div>
					</div>
											 									<div class="form-group">
						<label class="col-sm-5 control-label no-padding-right text-right">
							子系统描述:
						</label>
						<div class="col-sm-7">
							<p name="subSystemDesc" class="form-control-static"></p>
						</div>
					</div>
											 									<div class="form-group">
						<label class="col-sm-5 control-label no-padding-right text-right">
							子系统外部地址:
						</label>
						<div class="col-sm-7">
							<p name="hopDomain" class="form-control-static"></p>
						</div>
					</div>
											 									<div class="form-group">
						<label class="col-sm-5 control-label no-padding-right text-right">
							子系统内部地址:
						</label>
						<div class="col-sm-7">
							<p name="interfaceDomain" class="form-control-static"></p>
						</div>
					</div>
											 									<div class="form-group">
						<label class="col-sm-5 control-label no-padding-right text-right">
							发布模式:
						</label>
						<div class="col-sm-7">
							<p name="deployMode" class="form-control-static"></p>
						</div>
					</div>
											 									<div class="form-group">
						<label class="col-sm-5 control-label no-padding-right text-right">
							子系统所属的域:
						</label>
						<div class="col-sm-7">
							<p name="domain" class="form-control-static"></p>
						</div>
					</div>
															</form>
				</div>

				<div class="modal-footer">
					<button class="btn btn-sm" type="button"  data-dismiss="modal">
						<i class="ace-icon fa fa-times"></i>
						确定
					</button>
				</div>
			</div>
		</div>
	</div>

	<#include "/include/footer.ftl">

	<script type="text/javascript">
	var list_url  = ctxPaths+ '/subSystem/query.ajax';
	var update_url   = ctxPaths+ '/subSystem/add.ajax';
	var delete_url   = ctxPaths+ '/subSystem/delete.ajax';
	var view_url 	 = ctxPaths+ '/subSystem/get.ajax';
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	var add_validator;
	$(function($) {
				jqGrid_init($(grid_selector),pager_selector,{
					url: list_url,
					sortable : true,
																									sortname : 'subSystemName',
																																																																	sortorder:'desc',
					colNames:[
										'子系统ID',
										'子系统名称',
										'子系统描述',
										'子系统外部地址',
										'子系统内部地址',
										'发布模式',
										'子系统所属的域',
										''
					],
					colModel:[
												   					{name:'subSystemId',index:'subSystemId'},
						   					   						   												{name:'subSystemName',index:'subSystemName', sortable:true,sortname : 'sub_system_name',width:100,formatter:formatName},
													   					   						   												{name:'subSystemDesc',index:'subSystemDesc', sortable:true,sortname : 'sub_system_desc',width:100},
													   					   						   												{name:'hopDomain',index:'hopDomain', sortable:true,sortname : 'hop_domain',width:100},
													   					   						   												{name:'interfaceDomain',index:'interfaceDomain', sortable:true,sortname : 'interface_domain',width:100},
													   					   						   												{name:'deployMode',index:'deployMode', sortable:true,sortname : 'deploy_mode',width:100},
													   					   						   												{name:'domain',index:'domain', sortable:true,sortname : 'domain',width:100},
													   					   					{name:'myac',index:'', width:120, fixed:true, sortable:false, resize:false,formatter:actionButtons}					
					]
				});
				function formatName(cellvalue, options, rowObject){
					return '<a href="javascript:;" onclick="viewEvent(\''+rowObject['subSystemId']+'\')">' + cellvalue + '</a>';
				}
				function actionButtons(cellvalue, options, rowObject){
					return '<div >' + 
								'<button onclick=\"editEvent('+rowObject['subSystemId']+')\" class=\"btn btn-xs btn-info\" data-rel=\"tooltip\" title=\"编辑\" >' +
									'<i class=\"ace-icon fa fa-pencil bigger-120\"></i>' +
								'</button>' + 
								'<button onclick=\"deleteEvent('+rowObject['subSystemId']+')\" class=\"btn btn-xs btn-danger\" data-rel=\"tooltip\" title=\"删除\" >' +
									'<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>' +
								'</button>'  + 
							'</div>';
				}
				
			});

			function editEvent(id){
				$.ajaxSubmit(view_url,{'subSystemId': id}, function(data){
				    if(data.success == true){
						resetForm($('#add-form'),add_validator);
						$('#add-form').json2Form2(data.data);
						$('#subSystemId').attr('readonly','readonly');
						$('#updId').val($('#subSystemId').val());
						$('#add-form-modal').modal2({backdrop:"static",show:true});
					}else{
						Q_Alert_Fail(data.message);
					}
				});
			};
			function viewEvent(id){
				$.ajaxSubmit(view_url,{'subSystemId' : id}, function(data){
					$.dataInput($('#view-form-modal').find('.form-control-static'),data.data);
					$('#view-form-modal').modal2({backdrop:"static",show:true});		
				});
			};
			function deleteEvent(id){
				Q_Confirm("是否要删除？",function(result) {
					if(result){
						$.ajaxSubmit(delete_url,{'subSystemId' : id}, function(data){
							$(grid_selector).jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
						});
					}
				});
			};
			
			add_validator = $('#add-form').validate({
					rules: {
							 						 					 										'subSystemName' : {
	 						 					required:true,
	 						 					maxlength: 50
					 },	 				 	 					 										'subSystemDesc' : {
	 						 					maxlength: 200
					 },	 				 	 					 										'hopDomain' : {
	 						 					maxlength: 200
					 },	 				 	 					 										'interfaceDomain' : {
	 						 					maxlength: 200
					 },	 				 	 					 										'deployMode' : {
	 						 					required:true,
	 						 					maxlength: 20
					 },	 				 	 					 										'domain' : {
	 						 					required:true,
	 						 					maxlength: 20
					 }	 				 	 									},
					submitHandler: function (form) {
						$.ajaxSubmit(update_url,$(form).serializeJson(),function(data){
								if(data.success == true){
								    $('#add-form-modal').modal2('hide');
									$(grid_selector).trigger("reloadGrid");
								}else{
									Q_Alert_Fail(data.message); 
								}
						});
						return false;		
					}
			});
			
			$('#addBtn').on('click', function(){
			    resetForm($('#add-form'),add_validator);
				$('#subSystemId').removeAttr('readonly').val('');
				
				$('#add-form-modal').modal2({backdrop:"static",show:true});
			});
			
			$('#seachBtn').on('click', function(){
				$(grid_selector).jqGrid('setGridParam',{postData: $('#queryForm').serializeJson(),page:1}).trigger("reloadGrid");
			});
			
		</script>
</body>
</html>