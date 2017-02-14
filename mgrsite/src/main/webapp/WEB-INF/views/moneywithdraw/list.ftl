<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>蓝源Eloan-P2P平台(系统管理平台)</title>
<#include "../common/header.ftl"/>
<link type="text/css" rel="stylesheet" href="/css/account.css" />
<script type="text/javascript" src="/js/bank.js"></script>
<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
<script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js" ></script>

<script type="text/javascript">
	$(function() {
		$("#beginDate,#endDate").click(function(){
			WdatePicker();
		});
		
		$('#pagination').twbsPagination({
			totalPages : ${pageResult.totalPage}||1,
			startPage : ${pageResult.currentPage},
			visiblePages : 5,
			first : "首页",
			prev : "上一页",
			next : "下一页",
			last : "最后一页",
			onPageClick : function(event, page) {
				$("#currentPage").val(page);
				$("#searchForm").submit();
			}
		});
		
		$("#query").click(function(){
			$("#currentPage").val(1);
			$("#searchForm").submit();
		})
		
		$(".auditClass").click(function(){
			$("#myModal").modal("show");
			var data=$(this).data("json");
			$("#myModal [name=id]").val(data.id);
			$("#myModal [name=username]").html(data.username);
			$("#myModal [name=realName]").html(data.realName);
			$("#myModal [name=applyTime]").html(data.applyTime);
			$("#myModal [name=bankName]").html(SITE_BANK_TYPE_NAME_MAP[data.bankName]);
			$("#myModal [name=accountNumber]").html(data.accountNumber);
			$("#myModal [name=bankforkname]").html(data.bankforkname);
			$("#myModal [name=moneyAmount]").html(data.moneyAmount);
		});
		
		$(".btn_audit").click(function(){
			var form=$("#editform");
			form.find("[name=state]").val($(this).val());
			$("#myModal").modal("hide");
			form.ajaxSubmit(function(data){
				if(data.success){
					$.messager.confirm("提示","审核成功!",function(){
						window.location.reload();
					});
				}
			});
			return false;
		});
	});
</script>
</head>
<body>
	<div class="container">
		<#include "../common/top.ftl"/>
		<div class="row">
			<div class="col-sm-3">
				<#assign currentMenu="moneyWithdraw" />
				<#include "../common/menu.ftl" />
			</div>
			<div class="col-sm-9">
				<div class="page-header">
					<h3>用户提现审核管理</h3>
				</div>
				<form id="searchForm" class="form-inline" method="post" action="/moneyWithdraw.do">
					<input type="hidden" id="currentPage" name="currentPage" value=""/>
					<div class="form-group">
					    <label>状态</label>
					    <select class="form-control" name="state">
					    	<option value="-1">全部</option>
					    	<option value="0">申请中</option>
					    	<option value="1">审核通过</option>
					    	<option value="2">审核拒绝</option>
					    </select>
					    <script type="text/javascript">
					    	$("[name=state] option[value='${(qo.state)!''}']").attr("selected","selected");
					    </script>
					</div>
					<div class="form-group">
					    <label>申请时间</label>
					    <input class="form-control" type="text" name="beginDate" id="beginDate" value="${(qo.beginDate?string('yyyy-MM-dd'))!''}" />到
					    <input class="form-control" type="text" name="endDate" id="endDate" value="${(qo.endDate?string('yyyy-MM-dd'))!''}" />
					</div>
					<div class="form-group">
						<button id="query" class="btn btn-success"><i class="icon-search"></i> 查询</button>
					</div>
				</form>
				<div class="panel panel-default">
					<table class="table">
						<thead>
							<tr>
								<th>用户名</th>
								<th>申请时间</th>
								<th>提现金额</th>
								<th>状态</th>
								<th>审核人</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
						<#list pageResult.listData as info>
							<tr>
								<td>${info.applier.username}</td>
								<td>${info.applyTime?string('yyyy-MM-dd')}</td>
								<td>${info.amount}</td>
								<td>${info.stateDisplay}</td>
								<td>${(info.auditor.username)!""}</td>
								<td>
									<#if (info.state == 0)>
									<a href="javascript:void(-1);" class="auditClass" data-json='${info.jsonString}'>审核</a>
									</#if>
								</td>
							</tr>
						</#list>
						</tbody>
					</table>
					<div style="text-align: center;">
						<ul id="pagination" class="pagination"></ul>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog">
		  <div class="modal-dialog modal-lg" role="document">
		    <div class="modal-content">
		      <div class="modal-body">
		      	<form class="form-horizontal" id="editform" method="post" action="/moneyWithdraw_audit.do">
					<fieldset>
						<div id="legend" class="">
							<legend>提现申请审核</legend>
						</div>
						<input type="hidden" name="id" value="" />
						<input type="hidden" name="state" value="" /> 
						<div class="form-group">
				        	<label class="col-sm-2 control-label" for="name">申请用户</label>
				        	<div class="col-sm-6">
				        		<label class="form-control" name="username"></label>
				        	</div>
			        	</div>
			        	<div class="form-group">
				        	<label class="col-sm-2 control-label" for="realName">账户名</label>
				        	<div class="col-sm-6">
				        		<label class="form-control" name="realName"></label>
				        	</div>
			        	</div>
			        	<div class="form-group">
				        	<label class="col-sm-2 control-label" for="applyTime">申请时间</label>
				        	<div class="col-sm-6">
				        		<label class="form-control" name="applyTime"></label>
				        	</div>
			        	</div>
			        	<div class="form-group">
				        	<label class="col-sm-2 control-label" for="bankName">开户行</label>
				        	<div class="col-sm-6">
				        		<label class="form-control" name="bankName"></label>
				        	</div>
			        	</div>
			        	<div class="form-group">
				        	<label class="col-sm-2 control-label" for="accountNumber">账号</label>
				        	<div class="col-sm-6">
				        		<label class="form-control" name="accountNumber"></label>
				        	</div>
			        	</div>
			        	<div class="form-group">
				        	<label class="col-sm-2 control-label" for="bankforkname">开户支行</label>
				        	<div class="col-sm-6">
				        		<label class="form-control" name="bankforkname"></label>
				        	</div>
			        	</div>
			        	<div class="form-group">
				        	<label class="col-sm-2 control-label" for="moneyAmount">提现金额</label>
				        	<div class="col-sm-6">
				        		<label class="form-control" name="moneyAmount"></label>
				        	</div>
			        	</div>
			        	
						<div class="form-group">
							<label class="col-sm-2 control-label" for="name">审核备注</label>
				        	<div class="col-sm-6">
				        		<textarea name="remark" rows="4" cols="60"></textarea>
				        	</div>
						</div>
					</fieldset>
				</form>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		        <button type="button" class="btn btn-success btn_audit" value="1">审核通过</button>
		        <button type="button" class="btn btn-warning btn_audit" value="2">审核拒绝</button>
		      </div>
		    </div>
		  </div>
		</div>
	</div>
</body>
</html>