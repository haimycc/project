<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>蓝源Eloan-P2P平台(系统管理平台)</title>
<#include "../common/header.ftl"/>
    <script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
    <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>

    <script type="text/javascript">
        $(function () {
            $(".beginDate").addClass("Wdate").click(function () {
                WdatePicker({
                    skin: 'twoer',
                    maxDate: $(".endDate").val() || new Date()
                });
            });
            $(".endDate").addClass("Wdate").click(function () {
                WdatePicker({
                    skin: 'twoer',
                    minDate: $(".beginDate").val()
                });
            });
            <#--$("#state").val(${qo.state});-->
            <#--$("#userType").val(${qo.userType});-->

            var searchForm = $("#searchForm");
            var tbody = $("#tbody");
            searchForm.ajaxForm(function(data){
                tbody.hide();
                tbody.html(data)
                tbody.show(500);
            });
            searchForm.submit();
        });
    </script>
</head>
<body>
<div class="container">
<#include "../common/top.ftl"/>
    <div class="row">
        <div class="col-sm-3">
        <#assign currentMenu="ipLog">
				<#include "../common/menu.ftl" />
        </div>
        <div class="col-sm-9">
            <div class="page-header">
                <h3>登录日志查询</h3>
            </div>
            <form id="searchForm" class="form-inline" method="post" action="/iplog_list.do">
                <input type="hidden" id="currentPage" name="currentPage" value="1"/>
                <div class="form-group">
                    <label>状态</label>
                    <select class="form-control" name="state" id="state">
                        <option value="-1">全部</option>
                        <option value="1">登录失败</option>
                        <option value="0">登录成功</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>登陆时间</label>
                    <input class="form-control beginDate" type="text" name="beginDate"
                           value='${(qo.beginDate?string("yyyy-MM-dd"))!""}'/>到
                    <input class="form-control endDate" type="text" name="endDate"
                           value='${(qo.endDate?string("yyyy-MM-dd"))!""}'/>
                </div>
                <div class="form-group">
                    <label>用户类型</label>
                    <select class="form-control" name="userType" id="userType">
                        <option value="-1">全部</option>
                        <option value="1">后台管理员</option>
                        <option value="0">前台用户</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>用户名</label>
                    <input class="form-control" type="text" name="username" value='${(qo.username)!""}'/>
                </div>
                <div class="form-group">
                    <button id="query" type="type" class="btn btn-success"><i class="icon-search"></i> 查询</button>
                </div>
            </form>
            <div class="panel panel-default">
                <table class="table">
                    <thead>
                    <tr>
                        <th>用户</th>
                        <th>登录时间</th>
                        <th>登录ip</th>
                        <th>登录状态</th>
                        <th>用户类型</th>
                    </tr>
                    </thead>
                    <tbody id="tbody">

                    </tbody>
                </table>
                <div style="text-align: center;">
                    <ul id="pagination" class="pagination"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        var obj = $('#pagination').twbsPagination({
            totalPages: ${pageResult.totalPage},
            visiblePages: 5,
            startPage: ${qo.currentPage},
            first:"首页",
            prev:"上一页",
            next:"下一页",
            last:"尾页",
            onPageClick: function (event, page) {
                $("#currentPage").val(page);
                $("#searchForm").submit();
            }
        });
    });
</script>
</body>
</html>