<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>蓝源Eloan-P2P平台</title>
    <!-- 包含一个模板文件,模板文件的路径是从当前路径开始找 -->
<#include "common/links-tpl.ftl" />
    <script type="text/javascript" src="/js/plugins/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
    <link type="text/css" rel="stylesheet" href="/css/account.css"/>
    <script type="text/javascript">
        $(function () {
            //点击弹出手机绑定
            $("#showBindPhoneModal").click(function () {
                $("#bindPhoneModal").modal("show");
            });

            //点击出现绑定邮箱
            $("#showBindEmailModal").click(function () {
                $("#bindEmailModal").modal("show");
            });

            //点击绑定发送ajax请求
            $("#bindEmail").click(function () {
                $("#bindEmailForm").ajaxSubmit({
                    success: function (data) {
                        if (data.success) {
                            window.location.reload();
                        } else {
                            $.messager.popup(data.msg);
                        }
                    }
                });
            });

            //点击绑定按钮,执行绑定操作
            $("#bindPhone").click(function () {
                $("#bindPhoneForm").ajaxSubmit({
                    success: function (data) {
                        if (data.success) {
                            window.location.reload();
                        } else {
                            $.messager.popup(data.msg);
                        }
                    }
                });
            });

            //发送验证码
            $("#sendVerifyCode").click(function () {
                var phoneNumber = $("#phoneNumber").val();
                var _this = $(this)
                _this.attr("disabled", true);
                if (phoneNumber) {
                    $.ajax({
                        dataType: "json",
                        type: "post",
                        data: {phoneNumber: phoneNumber},
                        url: "/sendVerifyCode.do",
                        success: function (data) {
                            if (data.success) {
                                var count = 5;
                                var timer = window.setInterval(function () {
                                    count--;
                                    if (count >= 0) {
                                        //修改倒计时文字
                                        _this.text(count + "秒之后重新发送");
                                    } else {
                                        //清空定时器,恢复文字
                                        window.clearInterval(timer);
                                        _this.text("重新发送验证码");
                                        _this.attr("disabled", false);
                                    }
                                }, 1000);
                            } else {
                                $.messager.popup(data.msg);
                                _this.attr("disabled", false);
                            }
                        }
                    });
                }
                else {
                    $.messager.popup("请填写电话号码");
                }
            });

            //更改图片
            $("#changeBtn1").uploadify({
                buttonText: '上传正面',
                fileObjName: 'image',
                fileTypeDesc: "*.png *.jpg *.gif",
                fileTypeExts: "*.gif;*.jpg;*.png",
                multi: false,
                swf: '/js/plugins/uploadify/uploadify.swf',
                uploader: '/personal_img.do',
                overrideEvents: ["onUploadSuccess", "onSelect"],
                onUploadSuccess: function (file, data) {
                    $(".icon").attr("src", data);
                }
            });


            //分界线
        });
    </script>
</head>
<body>
<!-- 网页顶部导航 -->
<#include "common/head-tpl.ftl" />
<!-- 网页导航 -->
<!-- 在当前的freemarker的上下文中,添加一个变量,变量的名字叫nav,变量的值叫personal -->
<#assign currentNav="personal"/>
<#include "common/navbar-tpl.ftl" />
<div class="container">
    <div class="row">
        <!--导航菜单-->
        <div class="col-sm-3">
        <#assign currentMenu="personal"/>
        <#include "common/leftmenu-tpl.ftl" />
        </div>
        <!-- 功能页面 -->
        <div class="col-sm-9">
            <div class="panel panel-default">
                <div class="panel-body el-account">
                    <div class="el-account-info">
                        <div class="pull-left el-head-img">
                            <form class="form-horizontal" id="changeImg" method="post"
                                  novalidate="novalidate">
                                <img class="icon" src="${(userinfo.image)!"/images/ms.png"}" height="241px"
                                     width="200"/>
                                </br>
                                <div align="center">
                                    <a href="javascript:;" id="changeBtn1">更改图片</a>
                                </div>
                            </form>
                        </div>
                        <div class="pull-left el-head">
                            <p>用户名：</p>
                            <p>最后登录时间：2015-01-25 15:30:10</p>
                        </div>
                        <div class="pull-left" style="text-align: center;width: 400px;margin:30px auto 0px auto;">
                            <a class="btn btn-primary btn-lg" href="/recharge.do">账户充值</a>
                            <a class="btn btn-danger btn-lg" href="/moneyWithdraw.do">账户提现</a>
                        </div>
                        <div class="clearfix"></div>
                    </div>

                    <div class="row h4 account-info">
                        <div class="col-sm-4">
                            账户总额：<span class="text-primary">${account.totalAmount}元</span>
                        </div>
                        <div class="col-sm-4">
                            可用金额：<span class="text-primary">${account.usableAmount}元</span>
                        </div>
                        <div class="col-sm-4">
                            冻结金额：<span class="text-primary">${account.freezedAmount}元</span>
                        </div>
                    </div>

                    <div class="row h4 account-info">
                        <div class="col-sm-4">
                            待收利息：<span class="text-primary">${account.unReceiveInterest}元</span>
                        </div>
                        <div class="col-sm-4">
                            待收本金：<span class="text-primary">${account.unReceivePrincipal}元</span>
                        </div>
                        <div class="col-sm-4">
                            待还本息：<span class="text-primary">${account.unReturnAmount}元</span>
                        </div>
                    </div>

                    <div class="el-account-info top-margin">
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="el-accoun-auth">
                                    <div class="el-accoun-auth-left">
                                        <img src="images/shiming.png"/>
                                    </div>
                                    <div class="el-accoun-auth-right">
                                        <h5>实名认证</h5>
                                    <#if userinfo.isRealAuth>
                                        <p>
                                            已认证
                                            <a href="/realAuth.do" id="">查看认证</a>
                                        </p>
                                    <#else>
                                        <p>
                                            未认证
                                            <a href="/realAuth.do" id="">立刻绑定</a>
                                        </p>
                                    </#if>
                                    </div>
                                    <div class="clearfix"></div>
                                    <p class="info">实名认证之后才能在平台投资</p>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="el-accoun-auth">
                                    <div class="el-accoun-auth-left">
                                        <img src="images/shouji.jpg"/>
                                    </div>
                                    <div class="el-accoun-auth-right">
                                        <h5>手机认证</h5>
                                    <#if userinfo.isBindPhone>
                                        <p>
                                            已认证
                                            <a href="javascript:;">修改</a>
                                        </p>
                                    <#else>
                                        <p>
                                            未认证
                                            <a href="javascript:;" id="showBindPhoneModal">立刻绑定</a>
                                        </p>
                                    </#if>
                                    </div>
                                    <div class="clearfix"></div>
                                    <p class="info">可以收到系统操作信息,并增加使用安全性</p>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="el-accoun-auth">
                                    <div class="el-accoun-auth-left">
                                        <img src="images/youxiang.jpg"/>
                                    </div>
                                    <div class="el-accoun-auth-right">
                                        <h5>邮箱认证</h5>
                                    <#if userinfo.isBindEmail>
                                        <p>
                                            已绑定
                                            <a href="javascript:;">修改</a>
                                        </p>
                                    <#else>
                                        <p>
                                            未绑定
                                            <a href="javascript:;" id="showBindEmailModal">去绑定</a>
                                        </p>
                                    </#if>
                                    </div>
                                    <div class="clearfix"></div>
                                    <p class="info">您可以设置邮箱来接收重要信息</p>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="el-accoun-auth">
                                    <div class="el-accoun-auth-left">
                                        <img src="images/baozhan.jpg"/>
                                    </div>
                                    <div class="el-accoun-auth-right">
                                        <h5>VIP会员</h5>
                                        <p>
                                            普通用户
                                            <a href="#">查看</a>
                                        </p>
                                    </div>
                                    <div class="clearfix"></div>
                                    <p class="info">VIP会员，让你更快捷的投资</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#if !userinfo.isBindPhone>
<div class="modal fade" id="bindPhoneModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">绑定手机</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="bindPhoneForm" method="post" action="/bindPhone.do">
                    <div class="form-group">
                        <label for="phoneNumber" class="col-sm-2 control-label">手机号:</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"/>
                            <button id="sendVerifyCode" class="btn btn-primary" type="button" autocomplate="off">发送验证码
                            </button>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="verifyCode" class="col-sm-2 control-label">验证码:</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="verifyCode" name="verifyCode"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="bindPhone">绑定</button>
            </div>
        </div>
    </div>
</div>
</#if>
<#if !userinfo.isBindEmail>
<div class="modal fade" id="bindEmailModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">绑定邮箱</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="bindEmailForm" method="post" action="/sendEmail.do">
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">Email:</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="email" name="email"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="bindEmail">发送</button>
            </div>
        </div>
    </div>
</div>
</#if>
<#include "common/footer-tpl.ftl" />
</body>
</html>