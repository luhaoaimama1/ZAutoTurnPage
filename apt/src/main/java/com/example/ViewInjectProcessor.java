package com.example;

import com.example.entity.ClassEntity;
import com.google.auto.service.AutoService;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class ViewInjectProcessor extends AbstractProcessorAPT {
    public static final String SUFFIX = ElementResolver.GENERATE_LABEL + "Injector";
    public static final String SUFFIX2 = ElementResolver.GENERATE_LABEL + "Api";
    private Class<? extends Annotation>[] supports = new Class[]{
            ZClass.class,
    };

    static {
        LogConfig.setFileAddress("/Users/fuzhipeng/AndroidStudioProjects/RetrofitListHelper/apt/src/main/java/com/example/process.txt");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment env) {
        //解析成实体类
        for (Class<? extends Annotation> support : supports)
            mElementResolver.resolve(env, support);

        //文件写入
        for (Map.Entry<String, ClassEntity> item : mElementResolver.getClassEntityMap().entrySet()) {
            try {
                final ZClass zClassA = (ZClass) (item.getValue().getAnnotataionMap().get(ZClass.class));

                JavaGenerate.ElementEntity[]  arr=new  JavaGenerate.ElementEntity[ zClassA.entitys().length];
                for (int i = 0; i < zClassA.entitys().length; i++) {
                    final int finalI = i;
                    Element clas = new MirroredHelp() {
                        @Override
                        public void excepetMothod() {
                            zClassA.entitys()[finalI].cla();
                        }
                    }.getExcepetElement();

                    Element obj = new MirroredHelp() {

                        @Override
                        public void excepetMothod() {
                            zClassA.entitys()[finalI].obj();
                        }
                    }.getExcepetElement();

                    arr[i]= new JavaGenerate.ElementEntity(clas,obj);
                }

                item.getValue().setAPTClassName(item.getValue().getClassSimpleName() + ViewInjectProcessor.SUFFIX );
                JavaFileUtils.write(mElementResolver, item.getValue(),JavaGenerate.brewInterface(item.getValue(),arr));
                mElementResolver.getMessager()
                        .printMessage(Diagnostic.Kind.OTHER, " 写入成功1：" );

                item.getValue().setAPTClassName(item.getValue().getClassSimpleName() + ViewInjectProcessor.SUFFIX2 );
                String otherName="zone.com.sdk."+item.getValue().getClassSimpleName() + ViewInjectProcessor.SUFFIX;
                JavaFileUtils.write(mElementResolver, item.getValue(),JavaGenerate.brewAPI(item.getValue(),arr,otherName ));

                mElementResolver.getMessager()
                        .printMessage(Diagnostic.Kind.OTHER, " 写入成功2：" );

            } catch (MirroredTypeException e) {
                mElementResolver.getMessager()
                        .printMessage(Diagnostic.Kind.OTHER, "MirroredTypeException： 写入" + e.getMessage());
            } catch (Exception e) {
                mElementResolver.getMessager()
                        .printMessage(Diagnostic.Kind.OTHER, "Exception 写入：" + e.getMessage());
            }
        }
        //log打印；
//        mElementResolver.printLog();
        return true;
    }


    @Override
    public Class<? extends Annotation>[] getSupportedAnnotationClasses() {
        return supports;
    }
}