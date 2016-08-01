package com.xiu.common.web.ext.freemarker;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

import org.apache.commons.configuration.Configuration;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.ext.util.ModelFactory;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : xiu@xiu.com
 * @DATE :2012-5-4 上午11:09:06
 *       </p>
 **************************************************************** 
 */
public class ApacheCommonConfigurationModel extends BeanModel implements TemplateMethodModelEx {

    static final ModelFactory FACTORY = new ModelFactory() {
        public TemplateModel create(Object object, ObjectWrapper wrapper) {
            return new ApacheCommonConfigurationModel((Configuration) object, (BeansWrapper) wrapper);
        }
    };

    public ApacheCommonConfigurationModel(Configuration config, BeansWrapper wrapper) {
        super(config, wrapper);
    }

    /**
     * Overridden to invoke the getObject method of the resource bundle.
     */
    protected TemplateModel invokeGenericGet(Map keyMap, Class clazz, String key) throws TemplateModelException {
        try {
            return wrap(((Configuration) object).getString(key));
        } catch (MissingResourceException e) {
            throw new TemplateModelException("No such key: " + key, e);
        }
    }

    /**
     * Returns true if this bundle contains no objects.
     */
    public boolean isEmpty() {
        return ((Configuration) object).isEmpty() && super.isEmpty();
    }

    public int size() {
        return keySet().size();
    }

    protected Set keySet() {
        Set set = super.keySet();
        Iterator e = ((Configuration) object).getKeys();
        while (e.hasNext()) {
            set.add(e.next());
        }
        return set;
    }

    /**
     * Takes first argument as a resource key, looks up a string in resource bundle with this key, then applies a
     * MessageFormat.format on the string with the rest of the arguments. The created MessageFormats are cached for
     * later reuse.
     */
    public Object exec(List arguments) throws TemplateModelException {
        // Must have at least one argument - the key
        if (arguments.size() < 1) {
            throw new TemplateModelException("No properties key was specified");
        }

        // Read it
        Iterator it = arguments.iterator();
        String key = unwrap((TemplateModel) it.next()).toString();
        String defValue = null;
        if (it.hasNext()) {
            defValue = unwrap((TemplateModel) it.next()).toString();
        }

        try {

            String value = ((Configuration) object).getString(key);
            if (value == null) {
                value = defValue;
            }

            return wrap(value);
        } catch (Exception e) {
            throw new TemplateModelException(e);
        }
    }

}
