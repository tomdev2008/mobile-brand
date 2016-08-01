<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">		
<script type="text/javascript" src="${ctx.getContextPath()}/js/page.js"></script>
<!-- 分页信息 -->
<div class="paging">
	<ul class="clearfix">
		<#if pageNo??>
			<#if pageNo==1>
				<li class="disPrevious">
					<a href="javascript:void(0);" unable="false">上一页</a>
				</li>
			<#else>
		 		<li class="previous">
					<a href="javascript:void(0);" onclick="prePage();">上一页</a>
				</li>
			</#if>
		</#if>
		<li class="currpage">
			<input type="hidden" id="currentPage" value="${pageNo!'1'}"/>
			<input type="hidden" id="totalPage" value="${totalPage!'0'}"/>
			<select id="pageNo" name="pageNo">
				<#assign x=totalPage>
				<#list 1..x as i>
					<option value="${i}" <#if pageNo==i>selected="selected"</#if>>${i}</option>
				</#list>
			</select>
			<span>/<span>
			<span>${totalPage}</span>
		</li>
		<#if pageNo==totalPage>
			<li class="disNext">
				<a href="javascript:void(0);" unable="false">下一页</a>
			</li>
		<#else>
			<li class="next">
				<a href="javascript:void(0);" onclick="nextPage();">下一页</a>
			</li>	
		</#if>
	</ul>
	<div>
		<a href="javascript:void(0);" class="go" onclick="gotoPage();">跳转</a>
	</div>
</div>