package com.zwx.scan.app.widget.treeview.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author : lizhilong
 * time   : 2019/01/10
 * desc   :
 * version: 1.0
 **/

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TreeNodeType {
}
