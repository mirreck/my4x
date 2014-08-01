package net.my4x.taglib;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by thomas.decoster on 01/08/2014.
 */
public abstract class AbsctractIncludeAllTag extends TagSupport {

    private String pattern;

    private String basePath;

    private String excludes;

    private String prefix;
    private String suffix;

    protected abstract String before();
    protected abstract String after();

    @Override
    public int doStartTag() throws JspException {
        List<String> resourcePaths = Lists.newArrayList(findResources(basePath,  pattern));
        final JspWriter out = pageContext.getOut();
        try {
            Collections.sort(resourcePaths);
            if(!StringUtils.isBlank(prefix) && !resourcePaths.isEmpty()){
                out.println(prefix);
            }
            for (String resourcePath : resourcePaths) {
                out.println(before()+resourcePath+after());
            }
            if(!StringUtils.isBlank(suffix) && !resourcePaths.isEmpty()){
                out.println(suffix);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return super.doStartTag();
    }

    private Set<String> findResources(String basePath, String pattern){
        Set<String> result = new HashSet<>();
        Set<String> paths = (Set<String>) this.pageContext.getServletContext().getResourcePaths(basePath);
        for (String path : paths) {

            if(isDirectory(path)){
                result.addAll(findResources(path, pattern));
            } else {
                boolean excluded = false;
                if (!StringUtils.isBlank(excludes)) {
                    for (String exclude : excludes.split(",")) {
                        if (path.contains(exclude)) {
                            excluded = true;
                        }
                    }
                }
                if(!excluded && path.endsWith(pattern)) {
                    result.add(path);
                }
            }
        }
        return result;
    }

    private boolean isDirectory(String path) {
        return path.endsWith("/");
    }


    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getExcludes() {
        return excludes;
    }

    public void setExcludes(String excludes) {
        this.excludes = excludes;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
