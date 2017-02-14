<#list pageResult.listData as item>
<tr>
    <td>${item.username}</td>
    <td>${item.loginTime?string("yyyy-MM-dd")}</td>
    <td>${item.ip}</td>
    <td>${item.stateDisplay}</td>
    <td>${item.userTypeDisplay}</td>
</tr>
</#list>

<script type="text/javascript">
    $(function(){
        $("#page_container").empty().append($('<ul id="pagination" class="pagination"></ul>'));
        $("#pagination").twbsPagination({
            totalPages:${pageResult.totalPage},
            startPage:${pageResult.currentPage},
            initiateStartPageClick:false,
            first:"首页",
            prev:"上一页",
            next:"下一页",
            last:"尾页",
            onPageClick : function(event, page) {
                $("#currentPage").val(page);
                $("#searchForm").submit();
            }
        });
    });
</script>