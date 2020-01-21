package com.impl

import com.example.ZClass
import com.google.auto.service.AutoService
import com.zone.apt.AbstractProcessorAPT
import com.zone.apt.ElementResolver
import com.zone.apt.JavaFileUtils
import com.zone.apt.LogConfig
import com.zone.apt.MirroredHelp
import com.zone.apt.entity.ClassEntity

import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypeException
import javax.tools.Diagnostic

@AutoService(Processor::class)
class ViewInjectProcessor : AbstractProcessorAPT() {

    companion object {
        val SUFFIX = ElementResolver.GENERATE_LABEL + "Injector"
        val SUFFIX2 = ElementResolver.GENERATE_LABEL + "Api"

        init {
            LogConfig.setFileAddress("/Users/nutstore/AndroidStudioProjects/RetrofitListHelper/apt/src/main/java/com/example/process.txt")
        }
    }

    override fun getSupportedAnnotationClasses(): Array<Class<out Annotation>> = arrayOf(ZClass::class.java)

    override fun process(annotations: Set<TypeElement>,
                         env: RoundEnvironment): Boolean {
        //解析成实体类
        for (support in supportedAnnotationClasses)
            mElementResolver.resolve(env, support)

        //文件写入
        for ((_, value) in mElementResolver.classEntityMap) {
            try {
                val zClassA = value.annotataionMap[ZClass::class.java] as ZClass

                val arr = arrayOfNulls<JavaGenerate.ElementEntity>(zClassA.entitys.size)
                for (i in zClassA.entitys.indices) {
                    val clas = object : MirroredHelp() {
                        override fun excepetMothod() {
                            zClassA.entitys[i].cla
                        }
                    }.excepetElement

                    val obj = object : MirroredHelp() {
                        override fun excepetMothod() {
                            zClassA.entitys[i].obj
                        }
                    }.excepetElement

                    arr[i] = JavaGenerate.ElementEntity(clas, obj)
                }

                value.aptClassName = value.classSimpleName.toString() + SUFFIX
                JavaFileUtils.write(mElementResolver, value) { JavaGenerate.brewInterface(value, arr) }
                mElementResolver.messager.printMessage(Diagnostic.Kind.OTHER, " 写入成功1：")

                value.aptClassName = value.classSimpleName.toString() + SUFFIX2
                val otherName = "zone.com.sdk." + value.classSimpleName + SUFFIX
                JavaFileUtils.write(mElementResolver, value) { JavaGenerate.brewAPI(value, arr, otherName) }

                mElementResolver.messager.printMessage(Diagnostic.Kind.OTHER, " 写入成功2：")

            } catch (e: MirroredTypeException) {
                mElementResolver.messager.printMessage(Diagnostic.Kind.OTHER, "MirroredTypeException： 写入" + e.message)
            } catch (e: Exception) {
                mElementResolver.messager.printMessage(Diagnostic.Kind.OTHER, "Exception 写入：" + e.message)
            }

        }
        //log打印；
        //        mElementResolver.printLog();
        return true
    }

}