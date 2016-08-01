<div style="width: 100%;">
	<#assign  dwidth="column1"/>
	<#list tableHeader as item>
		<#if item_index  == 0><#assign  dwidth="column1"/>
		<#elseif item_index  == 1><#assign  dwidth="column2"/>
		<#elseif item_index  == 2><#assign  dwidth="column3"/>
		<#elseif item_index  == 3><#assign  dwidth="column4"/>
		<#elseif item_index  == 4><#assign  dwidth="column5"/>
		<#elseif item_index  == 5><#assign  dwidth="column6"/>
		<#elseif item_index  == 6><#assign  dwidth="column7"/>
		</#if>
		<div class="${dwidth!''}">${item}<#if item_index == 6>（背景为灰色表示已上传过）</#if></div>
	</#list>
</div>

<#list dataList as item>
	<div style="width: 100%;">
	<div class="column1">${item.groupIndex!''}</div>
	<div class="column2"><input type="text" name="position" value="${item.position!''}" readOnly/></div>
	<div class="column3"><input type="text" name="title" value="${item.title!''}"/></div>
	<div class="column4"><input type="text" name="viceTitle" value="${item.viceTitle!''}"/></div>
	<div class="column5">
		<select name="dataType">
			<option value="">无</option>
			<#list dataTypes?keys as key>
				<option value="${key}" <#if item.dataType?? && item.dataType = dataTypes[key]>selected</#if> >${dataTypes[key]}</option>
			</#list>
		</select>
	</div>
	<div class="column6"><input type="text" name="link" value="${item.link!''}"/></div>
	<div class="column7" <#if item.img??>style="background-color:#f1f1ef"</#if>><input type="file" name="moduleImgData" id="moduleImgData"></div>
		<input type="hidden" name="img" value="${item.img!''}">
	</div>
</#list>

<input type="hidden" name="moduleId" value="${moduleId?c}">