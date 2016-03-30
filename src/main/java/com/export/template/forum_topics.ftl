
<#if nodes ??>
<#assign index=1>
	<#list nodes as item>
		<#if item.realId == 1>
			<#if index != 1>
			<br clear="all" style="page-break-before: always; mso-special-character: line-break" />
			</#if>
			<#assign index = 2>
			<h1>${item.nodeNameStatic}</h1>
		<#elseif item.realId == 2>
			<h2>${item.nodeNameStatic}</h2>
		<#elseif item.realId == 3>
			<h3>${item.nodeNameStatic}</h3>
		<#elseif item.realId == 4>
			<h4>${item.nodeNameStatic}</h4>
		<#elseif item.realId == 5>
			<h5>${item.nodeNameStatic}</h5>
		<#elseif item.realId == 6>
			<h6>${item.nodeNameStatic}</h6>
		<#elseif item.realId == 7>
			<p class="MsoHeading7">${item.nodeNameStatic}</p>
		<#elseif item.realId == 8>
			<p class="MsoHeading8">${item.nodeNameStatic}</p>
		<#elseif item.realId == 9>
			<p class="MsoHeading9">${item.nodeNameStatic}</p>
		<#else>	
			<p class="MsoHeadingDef">${item.nodeNameStatic}</p>
		</#if>

		<#if item.content ??>
			${item.content}
		</#if>
	</#list>
</#if>
