package net.my4x.taglib;

/**
 * Created by thomas.decoster on 01/08/2014.
 */
public class IncludeAllCssTag extends AbsctractIncludeAllTag {
    @Override
    protected String before() {
        return "<link href=\"";
    }

    @Override
    protected String after() {
        return "\" rel=\"stylesheet\" />";
    }
}
