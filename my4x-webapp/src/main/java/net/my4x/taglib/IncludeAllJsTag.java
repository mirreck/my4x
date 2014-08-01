package net.my4x.taglib;
/**
 * Created by thomas.decoster on 01/08/2014.
 */
public class IncludeAllJsTag extends AbsctractIncludeAllTag {


    @Override
    protected String before() {
        return "<script src=\"";
    }

    @Override
    protected String after() {
        return "\"></script>";
    }

}
