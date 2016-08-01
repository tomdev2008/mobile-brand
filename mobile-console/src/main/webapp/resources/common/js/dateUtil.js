/**
 * 生成日期
 * @param ystart 开始日期
 * @param yend 结束日期
 * @return
 */
function dateCreate(yearId, monthId, ystart,yend)
{
	 
	 for (var i = yend; i >= ystart; i--) //以今年为准，前30年，后30年
		 $("#"+yearId).append("<option value='"+i+"'>"+i+"</option>");
	 for (var i = 1; i < 13; i++)
		 $("#"+monthId).append("<option value='"+i+"'>"+i+"</option>");
	
}
/**
 * 选择年，月时调用
 * @return
 */
function changeDate(yearId, monthId, dateId){
	var maxMonthDate = [0,31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	var yearValue = $("#"+yearId).val();
	var monthValue = $("#"+monthId).val();
	var $date = $("#"+dateId);
	

	$date.empty();
	$date.append("<option value='-1'>日</option>");
	if(yearValue != 0 && monthValue != 0){
		if (((yearValue % 4) == 0) && ((yearValue % 100) != 0) || ((yearValue % 400) == 0))
		{
			maxMonthDate = [0,31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
		}
		
		for(var i=1;i<=maxMonthDate[monthValue] ;i++){
			$date.append("<option value='"+i+"'>"+i+"</option>");			
		}
	}
}