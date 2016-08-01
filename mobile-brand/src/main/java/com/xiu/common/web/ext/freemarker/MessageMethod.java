package com.xiu.common.web.ext.freemarker;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.xiu.common.web.utils.SpringUtils;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;

@Component("messageMethod")
public class MessageMethod
  implements TemplateMethodModel
{
  public Object exec(List arguments)
  {
    if ((arguments != null) && (!arguments.isEmpty()) && (arguments.get(0) != null) && (StringUtils.isNotEmpty(arguments.get(0).toString())))
    {
      String str1 = null;
      String str2 = arguments.get(0).toString();
      if (arguments.size() > 1)
      {
        Object[] arrayOfObject = arguments.subList(1, arguments.size()).toArray();
        str1 = SpringUtils.getMessage(str2, arrayOfObject);
      }
      else
      {
        str1 = SpringUtils.getMessage(str2, new Object[0]);
      }
      return new SimpleScalar(str1);
    }
    return null;
  }
}

/* Location:           D:\ecommerce\shopxx-3.0Beta\WebContent\WEB-INF\classes\
 * Qualified Name:     net.shopxx.template.method.MessageMethod
 * JD-Core Version:    0.6.0
 */