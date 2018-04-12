package com.example;

import com.example.entity.ClassEntity;
import com.example.entity.FieldEntity;
import com.example.entity.MethodEntity;

import com.example.entity.ClassEntity;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.lang.model.element.Element;

/**
 * Created by fuzhipeng on 2016/11/2.
 */

public class JavaGenerate {
//// Generated code from Butter Knife. Do not modify!
//    package com.shenxian.jizhang.acvitiy;
//
//    import android.view.View;
//    import butterknife.ButterKnife.Finder;
//    import butterknife.ButterKnife.ViewBinder;
//
//    public class AccountManageActivity$$ViewBinder<T extends com.shenxian.jizhang.acvitiy.AccountManageActivity> implements ViewBinder<T> {
//        @Override public void bind(final Finder finder, final T target, Object source) {
//            View view;
//            view = finder.findRequiredView(source, 2131492961, "field 'civ_image'");
//            target.civ_image = finder.castView(view, 2131492961, "field 'civ_image'");
//            view = finder.findRequiredView(source, 2131492963, "field 'tv_weixinName'");
//            target.tv_weixinName = finder.castView(view, 2131492963, "field 'tv_weixinName'");
//            view = finder.findRequiredView(source, 2131492964, "field 'tvWeiXinId'");
//            target.tvWeiXinId = finder.castView(view, 2131492964, "field 'tvWeiXinId'");
//            view = finder.findRequiredView(source, 2131492965, "field 'rlLoginOut' and method 'onClick'");
//            target.rlLoginOut = finder.castView(view, 2131492965, "field 'rlLoginOut'");
//            view.setOnClickListener(
//                    new butterknife.internal.DebouncingOnClickListener() {
//                        @Override public void doClick(android.view.View p0) {
//                            target.onClick();
//                        }
//                    });
//        }
//
//        @Override public void unbind(T target) {
//            target.civ_image = null;
//            target.tv_weixinName = null;
//            target.tvWeiXinId = null;
//            target.rlLoginOut = null;
//        }
//    }

    public static String brewJava(ClassEntity classEntity) {
        StringBuilder sb = new StringBuilder();
//        sb.append("package " + classEntity.getClassPackage() + ";\n");
//        sb.append("import com.example.ZBinder;\n");
//        sb.append("import android.view.View;\n");
//        sb.append(" public class "+classEntity.getClassSimpleName()+ViewInjectProcessor.SUFFIX
//                +"<T extends " +classEntity.getClassName()+"> implements ZBinder<T>{ \n");
//        sb.append(" @Override public void bind(final T target) {\n");
//        for (Map.Entry<String, FieldEntity> itemField : classEntity.getFields().entrySet()) {
//            FieldEntity field = itemField.getValue();
//            sb.append("target."+field.getName()+
//                    " =  ("+field.getType()+")target.findViewById("+
//                    ((ZField)field.getAnnotataionMap().get(ZField.class)).value()+");\n");
//        }
////        findViewById().setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                //target.onclick(v);
////            }
////        });
//        for (Map.Entry<String, MethodEntity> itemMethod : classEntity.getMethods().entrySet()) {
//            MethodEntity method = itemMethod.getValue();
//            int[] ids = ((ZMethod) method.getAnnotataionMap().get(ZMethod.class)).value();
//            for (int id : ids) {
//                sb.append("target.findViewById("+id+").setOnClickListener(new View.OnClickListener() {\n" +
//                        "            @Override\n" +
//                        "            public void onClick(View v) {\n" +
//                        "                    //target.onclick(v);\n" +
//                        "            }\n" +
//                        "        });\n");
//            }
//        }
//        sb.append("}");
//        sb.append(" @Override public void unbind(T target) {\n");
//        for (Map.Entry<String, FieldEntity> itemField : classEntity.getFields().entrySet()) {
//            FieldEntity field = itemField.getValue();
//            sb.append("target."+field.getName()+" =  null;\n");
//        }
//        sb.append(" }\n");
//        sb.append(" }\n");
        return sb.toString();
    }
//
    public static String brewInterface(ClassEntity classEntity, ElementEntity[] arr) {
        ZClass ZClassA = (ZClass) (classEntity.getAnnotataionMap().get(ZClass.class));
        String clsName = classEntity.getAPTClassName();
        StringBuilder sb = new StringBuilder();
        sb.append("package zone.com.sdk;\n");
        for (ElementEntity elementEntity : arr) {
            sb.append("import " + elementEntity.cls.toString() + ";\n");
        }
        sb.append("public interface " +clsName+ " extends ");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i].cls.getSimpleName());
            if (i != ZClassA.entitys().length - 1)
                sb.append(",");
        }
        sb.append("{}\n");
        return sb.toString();
    }

    public static class ElementEntity {
        Element cls;
        Element obj;

        public ElementEntity(Element cls, Element obj) {
            this.cls = cls;
            this.obj = obj;
        }
    }

    public static String brewAPI(ClassEntity classEntity, ElementEntity[] arr,String otherName) {
        StringBuilder sb = new StringBuilder();
        String clsName = classEntity.getAPTClassName();

        sb.append("package zone.com.sdk;\n");
        sb.append("import java.lang.reflect.InvocationHandler;\n");
        sb.append("import java.lang.reflect.Proxy;\n");
        sb.append("import java.util.ArrayList;\n");
        sb.append("import java.util.List;\n");
        for (ElementEntity elementEntity : arr) {
            sb.append("import " + elementEntity.cls.toString() + ";\n");
            sb.append("import " + elementEntity.obj.toString() + ";\n");
        }
        //todo 名字
        sb.append("public class " + clsName + " {\n");
        sb.append("    static " + otherName + " mAPICall;\n");
        sb.append("    public static synchronized " + otherName + " getInstance() {\n");
        sb.append("        if (mAPICall == null) {\n");
        sb.append("            synchronized (" + clsName + ".class) {\n");
        sb.append("                if (mAPICall == null) {\n");
        sb.append("                    mAPICall = generate();\n");
        sb.append("                }\n");
        sb.append("            }\n");
        sb.append("        }\n");
        sb.append("        return mAPICall;};\n");
        sb.append("    public static List<Diycode.Entity> entityList = new ArrayList<>();\n");
        sb.append("    static {\n");
        for (int i = 0; i < arr.length; i++) {
            sb.append("   entityList.add(new Diycode.Entity(" + arr[i].cls.getSimpleName() + ".class, new " + arr[i].obj.getSimpleName() + "())); ");
        }
        sb.append("    }\n");

        sb.append("    private static " + otherName + " generate() {\n");
        sb.append("        " + clsName + " aPIController = new " + clsName + "();\n");
        sb.append("        InvocationHandler handler = new DyHandler2(aPIController);\n");
        sb.append("        Class<?>[] interfaces = new Class[entityList.size()];\n");
        sb.append("        for (int i = 0; i < entityList.size(); i++) {\n");
        sb.append("            interfaces[i] = entityList.get(i).interfaceClass;\n");
        sb.append("        }\n");
        sb.append("        "+otherName+" proxy = ("+otherName+") Proxy.newProxyInstance(\n");
        sb.append("                aPIController.getClass().getClassLoader(),\n");
        sb.append("                new Class<?>[]{"+otherName+".class}, handler);\n");
        sb.append("        return proxy;\n}");
        sb.append("       \n}");
        return sb.toString();
    }


}
